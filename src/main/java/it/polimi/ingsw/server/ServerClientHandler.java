package it.polimi.ingsw.server;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.TCPVirtualView;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.Timer.TimerInterface;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.*;

public class ServerClientHandler implements Runnable, TimerInterface {
    private final Controller controller;
    private String username;
    private int gameID;
    private Player player;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private final Object lock = new Object();
    private final Object pingLock = new Object();

    private final ExecutorService t = Executors.newSingleThreadExecutor();
    private boolean ponging = true;
    private boolean disconnected = false;
    //Timer
    private ScheduledExecutorService e;
    private Timer timer;
    private static final int initialDelay = 50;
    private static final int delta = 500;
    private int time = 0;


    public ServerClientHandler(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller=controller;
    }

    public void run() {

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            pingPong();

            while (!disconnected) {
                if (!socket.isConnected()) {
                    disconnected = true;
                    disconnect();
                }

                Message messageIn;
                try {
                    messageIn = (Message) ois.readObject();
                    processMessage(messageIn);

                } catch (IOException | ClassNotFoundException ex) {
                    disconnect();
                }
            }

            socket.close();
            ois.close();
            oos.close();

        } catch (IOException ex) {
            disconnect();
        }
    }

    private void processMessage(Message incomingMsg) throws IOException {
        Message messageOut=null;
        if(incomingMsg != null) {

            if(!incomingMsg.getType().equals(MessageTypes.PONG))
                System.out.println("Server received " + incomingMsg.getType() + " from: " + username);

            switch (incomingMsg.getType()) {
                case USERNAME -> {
                    //Check if there are waiting rooms or the client has to start another game

                     t.submit(()-> {
                         Message message = controller.handleNewClient(incomingMsg.getText(),
                                new TCPVirtualView(incomingMsg.getText().toLowerCase(), this));

                        if (!message.getType().equals(MessageTypes.ERROR)) {
                            this.gameID = ((IntMessage) message).getNum();
                            this.username = incomingMsg.getText();
                        }

                        sendMessage(message);
                    });
                }
                case NUM_OF_PLAYERS -> {
                    messageOut = controller.newLobby(this.username,((IntMessage) incomingMsg).getNum());
                    this.gameID = ((IntMessage) messageOut).getNum();
                }
                case REMOVE_FROM_BOARD -> {
                    messageOut = controller.removeTiles(this.gameID,this.username, ((PointsMessage) incomingMsg).getTiles());
                }
                case SWITCH_PLACE -> {
                    messageOut = controller.swapOrder(((IntArrayMessage) incomingMsg).getIntegers(),gameID,username);
                }
                case ADD_TO_BOOKSHELF -> {
                    messageOut = controller.addToBookshelf(gameID,username,((IntMessage)incomingMsg).getNum());
                }
                case DISCONNECT -> {
                    disconnect();
                }
                case PONG -> {
                    this.time=0;
                }
                case CHAT -> {
                    if (((ChatMessage) incomingMsg).getReceivingUsername() == null)
                        messageOut = controller.sendMessage(gameID, username, ((ChatMessage)incomingMsg).getMessage());
                    else
                        messageOut = controller.sendMessage(gameID, username, ((ChatMessage)incomingMsg).getMessage(), ((ChatMessage)incomingMsg).getReceivingUsername());
                }
                default -> {
                    System.out.println("Server received: " + incomingMsg);
                }
            }
            if(messageOut!=null) {
                sendMessage(messageOut);
            }
        }
    }

    public void sendMessage(Message message){

        if(message.getType().equals(MessageTypes.PING)){
            synchronized (pingLock) {
                send(message);
            }
        }else{
            synchronized (lock) {
                send(message);
            }
        }
    }



    private void send(Message message){
        try {

            if(!disconnected) {
                oos.writeUnshared(message);
                oos.flush();
                oos.reset();
            }
        } catch (IOException ex) {
            if (ponging&&!disconnected) {
                System.out.println(username + " is not responding...");
                ponging=false;
            }
        }
    }


    private void receivePing(){



    }

    private void pingPong(){
        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{
             Message msg = new Message();
             msg.setType(MessageTypes.PING);
             sendMessage(msg);
        },10,500, TimeUnit.MILLISECONDS);

        timer = new Timer();
        timer.schedule(new TimerCounter(this), initialDelay, delta);

    }



    @Override
    public void disconnect() {
        t.shutdown();
        e.shutdown();
        timer.cancel();
        this.disconnected=true;

        if(username!=null){
            controller.playerDisconnection(username);
        }else System.out.println("A client has disconnected before having successfully logged in");

    }

    public void stopTimer(){
        if(timer!=null) timer.cancel();
        if(e!=null) e.shutdown();
        disconnected=true;
    }



    @Override
    public int updateTime() {
        this.time+=1;
        return this.time;
    }

    @Override
    public String getErrorMessage() {
        return this.username+" timed out";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

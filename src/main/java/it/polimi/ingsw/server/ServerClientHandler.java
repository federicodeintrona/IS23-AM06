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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerClientHandler implements Runnable, TimerInterface {
    private final Controller controller;
    private String username;
    private int gameID;
    private Player player;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private boolean disconnected = false;
    //Timer
    private ScheduledExecutorService e;
    private final Timer timer = new Timer();
    private static final int initialDelay = 50;
    private static final int delta = 1000;
    private int time = 0;


    public ServerClientHandler(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller=controller;
    }
    public ServerClientHandler(Controller controller) {
        this.controller=controller;
    }

    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

           pingPong();

            while (!disconnected) {

                if(!socket.isConnected()){
                    disconnected=false;
                }

                Message messageIn = (Message) ois.readObject();
                processMessage(messageIn);

            }

            socket.close();
            ois.close();
            oos.close();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(Message incomingMsg) throws IOException {
        Message messageOut=null;
        if(incomingMsg != null) {

            if(!incomingMsg.getType().equals(MessageTypes.PONG))System.out.println("Server received " + incomingMsg.getType() + " from: " + username);

            switch (incomingMsg.getType()) {
                case USERNAME -> {
                    //Check if there are waiting rooms or the client has to start another game
                    synchronized (this){
                        messageOut = controller.handleNewClient(incomingMsg.getUsername(),
                                new TCPVirtualView(incomingMsg.getUsername(),this));
                    }

                    if(!messageOut.getType().equals(MessageTypes.ERROR)){
                        this.gameID = ((IntMessage) messageOut).getNum();
                        this.username = incomingMsg.getUsername();
                    }
                }
                case NUM_OF_PLAYERS -> {
                    messageOut = controller.newLobby(this.username,((IntMessage) incomingMsg).getNum());
                    this.gameID = ((IntMessage) messageOut).getNum();
                }
                case REMOVE_FROM_BOARD -> {
                    messageOut = controller.removeTiles(this.gameID,this.username, ((PointsMessage) incomingMsg).getTiles());
                }
                case SWITCH_PLACE -> {
                    controller.swapOrder(((IntArrayMessage) incomingMsg).getIntegers(),gameID,username);
                }
                case ADD_TO_BOOKSHELF -> {
                    controller.addToBookshelf(gameID,username,((IntMessage)incomingMsg).getNum());
                }
                case DISCONNECT -> {
                    disconnect();
                }
                case PONG -> {
                    this.time=0;
                }
                default -> {
                    System.out.println("Server received: " + incomingMsg.toString());
                }
            }
            if(messageOut!=null) {
                System.out.println("sending "+ messageOut.getType());
                this.oos.writeObject(messageOut);
            }
        }
    }

    public void sendMessage(Message message){

        try {
            oos.writeObject(message);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void pingPong(){

        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{
             Message msg = new Message();
             msg.setType(MessageTypes.PING);
            try {
                oos.writeObject(msg);
            } catch (IOException ex) {
                if(!disconnected)
                    System.out.println(username + " is not responding...");
            }
        },10,500, TimeUnit.MILLISECONDS);

        timer.schedule(new TimerCounter(this), initialDelay, delta);

    }

    @Override
    public void disconnect() {
        e.shutdown();
        timer.cancel();

        if(username!=null){
            controller.playerDisconnection(username);
        }else System.out.println("A client has disconnected before having successfully logged in");

        this.disconnected=true;
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

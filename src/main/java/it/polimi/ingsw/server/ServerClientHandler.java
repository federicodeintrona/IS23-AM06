package it.polimi.ingsw.server;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.TCPVirtualView;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.Timer.ClientTimerInterface;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerClientHandler implements Runnable, ClientTimerInterface {
    private final Controller controller;
    private String username;
    private int gameID;
    private Player player;
    private Socket socket;
    private ObjectOutputStream oos;
    private Message messageOut;
    boolean disconnected = false;

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

    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
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
        if(incomingMsg != null) {
            System.out.println("Server received " + incomingMsg.getType()+ incomingMsg.getUsername() + "from: " + username);
            switch (incomingMsg.getType()) {
                case USERNAME -> {
                    messageOut = controller.handleNewClient(incomingMsg.getUsername(),
                                new TCPVirtualView(incomingMsg.getUsername(),this.socket,oos));

                    if(!messageOut.getType().equals(MessageTypes.ERROR)){
                        this.gameID = ((IntMessage) messageOut).getNum();
                        this.username=incomingMsg.getUsername();
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
                    controller.playerDisconnection(username);
                    this.disconnected = true;
                }
                case PONG -> {
                    this.time=0;
                }
                default -> {
                    System.out.println("Server received: " + incomingMsg.toString());
                }
            }
            this.oos.writeObject(messageOut);
        }
    }


    private void pingPong() throws IOException{
        System.out.println(username + "'s timer has started");

        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{
             Message msg = new Message();
             msg.setType(MessageTypes.PING);
            try {
                oos.writeObject(msg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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
        }
        System.out.println(username + " has disconnected");
        this.disconnected=true;
    }

    @Override
    public int updateTime() {
        this.time+=1;
        return this.time;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

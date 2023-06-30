package it.polimi.ingsw.client;
import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Messages.ViewMessage;
import it.polimi.ingsw.utils.Tile;
import it.polimi.ingsw.utils.Tiles;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimerInterface;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * <p>Class used to read all the message sent from the controller and modify client state.</p>
 * If the message received is:
 * <ul>
 *     <li> a view message then the class changes client state;</li>
 *     <li> a ping message then sends back the pong message to server and in case of
 *     missing answer starts disconnection;</li>
 *     <li> in other case send to networker the message.</li>
 * </ul>
 * It also close socket, object input stream and object output stream in case of disconnection.
 */
public class Reader extends Thread implements TimerInterface {
    private final Socket socket;
    private final ObjectInputStream client;
    private final ObjectOutputStream oos;
    private final NetworkerTcp networkerTcp;
    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);
    private final ClientState clientState;

    private boolean disconnected = false;
    private ScheduledExecutorService e;

    //Timer
    private boolean ponging = true;
    private  Timer timer;
    private int time = 0;
    private static final int initialDelay = 50;
    private static final int delta = 1000;

    /**
     * Initialize socket, object output stream, networker tcp and client state and creates a new object input stream.
     * @param socket server socket.
     * @param oos object output stream.
     * @param networkerTcp networker.
     * @param clientState client state to modify.
     * @throws IOException in case of problem with input and output.
     */
    public Reader(Socket socket,ObjectOutputStream oos, NetworkerTcp networkerTcp, ClientState clientState) throws IOException {
        this.client = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        this.oos = oos;
        this.socket=socket;
        this.networkerTcp = networkerTcp;
        this.clientState = clientState;
    }

    /**
     * Method to read messages sent from the Server.
     *If the message received is:
     *  <ul>
     *     <li> a view message then the class changes client state;</li>
     *     <li> a ping message then sends back the pong message to server and in case of
     *           missing answer starts disconnection;</li>
     *     <li> in other case send to networker the message.</li>
     *  </ul>
     */
    public void run() {
        notifier.addPropertyChangeListener(networkerTcp);
        pingPong();
        Message newMessage = null;
        Message oldMessage = null;

        try {
            while(!disconnected){

                if(socket.isConnected()) newMessage = (Message) client.readObject();

                if(newMessage!=null) {
                    if (newMessage.getType() == MessageTypes.VIEW) {
                        processMessage(newMessage);
                    }
                    else if (newMessage.getType().equals(MessageTypes.PING)) {
                        time=0;
                    }
                    else {
                        notifier.firePropertyChange(new PropertyChangeEvent(newMessage,
                                newMessage.getType().toString(), oldMessage, newMessage));

                        oldMessage = newMessage;
                    }
                }
            }
        }
        catch(SocketException | EOFException e){
            if(!disconnected) out.println("The game is about to close! Have fun! The game will go on without you:)");
        }
        catch (IOException e) {
            System.out.println( "Server is not responding...");

        } catch (ClassNotFoundException e) {
            System.out.println( "Unable to identify the received object...");

        }
    }


    /**
     * Starts sending pings to the server and starts the countdown timer.
     */
    private void pingPong(){
        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{
            Message msg = new Message();
            msg.setType(MessageTypes.PONG);
            try {
                oos.writeObject(msg);
                oos.flush();
            } catch (IOException e) {
                if(ponging&&!disconnected) {
                    System.out.println("Server is not responding...");
                    ponging=false;
                }
            }
        },10,500, TimeUnit.MILLISECONDS);

        timer = new Timer();
        TimerTask task = new TimerCounter(this);
        timer.schedule(task,initialDelay,delta);
    }

    private void processMessage(Message newMessage){

        ViewMessage message = (ViewMessage) newMessage;
        switch (message.getObjName()) {
            case ("board") ->
                clientState.setBoard((Matrix) message.getContent());
            case ("bookshelf") ->
                clientState.setAllBookshelf(message.getText(), (Matrix) message.getContent());
            case ("currPlayer") ->
                    clientState.setCurrentPlayer((String) message.getContent());
            case ("playerNames") ->
                    clientState.setAllUsername(new ArrayList<String>((List<String>) message.getContent()));
            case ("commonObj") ->
                    clientState.setGameCommonObjective(new ArrayList<Integer>((List<Integer>) message.getContent()));
            case ("commonObjPoints") ->
                    clientState.setCommonObjectivePoints(new ArrayList<>((List<Integer>) message.getContent()));
            case ("commonObjCompleted") ->
                    clientState.setCommonObjMaps(new ArrayList<HashMap<String, Integer>>((List<HashMap<String, Integer>>) message.getContent()));
            case ("publicPoints") ->
                    clientState.setAllPublicPoints(message.getText(), (int) message.getContent());
            case ("selectedTiles") ->
                    clientState.setSelectedTiles((ArrayList<Tile>) message.getContent());
            case ("personalObj") ->
                    clientState.setMyPersonalObjective((HashMap<Point, Tiles>) message.getContent());
            case ("personalObjNum")->
                clientState.setMyPersonalObjectiveInt((int) message.getContent());
            case ("privatePoints") ->
                    clientState.setMyPoints((int) message.getContent());
            case ("nextPlayer") ->
                    clientState.setNextPlayer((String) message.getContent());
            case ("chairPlayer")->
                    clientState.setChair((String) message.getContent());
            case ("winner") ->
                    clientState.setWinnerPlayer((String) message.getContent());
            case ("disconnectionWinner") ->
                    clientState.setDisconnectionWinner(true);
            case ("start") ->
                    clientState.setGameHasStarted((Boolean) message.getContent());
            case ("message") ->
                    clientState.newMessageHandler((ChatMessage) message.getContent());
            case ("notification") ->
                    clientState.notify((String)message.getContent(),message.getText());
            case ("reloadChats") ->
                    clientState.reloadChats((ChatController) message.getContent());
            case ("end") -> {
                clientState.setGameIsEnded((Boolean) message.getContent());
            }
        }
    }


    /**
     * Stops the countdown timer and the ping pong
     */
    public void stopTimer(){
        e.shutdown();
        timer.cancel();
    }


    /**
     * Method used to notify networker to start disconnection process.
     */
    @Override
    public void disconnect() {
        notifier.firePropertyChange(new PropertyChangeEvent(true,
                "disconnect", null, null));
    }

    /**
     * Method to close all active thread, socket, object output stream and object input stream
     * and then close the system.
     */
    public void disconnection() {
        disconnected=true;
        e.shutdown();
        timer.cancel();

        try {
            socket.close();
            oos.close();
            client.close();
            out.println("The game is about to close! Have fun! :)");
            System.exit(0);

        } catch (IOException ex) {
            out.println("The game is about to close! Have fun! :)");
            System.exit(0);
        }
    }

    /**
     * Method to update countdown for skipped ping message.
     * @return the number of skipped ping message.
     */
    @Override
    public int updateTime() {
        time++;
        return time;
    }

    /**
     * <strong>Getter</strong> -> Returns the error message.
     * @return error message.
     */
    @Override
    public String getErrorMessage() {
        return "Server is not responding. Retry later";
    }
}
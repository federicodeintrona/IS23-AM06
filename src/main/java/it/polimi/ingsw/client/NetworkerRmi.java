package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.server.RMIHandlerInterface;
import it.polimi.ingsw.utils.JsonReader;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimerInterface;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class that regulates the RMI_Client.
 */
public class NetworkerRmi implements Networker, TimerInterface {
    private static int port;
    private  String serverIP;
    private String username;
    private int gameID;
    private Message message;
    private static RMIHandlerInterface rmiHandler;
    private final ClientState clientState;
    private View view;

    private ScheduledExecutorService e ;
    private Timer timer;
    private boolean ponging = true;
    private int time = 0;
    private static final int initialDelay = 50;
    private static final int delta = 1000;
    private boolean disconnected=false;


    /**
     * Constructor that sets the rmiPort via Json
     * and create an instance of ClientState.
     */
    public NetworkerRmi()  {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config = new JsonReader(is);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        port = config.getInt("rmiPort");

        try {
            clientState = new ClientState(new Object());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor that sets the rmiPort via Json
     * and sets the instance of ClientState.
     *
     * @param state     instance of ClientState to set.
     */
    public NetworkerRmi(ClientState state)  {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config = new JsonReader(is);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        port = config.getInt("rmiPort");

        clientState = state;
    }

    /**
     * Constructor that sets the rmiPort via Json
     * and sets the instance of ClientState and
     * the string containing the serverIp.
     *
     * @param state     Instance of ClientState to set.
     * @param serverIP      String containing the serverIp.
     */
    public NetworkerRmi(ClientState state,String serverIP)  {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config = new JsonReader(is);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        port = config.getInt("rmiPort");

        clientState = state;
        this.serverIP = serverIP;
    }

    /**
     * Method to initialize an RMI connection.
     */
    public boolean initializeConnection () {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            // Looking up the registry for the remote object
            rmiHandler = (RMIHandlerInterface) registry.lookup("RMIHandler");

            System.out.println("Created RMI connection with Server");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Method to ask client to enter a valid username. Once he has
     * done the client gets added to the lobby.
     *
     * @param username  Message from the View containing the username.
     */
    public void firstConnection (Message username) {
        IntMessage message1;
        try {
             message1 = rmiHandler.acceptRmiConnection(username.getText(),clientState);

        // Saving the username after assuring that is ok
        if (!message1.getType().equals(MessageTypes.ERROR)){
           this.username = username.getText();
           pingPong();
        }

        // Saving the gameID once the new game has been created
        if (message1.getType().equals(MessageTypes.WAITING_FOR_PLAYERS) ||
                message1.getType().equals(MessageTypes.RECONNECT )){
            gameID =  message1.getNum();
        }

            view.receivedMessage(message1);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");
        }


    }



    /**
     * Method used to select the number of players in a game.
     * Casts the message coming from the View to an IntMessage and calls
     * the controller method passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param numberOfPlayers   Message to cast from the View.
     */
    public void numberOfPlayersSelection(Message numberOfPlayers) {
        IntMessage tempMessage = (IntMessage) numberOfPlayers;
        IntMessage message1;
        try {
            message1 = rmiHandler.newLobby(this.username, tempMessage.getNum());
            this.gameID = message1.getNum();
            view.receivedMessage(message1);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");
        }

    }

    /**
     * Method used to remove a maximum of 3 tiles from the board send by coordinates.
     * Casts the message coming from the View to a PointsMessage and calls
     * the controller method passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param tiles     Message to cast from the View.
     */
    public void removeTilesFromBoard(Message tiles) {
        PointsMessage tempMessage = (PointsMessage) tiles;
        try {
            message = rmiHandler.removeTiles(gameID, username, tempMessage.getTiles());
            view.receivedMessage(message);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");
        }
    }

    /**
     * Method used to select the new order for the chosen tiles and switch them.
     * Casts the message coming from the View to a IntArrayMessage and calls
     * the controller method passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param ints  Message to cast from the View.
     */
    public void switchTilesOrder(Message ints) {
        IntArrayMessage tempMessage = (IntArrayMessage) ints;
        try {
            message = rmiHandler.swapOrder(tempMessage.getIntegers(), gameID, username);
            view.receivedMessage(message);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");

        }
    }

    /**
     * Method used to select the column to add the chosen tiles.
     * Casts the message coming from the View to a IntArrayMessage and calls
     * the controller method passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param column    Message to cast from the View.
     */
    public void addTilesToBookshelf (Message column) {
        IntMessage tempMessage = (IntMessage) column;
        try {
            message = rmiHandler.addToBookshelf(gameID, username, tempMessage.getNum());
            view.receivedMessage(message);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");
        }

    }

    /**
     * <strong>Setter</strong> -> Sets the user interface.
     * @param view user interface created by the clientbase.
     */
    @Override
    public void setView(View view) {
        this.view=view;
    }

    /**
     * Method used to send private or public messages to the other players via server.
     * Casts the message coming from the View to a ChatMessage and calls one of
     * the 2 controller methods, depending on that the message is for a private or
     * public chat, passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param message   Message to cast from the View.
     */
    public void chat (Message message) {
        ChatMessage tempMessage = (ChatMessage) message;

        // The message is intended for a public chat due to having the receivingUsername equals to null
        if (tempMessage.getReceivingUsername() == null) {
            try {
                this.message = rmiHandler.sendMessage(gameID, username, tempMessage.getMessage());
            } catch (RemoteException e) {
                System.out.println("Unable to reach the Server while chatting...");
                System.out.println("Client closing please try again later");
                view.close();
            }
        }

        // The message is intended for a private chat
        else {
            try {
                this.message = rmiHandler.sendMessage(gameID, username, tempMessage.getMessage(), tempMessage.getReceivingUsername());
            } catch (RemoteException e) {
                System.out.println("Unable to reach the Server while chatting...");
                System.out.println("Client closing please try again later");
                view.close();
            }
        }
    }

    /**
     * Starts sending pings to the server and starts the countdown timer.
     */
    private void pingPong(){
        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{

            try {
                if(rmiHandler.pingPong()){
                    this.time=0;

                }
            } catch (RemoteException ex) {
                if(ponging&&!disconnected) {
                    System.out.println("Server is not responding...");
                    ponging=false;
                }
            }

        },10,1000, TimeUnit.MILLISECONDS);

        //Timer
        timer = new Timer();
        TimerTask task = new TimerCounter(this);
        timer.schedule(task,initialDelay,delta);
    }


    /**
     * Stops the countdown timer and the ping pong
     */
    public void stopTimer(){
        e.shutdown();
        timer.cancel();
    }


    /**
     * Method to disconnect the client.
     */
    @Override
    public void disconnect() {
        disconnected=true;
        view.close();
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
        return "Server is not responding. Retry later.";
    }


    /**
     * <Strong>Setter</Strong> -> Set the serverIp.
     *
     * @param serverIP      String containing the serverIp.
     */
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    /**
     * Method that regulates the proper closing.
     * of the program during logout.
     * @param closing       closing message.
     */
    @Override
    public void closeProgram(Message closing) {

        if(!disconnected) {
            try {
                rmiHandler.disconnect(username);
            } catch (RemoteException e) {
                System.out.println("Server is not responding...");
            }
        }

        System.out.println("Client disconnecting...");
        System.exit(0);
    }
}

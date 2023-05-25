package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.server.RMIHandlerInterface;
import it.polimi.ingsw.utils.JsonReader;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimerInterface;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NetworkerRmi implements Networker, TimerInterface {
    private static int port;
    private  String serverIP;
    private String username;
    private int gameID;
    private Message message;
    private static RMIHandlerInterface rmiHandler;
    private ClientState clientState;
    private View view;

    //Timer

    private  Timer timer;
    private int time = 0;
    private static final int initialDelay = 50;
    private static final int delta = 2000;
    private boolean disconnected=false;


    /**
     * Constructor
     */
    public NetworkerRmi()  {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config = new JsonReader(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        port = config.getInt("rmiPort");

        try {
            clientState = new ClientState(new Object());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public NetworkerRmi(ClientState state,String serverIP)  {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config = new JsonReader(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        port = config.getInt("rmiPort");

        clientState = state;
        this.serverIP = serverIP;
    }

    /**
     * Method to initialize an RMI connection
     */
    public void initializeConnection () {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            // Looking up the registry for the remote object
            rmiHandler = (RMIHandlerInterface) registry.lookup("RMIHandler");

            System.out.println("Created RMI connection with Server");
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Asks the client to enter a valid username. Once he has
     * done the client gets added to the lobby
     */
    public void firstConnection (Message username) {
        IntMessage message1;
        try {
             message1 = rmiHandler.acceptRmiConnection(username.getUsername(),clientState);
            if (!message1.getType().equals(MessageTypes.ERROR)){
                this.username = username.getUsername();
                pingPong();
            }
            if (message1.getType().equals(MessageTypes.WAITING_FOR_PLAYERS)){
                gameID =  message1.getNum();
            }

            view.receivedMessage(message1);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }


    }



    /**
     *
     * @param numberOfPlayers
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
            e.printStackTrace();
        }

    }

    /**
     * Removes a maximum of 3 tiles from the board send by coordinates
     *
     * @param tiles     ArrayList containing the coordinates of the tiles to remove
     */
    public void removeTilesFromBoard(Message tiles) {
        PointsMessage tempMessage = (PointsMessage) tiles;
        try {
            message = rmiHandler.removeTiles(gameID, username, tempMessage.getTiles());
            view.receivedMessage(message);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }

    }

    /**
     *
     * @param ints
     */
    public void switchTilesOrder(Message ints) {
        IntArrayMessage tempMessage = (IntArrayMessage) ints;
        try {
            message = rmiHandler.swapOrder(tempMessage.getIntegers(), gameID, username);
            view.receivedMessage(message);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param column
     */
    public void addTilesToBookshelf (Message column) {
        IntMessage tempMessage = (IntMessage) column;
        try {
            message = rmiHandler.addToBookshelf(gameID, username, tempMessage.getNum());
            view.receivedMessage(message);
        } catch (RemoteException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }


    }

    @Override
    public void setView(View view) {
        this.view=view;
    }

    private String getClientIP() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostAddress();
    }

    public static String getLocalIPAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface current = interfaces.nextElement();
            if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
                continue;
            }
            Enumeration<InetAddress> addresses = current.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress currentAddr = addresses.nextElement();
                if (currentAddr.isLoopbackAddress()) {
                    continue;
                }
                if (currentAddr instanceof Inet4Address) {
                    return currentAddr.getHostAddress();
                }
            }
        }
        return null;
    }


    private void pingPong(){
        ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{
            Message msg = new Message();
            msg.setType(MessageTypes.PONG);
            try {
                if(rmiHandler.pingPong()){
                    this.time=0;
                }
            } catch (RemoteException ex) {
                if(!disconnected)
                    System.out.println(username + " is not responding...");
            }

        },10,1000, TimeUnit.MILLISECONDS);

        timer = new Timer();
        TimerTask task = new TimerCounter(this);
        timer.schedule(task,initialDelay,delta);
    }

    @Override
    public void disconnect() {

        disconnected=true;

    }

    @Override
    public int updateTime() {
        time++;
        return time;
    }

    @Override
    public String getErrorMessage() {
        return "Server is not responding. Retry later.";
    }
}

package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.server.RMIHandlerInterface;
import it.polimi.ingsw.utils.JsonReader;
import it.polimi.ingsw.utils.Messages.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;

public class NetworkerRmi implements Networker {
    private static int port;
    private  String serverIP;
    private String username;
    private int gameID;
    private Message message;
    private static RMIHandlerInterface rmiHandler;
    private ClientState clientState;
    private CLIMain cli;


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
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Calling the completeRmiConnection() method to complete the client-server connection
        if (!message1.getType().equals(MessageTypes.ERROR)){
           this.username = username.getUsername();
        }
        if (message1.getType().equals(MessageTypes.WAITING_FOR_PLAYERS)){
            gameID =  message1.getNum();
        }

        cli.receivedMessage(message1);
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
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.gameID = message1.getNum();
        cli.receivedMessage(message1);
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
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        cli.receivedMessage(message);
    }

    /**
     *
     * @param ints
     */
    public void switchTilesOrder(Message ints) {
        IntArrayMessage tempMessage = (IntArrayMessage) ints;
        try {
            message = rmiHandler.swapOrder(tempMessage.getIntegers(), gameID, username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        cli.receivedMessage(message);
    }

    /**
     *
     * @param column
     */
    public void addTilesToBookshelf (Message column) {
        IntMessage tempMessage = (IntMessage) column;
        try {
            message = rmiHandler.addToBookshelf(gameID, username, tempMessage.getNum());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        cli.receivedMessage(message);
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

    public void chat (Message message) {
        try {
            this.message = rmiHandler.sendMessage(gameID, username, message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        cli.receivedMessage(this.message);
    }

    @Override
    public void privateChat(Message message) {

    }

    public void setCli(CLIMain cli) {
        this.cli = cli;
    }
}

package it.polimi.ingsw.client;



import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.server.RMIHandlerInterface;
import it.polimi.ingsw.utils.Messages.*;


import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;

public class NetworkerRmi implements Networker {
    private static int portIn = 1099;
    private static int portOut = 0;
    private static String clientIP;
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
        clientState = new ClientState();

        try {
            clientIP = getLocalIPAddress();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public NetworkerRmi(ClientState state)  {
        clientState = state;

        try {
            clientIP = getLocalIPAddress();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to initialize an RMI connection
     */
    public void initializeConnection () {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", portIn);
            // Looking up the registry for the remote object
            rmiHandler = (RMIHandlerInterface) registry.lookup("RMIHandler");

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }

        System.out.println("Created RMI connection with Server");
        System.out.println(clientIP);

        clientStateExportRmi();
    }

    /**
     * Preparing the instance of clientState to export through RMI connection
     */
    private void clientStateExportRmi () {
        ClientStateRemoteInterface stub = null;
        try {
            stub = (ClientStateRemoteInterface) UnicastRemoteObject.exportObject(clientState, portOut);
        } catch (RemoteException e) {
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
             message1 = rmiHandler.acceptRmiConnection(username.getUsername(), clientIP, portOut,clientState);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Calling the completeRmiConnection() method to complete the client-server connection
        if (!message1.getType().equals(MessageTypes.ERROR)){
           this.username = username.getUsername();
           gameID =  (message1).getNum();
        }

        cli.receivedMessage(message1);
    }



    /**
     *
     * @param numberOfPlayers
     */
    public void numberOfPlayersSelection(Message numberOfPlayers) {
        IntMessage tempMessage = (IntMessage) numberOfPlayers;

        try {
            message = rmiHandler.newLobby(this.username, tempMessage.getNum());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        cli.receivedMessage(message);
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


    public void setCli(CLIMain cli) {
        this.cli = cli;
    }
}

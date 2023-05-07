package it.polimi.ingsw.client;


import it.polimi.ingsw.server.ControllerInterface;
import it.polimi.ingsw.server.Messages.*;

import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;

public class NetworkerRmi implements Networker {
    private static int portIn = 1099;
    private static int portOut = 1234;
    private static String clientIP;
    private String username;
    private int lobbyID;
    private int gameID;
    private Message message;
    private static ControllerInterface controller;
    private ClientState clientState;

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

    /**
     * Method to initialize an RMI connection
     */
    public void initializeConnection () {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", portIn);
            // Looking up the registry for the remote object
            controller = (ControllerInterface) registry.lookup("Controller");

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
        // Bind the remote object's stub in the registry
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(portOut);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            registry.bind("ClientState", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks the client to enter a valid username. Once he has
     * done the client gets added to the lobby
     */
    public void firstConnection (Message username) {
        try {
            message = controller.handleNewClient(username.getUsername());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Calling the completeRmiConnection() method to complete the client-server connection
        if (!message.getType().equals(MessageTypes.ERROR)) completeRmiConnection();
    }

    /**
     * Method used privately to make the controller accept the
     * instance of clientState previously prepared
     */
    private void completeRmiConnection () {
        try {
            controller.acceptRmiConnection(username, clientIP, portOut);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param numberOfPlayers
     */
    public void numberOfPlayersSelection(Message numberOfPlayers) {
        IntMessage tempMessage = (IntMessage) numberOfPlayers;

        try {
            message = controller.newLobby(this.username, tempMessage.getNum());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
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
            message = controller.removeTiles(gameID, username, tempMessage.getTiles());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param ints
     */
    public void switchTilesOrder(Message ints) {
        IntArrayMessage tempMessage = (IntArrayMessage) ints;
        try {
            message = controller.swapOrder(tempMessage.getIntegers(), gameID, username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param column
     */
    public void addTilesToBookshelf (Message column) {
        IntMessage tempMessage = (IntMessage) column;
        try {
            message = controller.addToBookshelf(gameID, username, tempMessage.getNum());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

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
}

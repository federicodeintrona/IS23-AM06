package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIHandler implements RMIHandlerInterface{


    private final Controller controller;

    public RMIHandler(Controller controller) {
        this.controller = controller;
    }


    @Override
    public Message addToBookshelf(int gameID, String playerID, int col) throws RemoteException {
        return controller.addToBookshelf(gameID,playerID,col);
    }

    @Override
    public Message swapOrder(ArrayList<Integer> ints, int gameID, String playerID) throws RemoteException {
        return controller.swapOrder(ints,gameID,playerID);
    }

    @Override
    public Message removeTiles(int gameID, String playerID, ArrayList<Point> points) throws RemoteException {
        return controller.removeTiles(gameID,playerID,points);
    }

    @Override
    public IntMessage newLobby(String client, int players) throws RemoteException {
        return controller.newLobby(client,players);
    }

    @Override
    public IntMessage acceptRmiConnection(String username, String ipAddress, int port) throws RemoteException {

        IntMessage message = null;
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(ipAddress, port);
            // Looking up the registry for the remote object
            ClientStateRemoteInterface clientState = (ClientStateRemoteInterface) registry.lookup("ClientState");

            System.out.println("rmi vv: " + username);

            message = controller.handleNewClient(username,new RMIVirtualView(username,clientState));


        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }

        return message;
    }
}

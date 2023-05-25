package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;

import java.awt.*;
import java.rmi.RemoteException;
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

    /**
     * Gets the instance of clientState from a specific Client given his ip address and port
     *
     * @param username      client's username
     */
    @Override
    public IntMessage acceptRmiConnection(String username, ClientStateRemoteInterface state) throws RemoteException {

        IntMessage message = null;
        try {

            RMIVirtualView myView = new RMIVirtualView(username,state);
            RMITimer myTimeout = new RMITimer(username,myView,controller);
            myTimeout.pingPong();

            message = controller.handleNewClient(username, myView);

            if(!message.getType().equals(MessageTypes.ERROR)){
                myTimeout.setUsername(username);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public boolean pingPong() throws RemoteException {
        return true;
    }

    @Override
    public Message sendMessage(int gameId, String playerForwarding, String message) throws RemoteException {
        return controller.sendMessage(gameId, playerForwarding, message);
    }
}

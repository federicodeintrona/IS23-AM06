package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Timer.RMITimer;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * <p>
 *     Class that forwards all messages form
 *     a RMI Client to the actual Controller.
 * </p>
 */
public class RMIHandler implements RMIHandlerInterface{
    private final Controller controller;

    /**
     * Constructor.
     *
     * @param controller        Controller to set,
     */
    public RMIHandler(Controller controller) {
        this.controller = controller;
    }


    /**
     * Method that calls the addToBookshelf in the Controller.
     *
     * @param gameID         Key to access the correct game in the HashMap games.
     * @param playerID          Player's username.
     * @param col           Column to add the selected tiles.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    @Override
    public Message addToBookshelf(int gameID, String playerID, int col) throws RemoteException {
        return controller.addToBookshelf(gameID,playerID,col);
    }

    /**
     * Method that calls the swapOrder in the Controller.
     *
     * @param ints      ArrayList of positions to switch.
     * @param gameID        Key to access the correct game in the HashMap games.
     * @param playerID      Player's username.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    @Override
    public Message swapOrder(ArrayList<Integer> ints, int gameID, String playerID) throws RemoteException {
        return controller.swapOrder(ints,gameID,playerID);
    }

    /**
     * Method that calls the removeTiles in the Controller.
     *
     * @param gameID        Key to access the correct game in the HashMap games.
     * @param playerID      Player's username.
     * @param points        ArrayList of coordinates to remove.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    @Override
    public Message removeTiles(int gameID, String playerID, ArrayList<Point> points) throws RemoteException {
        return controller.removeTiles(gameID,playerID,points);
    }

    /**
     * Method that calls the newLobby in the Controller.
     *
     * @param client        Player's username.
     * @param players       Number of players.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    @Override
    public IntMessage newLobby(String client, int players) throws RemoteException {
        return controller.newLobby(client,players);
    }

    /**
     * Method to get the instance of clientState from a specific Client given his ip address and port.
     *
     * @param username      client's username.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    @Override
    public IntMessage acceptRmiConnection(String username, ClientStateRemoteInterface state) throws RemoteException {

        IntMessage message = null;


        RMIVirtualView myView = new RMIVirtualView(username.toLowerCase(),state);
        RMITimer myTimer = new RMITimer(myView,controller);
        myTimer.pingPong();
        myView.setTimer(myTimer);

        message = controller.handleNewClient(username, myView);

        if(message.getType().equals(MessageTypes.ERROR)){
            myView.stopTimer();
        }



        return message;
    }

    /**
     * Method used by the client to check if the server is still running.
     * @return <i>true</i>.
     * @throws RemoteException If the rmi connection stops working.
     */
    @Override
    public boolean pingPong() throws RemoteException {
        return true;
    }

    /**
     * Method that calls the sendMessage
     * (for PublicChat) in the Controller.
     *
     * @param gameId        Key to access the correct game in the HashMap games.
     * @param playerForwarding      Player who wrote the message.
     * @param message       Message to forward.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    @Override
    public Message sendMessage(int gameId, String playerForwarding, String message) throws RemoteException {
        return controller.sendMessage(gameId, playerForwarding, message);
    }

    /**
     * Method that calls the sendMessage
     * (for PrivateChat) in the Controller.
     *
     * @param gameId        Key to access the correct game in the HashMap games.
     * @param playerForwarding      Player who wrote the message.
     * @param message       Message to forward.
     * @param playerReceiving       Player who the message is destined to.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    public Message sendMessage(int gameId, String playerForwarding, String message, String playerReceiving) throws RemoteException {
        return controller.sendMessage(gameId, playerForwarding, message, playerReceiving);
    }


    /**
     * Method to disconnect the associated player.
     * @param username The username of the player.
     * @throws RemoteException  In case the connection fails between Server and RMI Client.
     */
    @Override
    public void disconnect(String username) throws RemoteException {
        controller.playerDisconnection(username);
    }
}

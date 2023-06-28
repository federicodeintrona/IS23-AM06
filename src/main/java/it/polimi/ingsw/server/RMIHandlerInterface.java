package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.Message;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Remote interface of the RMI client handler used for RMI connection.
 */
public interface RMIHandlerInterface extends Remote {
    /**
     * Method that calls the addToBookshelf in the Controller.
     *
     * @param gameID         Key to access the correct game in the HashMap games.
     * @param playerID          Player's username.
     * @param col           Column to add the selected tiles.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    Message addToBookshelf(int gameID, String playerID, int col ) throws RemoteException;

    /**
     * Method that calls the swapOrder in the Controller.
     *
     * @param ints      ArrayList of positions to switch.
     * @param gameID        Key to access the correct game in the HashMap games.
     * @param playerID      Player's username.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    Message swapOrder(ArrayList<Integer> ints, int gameID, String playerID) throws RemoteException;

    /**
     * Method that calls the removeTiles in the Controller.
     *
     * @param gameID        Key to access the correct game in the HashMap games.
     * @param playerID      Player's username.
     * @param points        ArrayList of cordinates to remove.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    Message removeTiles(int gameID,String playerID, ArrayList<Point> points) throws RemoteException;

    /**
     * Method that calls the newLobby in the Controller.
     *
     * @param client        Player's username.
     * @param players       Number of players.
     * @return      <i>Message</i> from Controller.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     */
    IntMessage newLobby(String client, int players) throws RemoteException;

    /**
     * Gets the instance of clientState from a specific Client given his ip address and port.
     *
     * @param username      client's username.
     * @param state     ClientState interface from RMI client.
     * @throws RemoteException      In case the connection fails between Server and RMI Client.
     * @return      <i>IntMessage</i> from Controller.
     */
    IntMessage acceptRmiConnection (String username, ClientStateRemoteInterface state) throws RemoteException;

    /**
     * Method used by the client to check if the server is still running.
     *
     * @return <i>true</i>.
     * @throws RemoteException If the rmi connection stops working.
     */
    boolean pingPong() throws RemoteException;

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
    Message sendMessage (int gameId, String playerForwarding, String message) throws RemoteException;

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
    Message sendMessage (int gameId, String playerForwarding, String message, String playerReceiving) throws RemoteException;

    /**
     * Method to disconnect the associated player.
     * @param username The username of the player.
     * @throws RemoteException  In case the connection fails between Server and RMI Client.
     */
    void disconnect(String username) throws RemoteException;
}

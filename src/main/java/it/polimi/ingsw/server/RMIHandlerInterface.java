package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.Message;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIHandlerInterface extends Remote {
    Message addToBookshelf(int gameID, String playerID, int col ) throws RemoteException;
    Message swapOrder(ArrayList<Integer> ints, int gameID, String playerID) throws RemoteException;
    Message removeTiles(int gameID,String playerID, ArrayList<Point> points) throws RemoteException;
    IntMessage newLobby(String client, int players) throws RemoteException;
    IntMessage acceptRmiConnection (String username, ClientStateRemoteInterface state) throws RemoteException;
    boolean pingPong() throws RemoteException;
    Message sendMessage (int gameId, String playerForwarding, String message) throws RemoteException;
    Message sendMessage (int gameId, String playerForwarding, String message, String playerReceiving) throws RemoteException;

}

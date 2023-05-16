package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.Message;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIHandlerInterface extends Remote {
    public Message addToBookshelf(int gameID, String playerID, int col ) throws RemoteException;
    public Message swapOrder(ArrayList<Integer> ints, int gameID, String playerID) throws RemoteException;
    public Message removeTiles(int gameID,String playerID, ArrayList<Point> points) throws RemoteException;
    public IntMessage newLobby(String client, int players) throws RemoteException;
    public IntMessage acceptRmiConnection (String username, String ipAddress, int port, ClientStateRemoteInterface state) throws RemoteException;
}

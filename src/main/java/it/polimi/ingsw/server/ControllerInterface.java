package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Messages.Message;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ControllerInterface extends Remote {

    public void startGame (int ID) throws RemoteException;

    public Message addToBookshelf(int gameID, String playerID, int col ) throws RemoteException;

    public Message swapOrder(ArrayList<Integer> ints, int gameID, String playerID) throws RemoteException;

    public Message removeTiles(int gameID,String playerID, ArrayList<Point> points) throws RemoteException;

    public Message newLobby(String client,int players) throws RemoteException;

    public Message handleNewClient(String client) throws RemoteException;

    public void acceptRmiConnection (String ipAddress, int port) throws RemoteException;
    }

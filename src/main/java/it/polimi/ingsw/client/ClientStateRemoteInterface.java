package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Model.Tiles;
import it.polimi.ingsw.utils.Matrix;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;


public interface ClientStateRemoteInterface extends Remote {
    public void setMyUsername(String myUsername) throws RemoteException;

    public void setMyBookshelf(Matrix myBookshelf) throws RemoteException;

    public void setAllBookshelf(HashMap<String, Matrix> allBookshelf) throws RemoteException;

    public void setAllBookshelf(String username, Matrix bookshelf) throws RemoteException;

    public void setBoard(Matrix board) throws RemoteException;

    public void setAllUsername(ArrayList<String> allUsername) throws RemoteException;

    public void setMyPersonalObjective(HashMap<Point, Tiles> myPersonalObjective) throws RemoteException;

    public void setGameCommonObjective(ArrayList<Integer> gameCommonObjective) throws RemoteException;

    public void setMyPoints(Integer myPoints) throws RemoteException;

    public void setAllPublicPoints(HashMap<String, Integer> allPublicPoints) throws RemoteException;

    public void setAllPublicPoints(String username, Integer point) throws RemoteException;

    public void setSelectedTiles(ArrayList<Point> selectedTiles) throws RemoteException;

    public void setCurrentPlayer(String currentPlayer) throws RemoteException;

    public void setNextPlayer(String nextPlayer) throws RemoteException;

    public void setWinnerPlayer(String winnerPlayer) throws RemoteException;

    public void setGameIsEnded(boolean gameIsEnded) throws RemoteException;

    public void setGameHasStarted(boolean gameHasStarted) throws RemoteException;
}

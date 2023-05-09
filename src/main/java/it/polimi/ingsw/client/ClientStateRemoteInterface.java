package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Model.Tiles;
import it.polimi.ingsw.utils.Matrix;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ClientStateRemoteInterface extends Remote {
    void setMyUsername(String myUsername) throws RemoteException;

    void setMyBookshelf(Matrix myBookshelf) throws RemoteException;

    void setAllBookshelf(String username, Matrix bookshelf) throws RemoteException;

    void setBoard(Matrix board) throws RemoteException;

    void setAllUsername(ArrayList<String> allUsername) throws RemoteException;

    void setMyPersonalObjective(HashMap<Point, Tiles> myPersonalObjective) throws RemoteException;

    void setGameCommonObjective(ArrayList<Integer> gameCommonObjective) throws RemoteException;

    void setMyPoints(Integer myPoints) throws RemoteException;

    void setAllPublicPoints(String username, Integer point) throws RemoteException;

    void setSelectedTiles(ArrayList<Tiles> selectedTiles) throws RemoteException;

    void setCurrentPlayer(String currentPlayer) throws RemoteException;

    void setNextPlayer(String nextPlayer) throws RemoteException;

    void setWinnerPlayer(String winnerPlayer) throws RemoteException;

    void setGameIsEnded(boolean gameIsEnded) throws RemoteException;
}

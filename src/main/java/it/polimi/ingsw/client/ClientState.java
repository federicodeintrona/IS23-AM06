package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.Tiles;
import it.polimi.ingsw.utils.Matrix;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientState extends UnicastRemoteObject implements ClientStateRemoteInterface{

    private Object viewLock; //TODO da fare final

    private PropertyChangeSupport notifier = new PropertyChangeSupport(this);
    private String myUsername;
    private ArrayList<String> allUsername;
    private HashMap<Point, Tiles> myPersonalObjective = new HashMap<>();
    private ArrayList<Integer> gameCommonObjective ;
    private Matrix board;
    private Matrix myBookshelf;
    private HashMap<String, Matrix> allBookshelf = new HashMap<>();
    private Integer myPoints;
    private HashMap<String, Integer> allPublicPoints= new HashMap<>();
    private ArrayList<Tiles> selectedTiles;
    private String currentPlayer;
    private String nextPlayer;
    private String winnerPlayer;
    private boolean gameHasStarted;
    private boolean gameIsEnded;

    private boolean username=true;

    public boolean isUsername() {
        return username;
    }

    public void setUsername(boolean username) {
        this.username = username;
    }

    public ClientState(Object viewLock) throws RemoteException {
        super();
        this.viewLock = viewLock;
    }

    public ClientState() throws RemoteException {
        super();
    }

    public ClientState(String s, Object o) throws RemoteException {
        super();
        myUsername=s;
        viewLock=o;
    }

    public String getMyUsername() {
        synchronized (viewLock){
            return myUsername;
        }
    }

    public void setMyUsername(String myUsername) {
        synchronized (viewLock){
            this.myUsername = myUsername;
        }
    }

    public ArrayList<String> getAllUsername() {
        synchronized (viewLock){
            return allUsername;
        }
    }

    public void setAllUsername(ArrayList<String> allUsername) {
        synchronized (viewLock){
            this.allUsername = allUsername;
        }
    }

    public HashMap<Point, Tiles> getMyPersonalObjective() {
        synchronized (viewLock){
            return myPersonalObjective;
        }
    }

    public void setMyPersonalObjective(HashMap<Point, Tiles> myPersonalObjective) {
        synchronized (viewLock){
            this.myPersonalObjective = myPersonalObjective;
        }
    }

    public ArrayList<Integer> getGameCommonObjective() {
        synchronized (viewLock){
            return gameCommonObjective;
        }
    }

    public void setGameCommonObjective(ArrayList<Integer> gameCommonObjective) {
        synchronized (viewLock){
            this.gameCommonObjective = gameCommonObjective;
        }
    }

    public Matrix getBoard() {
        synchronized (viewLock){
            return board;
        }
    }

    public void setBoard(Matrix board) {
        synchronized (viewLock){
            this.board = board;
        }
    }

    public Matrix getMyBookshelf() {
        synchronized (viewLock){
            return myBookshelf;
        }
    }

    public void setMyBookshelf(Matrix myBookshelf) {
        synchronized (viewLock){
            this.myBookshelf = myBookshelf;
        }
    }

    public HashMap<String, Matrix> getAllBookshelf() {
        synchronized (viewLock){
            return allBookshelf;
        }
    }

    public void setAllBookshelf(HashMap<String, Matrix> allBookshelf) {
        synchronized (viewLock){
            this.allBookshelf = allBookshelf;
        }
    }

    public void setAllBookshelf(String username, Matrix bookshelf){
        synchronized (viewLock) {
            allBookshelf.put(username, bookshelf);
            if (myUsername.equals(username)) {
                myBookshelf = bookshelf;
            }
        }
    }

    public Integer getMyPoints() {
        synchronized (viewLock) {
            return myPoints;
        }
    }

    public void setMyPoints(Integer myPoints) {
        synchronized (viewLock) {
            this.myPoints = myPoints;
        }
    }

    public HashMap<String, Integer> getAllPublicPoints() {
        synchronized (viewLock) {
            return allPublicPoints;
        }
    }

    public void setAllPublicPoints(HashMap<String, Integer> allPublicPoints) {
        synchronized (viewLock) {
            this.allPublicPoints = allPublicPoints;
        }
    }

    public void setAllPublicPoints(String username, Integer point){
        synchronized (viewLock) {
            allPublicPoints.put(username, point);
        }
    }

    public ArrayList<Tiles> getSelectedTiles() {
        synchronized (viewLock) {
            return selectedTiles;
        }
    }

    public void setSelectedTiles(ArrayList<Tiles> selectedTiles) {
        synchronized (viewLock) {
            this.selectedTiles = selectedTiles;
        }
    }

    public String getCurrentPlayer() {
        synchronized (viewLock) {
            return currentPlayer;
        }
    }

    public void setCurrentPlayer(String currentPlayer) {
        synchronized (viewLock) {
            this.currentPlayer = currentPlayer;
        }
    }

    public String getNextPlayer() {
        synchronized (viewLock) {
            return nextPlayer;
        }
    }

    public void setNextPlayer(String nextPlayer) {
        synchronized (viewLock) {
            this.nextPlayer = nextPlayer;
        }
    }

    public String getWinnerPlayer() {
        synchronized (viewLock) {
            return winnerPlayer;
        }
    }

    public void setWinnerPlayer(String winnerPlayer) {
        synchronized (viewLock) {
            this.winnerPlayer = winnerPlayer;
        }
    }

    public boolean isGameIsEnded() {
        synchronized (viewLock) {
            return gameIsEnded;
        }
    }

    public void setGameIsEnded(boolean gameIsEnded) {
        synchronized (viewLock) {
            this.gameIsEnded = gameIsEnded;
        }
    }

    public boolean gameHasStarted () {
        synchronized (viewLock){ return gameHasStarted; }
    }

    public void setGameHasStarted (boolean gameHasStarted) {
        synchronized (viewLock) {
            notifier.firePropertyChange(new PropertyChangeEvent(this,"start",null,true));
            this.gameHasStarted = gameHasStarted;
        }
    }

    @Override
    public boolean pingPong() throws RemoteException {
        return true;
    }

    public void addListener(PropertyChangeListener listener){
        notifier.addPropertyChangeListener(listener);
    }
}

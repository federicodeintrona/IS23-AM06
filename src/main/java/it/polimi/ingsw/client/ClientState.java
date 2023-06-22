package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.Messages.ChatMessage;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//salva i dati che arrivano dal server per poi mostrarli al client
public class ClientState extends UnicastRemoteObject implements ClientStateRemoteInterface{
    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);
    private final Object viewLock; //TODO da fare final
    private String myUsername;
    private ArrayList<String> allUsername;
    private HashMap<Point, Tiles> myPersonalObjective = new HashMap<>();
    private int myPersonalObjectiveInt;
    private ArrayList<Integer> gameCommonObjective;
    private ArrayList<Integer> commonObjectivePoints;
    private ArrayList<Integer> oldCommonObjectivePoints;
    private Matrix board;
    private Matrix myBookshelf;
    private HashMap<String, Matrix> allBookshelf = new HashMap<>();
    private Integer myPoints;
    private HashMap<String, Integer> allPublicPoints= new HashMap<>();
    private ArrayList<Tiles> selectedTiles;
    private String currentPlayer;
    private String nextPlayer;
    private String winnerPlayer;
    private boolean disconnectionWinner;
    private boolean gameHasStarted=false;
    private boolean gameIsEnded=false;
    private boolean waiting=false;
    private String chair;
    private ChatController chatController = new ChatController();

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

        // Initializing for each player a particular version of ChatController specifically designed for Server
        List<String> allOtherUsernames = allUsername.stream()
                .filter(x -> !x.equals(myUsername))
                .toList();
        for (String player: allOtherUsernames) {
            chatController.getPrivateChats().put(player, new Chat());
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
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"board",null,this.board));
    }

    public Matrix getMyBookshelf() {
        synchronized (viewLock){
            return myBookshelf;
        }
    }

    public HashMap<String, Matrix> getAllBookshelf() {
        synchronized (viewLock){
            return allBookshelf;
        }
    }

    public void setAllBookshelf(String username, Matrix bookshelf){
        synchronized (viewLock) {
            allBookshelf.put(username, bookshelf);
            if (myUsername.equals(username)){
                myBookshelf = bookshelf;
            }
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"bookshelf",username,bookshelf));
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
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"privatePoints",username,myPoints));
    }

    public HashMap<String, Integer> getAllPublicPoints() {
        synchronized (viewLock) {
            return allPublicPoints;
        }
    }

    public void setAllPublicPoints(String username, Integer point){
        synchronized (viewLock) {
            allPublicPoints.put(username, point);
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"publicPoints",username,point));
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
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"selectedTiles", null,selectedTiles));
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
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"currPlayer",null,currentPlayer));
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
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"end",null,true));
    }

    public boolean gameHasStarted () {
        synchronized (viewLock){ return gameHasStarted; }
    }

    public void setGameHasStarted (boolean gameHasStarted) {
        synchronized (viewLock) {
            this.gameHasStarted = gameHasStarted;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"start",null,true));
    }

    @Override
    public boolean pingPong() throws RemoteException {
        return true;
    }

    public void addListener(PropertyChangeListener listener){
        notifier.addPropertyChangeListener(listener);
    }

    public void addListener(PropertyChangeListener listener,String property){
        notifier.addPropertyChangeListener(property,listener);
    }


    public String getChair() {
        synchronized (viewLock) {
            return chair;
        }
    }

    public void setChair(String chair) {
        synchronized (viewLock){
            this.chair = chair;
        }
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    @Override
    public void newMessageHandler (ChatMessage message) {
        if (message.getReceivingUsername() == null)
            notifier.firePropertyChange(new PropertyChangeEvent(this,"publicChat",null, message));

        else
            notifier.firePropertyChange(new PropertyChangeEvent(this,"privateChat",null, message));
    }

    public void reloadChats (ChatController backup) { this.chatController = backup; }

    public ChatController getChatController() {
        return chatController;
    }


    public int getMyPersonalObjectiveInt() {
        synchronized (viewLock){
            return myPersonalObjectiveInt;
        }
    }

    public void setMyPersonalObjectiveInt(int myPersonalObjectiveInt) {
        synchronized (viewLock){
            this.myPersonalObjectiveInt = myPersonalObjectiveInt;
        }
    }

    public ArrayList<Integer> getCommonObjectivePoints() {
        synchronized (viewLock){
        return commonObjectivePoints;
        }
    }

    public void setCommonObjectivePoints(ArrayList<Integer> commonObjectivePoints) {
        synchronized (viewLock) {
            this.commonObjectivePoints = commonObjectivePoints;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this.commonObjectivePoints,"commonObjPoints",null,this.commonObjectivePoints));
    }
    public boolean isDisconnectionWinner() {
        synchronized (viewLock) {
            return disconnectionWinner;
        }
    }

    public void setDisconnectionWinner(boolean disconnectionWinner) {
        synchronized (viewLock) {
            this.disconnectionWinner = disconnectionWinner;
        }
    }

    public ArrayList<Integer> getOldCommonObjectivePoints() {
        synchronized (viewLock) {
            return oldCommonObjectivePoints;
        }
    }

    public void setOldCommonObjectivePoints(ArrayList<Integer> oldCommonObjectivePoints) {
        synchronized (viewLock) {
            this.oldCommonObjectivePoints = oldCommonObjectivePoints;
        }
    }
    public ArrayList<Integer> checkFreeColumn(int numTilesSelected){
        synchronized (viewLock) {
            ArrayList<Integer> list=null;
            for (int i=0;i< Define.NUMBEROFCOLUMNS_BOOKSHELF.getI();i++) {
                if (myBookshelf.getTile(6 - numTilesSelected, i)==Tiles.EMPTY){
                    list.add(i);
                }
            }
            return list;
        }
    }
}

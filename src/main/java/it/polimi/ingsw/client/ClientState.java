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

/**
 * Class to save all the reference client's information received from the server (one ClientState for each instance Client)
 * <ul>
 *     <li>all players' username</li>
 *     <li>board</li>
 *     <li>all players' bookshelf</li>
 *     <li>client personal objective</li>
 *     <li>game common objective</li>
 *     <li>which player has the chair (which one is the first player)</li>
 *     <li>all player's points</li>
 *     <li>current and next player to play</li>
 *     <li>winner player</li>
 * </ul>
 */
public class ClientState extends UnicastRemoteObject implements ClientStateRemoteInterface{

    /**
     * Notify the Client of the change
     */
    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);
    /**
     * The object to lock all the get and set update
     */
    private final Object viewLock;
    /**
     * Client's username
     */
    private String myUsername;
    /**
     * All players' username - contains also my username
     */
    private ArrayList<String> allUsername;
    /**
     * My personal objective - the card
     */
    private HashMap<Point, Tiles> myPersonalObjective = new HashMap<>();
    /**
     * My personal objective - the number
     */
    private int myPersonalObjectiveInt;
    /**
     * Game's common objective - the number of two common objective in the game
     */
    private ArrayList<Integer> gameCommonObjective;
    /**
     * Actual common objective points - the first one is associated with the first one of gameCommonObjective and the second also
     */
    private ArrayList<Integer> commonObjectivePoints;
    /**
     * Old common objective points - the points before the actual update
     */
    private ArrayList<Integer> oldCommonObjectivePoints;
    /**
     * Board
     */
    private Matrix board;
    /**
     * Client's bookshelf
     */
    private Matrix myBookshelf;
    /**
     * All bookshelf - my bookshelf is not contains
     */
    private final HashMap<String, Matrix> allBookshelf = new HashMap<>();
    /**
     * Client's points - common + private points
     */
    private Integer myPoints;
    /**
     * All players' public points - my public points are contains
     */
    private final HashMap<String, Integer> allPublicPoints= new HashMap<>();
    /**
     * The selected tiles
     */
    private ArrayList<Tiles> selectedTiles;
    /**
     * The current player to play
     */
    private String currentPlayer;
    /**
     * The next player to play
     */
    private String nextPlayer;
    /**
     * The player who won
     */
    private String winnerPlayer;
    /**
     * The player who won - he wins because all the other players disconnected
     */
    private boolean disconnectionWinner;
    /**
     * The game has started
     */
    private boolean gameHasStarted=false;
    /**
     * The game is ended
     */
    private boolean gameIsEnded=false;
    /**
     * We are in waiting for the other player
     */
    private boolean waiting=false;
    /**
     * Which player has the chair
     */
    private String chair;
    /**
     * The chat controller
     */
    private ChatController chatController = new ChatController();



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

        //Initializing for each player a particular version of ChatController specifically designed for Server
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
                new PropertyChangeEvent(this,"privatePoints",null,myPoints));
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
            ArrayList<Integer> list=new ArrayList<>();
            for (int i=0;i< Define.NUMBEROFCOLUMNS_BOOKSHELF.getI();i++) {
                if (myBookshelf.getTile( numTilesSelected-1, i)==Tiles.EMPTY){
                    list.add(i);
                }
            }
            return list;
        }
    }
}

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
 * Class to save all the reference client's information received from the server (one ClientState for each instance Client).
 * <p>
 * The information received are:
 * <ul>
 *     <li>all players' username;</li>
 *     <li>board;</li>
 *     <li>all players' bookshelf;</li>
 *     <li>client personal objective;</li>
 *     <li>game common objective;</li>
 *     <li>which player has the chair (which one is the first player);</li>
 *     <li>all player's points;</li>
 *     <li>current and next player to play;</li>
 *     <li>winner player.</li>
 * </ul>
 */
public class ClientState extends UnicastRemoteObject implements ClientStateRemoteInterface{

    /**
     * Notify the Client of the change.
     */
    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);
    /**
     * The object to lock all the get and set update.
     */
    private final Object viewLock;
    /**
     * Client's username.
     */
    private String myUsername;
    /**
     * All players' username - contains also my username.
     */
    private ArrayList<String> allUsername;
    /**
     * My personal objective - the card.
     */
    private HashMap<Point, Tiles> myPersonalObjective = new HashMap<>();
    /**
     * My personal objective - the number.
     */
    private int myPersonalObjectiveInt;
    /**
     * Game's common objective - the number of two common objective in the game.
     */
    private ArrayList<Integer> gameCommonObjective;
    /**
     * Actual common objective points - the first one is associated with the first one of gameCommonObjective and the second also.
     */
    private ArrayList<Integer> commonObjectivePoints;

    private  HashMap<String, Integer> commonMap1;

    private  HashMap<String, Integer> commonMap2;

    /**
     * Old common objective points - the points before the actual update.
     */
    private ArrayList<Integer> oldCommonObjectivePoints;
    /**
     * Board.
     */
    private Matrix board;
    /**
     * Client's bookshelf.
     */
    private Matrix myBookshelf;
    /**
     * All bookshelf - my bookshelf is not contains.
     */
    private final HashMap<String, Matrix> allBookshelf = new HashMap<>();
    /**
     * Client's points - common + private points.
     */
    private Integer myPoints;
    /**
     * All players' public points - my public points are contains.
     */
    private final HashMap<String, Integer> allPublicPoints= new HashMap<>();
    /**
     * The selected tiles.
     */
    private ArrayList<Tile> selectedTiles;
    /**
     * The current player to play.
     */
    private String currentPlayer;
    /**
     * The next player to play.
     */
    private String nextPlayer;
    /**
     * The player who won.
     */
    private String winnerPlayer;
    /**
     * The player who won - he wins because all the other players disconnected.
     */
    private boolean disconnectionWinner;
    /**
     * The game has started.
     */
    private boolean gameHasStarted=false;
    /**
     * The game is ended.
     */
    private boolean gameIsEnded=false;
    /**
     * We are in waiting for the other player.
     */
    private boolean waiting=false;
    /**
     * Which player has the chair.
     */
    private String chair;
    /**
     * The chat controller.
     */
    private ChatController chatController = new ChatController();


    /**
     * Initialize the ClientState with the lock.
     *
     * @param viewLock the object to lock on.
     * @throws RemoteException In case of error during the rmi connection process
     */
    public ClientState(Object viewLock) throws RemoteException {
        super();
        this.viewLock = viewLock;
    }

    /**
     * Initialize the ClientState with the lock and
     * with the username of the client.
     *
     * @param s the username of the client.
     * @param o the object to lock on.
     * @throws RemoteException In case of error during the rmi connection process
     */
    public ClientState(String s, Object o) throws RemoteException {
        super();
        myUsername=s;
        viewLock=o;
    }



    /**
     * <strong>Getter</strong> -> Returns the username of the client.
     *
     * @return the username of the client.
     */
    public String getMyUsername() {
        synchronized (viewLock){
            return myUsername;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the ArrayList with all players' username.
     *
     * @return the <i>ArrayList</i> with all players' username.
     */
    public ArrayList<String> getAllUsername() {
        synchronized (viewLock){
            return allUsername;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the HashMap that represent the personal objective.
     *
     * @return the <i>HashMap</i> that represent the personal objective.
     */
    public HashMap<Point, Tiles> getMyPersonalObjective() {
        synchronized (viewLock){
            return myPersonalObjective;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the Arraylist that contains the number of common objective.
     *
     * @return the <i>Arraylist</i> that contains the number of common objective.
     */
    public ArrayList<Integer> getGameCommonObjective() {
        synchronized (viewLock){
            return gameCommonObjective;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the board.
     *
     * @return the board.
     */
    public Matrix getBoard() {
        synchronized (viewLock){
            return board;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the client's bookshelf.
     *
     * @return the client's bookshelf.
     */
    public Matrix getMyBookshelf() {
        synchronized (viewLock){
            return myBookshelf;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the HashMap that contains all players' bookshelf.
     *
     * @return the <i>HashMap</i> that contains all players' bookshelf.
     */
    public HashMap<String, Matrix> getAllBookshelf() {
        synchronized (viewLock){
            return allBookshelf;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the client's points (public + private points).
     *
     * @return the client's points (public + private points).
     */
    public Integer getMyPoints() {
        synchronized (viewLock) {
            return myPoints;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the HashMap that contains all players' point (only the public one).
     *
     * @return the <i>HashMap</i> that contains all players' point (only the public one).
     */
    public HashMap<String, Integer> getAllPublicPoints() {
        synchronized (viewLock) {
            return allPublicPoints;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the tiles selected from the client.
     *
     * @return the tiles selected from the client.
     */
    public ArrayList<Tile> getSelectedTiles() {
        synchronized (viewLock) {
            return selectedTiles;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the current player.
     *
     * @return the current player.
     */
    public String getCurrentPlayer() {
        synchronized (viewLock) {
            return currentPlayer;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the next player.
     *
     * @return the next player.
     */
    public String getNextPlayer() {
        synchronized (viewLock) {
            return nextPlayer;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the winner player.
     *
     * @return the winner player.
     */
    public String getWinnerPlayer() {
        synchronized (viewLock) {
            return winnerPlayer;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns if game is ended.
     *
     * @return <i>true </i>if game is ended, else <i>false</i>.
     */
    public boolean isGameIsEnded() {
        synchronized (viewLock) {
            return !gameIsEnded;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns if game has started.
     *
     * @return <i>true</i> if game has started, else <i>false</i>.
     */
    public boolean gameHasStarted () {
        synchronized (viewLock){
            return !gameHasStarted;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the player that has the chair.
     *
     * @return the player that has the chair.
     */
    public String getChair() {
        synchronized (viewLock) {
            return chair;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns if we are to be waiting for another player.
     *
     * @return <i>true</i> if we are to be waiting for another player, else <i>false</i>.
     */
    public boolean isWaiting() {
        return waiting;
    }

    /**
     * <strong>Getter</strong> -> Returns the chat controller.
     *
     * @return the chat controller.
     */
    public ChatController getChatController() {
        return chatController;
    }

    /**
     * <strong>Getter</strong> -> Returns the number of client's personal objective.
     *
     * @return the number of client's personal objective.
     */
    public int getMyPersonalObjectiveInt() {
        synchronized (viewLock){
            return myPersonalObjectiveInt;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the ArrayList that contains the numbers of game's common objectives.
     *
     * @return the <i>ArrayList</i> that contains the numbers of game's common objectives.
     */
    public ArrayList<Integer> getCommonObjectivePoints() {
        synchronized (viewLock){
            return commonObjectivePoints;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns true if the player won because all other players disconnected.
     *
     * @return <i>true</i> if the player won because all other players disconnected, else <i>false</i>.
     */
    public boolean isDisconnectionWinner() {
        synchronized (viewLock) {
            return disconnectionWinner;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the old common objectives points.
     *
     * @return the old common objectives points.
     */
    public ArrayList<Integer> getOldCommonObjectivePoints() {
        synchronized (viewLock) {
            return oldCommonObjectivePoints;
        }
    }

    /**
     * <strong>Getter</strong> -> Returns all players that complete the 1st common objective.
     *
     * @return the <i>HashMap</i> of all players that complete the 1st common objective.
     */
    public HashMap<String, Integer> getCommonMap1() {
        return commonMap1;
    }

    /**
     * <strong>Getter</strong> -> Returns all players that complete the 2nd common objective.
     *
     * @return the <i>HashMap</i> of all players that complete the 2nd common objective.
     */
    public HashMap<String, Integer> getCommonMap2() {
        return commonMap2;
    }

    /**
     * Fires a notification of a disconnection or reconnection.
     * @param username The username of the player.
     * @param type String saying if the user disconnected or reconnected.
     */
    public void notify(String username, String type) {
        notifier.firePropertyChange(new PropertyChangeEvent(
                username, "notification", null, type));
    }



    /**
     * <strong>Setter</strong> -> Sets the username of the client.
     *
     * @param myUsername the username of the client.
     */
    public void setMyUsername(String myUsername) {
        synchronized (viewLock){
            this.myUsername = myUsername.toLowerCase();
        }
    }

    /**
     * <strong>Setter</strong> -> Sets the ArrayList with all players' username.
     *
     * @param allUsername the ArrayList with all players' username.
     */
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

    /**
     * <strong>Setter</strong> -> Sets the HashMap that represent the personal objective.
     *
     * @param myPersonalObjective the HashMap that represent the personal objective.
     */
    public void setMyPersonalObjective(HashMap<Point, Tiles> myPersonalObjective) {
        synchronized (viewLock){
            this.myPersonalObjective = myPersonalObjective;
        }
    }

    /**
     * <strong>Setter</strong> -> Sets the Arraylist that contains the number of common objective.
     *
     * @param gameCommonObjective the Arraylist that contains the number of common objective.
     */
    public void setGameCommonObjective(ArrayList<Integer> gameCommonObjective) {
        synchronized (viewLock){
            this.gameCommonObjective = gameCommonObjective;
        }
    }

    /**
     * <strong>Setter</strong> -> Sets the board.
     *
     * @param board the game's board.
     */
    public void setBoard(Matrix board) {
        Matrix old;
        synchronized (viewLock){
            old = this.board;
            this.board = board;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"board",old,this.board));
    }

    /**
     * <strong>Setter</strong> -> Sets the client's points (public + privet points).
     *
     * @param myPoints the client's points (public + privet points).
     */
    public void setMyPoints(Integer myPoints) {
        synchronized (viewLock) {
            this.myPoints = myPoints;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"privatePoints",null,myPoints));
    }

    /**
     * <strong>Setter</strong> -> Sets the tiles selected from the client.
     *
     * @param selectedTiles the tiles selected from the client.
     */
    public void setSelectedTiles(ArrayList<Tile> selectedTiles) {
        synchronized (viewLock) {
            this.selectedTiles = selectedTiles;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"selectedTiles", null,selectedTiles));
    }

    /**
     * <strong>Setter</strong> -> Sets the current player.
     *
     * @param currentPlayer the current player.
     */
    public void setCurrentPlayer(String currentPlayer) {
        synchronized (viewLock) {
            this.currentPlayer = currentPlayer;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"currPlayer",null,currentPlayer));
    }

    /**
     * <strong>Setter</strong> -> Sets the next player.
     *
     * @param nextPlayer the next player.
     */
    public void setNextPlayer(String nextPlayer) {
        synchronized (viewLock) {
            this.nextPlayer = nextPlayer;
        }
    }

    /**
     * <strong>Setter</strong> -> Sets the winner player.
     *
     * @param winnerPlayer the winner player.
     */
    public void setWinnerPlayer(String winnerPlayer) {
        synchronized (viewLock) {
            this.winnerPlayer = winnerPlayer;
        }
    }

    /**
     * <strong>Setter</strong> -> Sets if game is ended.
     *
     * @param gameIsEnded true if game is ended, else false.
     */
    public void setGameIsEnded(boolean gameIsEnded) {
        synchronized (viewLock) {
            this.gameIsEnded = gameIsEnded;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"end",null,true));
    }

    /**
     * <strong>Setter</strong> -> Sets if game has started.
     *
     * @param gameHasStarted true if game has started, else false.
     */
    public void setGameHasStarted (boolean gameHasStarted) {
        synchronized (viewLock) {
            this.gameHasStarted = gameHasStarted;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"start",null,true));
    }

    /**
     * <strong>Setter</strong> -> Sets the player that has the chair.
     *
     * @param chair the player that has the chair.
     */
    public void setChair(String chair) {
        synchronized (viewLock){
            this.chair = chair;
        }
    }

    /**
     * <strong>Setter</strong> -> Sets if we have to be waiting for another player.
     *
     * @param waiting true if we have to be waiting for another player, else false.
     */
    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    /**
     * <strong>Setter</strong> -> Sets the number of client's personal objective.
     *
     * @param myPersonalObjectiveInt the number of client's personal objective.
     */
    public void setMyPersonalObjectiveInt(int myPersonalObjectiveInt) {
        synchronized (viewLock){
            this.myPersonalObjectiveInt = myPersonalObjectiveInt;
        }
    }

    /**
     * <strong>Setter</strong> -> Sets the ArrayList that contains the numbers of game's common objectives.
     *
     * @param commonObjectivePoints the ArrayList that contains the numbers of game's common objectives.
     */
    public void setCommonObjectivePoints(ArrayList<Integer> commonObjectivePoints) {
        synchronized (viewLock) {
            this.commonObjectivePoints = commonObjectivePoints;
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this.commonObjectivePoints,"commonObjPoints",null,this.commonObjectivePoints));
    }

    /**
     * <strong>Setter</strong> -> Sets true if the player won because all other players disconnected.
     *
     * @param disconnectionWinner true if the player won because all other players disconnected.
     */
    public void setDisconnectionWinner(boolean disconnectionWinner) {
        synchronized (viewLock) {
            this.disconnectionWinner = disconnectionWinner;
        }
    }

    /**
     * <strong>Setter</strong> -> Sets the old common objectives points.
     *
     * @param oldCommonObjectivePoints the old common objectives points.
     */
    public void setOldCommonObjectivePoints(ArrayList<Integer> oldCommonObjectivePoints) {
        synchronized (viewLock) {
            this.oldCommonObjectivePoints = oldCommonObjectivePoints;
        }
    }

    /**
     * <strong>Setter</strong> -> Update the HashMap that contains all players' bookshelf.
     *
     * @param username the username of interest.
     * @param bookshelf the bookshelf of the username of interest.
     */
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

    /**
     * <strong>Setter</strong> -> Update the HashMap that contains all players' points.
     *
     * @param username the username of interest.
     * @param point the points of the username of interest.
     */
    public void setAllPublicPoints(String username, Integer point){
        synchronized (viewLock) {
            allPublicPoints.put(username, point);
        }
        notifier.firePropertyChange(
                new PropertyChangeEvent(this,"publicPoints",username,point));
    }

    /**
     * <strong>Setter</strong> -> Set the two HashMap that contains all players that completed the common objective.
     *
     * @param list The list of the maps of the player who completed the common objectives.
     */
    public void setCommonObjMaps(ArrayList<HashMap<String, Integer>> list){
        synchronized (viewLock) {
            commonMap1 = list.get(0);
            commonMap2 = list.get(1);
        }
    }



    /**
     * Adds a `PropertyChangeListener` object to the list of listeners.
     * The listener will be notified when property changes occur.
     *
     * @param listener the `PropertyChangeListener` to add.
     */
    public void addListener(PropertyChangeListener listener){
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * Adds a `PropertyChangeListener` object to the list of listeners for the specified property.
     * The listener will be notified when property changes occur.
     *
     * @param listener the `PropertyChangeListener` to add.
     * @param property the name of the property to listen for changes.
     */
    public void addListener(PropertyChangeListener listener,String property){
        notifier.addPropertyChangeListener(property,listener);
    }


    /**
     * Method used to check if the server is still running.
     * <p>Only used with RMI connection.</p>
     *
     * @return <i>true</i>.
     * @throws RemoteException When the communication with the server fails.
     */
    @Override
    public boolean pingPong() throws RemoteException {
        return true;
    }

    /**
     * Method that analyzes message to see if is intended for the public
     * or private chat and calls the proper method via notifier.
     *
     * @param message ChatMessage containing the conversation for a Chat.
     */
    @Override
    public void newMessageHandler (ChatMessage message) {
        if (message.getReceivingUsername() == null)
            notifier.firePropertyChange(new PropertyChangeEvent(this,"publicChat",null, message));

        else
            notifier.firePropertyChange(new PropertyChangeEvent(this,"privateChat",null, message));
    }



    /**
     * Method to restore all chats via backup.
     *
     * @param backup        ChatController containing the Server's backup for the chats.
     */
    public void reloadChats (ChatController backup) { this.chatController = backup; }



    /**
     * Method to check if is possible to add the number of selected tiles in a column.
     * @param numTilesSelected number of tiles selected.
     * @return the <i>ArrayList</i> with the usable columns.
     */
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

package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.Chat;
import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientState extends UnicastRemoteObject implements ClientStateRemoteInterface{
    private PropertyChangeSupport notifier = new PropertyChangeSupport(this);
    private Object viewLock;
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
    private boolean gameHasStarted=false;
    private boolean gameIsEnded=false;
    private boolean waiting=false;
    private String chair;
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
            System.out.println("board in state");
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
        if (message.getReceivingUsername() == null) newPublicMessage(message);
        else newPrivateMessage(message);
    }

    public void newPublicMessage(ChatMessage message) {
        if (chatController.getPublicChat().ChatIsEnable()) {
            if (!message.getUsername().equals(myUsername)) System.out.println(message.getUsername() + ": " + message.getMessage());
        }
        else {
            chatController.getPublicChat().updateUnReadMessages();
            if (chatController.getPublicChat().getUnReadMessages() == 1) System.out.println("*One new message from the PUBLIC CHAT*");
            else System.out.println("*" + chatController.getPublicChat().getUnReadMessages() + " new messages from the PUBLIC CHAT*");
        }

        chatController.getPublicChat().addMessage(message);

    }

    public void newPrivateMessage(ChatMessage message) {
        String forwardingPlayer = message.getUsername();
        String conversation = message.getMessage();
        String receivingPlayer = message.getReceivingUsername();

        if (forwardingPlayer.equals(myUsername)) {
            chatController.getPrivateChat(receivingPlayer).addMessage(message);
        }
        else {
            if (chatController.getPrivateChat(forwardingPlayer).ChatIsEnable()) System.out.println(forwardingPlayer + ": " + conversation);
            else {
                chatController.getPrivateChat(forwardingPlayer).updateUnReadMessages();

                if (chatController.getPrivateChat(forwardingPlayer).getUnReadMessages() == 1)
                    System.out.println("*One new message from the PRIVATE CHAT with " + forwardingPlayer + "*");
                else
                    System.out.println("*" + chatController.getPrivateChat(forwardingPlayer).getUnReadMessages() + " new messages from the PRIVATE CHAT with " + forwardingPlayer + "*");
            }
            chatController.getPrivateChat(forwardingPlayer).addMessage(message);
        }

    }

    public ChatController getChatController() {
        return chatController;
    }

}

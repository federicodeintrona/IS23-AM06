package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface of ClientState.
 */
public interface ClientStateRemoteInterface extends Remote {

    /**
     * <strong>Setter</strong> -> Sets the username of the client.
     *
     * @param myUsername the username of the client.
     */
    void setMyUsername(String myUsername) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Update the HashMap that contains all players' bookshelf.
     *
     * @param username the username of interest.
     * @param bookshelf the bookshelf of the username of interest.
     */
    void setAllBookshelf(String username, Matrix bookshelf) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the board.
     *
     * @param board the game's board.
     */
    void setBoard(Matrix board) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the ArrayList with all players' username.
     *
     * @param allUsername the ArrayList with all players' username.
     */
    void setAllUsername(ArrayList<String> allUsername) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the HashMap that represent the personal objective.
     *
     * @param myPersonalObjective the HashMap that represent the personal objective.
     */
    void setMyPersonalObjective(HashMap<Point, Tiles> myPersonalObjective) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the Arraylist that contains the number of common objective.
     *
     * @param gameCommonObjective the Arraylist that contains the number of common objective.
     */
    void setGameCommonObjective(ArrayList<Integer> gameCommonObjective) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the client's points (public + privet points).
     *
     * @param myPoints the client's points (public + privet points).
     */
    void setMyPoints(Integer myPoints) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Update the HashMap that contains all players' points.
     *
     * @param username the username of interest.
     * @param point the points of the username of interest.
     */
    void setAllPublicPoints(String username, Integer point) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the tiles selected from the client.
     *
     * @param selectedTiles the tiles selected from the client.
     */
    void setSelectedTiles(ArrayList<Tiles> selectedTiles) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the next player.
     *
     * @param nextPlayer the next player.
     */
    void setNextPlayer(String nextPlayer) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the winner player.
     *
     * @param winnerPlayer the winner player.
     */
    void setWinnerPlayer(String winnerPlayer) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets if game is ended.
     *
     * @param gameIsEnded game is ended.
     */
    void setGameIsEnded(boolean gameIsEnded) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets if game has started.
     *
     * @param gameHasStarted game has started.
     */
    void setGameHasStarted(boolean gameHasStarted) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the current player.
     *
     * @param currentPlayer the current player.
     */
    void setCurrentPlayer(String currentPlayer) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the number of client's personal objective.
     *
     * @param myPersonalObjectiveInt the number of client's personal objective.
     */
    void setMyPersonalObjectiveInt(int myPersonalObjectiveInt) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the ArrayList that contains the numbers of game's common objectives.
     *
     * @param commonObjectivePoints the ArrayList that contains the numbers of game's common objectives.
     */
    void setCommonObjectivePoints(ArrayList<Integer> commonObjectivePoints) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets true if the player won because all other players disconnected.
     *
     * @param disconnectionWinner true if the player won because all other players disconnected.
     */
    void setDisconnectionWinner(boolean disconnectionWinner) throws RemoteException;

    /**
     * Method used to check if the server is still running.
     * <p>Only used with RMI connection.</p>.
     *
     * @return true.
     * @throws RemoteException When the communication with the server fails.
     */
    boolean pingPong() throws RemoteException;

    /**
     * Method that analyzes @message to see if is intended for the public
     * or private chat and calls the proper method via notifier.
     *
     * @param message ChatMessage containing the conversation for a Chat.
     */
    void newMessageHandler (ChatMessage message) throws RemoteException;

    /**
     * Method to restore all chats via @backup.
     *
     * @param backup        ChatController containing the Server's backup for the chats.
     */
    void reloadChats (ChatController backup) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the player that has the chair.
     *
     * @param chair the player that has the chair.
     */
    void setChair(String chair) throws RemoteException;

    /**
     * <strong>Setter</strong> -> Sets the old common objectives points.
     *
     * @param oldCommonObjectivePoints the old common objectives points.
     */
    void setOldCommonObjectivePoints(ArrayList<Integer> oldCommonObjectivePoints) throws RemoteException;
}

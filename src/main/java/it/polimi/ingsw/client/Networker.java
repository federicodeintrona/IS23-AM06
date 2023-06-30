package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.*;

/**
 * Interface used to manage connection.
 */
public interface Networker {
    /**
     * Method to initialize connection.
     * @return <i>true</i> if everything goes well, <i>false</i> in other cases.
     */
    boolean initializeConnection ();

    /**
     * Method to send to server the username of the client.
     * @param username username of the client.
     */
    void firstConnection (Message username);

    /**
     * Method to send to server the number of player for the game created.
     * @param numberOfPlayers number of player for the game created.
     */
    void numberOfPlayersSelection(Message numberOfPlayers);

    /**
     * Method to send to server tiles selected from board.
     * @param tiles tiles selected from baord.
     */
    void removeTilesFromBoard(Message tiles);

    /**
     * Method to send to server the order of the tile selected.
     * @param ints order of the tile selected.
     */
    void switchTilesOrder(Message ints);

    /**
     * Method to send to server the column chosen by the player.
     * @param column column chosen by the player.
     */
    void addTilesToBookshelf (Message column);

    /**
     * <strong>Setter</strong> -> Sets the user interface.
     * @param view user interface.
     */
    void setView(View view);

    /**
     * Method to send to server chat message.
     * @param message chat message.
     */
    void chat (Message message);

    /**
     * <strong>Setter</strong> -> Sets the server IP.
     * @param serverIP server IP.
     */
    void setServerIP(String serverIP);

    /**
     * Method to close all thread and system and send to server disconnection message.
     * @param closing disconnection message.
     */
    void closeProgram(Message closing);

    /**
     * Stops the countdown timer and the ping pong
     */
    void stopTimer();
}

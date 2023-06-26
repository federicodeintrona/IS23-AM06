package it.polimi.ingsw.utils.Messages;

/**
 * Enumeration which contains all kind of message types.
 */
public enum MessageTypes {

    /**
     * Message which contains the client's username.
     */
    USERNAME,
    /**
     * Message to create a new lobby.
     */
    NEW_LOBBY,
    /**
     * Message to put client in waiting for the other player.
     */
    WAITING_FOR_PLAYERS,
    /**
     * Message which contains the number of player selected.
     */
    NUM_OF_PLAYERS,
    /**
     * Message that contains the tiles removed from the game's board.
     */
    REMOVE_FROM_BOARD,
    /**
     * Message that contains the order of tiles removed from the game's board.
     */
    SWITCH_PLACE,
    /**
     * Message that contains the number of selected column where puts the tiles removed from the board.
     */
    ADD_TO_BOOKSHELF,
    /**
     * Message to confirm that everything went fine.
     */
    OK,
    /**
     * Message used for chat messages.
     */
    CHAT,
    /**
     * Message to reconnect to an already started game.
     */
    RECONNECT,
    /**
     * Message to disconnect from the server.
     */
    DISCONNECT,
    /**
     * Message created in case of an error occurs.
     */
    ERROR,
    /**
     * Message used for update what the User Interface shown.
     */
    VIEW,
    /**
     * Message to verify that a client is connected.
     */
    PING,
    /**
     * Message to confirm that a client is connected.
     */
    PONG
}

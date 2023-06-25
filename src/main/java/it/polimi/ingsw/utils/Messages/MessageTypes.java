package it.polimi.ingsw.utils.Messages;

/**
 * Enumeration which contains all kind of message types
 */
public enum MessageTypes {
    USERNAME, //invio username
    NEW_LOBBY,
    WAITING_FOR_PLAYERS,
    NUM_OF_PLAYERS,
    REMOVE_FROM_BOARD, //rimuove dalla board
    SWITCH_PLACE, //scambia posizioni tessere
    ADD_TO_BOOKSHELF, //colonna in cui inserire tiles
    OK,
    CHAT,
    RECONNECT,
    DISCONNECT,
    ERROR,
    VIEW,
    PING,
    PONG
}

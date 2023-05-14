package it.polimi.ingsw.utils.Messages;

public enum MessageTypes {
    USERNAME, //invio username
    NEW_LOBBY,
    WAITING_FOR_PLAYERS,
    NUM_OF_PLAYERS,
    REMOVE_FROM_BOARD, //rimuove dall board
    SWITCH_PLACE, //scambia posizioni tessere
    ADD_TO_BOOKSHELF, //colonna in cui inserire tiles
    OK,
    CHAT,
    ROLLBACK, //torna all mossa precedente
    DISCONNECT,
    ERROR,
    VIEW,
    END_GAME;
}

package it.polimi.ingsw.server;

public enum GameState {
    CONNECTING_TO_SERVER,
    WAITING_FOR_PLAYERS,
    NOT_YOUR_TURN,
    CHOOSING_TILES,
    CHOOSING_ORDER,
    CHOOSING_COLUMN,
    ENDING;
}

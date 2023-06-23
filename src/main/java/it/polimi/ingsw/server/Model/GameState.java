package it.polimi.ingsw.server.Model;

/**
 * Enumeration which contains states of the state machine
 */
public enum GameState {
    STARTING,
    CHOOSING_TILES,
    CHOOSING_ORDER,
    CHOOSING_COLUMN,
    STOPPED,
    ENDING
}

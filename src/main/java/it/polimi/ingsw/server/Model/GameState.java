package it.polimi.ingsw.server.Model;

/**
 * Enumeration which contains states of the state machine.
 */
public enum GameState {

    /**
     * The game is starting.
     */
    STARTING,
    /**
     * The state of the game when the player chooses which tiles to remove from board.
     */
    CHOOSING_TILES,
    /**
     * The state of the game when the player chooses the order of selected tiles.
     */
    CHOOSING_ORDER,
    /**
     * The state of the game when the player chooses in which column put the selected tiles.
     */
    CHOOSING_COLUMN,
    /**
     * The game is stopped because remains one player in the game.
     */
    STOPPED,
    /**
     * The game is ending.
     */
    ENDING
}

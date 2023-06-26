package it.polimi.ingsw.client.View.GUI.Scene;

/**
 * Enumeration of the game states.
 * <ul>
 *     <li>Remove state;</li>
 *     <li>switch state;</li>
 *     <li>add state.</li>
 * </ul>
 */
public enum State {

    /**
     * The state of the game when the player chooses which tiles to remove from board.
     */
    REMOVE,
    /**
     * The state of the game when the player chooses the order of selected tiles.
     */
    SWITCH,
    /**
     * The state of the game when the player chooses in which column put the selected tiles.
     */
    ADD

}

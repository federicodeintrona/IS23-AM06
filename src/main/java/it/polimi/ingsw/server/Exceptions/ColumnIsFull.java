package it.polimi.ingsw.server.Exceptions;

/**
 * Exception thrown when a player wants to add a tile to his bookshelf but the chosen column doesn't have enough space left.
 */
public class ColumnIsFull extends MoveNotPossible{

    /**
     * Default constructor.
     */
    public ColumnIsFull() {
    }
}

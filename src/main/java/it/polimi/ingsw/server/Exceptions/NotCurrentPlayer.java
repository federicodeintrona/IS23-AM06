package it.polimi.ingsw.server.Exceptions;

/**
 * Exception thrown when a player tries to make a move when it is not his turn.
 */
public class NotCurrentPlayer extends MoveNotPossible{
    /**
     * Default constructor.
     */
    public NotCurrentPlayer() {
    }
}

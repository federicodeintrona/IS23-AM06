package it.polimi.ingsw.server.Exceptions;

/**
 * Exception thrown when a lobby does not exist. It should never be thrown if the queue is made properly.
 */
public class LobbyNotExists extends Exception{

    /**
     * Default constructor.
     */
    public LobbyNotExists() {
    }
}

package it.polimi.ingsw.client.View;

import it.polimi.ingsw.utils.Messages.Message;

import java.beans.PropertyChangeListener;

/**
 * Interface of common things in User Interface - (CLI and GUI).
 * <ul>
 *     <li>Received message from the server;</li>
 *     <li>closing the User Interface.</li>
 * </ul>
 */
public interface View extends PropertyChangeListener {

    /**
     * Method to receive message from the server.
     *
     * @param message that received from the server.
     */
    void receivedMessage(Message message);

    /**
     * Method to close the User Interface.
     */
    void close();

}

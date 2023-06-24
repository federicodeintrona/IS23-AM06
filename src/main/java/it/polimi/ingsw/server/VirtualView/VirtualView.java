package it.polimi.ingsw.server.VirtualView;
import java.beans.PropertyChangeListener;

/**
 * <p>An interface used for virtual views</p>
 * <p>The main characteristic of this interface is that it extends the PropertyChangeListener interface;
 *    the classes which implement this interface receive notification through the property change and
 *    packages and forward the necessary information to the clients.
 * </p>
 */
public interface VirtualView extends PropertyChangeListener {

    /**
     * Method to check if the virtual view is disconnected.
     * @return True if disconnected, false otherwise.
     */
     boolean isDisconnected();

    /**
     * Method to set the connection status of the virtual view. True means it is disconnected.
     * @param disconnected True if you want the view to disconnect false otherwise.
     */
     void setDisconnected(boolean disconnected);

    /**
     * Method to receive the username of the player associated to the virtual view.
     * @return The username of the player.
     */
     String getUsername();

    /**
     * Method to set the username of the player associated to the virtual view.
     * @param username The username of the player.
     */
     void setUsername(String username);
}

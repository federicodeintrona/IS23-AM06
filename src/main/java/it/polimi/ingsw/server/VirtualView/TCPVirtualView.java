package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.server.ServerClientHandler;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Messages.ViewMessage;

import java.beans.PropertyChangeEvent;


/**
 * <p>TCP implementation of the virtual view.</p>
 * <p>Used to communicate with the client using sockets.</p>
 */
public class TCPVirtualView implements VirtualView{

    private final ServerClientHandler client;
    private String username;
    private boolean disconnected = false;

    /**
     * Main constructor of the class.
     * @param username The username of the player.
     * @param handler The ServerClientHandler of the user.
     */
    public TCPVirtualView(String username,ServerClientHandler handler) {
        setUsername(username);
        this.client = handler;
    }

    /**
     * <p>Implementation of the property change method.</p>
     * <p>Receives notification from the model when the state has changed and updates the client.</p>
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //objectName           Object

        //board                Matrix
        //bookshelf w/ name    Matrix
        //currPlayer           String
        //playerNames          List<String>
        //commonObj            List<Integer>
        //publicPoints w/ name List<Integer>
        //selectedTiles        List<Tiles>
        //personalObj          HashMap
        //privatePoints        int
        //Chat                 Message??

        //w/name vuol dire che nel campo username del messaggio scrivo
        //lo username del proprietario

        if(!isDisconnected()) {

            if(evt.getPropertyName().equals("personalObjNum")) {
                System.out.println(evt.getSource());
            }


                ViewMessage viewMsg = new ViewMessage();
                viewMsg.setType(MessageTypes.VIEW);
                viewMsg.setContent(evt.getSource());
                viewMsg.setObjName((String) evt.getNewValue());
                viewMsg.setText((String) evt.getOldValue());

                client.sendMessage(viewMsg);

        }
    }


    /**
     * <strong>Getter</strong> -> Method to check if the virtual view is disconnected.
     * @return True if disconnected, false otherwise.
     */
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * <strong>Setter</strong> -> Method to set the connection status of the virtual view. True means it is disconnected.
     * @param disconnected True if you want the view to disconnect false otherwise.
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
        if(disconnected) client.stopTimer();
    }

    /**
     * <strong>Getter</strong> -> Method to receive the username of the player associated to the virtual view.
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * <strong>Setter</strong> -> Method to set the username of the player associated to the virtual view.
     * @param username The username of the player.
     */
    public void setUsername(String username) {
        this.username = username;
    }


}

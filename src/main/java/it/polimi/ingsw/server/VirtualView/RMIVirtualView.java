package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Tile;
import it.polimi.ingsw.utils.Tiles;
import it.polimi.ingsw.utils.Timer.RMITimer;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * <p>RMI implementation of the virtual view.</p>
 * <p>Used to communicate with the client using RMI.</p>
 */
public class RMIVirtualView implements VirtualView{

    private ClientStateRemoteInterface clientState;
    private RMITimer timer;
    private String username;
    private boolean disconnected = false;

    private int counterino = 0;


    /**
     * Main constructor of the class.
     * @param username The username of the player.
     * @param clientState The Remote Interface used to communicate with the client.
     */
    public RMIVirtualView(String username, ClientStateRemoteInterface clientState) {
        this.setUsername(username);
        this.clientState = clientState;
    }

    /**
     * Constructor used for tests.
     * @param username The username of the player
     */
    public RMIVirtualView(String username) {
        this.setUsername(username);
    }


    /**
     * <p>Implementation of the property change method.</p>
     * <p>Receives notification from the model when the state has changed and updates the client.</p>
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(!isDisconnected()) {
            try {
                switch ((String) evt.getNewValue()) {
                    case ("playerNames") -> clientState.setAllUsername((new ArrayList<>((List<String>) evt.getSource())));
                    case ("board") -> {
                        clientState.setBoard((Matrix) evt.getSource());
                    }
                    case ("commonObj") -> {
                        clientState.setGameCommonObjective(new ArrayList<>((List<Integer>) evt.getSource()));
                    }
                    case ("commonObjPoints") -> {
                        clientState.setCommonObjectivePoints(new ArrayList<>((List<Integer>) evt.getSource()));
                    }
                    case ("commonObjCompleted") -> {
                        clientState.setCommonObjMaps(new ArrayList<>((List<HashMap<String, Integer>>) evt.getSource()));
                    }
                    case ("personalObj") -> {
                        clientState.setMyPersonalObjective((HashMap<Point, Tiles>) evt.getSource());
                    }
                    case ("personalObjNum")->{
                        clientState.setMyPersonalObjectiveInt((int) evt.getSource());
                    }
                    case ("selectedTiles") -> {
                        clientState.setSelectedTiles((ArrayList<Tile>) evt.getSource());
                    }
                    case ("bookshelf") -> {
                        clientState.setAllBookshelf((String) evt.getOldValue(), (Matrix) evt.getSource());
                    }
                    case ("publicPoints") -> {
                        clientState.setAllPublicPoints((String) evt.getOldValue(), (Integer) evt.getSource());
                    }
                    case ("privatePoints") -> {
                        clientState.setMyPoints((Integer) evt.getSource());
                    }
                    case ("currPlayer") -> {
                        clientState.setCurrentPlayer((String) evt.getSource());
                    }
                    case ("nextPlayer") -> {
                        clientState.setNextPlayer((String) evt.getSource());
                    }
                    case ("chairPlayer")->{
                        clientState.setChair((String) evt.getSource());
                    }
                    case ("winner") -> {
                        clientState.setWinnerPlayer((String) evt.getSource());
                    }
                    case ("disconnectionWinner") -> {
                        clientState.setDisconnectionWinner((boolean) evt.getSource());
                    }
                    case ("start") -> {
                        clientState.setGameHasStarted((boolean) evt.getSource());
                    }
                    case ("end") -> {
                        clientState.setGameIsEnded((boolean) evt.getSource());
                    }
                    case ("notification") -> {
                        clientState.notify((String)evt.getSource(),(String)evt.getOldValue());;
                    }
                    case ("message") -> {
                        clientState.newMessageHandler((ChatMessage) evt.getSource());
                    }
                    case ("reloadChats") -> {
                        clientState.reloadChats((ChatController) evt.getSource());
                    }
                }
            } catch (RemoteException e) {
                if(!isDisconnected()) System.out.println(getUsername()+"'s vv is not responding...");
            }

        }
    }

    /**
     * Method to stop the countdown timer used to check if a client disconnected abruptly.
     */
    public void stopTimer(){
        if(timer!=null) timer.stopTimer();
    }


    /**
     * <strong>Getter</strong> -> Method to return the remote interface.
     * @return The Remote Interface.
     */
    public ClientStateRemoteInterface getClientState() {
        return clientState;
    }


    /**
     * <strong>Setter</strong> -> Method to set the timer used in case of abrupt disconnection.
     * @param timer The RMITimer.
     */
    public void setTimer(RMITimer timer) {
        this.timer = timer;
    }


    /**
     * <strong>Getter</strong> -> Method to check if the virtual view is disconnected.
     * @return <i>true</i> if disconnected, <i>false</i> otherwise.
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
        if(disconnected) stopTimer();
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

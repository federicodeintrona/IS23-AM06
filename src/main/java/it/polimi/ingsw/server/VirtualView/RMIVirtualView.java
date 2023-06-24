package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.server.RMITimer;
import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Tile;
import it.polimi.ingsw.utils.Tiles;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RMIVirtualView implements VirtualView{

    private ClientStateRemoteInterface clientState;
    private RMITimer timer;

    private String username;
    private boolean disconnected = false;

    public RMIVirtualView(String username, ClientStateRemoteInterface clientState) {
        this.setUsername(username);
        this.clientState = clientState;
    }

    public RMIVirtualView(String username) {
        this.setUsername(username);
    }


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
                    case ("message") -> {
                        clientState.newMessageHandler((ChatMessage) evt.getSource());
                    }
                    case ("reloadChats") -> {
                        clientState.reloadChats((ChatController) evt.getSource());
                    }
                }
            } catch (RemoteException e) {
                if(!isDisconnected()) System.out.println(getUsername()+" is not responding...");
            }

        }
    }

    public ClientStateRemoteInterface getClientState() {
        return clientState;
    }

    public void setTimer(RMITimer timer) {
        this.timer = timer;
    }

    public void stopTimer(){
        if(timer!=null){
            timer.stopTimer();
        }
    }


    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
        stopTimer();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}

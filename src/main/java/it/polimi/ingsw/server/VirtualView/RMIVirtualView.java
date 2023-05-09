package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.client.ClientStateRemoteInterface;
import it.polimi.ingsw.server.Messages.*;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.Model.Tiles;
import it.polimi.ingsw.utils.Matrix;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RMIVirtualView extends VirtualView{

    private ClientStateRemoteInterface clientState;

    public RMIVirtualView(String username, ClientStateRemoteInterface clientState) {
        this.setUsername(username);
        this.clientState = clientState;
    }

    public RMIVirtualView(String username) {
        this.setUsername(username);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        try {
            switch ((String) evt.getNewValue()) {
                case ("playerNames") -> {
                    clientState.setAllUsername((new ArrayList<>((List<String>) evt.getSource())));
                }
                case ("board") -> {
                    clientState.setBoard((Matrix) evt.getSource());
                }
                case ("commonObj") -> {
                    clientState.setGameCommonObjective(new ArrayList<>((List<Integer>) evt.getSource()));
                }
                case ("personalObj") -> {
                    clientState.setMyPersonalObjective((HashMap<Point, Tiles>) evt.getSource());
                }
                case ("selectedTiles") -> {
                    clientState.setSelectedTiles((ArrayList<Point>) evt.getSource());
                }
                case ("bookshelf") -> {
                    clientState.setAllBookshelf((String)evt.getOldValue(),(Matrix) evt.getSource());
                }
                case ("publicPoints") -> {
                    clientState.setAllPublicPoints((String) evt.getOldValue(),(Integer) evt.getSource());
                }
                case ("privatePoints") -> {
                    clientState.setMyPoints((Integer) evt.getSource());
                }
                case ("currPlayer") -> {
                    clientState.setCurrentPlayer((String) evt.getSource());
                }
                case("winner")->{
                    clientState.setWinnerPlayer((String) evt.getSource());
                }
                case("start")->{
                    clientState.setGameHasStarted((boolean) evt.getSource());
                }
                case("end")->{
                    clientState.setGameIsEnded((boolean) evt.getSource());
                }

            }

        }catch (RemoteException e){
            throw  new RuntimeException();
        }

    }



}

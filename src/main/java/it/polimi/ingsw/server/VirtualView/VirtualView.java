package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.utils.Matrix;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface VirtualView extends PropertyChangeListener {

     boolean isDisconnected();
     void setDisconnected(boolean disconnected);
    String getUsername();
     void setUsername(String username);
}

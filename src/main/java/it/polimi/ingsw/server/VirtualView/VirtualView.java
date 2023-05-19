package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.utils.Matrix;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public abstract class VirtualView implements PropertyChangeListener {

    private String username;
    private boolean disconnected = false;

    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

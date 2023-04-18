package it.polimi.ingsw.server.Messages;

import it.polimi.ingsw.server.VirtualView;

public class ViewMessage extends Message{

    private VirtualView View;

    public VirtualView getView() {
        return View;
    }

    public void setView(VirtualView view) {
        View = view;
    }
}

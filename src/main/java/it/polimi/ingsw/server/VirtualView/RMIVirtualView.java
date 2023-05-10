package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.server.Messages.*;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;

public class RMIVirtualView extends VirtualView{

    private ViewMessage viewMsg;

    public RMIVirtualView(String username) {
        this.setUsername(username);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        ViewMessage viewMsg = new ViewMessage();
        viewMsg.setType(MessageTypes.VIEW);
        viewMsg.setContent(evt.getSource());
        viewMsg.setObjectName(evt.getPropertyName());
        viewMsg.setUsername((String) evt.getOldValue());




    }



}

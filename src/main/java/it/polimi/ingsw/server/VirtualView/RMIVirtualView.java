package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Messages.ViewMessage;

import java.beans.PropertyChangeEvent;

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

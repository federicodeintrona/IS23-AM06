package it.polimi.ingsw.client.View;

import it.polimi.ingsw.utils.Messages.Message;

import java.beans.PropertyChangeListener;

public interface View extends PropertyChangeListener {

    void receivedMessage(Message message);
    void close();

}

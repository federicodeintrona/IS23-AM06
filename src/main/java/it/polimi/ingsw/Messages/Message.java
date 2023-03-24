package it.polimi.ingsw.Messages;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Message {

    private Point senderID;
    private MessageTypes type;



    public MessageTypes getType() {
        return type;
    }

    public void setType(MessageTypes type) {
        this.type = type;
    }

    public void setContent(Object o){

    }
}

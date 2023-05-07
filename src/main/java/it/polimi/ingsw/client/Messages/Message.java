package it.polimi.ingsw.client.Messages;

import java.io.Serializable;

public class Message implements Serializable {

    private String username;
    private MessageTypes type;


    public MessageTypes getType() {
        return type;
    }
    public String getUsername () { return username; }

    public void setType(MessageTypes type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(Object o){}


}

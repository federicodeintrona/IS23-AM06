package it.polimi.ingsw.server.Messages;

public class Message {

    private String username;
    private MessageTypes type;

    public void setUsername(String username) {
        this.username = username;
    }

    public MessageTypes getType() {
        return type;
    }
    public String getUsername () { return username; }

    public void setType(MessageTypes type) {
        this.type = type;
    }

    public void setContent(Object o){}


}

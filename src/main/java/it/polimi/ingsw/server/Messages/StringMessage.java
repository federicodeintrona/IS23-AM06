package it.polimi.ingsw.server.Messages;

public class StringMessage extends Message{

    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}

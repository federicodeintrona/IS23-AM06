package it.polimi.ingsw.server.Model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    String username;
    String message;

    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

}

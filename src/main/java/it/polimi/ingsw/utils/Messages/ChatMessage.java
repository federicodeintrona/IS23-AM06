package it.polimi.ingsw.utils.Messages;

import java.io.Serializable;

public class ChatMessage extends Message implements Serializable {
    String message;
    String receivingUsername;

    public ChatMessage(String username, String message) {
        setUsername(username);
        this.message = message;
        receivingUsername = null;
    }

    public ChatMessage(String sendingUsername, String message, String receivingUsername) {
        setUsername(sendingUsername);
        this.message = message;
        this.receivingUsername = receivingUsername;
    }

    public String getMessage() {
        return message;
    }

    public String getReceivingUsername() {return receivingUsername;}

    public void getConversation () {
        System.out.println(getUsername() + " :" + message + " TO " + receivingUsername);
    }
}

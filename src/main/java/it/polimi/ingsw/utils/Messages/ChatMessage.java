package it.polimi.ingsw.utils.Messages;

import java.io.Serializable;

public class ChatMessage extends Message implements Serializable {
    String message;
    String receivingUsername;

    public ChatMessage(String username, String message) {
        setText(username);
        this.message = message;
        receivingUsername = null;
    }

    public ChatMessage(String sendingUsername, String message, String receivingUsername) {
        setText(sendingUsername);
        this.message = message;
        this.receivingUsername = receivingUsername;
    }

    public String getMessage() {
        return message;
    }

    public String getReceivingUsername() {return receivingUsername;}

    public void getConversation (Boolean complete) {
        System.out.println(getText() + " :" + message + " TO " + receivingUsername);
    }

    public String getConversation () {
        String conversation = getText() + ": " + message;
        return conversation;
    }

}

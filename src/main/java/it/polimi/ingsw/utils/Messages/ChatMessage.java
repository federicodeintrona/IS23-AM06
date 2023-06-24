package it.polimi.ingsw.utils.Messages;

import it.polimi.ingsw.utils.Chat;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Class for message sent by client and server used for chat</p>
 */
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

    /**
     * <strong>Getter</strong> -> Returns the content contained in the chat message
     * @return the content contained in the chat message
     */
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ChatMessage other = (ChatMessage) obj;

        return Objects.equals(getText(), other.getText())
                && Objects.equals(message, other.message)
                && Objects.equals(receivingUsername, other.receivingUsername);
    }

}

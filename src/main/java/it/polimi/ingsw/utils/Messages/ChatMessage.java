package it.polimi.ingsw.utils.Messages;

import it.polimi.ingsw.utils.Chat;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Class for message sent by client and server used for chat</p>
 */
public class ChatMessage extends Message implements Serializable {
    private final String message;
    private final String receivingUsername;

    /**
     * Initialize the chat message with the username of the sending client and the content of the message
     * @param username username of the sending client
     * @param message content of the chat message
     */
    public ChatMessage(String username, String message) {
        setText(username);
        this.message = message;
        receivingUsername = null;
    }
    /**
     * Initialize the chat message with the username of the sending and the receiving client and the content of the message
     * @param sendingUsername username of the sending client
     * @param message content of the chat message
     * @param receivingUsername username of the receiving client
     */
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

    /**
     * <strong>Getter</strong> -> Returns the receiving username in the chat message
     * @return the content contained in the chat message
     */
    public String getReceivingUsername() {return receivingUsername;}

    /*public void getConversation (Boolean complete) {
        System.out.println(getText() + " :" + message + " TO " + receivingUsername);
    }*/

    /**
     * <strong>Getter</strong> -> Returns the conversation in the chat message
     * @return the conversation in the chat message
     */
    public String getConversation () {
        return getText() + ": " + message;
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

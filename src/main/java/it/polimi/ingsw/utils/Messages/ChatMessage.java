package it.polimi.ingsw.utils.Messages;

import java.util.Objects;

/**
 * <p>Class for message sent by client and server used for chat.</p>
 */
public class ChatMessage extends Message  {

    private final String message;
    private final String receivingUsername;

    /**
     * Initialize the chat message with the username of the sending client and the content of the message.
     * @param username username of the sending client.
     * @param message content of the chat message.
     */
    public ChatMessage(String username, String message) {
        setText(username);
        this.message = message;
        receivingUsername = null;
    }
    /**
     * Initialize the chat message with the username of the sending and the receiving client and the content of the message.
     * @param sendingUsername username of the sending client.
     * @param message content of the chat message.
     * @param receivingUsername username of the receiving client.
     */
    public ChatMessage(String sendingUsername, String message, String receivingUsername) {
        setText(sendingUsername);
        this.message = message;
        this.receivingUsername = receivingUsername;
    }

    /**
     * <strong>Getter</strong> -> Returns the content contained in the chat message.
     * @return the content contained in the chat message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * <strong>Getter</strong> -> Returns the receiving username in the chat message.
     * @return the content contained in the chat message.
     */
    public String getReceivingUsername() {return receivingUsername;}

    /**
     * <strong>Getter</strong> -> Returns the conversation in the chat message.
     * @return the conversation in the chat message.
     */
    public String getConversation () {
        return getText() + ": " + message;
    }

    /**
     * Method that does Override of equals in order
     * to properly confront 2 ChatMessage-like structures.
     *
     * @param obj       ChatMessage-type Object to analyze.
     * @return      <i>true</i> or <i>false</i> depending on the result of the confrontation.
     */
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

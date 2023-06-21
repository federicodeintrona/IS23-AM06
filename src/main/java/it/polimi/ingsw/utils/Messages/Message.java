package it.polimi.ingsw.utils.Messages;

import java.io.Serializable;

/**
 * <p> Class for the all the message sent by client and server </p>
 */
public class Message implements Serializable {

    private String text;
    private MessageTypes type;

    /**
     * <strong>Setter</strong> -> Sets the content of the message
     * @param text text content of the message
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * <strong>Getter</strong> -> Sets the content of the message
     * @return text content of the message
     */
    public String getText() { return text; }

    /**
     * <strong>Setter</strong> -> Sets the type of the message
     * @param type type of the message
     */
    public void setType(MessageTypes type) {
        this.type = type;
    }

    /**
     * <strong>Getter</strong> -> Sets the content of the message
     * @return type of the message
     */
    public MessageTypes getType() {
        return type;
    }
}

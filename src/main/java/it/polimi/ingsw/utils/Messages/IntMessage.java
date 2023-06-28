package it.polimi.ingsw.utils.Messages;

/**
 * <p> Class for message sent by client and server which contains an integer number</p>
 */
public class IntMessage extends Message {

    private int num;

    /**
     * Default constructor
     */
    public IntMessage() {
    }

    /**
     * <strong>Getter</strong> -> Return the integer contained of the message
     * @return integer contained in the message
     */
    public int getNum() {
        return num;
    }

    /**
     * <strong>Setter</strong> -> Sets an integer in the message
     * @param num integer to put in the message
     */
    public void setNum(int num) {
        this.num = num;
    }
}

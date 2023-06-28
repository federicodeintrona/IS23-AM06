package it.polimi.ingsw.utils.Messages;

import java.util.ArrayList;

/**
 * <p> Class for message sent by client and server which contains an array of integer number.</p>
 */
public class IntArrayMessage extends Message{

    private ArrayList<Integer> integers;

    /**
     * Default constructor.
     */
    public IntArrayMessage() {
    }

    /**
     * <strong>Getter</strong> -> Return the array of integer contained of the message.
     * @return <i>ArrayList</i> of integer contained in the message.
     */
    public ArrayList<Integer> getIntegers () { return integers; }

    /**
     * <strong>Setter</strong> -> Sets an array of integer in the message.
     * @param integers array of integer to put in the message.
     */
    public void setIntegers(ArrayList<Integer> integers) {
        this.integers = integers;
    }
}

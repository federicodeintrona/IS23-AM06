package it.polimi.ingsw.utils.Messages;

import java.util.ArrayList;

public class IntArrayMessage extends Message{
    private ArrayList<Integer> integers;
    public ArrayList<Integer> getIntegers () { return integers; }

    public void setIntegers(ArrayList<Integer> integers) {
        this.integers = integers;
    }
}

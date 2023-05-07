package it.polimi.ingsw.server.Messages;

import java.util.ArrayList;

public class IntArrayMessage extends Message{
    private ArrayList<Integer> integers;

    public void setIntegers(ArrayList<Integer> integers) {
        this.integers = integers;
    }

    public ArrayList<Integer> getIntegers () { return integers; }

}

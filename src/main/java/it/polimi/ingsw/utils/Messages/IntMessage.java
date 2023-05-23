package it.polimi.ingsw.utils.Messages;

import java.io.Serializable;

public class IntMessage extends Message implements Serializable {

    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

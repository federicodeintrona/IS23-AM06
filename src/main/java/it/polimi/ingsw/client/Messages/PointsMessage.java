package it.polimi.ingsw.client.Messages;

import java.awt.*;
import java.util.ArrayList;

public class PointsMessage extends Message {
    private ArrayList<Point> tiles;

    public ArrayList<Point> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Point> tiles) {
        this.tiles = tiles;
    }
}

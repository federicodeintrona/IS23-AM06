package it.polimi.ingsw.utils.Messages;

import java.awt.*;
import java.util.ArrayList;
/**
 * <p> Class for message sent by client and server which contains an arraylist of point.</p>
 */
public class PointsMessage extends Message  {

    /**
     * Default constructor.
     */
    public PointsMessage() {
    }

    private ArrayList<Point> tiles;
    /**
     * <strong>Getter</strong> -> Return the array of point contained of the message.
     * @return <i>ArrayList</i> of point contained in the message.
     */
    public ArrayList<Point> getTiles() {
        return tiles;
    }
    /**
     * <strong>Setter</strong> -> Sets the array of point in the message.
     * @param tiles array of point to put in the message.
     */
    public void setTiles(ArrayList<Point> tiles) {
        this.tiles = tiles;
    }
}

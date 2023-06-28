package it.polimi.ingsw.utils;

import java.io.Serializable;
import java.util.Random;

/**
 * Class used to associate to each tile the corresponding path for the image.
 */
public class Tile implements Serializable {
    private Tiles tiles;
    private int image;

    /**
     * Initialize the tile and then associate a random path for the image.
     * @param tiles tiles used to create tile.
     */
    public Tile(Tiles tiles) {
        this.tiles = tiles;
        if(!tiles.equals(Tiles.EMPTY)&&!tiles.equals(Tiles.NOT_ALLOWED)) {
            Random random = new Random();
            image = random.nextInt(Define.NUMBEROFTILEIMAGES.getI());
        }
    }

    /**
     * <strong>Getter</strong> -> Returns tiles.
     * @return <i>Tiles</i> contained.
     */
    public Tiles getTiles() {
        return tiles;
    }

    /**
     * <strong>Setter</strong> -> Sets the corresponding tiles.
     * @param tiles tiles to set.
     */
    public void setTiles(Tiles tiles) {
        this.tiles = tiles;
        if(!tiles.equals(Tiles.EMPTY)&&!tiles.equals(Tiles.NOT_ALLOWED)) {
            Random random = new Random();
            image = random.nextInt(Define.NUMBEROFTILEIMAGES.getI());
        }
    }

    /**
     * <strong>Getter</strong> -> Returns the position in the array of the tile.
     * @return the position of the tile.
     */
    public int getImage() {
        return image;
    }
}

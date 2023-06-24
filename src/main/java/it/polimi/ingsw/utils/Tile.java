package it.polimi.ingsw.utils;

import java.util.Random;

public class Tile {
    private Tiles tiles;
    private int image;

    public Tile(Tiles tiles) {
        this.tiles = tiles;
        if(!tiles.equals(Tiles.EMPTY)&&!tiles.equals(Tiles.NOT_ALLOWED)) {
            Random random = new Random();
            image = random.nextInt(Define.NUMBEROFTILEIMAGES.getI());
        }
    }

    public Tiles getTiles() {
        return tiles;
    }

    public void setTiles(Tiles tiles) {
        this.tiles = tiles;
        if(!tiles.equals(Tiles.EMPTY)&&!tiles.equals(Tiles.NOT_ALLOWED)) {
            Random random = new Random();
            image = random.nextInt(Define.NUMBEROFTILEIMAGES.getI());
        }
    }

    public int getImage() {
        return image;
    }
}

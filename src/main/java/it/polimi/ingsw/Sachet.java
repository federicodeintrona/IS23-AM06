package it.polimi.ingsw;

import java.util.ArrayList;

public class Sachet {
    private ArrayList<Tiles> sachet;

    //ritorna tessere a caso dal sachet --> una tessera per volta
    public Tiles draw(){
        return Tiles.YELLOW;
    }
    public int remainingTiles(){
        int remTiles;
        remTiles=0;
        return remTiles;
    }
    public int remainingTilesPerColor(Tiles tiles){
        int remTiles;
        remTiles=0;
        return remTiles;
    }

    //aggiungere tile al sachet
    public void addTile(Tiles tile){

    }
}

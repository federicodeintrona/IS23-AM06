package it.polimi.ingsw;

import java.util.ArrayList;

public class Sachet {
    private ArrayList<Tiles> sachet;

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

    public void setSachet(ArrayList<Tiles> sachet) {
        this.sachet = sachet;
    }

    //aggiunge tiles nel sachet --> aggiunta ad array list
    public void addTiles(Tiles til){
        sachet.add(til);
    }

    //rimuove tiles dal sachet in posizione i
    public void removeTiles(int i){
        sachet.remove(i);
    }
}

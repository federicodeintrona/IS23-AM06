package it.polimi.ingsw;

import java.util.ArrayList;

public class Sachet {
    private ArrayList<Tiles> sachet;

    public Sachet() {
        sachet=new ArrayList<Tiles>(132);
        for (int i=0;i<22;i++){
            sachet.add(Tiles.GREEN);
        }
        for (int i=0;i<22;i++){
            sachet.add(Tiles.YELLOW);
        }
        for (int i=0;i<22;i++){
            sachet.add(Tiles.PINK );
        }
        for (int i=0;i<22;i++){
            sachet.add(Tiles.WHITE);
        }
        for (int i=0;i<22;i++){
            sachet.add(Tiles.BLUE);
        }
        for (int i=0;i<22;i++){
            sachet.add(Tiles.LIGHT_BLUE);
        }
    }

    public void draw(){

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
}

package it.polimi.ingsw.server;

import java.util.*;

public class Sachet {
    /**
     * defining number of tiles for each color
     */
    private static final int numberOfTilesPerColor=22;
    /**
     * defining number of different color
     */
    private static final int numberOfDifferentColor=6;
    /**
     * defining number of tiles in the sachet --> #Color * #TilesPerColor
     */
    private static final int maximumNumberOfTiles=numberOfDifferentColor*numberOfTilesPerColor;

    /**
     * attribute used as a real sachet
     */
    private ArrayList<Tiles> sachet;



    /**
     * Constructor --> create sachet that contains 132 tiles, 22 for each of 6 different color
     */
    public  Sachet() {
        sachet=new ArrayList<>();
        //132tiles = 6color * 22tiles
        for (int i = 0; i < numberOfTilesPerColor; i++) {
            sachet.add(Tiles.GREEN);
        }
        for (int i = 0; i < numberOfTilesPerColor; i++) {
            sachet.add(Tiles.BLUE);
        }
        for (int i = 0; i < numberOfTilesPerColor; i++) {
            sachet.add(Tiles.YELLOW);
        }
        for (int i = 0; i < numberOfTilesPerColor; i++) {
            sachet.add(Tiles.WHITE);
        }
        for (int i = 0; i < numberOfTilesPerColor; i++) {
            sachet.add(Tiles.PINK);
        }
        for (int i = 0; i < numberOfTilesPerColor; i++) {
            sachet.add(Tiles.LIGHT_BLUE);
        }
    }



    /**
     * return a randomic tile,
     * if sachet is empty (sachet.size()==0) return tiles.EMPTY
     *
     * 1. choose tile --> randomic position in sachet (ArrayList<Tiles>)
     * 2. remove tile --> remove tiles from sachet (ArrayList<Tiles>) --> removeTiles(position)
     *
     * @return Tiles    randomic tile
     */
    public Tiles draw(){
        if (sachet.size()==0){
            return Tiles.EMPTY;
        }
        Random random=new Random(); //creat Random object
        Tiles result;
        //choose random number
        int n=random.nextInt(sachet.size());
        //saving tile in the random position --> get(n)
        result=sachet.get(n);
        //remove tile from sachet
        removeTiles(n);
        return result;
   }

    /**
     * return number of tiles in the sachet --> sachet.size()
     *
     * @return int  number of tiles in the sachet
     */
    public int remainingTiles(){
        return sachet.size();
    }

    /**
     * return number of tiles in the sachet for color
     *
     * @param tiles the color of interest
     * @return int  number of tiles in the sachet for color
     */
    public int remainingTilesPerColor(Tiles tiles){
        int result=0;
        for (Tiles value : sachet) {
            if (value.equals(tiles)) {
                result++;
            }
        }
        return result;
    }

    /**
     * add tiles in the sachet --> sachet.add(til)
     *
     * 1.0. check if til can add in sachet --> size()+1<=132
     * 1.1. check if til can add in sachet --> remainingTilesPerColor(til)+1<=22
     * 2. add tile --> add(til)
     *
     * @param til   tile to add in sachet
     */
    public void addTiles(Tiles til){
        //check if til can add in sachet:
        //sachet.size()+1<=132
        if (sachet.size()+1<=maximumNumberOfTiles){
            //remainingTilesperColor(til)<=22
            if (remainingTilesPerColor(til)+1<=numberOfTilesPerColor){
                sachet.add(til);
            }
        }
    }

    /**
     * remove the tile in position i --> remove(i)
     *
     * @param i position of the tiles to remove
     */
    public void removeTiles(int i){
        sachet.remove(i);
    }

    /**
     * remove the first tile of color til --> remove(til)
     *
     * @param til the color of tile to remove
     */
    public void removeTiles(Tiles til){
        sachet.remove(til);
    }

}

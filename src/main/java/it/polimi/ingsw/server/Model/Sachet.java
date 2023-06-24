package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Tiles;

import java.util.*;

/**
 * Class used to manage the sachet.
 * <ul>
 *     <li>Draw a tile;</li>
 *     <li>number of tiles remaining;</li>
 *     <li>add a tile.</li>
 * </ul>
 */
public class Sachet {

    /**
     * Attribute used as a real sachet.
     */
    private final ArrayList<Tiles> sachet;


    /**
     * Initialize the sachet that contains 132 tiles, 22 for each of different color.
     */
    public  Sachet() {
        sachet=new ArrayList<>();
        //132tiles = 6color * 22tiles
        for (int i = 0; i < Define.NUMBEROFTILEPERCOLOR_SACHET.getI(); i++) {
            sachet.add(Tiles.GREEN);
        }
        for (int i = 0; i < Define.NUMBEROFTILEPERCOLOR_SACHET.getI(); i++) {
            sachet.add(Tiles.BLUE);
        }
        for (int i = 0; i < Define.NUMBEROFTILEPERCOLOR_SACHET.getI(); i++) {
            sachet.add(Tiles.YELLOW);
        }
        for (int i = 0; i < Define.NUMBEROFTILEPERCOLOR_SACHET.getI(); i++) {
            sachet.add(Tiles.WHITE);
        }
        for (int i = 0; i < Define.NUMBEROFTILEPERCOLOR_SACHET.getI(); i++) {
            sachet.add(Tiles.PINK);
        }
        for (int i = 0; i < Define.NUMBEROFTILEPERCOLOR_SACHET.getI(); i++) {
            sachet.add(Tiles.LIGHT_BLUE);
        }
    }



    /**
     * Method to return a random tile.
     * <p>
     *     If sachet is empty return a EMPTY tile else choose a random tile and remove it.
     * </p>
     *
     * @return a random tile.
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
     * Method to return the number of tiles in the sachet.
     *
     * @return the number of tiles in the sachet.
     */
    public int remainingTiles(){
        return sachet.size();
    }

    /**
     * Method to return the number of tiles in the sachet per color.
     *
     * @param tiles the color of interest.
     * @return the number of tiles in the sachet for color.
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
     * Method to add tile in the sachet.
     * <p>
     *     First of all check that the number of tiles already in the sachet is not equal to the maximum number of tiles.
     *     Then check that the number of tiles of that color, already in the sachet, is not equal to the maximum number of tiles for each color.
     *     If the two checks are successful, the tile is added to the sachet and the amount of tiles present are updated
     * </p>
     *
     * @param til the tile to add in sachet.
     */
    public void addTiles(Tiles til){
        //check if til can add in sachet:
        //sachet.size()+1<=132
        if (sachet.size()+1<=Define.MAXNUMBEROFTILES_SACHET.getI()){
            //remainingTilesperColor(til)<=22
            if (remainingTilesPerColor(til)+1<=Define.NUMBEROFTILEPERCOLOR_SACHET.getI()){
                sachet.add(til);
            }
        }
    }

    /**
     * Method to remove the tile in i position.
     *
     * @param i the position of the tiles to remove.
     */
    public void removeTiles(int i){
        sachet.remove(i);
    }

    /**
     * Method to remove the first tile in the sachet of the selected color.
     *
     * @param til the color of tile to remove.
     */
    public void removeTiles(Tiles til){
        sachet.remove(til);
    }

}

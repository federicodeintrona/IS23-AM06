package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class used to manage matrix which contains the tiles selected by the player
 * <ul>
 *     <li>to update</li>
 *     <li>to calculate the vicinity points </li>
 *     <li>to check conditions</li>
 * </ul>
 */
public class Bookshelf {
    private static int maxNumberOfTiles;
    private final Matrix tiles;
    private int num_of_tiles;

    /**
     * Initialize the bookshelf with the Matrix
     * and set every position to EMPTY
     */
    public Bookshelf(){
        maxNumberOfTiles=Define.NUMBEROFROWS_BOOKSHELF.getI()*Define.NUMBEROFCOLUMNS_BOOKSHELF.getI();
        tiles=new Matrix(Define.NUMBEROFROWS_BOOKSHELF.getI(),Define.NUMBEROFCOLUMNS_BOOKSHELF.getI());
        num_of_tiles=0;
        for( int i=0; i<Define.NUMBEROFROWS_BOOKSHELF.getI();i++){
            for( int j=0; j<Define.NUMBEROFCOLUMNS_BOOKSHELF.getI();j++){
                tiles.remove(i,j);
            }

        }
    }

    /**
    * <strong>Getter</strong> -> Returns the matrix of tiles
    * @return The matrix of tiles
     **/
    public Matrix getTiles(){
        return tiles;
    }

    /**
     * <strong>Getter</strong> -> Returns the number of tiles
     * @return the number of tiles
     */
    public int getNum_of_tiles () { return num_of_tiles; }

    /**
     * Method to check if the column given is full of tiles
     * @param choice number of tiles chosen
     * @param column index of the colum chosen
     * @return true if the column is full, false if it isn't
     */
    public boolean checkColumns(int choice, int column){
        return tiles.getTile(choice - 1, column).equals(Tiles.EMPTY);
    }

    /**
     * Method to check if the matrix is full
     * @return true if the matrix is full, false if it isn't
     */
    public boolean checkEndGame(){
        return num_of_tiles == maxNumberOfTiles;
    }
     /**
     * Method to check if the matrix is empty
     * @return true if the matrix is empty, false if it isn't
     */
    public boolean checkEmptyBoard(){
        return num_of_tiles == 0;
    }

    /**
     * Method to check if the matrix is empty
     * @return true if the matrix is empty, false if it isn't
     */
    public boolean isEmpty(){
        for (int j=0; j<Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++){
            if (!tiles.getTile(5, j).equals(Tiles.EMPTY)) return false;
        }
        return true;
    }

    /**
     * Method to insert the array of tiles in the matrix
     * in the column chosen
     * @param tiles tiles to put in the matrix
     * @param column index of the column for tiles
     */
    public void addTile(ArrayList<Tiles> tiles, int column){
            int pos=firstFree(column);
            if (tiles.size() == 1) {
                this.tiles.setTile(tiles.get(0), pos, column);
                num_of_tiles++;
            }
            else if (tiles.size()==2) {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos-1, column);
                num_of_tiles=num_of_tiles+2;
            }
            else {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos-1, column);
                this.tiles.setTile(tiles.get(2), pos-2, column);
                num_of_tiles=num_of_tiles+3;
            }
        }

    /**
     * <strong>Setter</strong> -> Sets the number of tiles
     * @param num number of tiles
     */
    public void setNum_of_tiles (int num) { num_of_tiles = num; }

    /**
     * Method to find the first free position of the matrix in the given column
     * @param column index of the column
     * @return first free row of the matrix
     */

    private int firstFree (int column){
        for (int i=5;i>=0;i--){
            if(tiles.getTile(i,column).equals(Tiles.EMPTY)){
                return i;
            }
        }
        return -1;
    }


    /**
     * Helping method that takes a matrix, a point inside and returns an ArrayList
     * of points, adjacent to the given one, that share the same tile color
     *
     * @param matrix    player's bookshelf
     * @param point coordinates of the analyzed tile
     * @return ArrayList    position with adjacent color
     */
    private static ArrayList<Point> getAdjacentSameColor(Matrix matrix, Point point) {
        ArrayList<Point> adjacent = new ArrayList<>();
        int x = point.x;
        int y = point.y;
        Tiles color = matrix.getTile(x, y);

        // Checking adjacent cells with the same color
        if (x > 0 && matrix.getTile(x - 1, y) == color) {
            adjacent.add(new Point(x - 1, y));
        }
        if (x < 5 && matrix.getTile(x + 1, y) == color) {
            adjacent.add(new Point(x + 1, y));
        }
        if (y > 0 && matrix.getTile(x, y - 1) == color) {
            adjacent.add(new Point(x, y - 1));
        }
        if (y < 4 && matrix.getTile(x, y + 1) == color) {
            adjacent.add(new Point(x, y + 1));
        }

        return adjacent;
    }

    /**
     * Method to associate the number of adjacent tiles to the corresponding point
     * @param n number of adjacent tiles
     * @return corresponding point
     */
    private int vicinityPointCount(int n){
        switch (n) {
            case 3 -> {
                return 2;
            }
            case 4 -> {
                return 3;
            }
            case 5 -> {
                return 5;
            }
            default -> {
                if (n >= 6) {
                    return 8;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * Method to calculate vicinity points
     * @return vicinity point
     */
    public int checkVicinityPoints(){
        int adjPoint=0;
        int sameColor;
        boolean[][] visited = new boolean[6][5];

        //for each position
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                //if position is NOT visited yet && tile!=EMPTY
                if (!visited[i][j] && !getTiles().getTile(i, j).equals(Tiles.EMPTY)) {
                    //queue initialization
                    Queue<Point> queue=new LinkedList<>();
                    queue.add(new Point(i, j));
                    visited[i][j]=true;
                    sameColor=1;
                    //as long as there is elemet in queue
                    while (!queue.isEmpty()){
                        //read head element and remove it
                        Point current=queue.poll();
                        //check all adjacent positions that have same color
                        for (Point adj : getAdjacentSameColor(getTiles(), current)){
                            //if there is NOT visited yet
                            if (!visited[adj.x][adj.y]){
                                visited[adj.x][adj.y]=true; //now position is visited
                                queue.add(adj);
                                sameColor++;
                            }
                        }
                        //sameColor has the number of adjacent tiles with the same color

                    }
                    adjPoint+=vicinityPointCount(sameColor);
                }

            }
        }

        return adjPoint;
    }


}

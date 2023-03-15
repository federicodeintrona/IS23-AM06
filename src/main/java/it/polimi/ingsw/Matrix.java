package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collection;

public class Matrix {
    /**
     *  Tile Matrix Class
     */
    private final ArrayList<ArrayList<Tiles>> board = new ArrayList<ArrayList<Tiles>>();
    private final int numRows;
    private final int numCols;


    public Matrix(int rows, int columns ){
        numCols=columns;
        numRows=rows;

        for( int i=0; i<rows;i++){
            board.add(new ArrayList<Tiles>());
            for( int j=0; j<columns;j++){
                board.get(i).add(Tiles.EMPTY);
            }

        }
    }

    /**
     * Method which set the selected tile to value 'tile'
     * @param tile  wanted value of the tile
     * @param row   the number of the row where you want to set the tile
     * @param col   the number of the column where you want to set the tile
     */
    public void add(Tiles tile ,int row, int col){

        board.get(row).set(col,tile);

    }

    /**
     * Set the selected tile to EMPTY
     * @param row   The number of the row where you want to set the tile
     * @param col   the number of the column where you want to set the tile
     */
    public void remove(int row, int col){

        board.get(row).set(col,Tiles.EMPTY);

    }
    public void setNotAllowed(int row, int col){

        board.get(row).set(col,Tiles.NOTALLOWED);

    }
    public Tiles getTile(int row, int col){
        return board.get(row).get(col);
    }

    public void print(){
        for( int i=0; i<numRows;i++){
            for( int j=0; j<numCols;j++){
                System.out.print(board.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public ArrayList<ArrayList<Tiles>> getBoard() {
        return board;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }
}

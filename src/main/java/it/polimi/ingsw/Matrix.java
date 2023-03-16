package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collection;


public class Matrix {
    /**
     *  Tile Matrix Class
     */
    private  ArrayList<ArrayList<Tiles>> board = new ArrayList<ArrayList<Tiles>>();
    private final int numRows;
    private final int numCols;


    /**
     * Constructor Method
     * @param rows Number of rows
     * @param columns Number of columns
     */
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
     * Method which set the selected tile value
     * @param tile  wanted value of the tile
     * @param row   Row number
     * @param col   Column number
     */
    public void setTile(Tiles tile ,int row, int col){

        board.get(row).set(col,tile);

    }

    /**
     * Set the selected tile to EMPTY
     * @param row   Row number
     * @param col   Column number
     */
    public void removeTile(int row, int col){

        board.get(row).set(col,Tiles.EMPTY);

    }

    /**
     * Set the selected tile to NOTALLOWED
     * @param row Row number
     * @param col Column number
     */
    public void setNotAllowed(int row, int col){

        board.get(row).set(col,Tiles.NOTALLOWED);

    }

    /**
     *  Returns the value of the selected tile
     * @param row Row number
     * @param col Column number
     * @return Value of the selected tile
     */
    public Tiles getTile(int row, int col){
        return board.get(row).get(col);
    }

    /**
     * Temporary
     * Used for debugging purposes
     */
    public void print(){
        for( int i=0; i<numRows;i++){
            for( int j=0; j<numCols;j++){
                System.out.print(board.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Returns the board
     * @return Board
     */
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

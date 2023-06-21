package it.polimi.ingsw.utils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * <p>Class that implements a matrix which contains Tiles.</p>
 * <p>It is used as a basis for the board and the bookshelves.</p>
 */
public class Matrix implements Serializable {

    private final ArrayList<ArrayList<Tiles>> board;
    private final int numRows;
    private final int numCols;

    /**
     *  <p>The constructor that ought to be used to create a matrix.</p>
     *  <p>It creats a matrix with a set number of rows and columns
     *      and initializes every cell with empty tiles</p>
     * @param rows The number of rows
     * @param columns The number of columns
     */
    public Matrix(int rows, int columns ){
        numCols=columns;
        numRows=rows;
        board = new ArrayList<>();
        for( int i=0; i<columns;i++){
            board.add(new ArrayList<>());
            for( int j=0; j<rows;j++){
                board.get(i).add(Tiles.EMPTY);
            }
        }
    }

    /**
     * <p>A constructor that creates a copy a given Matrix.</p>
     * @param mat The matrix you want to create a copy of
     */
    public Matrix (Matrix mat){
        numCols=mat.getNumCols();
        numRows=mat.getNumRows();
        this.board = mat.getBoard();
    }

    /**
     * <p><strong>Getter</strong> --> returns a selected column.</p>
     * @param col   The index of the column
     * @return    The selected column
     */
    public ArrayList<Tiles> getColumn(int col){
        return board.get(col);
    }

    /**
     * <p><strong>Setter</strong> --> set the selected tile.</p>
     * @param tile  Wanted value of the tile
     * @param row   The number of the row where you want to set the tile
     * @param col   The number of the column where you want to set the tile
     */
    public void setTile(Tiles tile, int row, int col){

        board.get(col).set(row,tile);

    }

    /**
     * <p><strong>Setter</strong> --> set the selected tile.</p>
     * @param tile Wanted value of the tile
     * @param pos The position of the tile as a point.
     */
    public void setTile(Tiles tile, Point pos) {

        board.get(pos.getLocation().y).set(pos.getLocation().x,tile);

    }

    /**
     * <p>Set the selected tile to EMPTY.</p>
     * @param row   The number of the row where you want to set the tile
     * @param col   the number of the column where you want to set the tile
     */
    public void remove(int row, int col){
        board.get(col).set(row,Tiles.EMPTY);

    }

    /**
     * <p>Set the selected tile to EMPTY.</p>
     * @param pos The position of the tile as a point.
     */
    public void remove(Point pos){
        board.get(pos.y).set(pos.x,Tiles.EMPTY);
    }

    /**
     * <p><strong>Setter</strong> --> set the selected tile to NOT_ALLOWED.</p>
     * @param row   The number of the row where you want to set the tile
     * @param col   the number of the column where you want to set the tile
     */
    public void setNotAllowed(int row, int col){
        board.get(col).set(row,Tiles.NOT_ALLOWED);
    }

    /**
     *  <p><strong>Getter</strong> --> get the selected tile.</p>
     * @param row The number of the row.
     * @param col The number of the column.
     * @return The selected tile.
     */
    public Tiles getTile(int row, int col){
        return board.get(col).get(row);
    }

    /**
     *  <p><strong>Getter</strong> --> get the selected tile.</p>
     * @param pos The position of the tile as a point
     * @return The selected tile.
     */
    public Tiles getTile(Point pos){
        return board.get(pos.y).get(pos.x);
    }


    private ArrayList<ArrayList<Tiles>> getBoard() {
        return board;
    }

    private int getNumRows() {
        return numRows;
    }


    /**
     * <p><strong>Getter</strong> --> get the number of columns.</p>
     * @return  The number of columns.
     */
    protected int getNumCols() {
        return numCols;
    }

    /**
     * <p>Method that returns true if the column is full, false otherwise</p>
     * @param x The column that is checked.
     * @return true if the column is full, false otherwise.
     */
    public boolean columnIsFull(int x){
        for (int i=0; i<numRows; i++){
            if (board.get(x).get(i).equals(Tiles.EMPTY)) return false;
        }
        return true;
    }

    /**
     * <p>Method that returns true if the row is full, false otherwise</p>
     * @param x The row that is checked.
     * @return true if the row is full, false otherwise.
     */
    public boolean rowIsFull(int x){
        for (int i=0; i<numCols; i++){
            if (board.get(i).get(x).equals(Tiles.EMPTY)) return false;
        }
        return true;
    }
}

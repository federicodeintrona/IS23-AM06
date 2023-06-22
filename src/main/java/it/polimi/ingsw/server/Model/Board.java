package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the game board
 * <ul>
 *     <li>initialization of the game board based on the number of players</li>
 *     <li>check if you need to reset the board</li>
 *     <li>reset the board</li>
 *     <li>add the tiles from the board and remove it from the sachet</li>
 *     <li>remove the tiles from the board</li>
 *     <li>define which tiles are free</li>
 *     <li>define which tiles are adjacent with each other</li>
 * </ul>
 */
public class Board {

    /**
     * Attribute used as a real board
     */
    private final Matrix gamesBoard=new Matrix(Define.NUMBEROFROWS_BOARD.getI(), Define.NUMBEROFCOLUMNS_BOARD.getI());
    /**
     * Attribute for saving the number of player that playing on the gamesBoard
     */
    private final int numberOfPlayer;
    /**
     * Attribute for associate the gamesBoard to its boardSachet
     */
    private Sachet boardSachet;



    /**
     * Initialize numberOfPlayer and create Board based on number of player
     * <p>
     * <strong>REQUIRES:</strong> numberOfPlayer to be a number between 2 and 4
     *
     * @param numberOfPlayer number of player
     */
    public Board(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
        creationBoard();

    }
    /**
     * Initialize numberOfPlayer, boardSachet and create Board based on number of player
     * <p>
     * <strong>REQUIRES:</strong> numberOfPlayer is between 2 and 4
     *
     * @param numberOfPlayer number of player
     * @param boardSachet Sachet that will fill the Board
     */
    public Board(int numberOfPlayer, Sachet boardSachet) {
        this.numberOfPlayer = numberOfPlayer;
        this.boardSachet = boardSachet;
        creationBoard();
    }



    /**
     * <strong>Getter</strong> -> Returns Matrix of gamesBoard
     *
     * @return gamesBoard
     */
    public  Matrix getGamesBoard() {
        return gamesBoard;
    }
    /**
     * <strong>Getter</strong> -> Returns numberOfPlayer
     *
     * @return number of player
     */
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }


    /**
     * Method to create the board based on number of players
     */
    private void creationBoard() {
        //assign Tiles.EMPTY to all position of matrix gamesBoard
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                gamesBoard.remove(i, j);
            }
        }
        //positions where do NOT put tiles --> assign Tiles.NOTALLOWED
        //row 0
        for (int i = 0; i < 3; i++) {
            gamesBoard.setNotAllowed(0, i);
        }
        for (int i = 5; i < 9; i++) {
            gamesBoard.setNotAllowed(0, i);
        }
        //row 1
        for (int i = 0; i < 3; i++) {
            gamesBoard.setNotAllowed(1, i);
        }
        for (int i = 6; i < 9; i++) {
            gamesBoard.setNotAllowed(1, i);
        }
        //row 2
        for (int i = 0; i < 2; i++) {
            gamesBoard.setNotAllowed(2, i);
        }
        for (int i = 7; i < 9; i++) {
            gamesBoard.setNotAllowed(2, i);
        }
        //row 3
        gamesBoard.setNotAllowed(3, 0);
        //row 4
        //row 5
        gamesBoard.setNotAllowed(5, 8);
        //riga 6
        for (int i = 0; i < 2; i++) {
            gamesBoard.setNotAllowed(6, i);
        }
        for (int i = 7; i < 9; i++) {
            gamesBoard.setNotAllowed(6, i);
        }
        //row 7
        for (int i = 0; i < 3; i++) {
            gamesBoard.setNotAllowed(7, i);
        }
        for (int i = 6; i < 9; i++) {
            gamesBoard.setNotAllowed(7, i);
        }
        //row 8
        for (int i = 0; i < 4; i++) {
            gamesBoard.setNotAllowed(8, i);
        }
        for (int i = 6; i < 9; i++) {
            gamesBoard.setNotAllowed(8, i);
        }
        //positions where do NOT put tiles based on numberOfPlayer --> assign Tiles.NOTALLOWED
        switch (numberOfPlayer) {
            case 2 -> {
                gamesBoard.setNotAllowed(0, 3);
                gamesBoard.setNotAllowed(0, 4);

                gamesBoard.setNotAllowed(1, 5);

                gamesBoard.setNotAllowed(2, 2);
                gamesBoard.setNotAllowed(2, 6);

                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(3, 8);

                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);

                gamesBoard.setNotAllowed(5, 0);
                gamesBoard.setNotAllowed(5, 7);

                gamesBoard.setNotAllowed(6, 2);
                gamesBoard.setNotAllowed(6, 6);

                gamesBoard.setNotAllowed(7, 3);

                gamesBoard.setNotAllowed(8, 4);
                gamesBoard.setNotAllowed(8, 5);
            }
            case 3 -> {
                gamesBoard.remove(0, 3);
                gamesBoard.setNotAllowed(0, 4);

                gamesBoard.setNotAllowed(1, 5);

                gamesBoard.remove(2, 2);
                gamesBoard.remove(2, 6);

                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.remove(3, 8);

                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);

                gamesBoard.remove(5, 0);
                gamesBoard.setNotAllowed(5, 7);

                gamesBoard.remove(6, 2);
                gamesBoard.remove(6, 6);

                gamesBoard.setNotAllowed(7, 3);

                gamesBoard.setNotAllowed(8, 4);
                gamesBoard.remove(8, 5);
            }
            default -> {}
        }
    }

    /**
     * Method to initialize board
     */
    public void BoardInitialization(){
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                //if tiles==EMPTY --> place randomically tiles
                if (gamesBoard.getTile(i, j).equals(Tiles.EMPTY)){
                    //in i,j position choose randomically tiles --> sachet.draw()
                    gamesBoard.setTile(boardSachet.draw(), i, j);
                }
            }
        }
    }

    /**
     * Method to return if the board needs to be reset (all remaining tiles have no adjacent ones)
     *
     * @return board needs to be reset
     */
    public boolean checkBoardReset(){
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                //for all tiles that !=NOTALLOWED && !=EMPTY
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(i, j).equals(Tiles.EMPTY)){
                    //if a tile has at leat 1 tile adjacent return false

                    //if we are NOT on the edge of board
                    if ((i!=0 && i!=Define.NUMBEROFROWS_BOARD.getI()-1) &&
                            (j!=0 && j!=Define.NUMBEROFCOLUMNS_BOARD.getI()-1)){
                        //adjacency:
                        //sx
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //dx
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //upper
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //bottom
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    //we are on upper edge
                    else if (i==0){
                        //we are on upper - left corner
                        if(j==0){
                            //bottom
                            if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                return false;
                            }
                            //dx
                            if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                return false;
                            }
                        }
                        //we are on upper - right corner
                        else if (j==Define.NUMBEROFCOLUMNS_BOARD.getI()-1) {
                            //bottom
                            if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                return false;
                            }
                            //sx
                            if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                return false;
                            }
                        }
                        //we are in the middle
                        else{
                            //sx
                            if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                return false;
                            }
                            //dx
                            if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                return false;
                            }
                            //bottom
                            if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                return false;
                            }
                        }
                    }
                    //we are on bottom edge
                    else if (i==Define.NUMBEROFROWS_BOARD.getI()-1){
                        //we are bottom - left corner
                        if (j==0){
                            //dx
                            if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                return false;
                            }
                            //upper
                            if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                return false;
                            }
                        }
                        //we are bottom - right corner
                        else if (j == Define.NUMBEROFCOLUMNS_BOARD.getI() - 1) {
                            //sx
                            if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                return false;
                            }
                            //upper
                            if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                return false;
                            }
                        }
                        //we are in the middle
                        else{
                            //sx
                            if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                return false;
                            }
                            //dx
                            if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                return false;
                            }
                            //upper
                            if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOT_ALLOWED) &&
                                    !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                return false;
                            }
                        }
                    }
                    //we are on left edge
                    else if (j==0){
                        //dx
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //upper
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //bottom
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    //we are on right edge
                    //if (j==numberOfColumn-1)
                    else {
                        //sx
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //upper
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //bottom
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOT_ALLOWED) &&
                                !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                }
            }
        }
        //if we arrived in this line of code it means that we have to reset the board
        return true;
    }

    /**
     * Method to reset the board according to ENGLISH rule
     * <p>
     *     First of all remove all the remaining tiles and put them back in sachet,
     *     then refill the board with tiles in sachet
     * </p>
     * <strong>REQUIRES:</strong> checkBoardReset() return true
     */
    public void boardResetENG(){
        //search tiles!=EMPTY && !=NOTALLOWED
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(i, j).equals(Tiles.EMPTY)) {
                    //tile's color is added in sachet --> addTiles(Tiles)
                    boardSachet.addTiles(gamesBoard.getTile(i, j));
                    //tile is removed from board --> remove(i,j)
                    gamesBoard.remove(i, j);
                }
            }
        }
        //board is filled with tiles in sachet --> BoardInitialization()
        BoardInitialization();
    }

    //TODO solo per i test
    /**
     * Method to place tile on the board and remove it from sachet
     *
     * @param tile tile to add on the board
     * @param row number of row
     * @param col number of column
     */
    public void placeTiles(Tiles tile, int row, int col){
        gamesBoard.setTile(tile, row, col);
        boardSachet.removeTiles(tile);
    }

    //TODO sti metodi sembra che non vengano mai chiamati - come rimuoviamo le tessere dalla board???
    /**
     * Method to remove the tiles in the positions shown
     * <p>
     * <strong>REQUIRES:</strong> position.size() is between 1 and 3
     *
     * @param position positions where to remove tiles
     */
    public void remove(List<Point> position){
        //for each position shown remove tiles from the gamesBoard
        for (Point point : position) {
            remove(point);
        }
    }
    /**
     * Method to remove tile in the position shown
     * <p>
     * <strong>REQUIRES:</strong> position is correct
     *
     * @param position position where to remove tile
     */
    public void remove(Point position){
        gamesBoard.remove(position.x, position.y);
    }

    /**
     * Method to return whether the tile at the given position is free (does the tile have at least 1 free side?)
     * <p>
     * <strong>REQUIRES:</strong> that position is correct
     *
     * @param position position of tile to check
     * @return true if the tiles are true, false if they aren't
     */
    public boolean checkFreeTiles(Point position){
        ArrayList<Point> list=adjacentTiles(position);
        return list.size() != 4;
    }

    /**
     * Method to return all the position of free tiles (does the tile have at least 1 free side?)
     *
     * @return position of free tiles
     */
    public ArrayList<Point> freeTiles(){
        ArrayList<Point> result=new ArrayList<>();
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(i, j).equals(Tiles.EMPTY) &
                                checkFreeTiles(new Point(i, j))){
                    result.add(new Point(i, j));
                }
            }
        }
        return result;
    }

    /**
     * Method to return if the tiles in the List are adjacent.
     * <p>
     *     Tiles are adjacent if and only if they have one side in common.
     *     if the size of the list is equal to 1 it returns FALSE because the tile is single.
     * </p>
     * <p><strong>REQUIRES:</strong> there isn't any equal position in the list </p>
     * <p><strong>REQUIRES:</strong> all position in the list are correct</p>
     *
     * @param position the position to check
     * @return true if the tiles are the adjacent, false if they aren't
     */
    public static boolean checkAdjacentTiles(List<Point> position){
        if (position.size()==1){
            return false;
        }
        //same row or same column
        Point p=new Point();
        Point p1=new Point();
        if (!checkSameRow(position) && !checkSameColumn(position)){
            //tiles are NOT adjacent
            return false;
        }
        //tiles are on the same row
        if (checkSameRow(position)){
            for (int i = 0; i < position.size(); i++) {
                //we are on x-th row first column
                if (position.get(i).y==0){
                    p.x=position.get(i).x;
                    p.y=position.get(i).y+1;
                    if (!position.contains(p)){
                        //tiles are NOT adjacent
                        return false;
                    }
                }
                //we are on x-th row last column
                else if (position.get(i).y==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                    p.x=position.get(i).x;
                    p.y=position.get(i).y-1;
                    if (!position.contains(p)){
                        //tiles are NOT adjacent
                        return false;
                    }
                }
                //we are on x-th row / NO limit cases
                else {
                    p.x=position.get(i).x;
                    p.y=position.get(i).y-1;
                    p1.x=position.get(i).x;
                    p1.y=position.get(i).y+1;
                    if (!position.contains(p) && !position.contains(p1)){
                        //tiles are NOT adjacent
                        return false;
                    }
                }
            }
        }
        //tiles are on the same column
        else if (checkSameColumn(position)){
            for (int i = 0; i < position.size(); i++) {
                //we are on y-th column first row
                if (position.get(i).x==0){
                    p.x=position.get(i).x+1;
                    p.y=position.get(i).y;
                    if (!position.contains(p)){
                        //tiles are NOT adjacent
                        return false;
                    }
                }
                //we are on y-th column last row
                else if (position.get(i).x==Define.NUMBEROFROWS_BOARD.getI()-1){
                    p.x=position.get(i).x-1;
                    p.y=position.get(i).y;
                    if (!position.contains(p)){
                        //tiles are NOT adjacent
                        return false;
                    }
                }
                //we are on y-th column / NO limit cases
                else {
                    p.x=position.get(i).x-1;
                    p.y=position.get(i).y;
                    p1.x=position.get(i).x+1;
                    p1.y=position.get(i).y;
                    if (!position.contains(p) && !position.contains(p1)){
                        //tiles are NOT adjacent
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to return the positions of adjacent tiles to the incoming one
     * <p>
     * <strong>REQUIRES:</strong> tile position is correct
     *
     * @param tile the position of which I want to know the adjacent tile
     * @return positions of tiles adjacent to the incoming one
     */
    public ArrayList<Point> adjacentTiles(Point tile){
        Point p=new Point();
        int x=tile.x;
        int y=tile.y;
        ArrayList<Point> result=new ArrayList<>();
        //we are NOT on the edge
        if ((tile.x!=0 && tile.x!=Define.NUMBEROFROWS_BOARD.getI()-1) &&
                (tile.y!=0 && tile.y!=Define.NUMBEROFCOLUMNS_BOARD.getI()-1)){
            //upper
            p.x=x-1;
            p.y=y;
            if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                result.add(new Point(x-1, y));
            }
            //bottom
            p.x=x+1;
            p.y=y;
            if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                result.add(new Point(x+1, y));
            }
            //sx
            p.x=x;
            p.y=y-1;
            if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                result.add(new Point(x, y-1));
            }
            //dx
            p.x=x;
            p.y=y+1;
            if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                result.add(new Point(x, y+1));
            }
        }
        //we are on the edge
        else {
            //we are on the upper edge
            if (tile.x==0){
                //we are on upper - left corner
                if (tile.y==0){
                    //bottom
                    p.x=x+1;
                    p.y=y;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x+1, y));
                    }
                    //dx
                    p.x=x;
                    p.y=y+1;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x, y+1));
                    }
                }
                //we are on upper - right corner
                else if (tile.y==Define.NUMBEROFCOLUMNS_BOARD.getI()-1) {
                    //bottom
                    p.x=x+1;
                    p.y=y;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x+1, y));
                    }
                    //sx
                    p.x=x;
                    p.y=y-1;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x,y-1));
                    }
                }
                //we are in the middle
                else {
                    //bottom
                    p.x=x+1;
                    p.y=y;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x+1, y));
                    }
                    //sx
                    p.x=x;
                    p.y=y-1;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x,y-1));
                    }
                    //dx
                    p.x=x;
                    p.y=y+1;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x, y+1));
                    }
                }
            }
            //we are on the bottom edge
            else if (tile.x==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                //we are on bottom - left corner
                if (tile.y==0){
                    //upper
                    p.x=x-1;
                    p.y=y;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x-1, y));
                    }
                    //dx
                    p.x=x;
                    p.y=y+1;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x, y+1));
                    }
                }
                //we are on bottom - right corner
                else if (tile.y==Define.NUMBEROFCOLUMNS_BOARD.getI()-1) {
                    //upper
                    p.x=x-1;
                    p.y=y;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x-1, y));
                    }
                    //sx
                    p.x=x;
                    p.y=y-1;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x, y-1));
                    }
                }
                //we are in the middle
                else{
                    //upper
                    p.x=x-1;
                    p.y=y;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x-1, y));
                    }
                    //sx
                    p.x=x;
                    p.y=y-1;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x, y-1));
                    }
                    //dx
                    p.x=x;
                    p.y=y+1;
                    if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                            !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                        result.add(new Point(x, y+1));
                    }
                }
            }
            //we are on the left edge
            else if (tile.y==0){
                //upper
                p.x=x-1;
                p.y=y;
                if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(new Point(x-1, y));
                }
                //bottom
                p.x=x+1;
                p.y=y;
                if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(new Point(x+1, y));
                }
                //dx
                p.x=x;
                p.y=y+1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(new Point(x, y+1));
                }
            }
            //we are on right edge
            //if (tile.y==numberOfColumn-1)
            else {
                //upper
                p.x=x-1;
                p.y=y;
                if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(new Point(x-1, y));
                }
                //bottom
                p.x=x+1;
                p.y=y;
                if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(new Point(x+1, y));
                }
                //sx
                p.x=x;
                p.y=y-1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(new Point(x, y-1));
                }
            }
        }
        //return ArrayList of adjacent tiles to the incoming one
        return result;
    }

    /**
     * Method to return if all element in the List are on the same row
     *
     * @param position List of position to check
     * @return true if the positions are on the same row, false in other cases
     */
    public static boolean checkSameRow(List<Point> position){
        for (int i = 0; i < position.size(); i++) {
            for (Point point : position) {
                if (position.get(i).x != point.x) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method to return if all element in the List are on the same column
     *
     * @param position List of position to check
     * @return true if the positions are on the same column, false in other cases
     */
    public static boolean checkSameColumn(List<Point> position){
        for (int i = 0; i < position.size(); i++) {
            for (Point point : position) {
                if (position.get(i).y != point.y) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method to return if the Point are EMPTY or not
     *
     * @param point position to check
     * @return true if the point is empty, false in other cases
     */
    private boolean checkEmpty(Point point){
        return gamesBoard.getTile(point).equals(Tiles.EMPTY);
    }

    /**
     * Method to return if the Point are NOTALLOWED or not
     *
     * @param point position to check
     * @return true if point is NotAllowed, false in other cases
     */
    private boolean checkNotAllowed(Point point){
        return gamesBoard.getTile(point).equals(Tiles.NOT_ALLOWED);
    }


    /**
     * Method to return if all element in the List are pickable
     * <p>
     * <strong>REQUIRES:</strong> tile position is correct
     *
     * @param position List of position to check
     * @return true if the positions are pickable, false in other cases
     */
    public boolean tilesArePickable(List<Point> position){
        //check that position are at most 3 Point
        if (position.size()>Define.MAXNUMBEROFTILESPICKABLE.getI()){
            return false;
        }
        //check that position are free / !=EMPTY / !=NOTALLOWED
        for (Point point : position) {
            if (!checkFreeTiles(point)) {
                return false;
            }
            if (checkEmpty(point)){
                return false;
            }
            if (checkNotAllowed(point)){
                return false;
            }
        }
        //check that all Point are adjacent
        return position.size() <= 1 || checkAdjacentTiles(position);

    }

}

//TODO capire come fare i REQUIRES
// conviene fare try catch con IllegalArgumentException??
package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the game board
 * - initialization of the game board based on the number of players
 * - check if you need to reset the board
 * - reset the board
 * - add the tiles from the board and remove it from the sachet
 * - remove the tiles from the board
 * - define which tiles are free
 * - define which tiles are adjacent with each other
 */
public class Board {

    /**
     * defining number of rows per board
     */
    private static final int numberOfRows=9;
    /**
     * defining number of columns per board
     */
    private static final int numberOfColumns=9;

    /**
     * attribute used as a real board
     */
    private Matrix gamesBoard=new Matrix(Define.NUMBEROFROWS_BOARD.getI(), Define.NUMBEROFCOLUMNS_BOARD.getI());
    /**
     * attribute for saving the number of player that playing on the gamesBoard
     */
    private int numberOfPlayer;
    /**
     * attribute for associate the gamesBoard to its boardSachet
     */
    private Sachet boardSachet;



    /**
     * Constructor --> assign numberOfPlayer
     *             --> create Board based on number of player
     *
     * REQUIRES THAT numberOfPlayer TO BE A NUMBER BETWEEN 2 AND 4
     * @param numberOfPlayer    number of player
     */
    public Board(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
        creationBoard();

    }
    /**
     * Constructor --> assign numberOfPlayer
     *             --> assign boardSachet
     *             --> create Board based on number of player
     *
     * REQUIRES THAT numberOfPlayer TO BE A NUMBER BETWEEN 2 AND 4
     * @param numberOfPlayer    number of player
     * @param boardSachet       Sachet that will fill the Board
     */
    public Board(int numberOfPlayer, Sachet boardSachet) {
        this.numberOfPlayer = numberOfPlayer;
        this.boardSachet = boardSachet;
        creationBoard();
    }

    /**
     * create the board based on number of players
     */
    private void creationBoard() {
        //assign Tiles.EMPTY to all position of matrix gamesBoard
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                gamesBoard.setEmpty(i, j);
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
                gamesBoard.setEmpty(0, 3);
                gamesBoard.setNotAllowed(0, 4);

                gamesBoard.setNotAllowed(1, 5);

                gamesBoard.setEmpty(2, 2);
                gamesBoard.setEmpty(2, 6);

                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setEmpty(3, 8);

                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);

                gamesBoard.setEmpty(5, 0);
                gamesBoard.setNotAllowed(5, 7);

                gamesBoard.setEmpty(6, 2);
                gamesBoard.setEmpty(6, 6);

                gamesBoard.setNotAllowed(7, 3);

                gamesBoard.setNotAllowed(8, 4);
                gamesBoard.setEmpty(8, 5);
            }
            default -> {}
        }
    }

    /**
     * Getter --> return Matrix of gamesBoard
     *
     * @return Matrix   gamesBoard
     */
    public  Matrix getGamesBoard() {
        return gamesBoard;
    }
    /**
     * Getter --> return numberOfPlayer
     *
     * @return int  number of player
     */
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }



    /**
     * initialization of board
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

    //può servire solo per testare il gioco completo
    public void BoardInitialization(List<Tiles> list){
        int counter=0;
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                if (gamesBoard.getTile(i,j).equals(Tiles.EMPTY)){
                    gamesBoard.setTile(list.get(counter), i,j);
                    counter++;
                }
            }
        }
    }

    /**
     * return if board needs to be reset
     * all remaining tiles have NOT adjacent tile
     *
     * @return boolean  board needs to be reset
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
     * reset the board according to ENGLISH rule
     *
     * 1. remove remaining riles and put back in sachet
     * 2. fill the board with tiles in sachet
     *
     * REQUIRES THAT checkBoardReset() return true
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

    /**
     * reset the board according to ITALIAN rule
     *
     * 0. the remaining tiles on board are NOT removed
     * 1. fill the board with tiles in sachet
     */
    public void boardResetITA(){
        //board is filled with tiles in sachet --> BoardInitialization()
        BoardInitialization();
    }

    /**
     * add tile on the board and remove tile from sachet
     *
     * @param tile  tile to add on the board
     * @param row   number of row
     * @param col   number of column
     */
    public void placeTiles(Tiles tile, int row, int col){
        gamesBoard.setTile(tile, row, col);
        boardSachet.removeTiles(tile);
    }

    /**
     * add tile on the board
     *
     * @param tile  tile to add on the board
     * @param row   number of row
     * @param col   number of column
     */
    public void addTile(Tiles tile, int row, int col){
        gamesBoard.setTile(tile, row, col);
    }

    /**
     * tiles in the position shown are removing
     *
     * REQUIRE THAT position.size() TO BE BETWEEN 1 AND 3
     *
     * @param position  positions where to remove tiles
     */
    public void remove(List<Point> position){
        //for each position shown remove tiles from the gamesBoard
        for (Point point : position) {
            remove(point);
        }
    }

    /**
     * tile in the position shown are removing
     *
     * REQUIRE THAT position IS CORRECT --> tile!=EMPTY && !=NOTALLOWED
     *
     * @param position  position where to remove tile
     */
    public void remove(Point position){
        gamesBoard.remove(position.x, position.y);
    }

    /**
     * return if tile are free --> tile has at least 1 free side
     *
     * REQUIRES THAT position IS CORRECT --> tile!=EMPTY && !=NOTALLOWED
     *
     * @param position  position of tile to check
     * @return boolean  are free tiles?
     */
    public boolean checkFreeTiles(Point position){
        ArrayList<Point> list=adjacentTiles(position);
        return list.size() != 4;
    }

    /**
     * return the position of free tiles
     *
     * @return ArrayList    position of free tiles
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
     * return if the tiles in the List are adjacent --> tiles have a common side
     * if List has List.size()==1 return FALSE
     *
     * REQUIRES THAT THERE IS NOT EQUALS position IN THE LIST
     * REQUIRES THAT ALL POSITION IN THE List ARE CORRECT --> tile!=EMPTY && !=NOTALLOWED
     *
     * @param position  the position to check
     * @return boolean  are the tiles adjacent?
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
     * return positions of tiles adjacent to the incoming one
     *
     * REQUIRES THAT tile POSITION IS CORRECT --> tile!=EMPTY && !=NOTALLOWED
     *
     * @param tile  the position of which I want to know the adjacent tile
     * @return ArrayList    positions of tiles adjacent to the incoming one
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
     * return if Points are on the same row
     *
     * @param position  List of position to check
     * @return boolean  are the position on the same row?
     */
    public static boolean checkSameRow(List<Point> position){
        for (int i = 0; i < position.size(); i++) {
            for (int j = 0; j < position.size(); j++) {
                if (position.get(i).x!=position.get(j).x){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * return if Points are on the same column
     *
     * @param position  List of position to check
     * @return boolean  are the position on the same column?
     */
    public static boolean checkSameColumn(List<Point> position){
        for (int i = 0; i < position.size(); i++) {
            for (int j = 0; j < position.size(); j++) {
                if (position.get(i).y!=position.get(j).y){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * return if Point are Empty or not
     *
     * @param point     position to check
     * @return boolean  is an empty point?
     */
    private boolean checkEmpty(Point point){
        return gamesBoard.getTile(point).equals(Tiles.EMPTY);
    }

    /**
     * return if Point are NotAllowed or not
     *
     * @param point     position to check
     * @return boolean  is a NotAllowed point?
     */
    private boolean checkNotAllowed(Point point){
        return gamesBoard.getTile(point).equals(Tiles.NOT_ALLOWED);
    }


    /**
     * return if Points are pickable
     *
     * REQUIRES THAT tile POSITION IS CORRECT --> tile!=EMPTY && !=NOTALLOWED
     *
     * @param position  List of position to check
     * @return boolean  are the positions pickable?
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
        if (position.size()>1 && !checkAdjacentTiles(position)){
            return false;
        }

        //if it passed the check...
        return true; //tiles pickable
    }

}

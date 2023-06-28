package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the game board.
 * <ul>
 *     <li>Initialization of the game board based on the number of players;</li>
 *     <li>check if you need to reset the board;</li>
 *     <li>reset the board;</li>
 *     <li>add the tiles from the board and remove it from the sachet;</li>
 *     <li>remove the tiles from the board;</li>
 *     <li>define which tiles are free;</li>
 *     <li>define which tiles are adjacent with each other;</li>
 * </ul>
 */
public class Board {

    /**
     * Attribute used as a real board.
     */
    private final Matrix gamesBoard=new Matrix(Define.NUMBEROFROWS_BOARD.getI(), Define.NUMBEROFCOLUMNS_BOARD.getI());
    /**
     * Attribute for saving the number of player that playing on the gamesBoard.
     */
    private final int numberOfPlayer;
    /**
     * Attribute for associate the gamesBoard to its boardSachet.
     */
    private final Sachet boardSachet;



    /**
     * Initialize numberOfPlayer, boardSachet and create Board based on number of player.
     * <p>
     * <strong>REQUIRES:</strong> numberOfPlayer is between 2 and 4.
     *
     * @param numberOfPlayer the number of player.
     * @param boardSachet the sachet that will fill the board.
     */
    public Board(int numberOfPlayer, Sachet boardSachet) {
        this.numberOfPlayer = numberOfPlayer;
        this.boardSachet = boardSachet;
        creationBoard();
    }



    /**
     * <strong>Getter</strong> -> Returns Matrix of gamesBoard.
     *
     * @return the gamesBoard.
     */
    public Matrix getGamesBoard() {
        return gamesBoard;
    }



    /**
     * Method to create the board based on number of players.
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
     * Method to initialize board.
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
     * Method to return if the board needs to be reset (all remaining tiles have no adjacent ones).
     *
     * @return <i>true</i> if the board needs to be reset, <i>else</i> in the other case.
     */
    public boolean checkBoardReset(){
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                //for all tiles that !=NOTALLOWED && !=EMPTY
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOT_ALLOWED) &&
                        !gamesBoard.getTile(i, j).equals(Tiles.EMPTY)){

                    //not on the edge board
                    if (i!=0 && i!=Define.NUMBEROFROWS_BOARD.getI()-1 &&
                            j!=0 && j!=Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                        if (leftAdjacency(i, j)){
                            return false;
                        }
                        if (rightAdjacency(i, j)){
                            return false;
                        }
                        if (upperAdjacency(i, j)){
                            return false;
                        }
                        if (bottomAdjacency(i, j)){
                            return false;
                        }
                    }
                    //we are on one edge
                    else {
                        //upper left corner
                        if (i==0 && j==0){
                            if (rightAdjacency(i, j)){
                                return false;
                            }
                            if (bottomAdjacency(i, j)){
                                return false;
                            }
                        }
                        //upper right corner
                        else if (i==0 && j==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                            if (leftAdjacency(i, j)){
                                return false;
                            }
                            if (bottomAdjacency(i, j)){
                                return false;
                            }
                        }
                        //bottom left corner
                        else if (i==Define.NUMBEROFROWS_BOARD.getI()-1 && j==0){
                            if (rightAdjacency(i, j)){
                                return false;
                            }
                            if (upperAdjacency(i, j)){
                                return false;
                            }
                        }
                        //bottom right corner
                        else if (i==Define.NUMBEROFROWS_BOARD.getI()-1 && j==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                            if (leftAdjacency(i, j)){
                                return false;
                            }
                            if (upperAdjacency(i, j)){
                                return false;
                            }
                        }
                        //upper edge
                        else if (i==0){
                            if (leftAdjacency(i, j)){
                                return false;
                            }
                            if (rightAdjacency(i, j)){
                                return false;
                            }
                            if (bottomAdjacency(i, j)){
                                return false;
                            }
                        }
                        //bottom edge
                        else if (i==Define.NUMBEROFROWS_BOARD.getI()-1){
                            if (leftAdjacency(i, j)){
                                return false;
                            }
                            if (rightAdjacency(i, j)){
                                return false;
                            }
                            if (upperAdjacency(i, j)){
                                return false;
                            }
                        }
                        //left edge
                        else if (j==0){
                            if (rightAdjacency(i, j)){
                                return false;
                            }
                            if (upperAdjacency(i, j)){
                                return false;
                            }
                            if (bottomAdjacency(i, j)){
                                return false;
                            }
                        }
                        //right edge
                        else if (j==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                            if (leftAdjacency(i, j)){
                                return false;
                            }
                            if (upperAdjacency(i, j)){
                                return false;
                            }
                            if (bottomAdjacency(i, j)){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        //if we arrived in this line of code it means that we have to reset the board
        return true;
    }

    /**
     * Method that verify the left adjacency.
     *
     * @param i the index of row.
     * @param j the index of column.
     * @return <i>true</i> if there is left adjacency, <i>false</i> in the other case.
     */
    private boolean leftAdjacency(int i, int j){
        return !gamesBoard.getTile(i, j - 1).equals(Tiles.NOT_ALLOWED) &&
                !gamesBoard.getTile(i, j - 1).equals(Tiles.EMPTY);
    }

    /**
     * Method that verify the right adjacency.
     *
     * @param i the index of row.
     * @param j the index of column.
     * @return <i>true</i> if there is right adjacency, <i>false</i> in the other case.
     */
    private boolean rightAdjacency(int i, int j){
        return !gamesBoard.getTile(i, j + 1).equals(Tiles.NOT_ALLOWED) &&
                !gamesBoard.getTile(i, j + 1).equals(Tiles.EMPTY);
    }

    /**
     * Method that verify the upper adjacency.
     *
     * @param i the index of row.
     * @param j the index of column.
     * @return <i>true</i> if there is upper adjacency, <i>false</i> in the other case.
     */
    private boolean upperAdjacency(int i, int j){
        return !gamesBoard.getTile(i-1, j).equals(Tiles.NOT_ALLOWED) &&
                !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY);
    }

    /**
     * Method that verify the bottom adjacency.
     *
     * @param i the index of row.
     * @param j the index of column.
     * @return <i>true</i> if there is bottom adjacency, <i>false</i> in the other case.
     */
    private boolean bottomAdjacency(int i, int j){
        return !gamesBoard.getTile(i+1, j).equals(Tiles.NOT_ALLOWED) &&
                !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY);
    }

    /**
     * Method that verify the left adjacency.
     *
     * @param tile the position to verify.
     * @return a <i>Point</i> with the left position of that given, <i>null</i> if the left position is EMPTY or NOTALLOWED.
     */
    private Point leftAdjacency(Point tile){
        int x=tile.x;
        int y=tile.y;
        if (!gamesBoard.getTile(x, y-1).equals(Tiles.NOT_ALLOWED) &&
                !gamesBoard.getTile(x, y-1).equals(Tiles.EMPTY)){
            return new Point(x, y-1);
        }
        return null;
    }

    /**
     * Method that verify the right adjacency.
     *
     * @param tile the position to verify.
     * @return a <i>Point</i> with the right position of that given, <i>null</i> if the right position is EMPTY or NOTALLOWED.
     */
    private Point rightAdjacency(Point tile){
        int x=tile.x;
        int y=tile.y;
        if (!gamesBoard.getTile(x, y+1).equals(Tiles.NOT_ALLOWED) &&
                !gamesBoard.getTile(x, y+1).equals(Tiles.EMPTY)){
            return new Point(x, y+1);
        }
        return null;
    }

    /**
     * Method that verify the upper adjacency.
     *
     * @param tile the position to verify.
     * @return a <i>Point</i> with the upper position of that given, <i>null</i> if the upper position is EMPTY or NOTALLOWED.
     */
    private Point upperAdjacency(Point tile){
        int x=tile.x;
        int y=tile.y;
        if (!gamesBoard.getTile(x-1, y).equals(Tiles.NOT_ALLOWED) &&
                !gamesBoard.getTile(x-1, y).equals(Tiles.EMPTY)){
            return new Point(x-1, y);
        }
        return null;
    }

    /**
     * Method that verify the bottom adjacency.
     *
     * @param tile the position to verify.
     * @return a <i>Point</i> with the bottom position of that given, <i>null</i> if the bottom position is EMPTY or NOTALLOWED.
     */
    private Point bottomAdjacency(Point tile){
        int x=tile.x;
        int y=tile.y;
        if (!gamesBoard.getTile(x+1, y).equals(Tiles.NOT_ALLOWED) &&
                !gamesBoard.getTile(x+1, y).equals(Tiles.EMPTY)){
            return new Point(x+1, y);
        }
        return null;
    }

    /**
     * Method to reset the board according to ENGLISH rule.
     * <p>
     *     First of all remove all the remaining tiles and put them back in sachet,
     *     then refill the board with tiles in sachet.
     * </p>
     * <strong>REQUIRES:</strong> checkBoardReset() return true.
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
     * Method to remove the tiles in the positions shown.
     * <p>
     * <strong>REQUIRES:</strong> position.size() is between 1 and 3.
     *
     * @param position the positions where to remove tiles.
     */
    public void remove(List<Point> position){
        //for each position shown remove tiles from the gamesBoard
        for (Point point : position) {
            remove(point);
        }
    }

    /**
     * Method to remove tile in the position shown.
     * <p>
     * <strong>REQUIRES:</strong> position is correct.
     *
     * @param position the position where to remove tile.
     */
    public void remove(Point position){
        gamesBoard.fullRemove(position.x, position.y);
    }

    /**
     * Method to return whether the tile at the given position is free (does the tile have at least 1 free side?).
     * <p>
     * <strong>REQUIRES:</strong> that position is correct.
     *
     * @param position the position of tile to check.
     * @return <i>true</i> if the tile is free, <i>false</i> if they aren't.
     */
    private boolean checkFreeTiles(Point position){
        ArrayList<Point> list=adjacentTiles(position);
        //the tile to be considered free must have at least one free side
        return list.size() != 4;
    }

    /**
     * Method to return if the tiles in the List are adjacent.
     * <p>
     *     Tiles are adjacent if and only if they have one side in common.
     *     If the size of the list is equal to 1 it returns FALSE because the tile is single.
     * </p>
     * <p><strong>REQUIRES:</strong> there isn't any equal position in the list. </p>
     * <p><strong>REQUIRES:</strong> all position in the list are correct.</p>
     *
     * @param position the position to check.
     * @return <i>true</i> if the tiles are the adjacent, <i>false</i> if they aren't.
     */
    public boolean checkAdjacentTiles(List<Point> position){
        //there is only one tile
        if (position.size()==1){
            return false;
        }

        //tiles are not in the same row or in the same column
        if (!checkSameRow(position) && !checkSameColumn(position)){
            return false;
        }
        //tiles are on the same row
        else if (checkSameRow(position)){
            for (int i = 0; i < position.size(); i++) {
                //we are on x-th row first column
                if (position.get(i).y==0){
                    if (!position.contains(new Point(position.get(i).x, position.get(i).y+1))){
                        return false;
                    }
                }
                //we are on x-th row last column
                else if (position.get(i).y==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                    if (!position.contains(new Point(position.get(i).x, position.get(i).y-1))){
                        return false;
                    }
                }
                //we are on x-th row / NO limit cases
                else {
                    if (!position.contains(new Point(position.get(i).x, position.get(i).y-1)) &&
                            !position.contains(new Point(position.get(i).x, position.get(i).y+1))) {
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
                    if (!position.contains(new Point(position.get(i).x+1, position.get(i).y))){
                        return false;
                    }
                }
                //we are on y-th column last row
                else if (position.get(i).x==Define.NUMBEROFROWS_BOARD.getI()-1){
                    if (!position.contains(new Point(position.get(i).x-1, position.get(i).y))){
                        return false;
                    }
                }
                //we are on y-th column / NO limit cases
                else {
                    if (!position.contains(new Point(position.get(i).x-1, position.get(i).y)) &&
                            !position.contains(new Point(position.get(i).x+1, position.get(i).y))){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to return the positions of adjacent tiles to the incoming one.
     * <p>
     * <strong>REQUIRES:</strong> tile position is correct.
     *
     * @param tile the position of which I want to know the adjacent tile.
     * @return the <i>ArrayList</i> that contains the positions of tiles adjacent to the incoming one.
     */
    private ArrayList<Point> adjacentTiles(Point tile){
        ArrayList<Point> result=new ArrayList<>();

        //not on the edge
        if ((tile.x!=0 && tile.x!=Define.NUMBEROFROWS_BOARD.getI()-1) &&
                (tile.y!=0 && tile.y!=Define.NUMBEROFCOLUMNS_BOARD.getI()-1)){
            //upper, bottom, left, right
            if (upperAdjacency(tile)!=null){
                result.add(upperAdjacency(tile));
            }
            if (bottomAdjacency(tile)!=null){
                result.add(bottomAdjacency(tile));
            }
            if (leftAdjacency(tile)!=null){
                result.add(leftAdjacency(tile));
            }
            if (rightAdjacency(tile)!=null){
                result.add(rightAdjacency(tile));
            }
        }
        //on the edge
        else {
            //upper left corner
            if (tile.x==0 && tile.y==0){
                //bottom, right
                if (bottomAdjacency(tile)!=null){
                    result.add(bottomAdjacency(tile));
                }
                if (rightAdjacency(tile)!=null){
                    result.add(rightAdjacency(tile));
                }
            }
            //upper right corner
            else if (tile.x==0 && tile.y==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                //bottom, left
                if (bottomAdjacency(tile)!=null){
                    result.add(bottomAdjacency(tile));
                }
                if (leftAdjacency(tile)!=null){
                    result.add(leftAdjacency(tile));
                }
            }
            //bottom left corner
            else if (tile.x==Define.NUMBEROFCOLUMNS_BOARD.getI()-1 && tile.y==0){
                //upper, right
                if (upperAdjacency(tile)!=null){
                    result.add(upperAdjacency(tile));
                }
                if (rightAdjacency(tile)!=null){
                    result.add(rightAdjacency(tile));
                }
            }
            //bottom right corner
            else if (tile.x==Define.NUMBEROFCOLUMNS_BOARD.getI()-1 && tile.y==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                //upper, left
                if (upperAdjacency(tile)!=null){
                    result.add(upperAdjacency(tile));
                }
                if (leftAdjacency(tile)!=null){
                    result.add(leftAdjacency(tile));
                }
            }
            //upper edge
            else if (tile.x==0){
                //bootom, left, right
                if (bottomAdjacency(tile)!=null){
                    result.add(bottomAdjacency(tile));
                }
                if (leftAdjacency(tile)!=null){
                    result.add(leftAdjacency(tile));
                }
                if (rightAdjacency(tile)!=null){
                    result.add(rightAdjacency(tile));
                }
            }
            //bottom edge
            else if (tile.x==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                //upper, left, right
                if (upperAdjacency(tile)!=null){
                    result.add(upperAdjacency(tile));
                }
                if (leftAdjacency(tile)!=null){
                    result.add(leftAdjacency(tile));
                }
                if (rightAdjacency(tile)!=null){
                    result.add(rightAdjacency(tile));
                }
            }
            //left edge
            else if (tile.y==0){
                //upper, bottom, right
                if (upperAdjacency(tile)!=null){
                    result.add(upperAdjacency(tile));
                }
                if (bottomAdjacency(tile)!=null){
                    result.add(bottomAdjacency(tile));
                }
                if (rightAdjacency(tile)!=null){
                    result.add(rightAdjacency(tile));
                }
            }
            //right edge
            else if (tile.y==Define.NUMBEROFCOLUMNS_BOARD.getI()-1){
                //upper, bottom, left
                if (upperAdjacency(tile)!=null){
                    result.add(upperAdjacency(tile));
                }
                if (bottomAdjacency(tile)!=null){
                    result.add(bottomAdjacency(tile));
                }
                if (leftAdjacency(tile)!=null){
                    result.add(leftAdjacency(tile));
                }
            }
        }

        //return ArrayList of adjacent tiles to the incoming one
        return result;
    }



    /**
     * Method to return if all element in the List are on the same row.
     *
     * @param position the List of position to check.
     * @return <i>true</i> if the positions are on the same row, <i>false</i> in other cases.
     */
    private boolean checkSameRow(List<Point> position){
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
     * Method to return if all element in the List are on the same column.
     *
     * @param position the List of position to check.
     * @return <i>true</i> if the positions are on the same column, <i>false</i> in other cases.
     */
    private boolean checkSameColumn(List<Point> position){
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
     * Method to return if the Point are EMPTY or not.
     *
     * @param point the position to check.
     * @return <i>true</i> if the point is empty, <i>false</i> in other cases.
     */
    private boolean checkEmpty(Point point){
        return gamesBoard.getTile(point).equals(Tiles.EMPTY);
    }

    /**
     * Method to return true if the Point are NOTALLOWED or not.
     *
     * @param point the position to check.
     * @return <i>true</i> if point is NotAllowed, <i>false</i> in other cases.
     */
    private boolean checkNotAllowed(Point point){
        return gamesBoard.getTile(point).equals(Tiles.NOT_ALLOWED);
    }

    /**
     * Method to return true if all element in the List are pickable.
     * <p>
     * <strong>REQUIRES:</strong> tile position is correct.
     *
     * @param position the List of position to check.
     * @return <i>true</i> if the positions are pickable, <i>false</i> in other cases.
     */
    public boolean tilesArePickable(List<Point> position){
        //check that position are at most 3 Point
        if (position.size()>Define.MAXNUMBEROFTILESPICKABLE.getI()){
            return false;
        }

        //check that position are free / !=EMPTY / !=NOTALLOWED
        for (Point point : position) {
            if (checkEmpty(point)){
                return false;
            }
            if (checkNotAllowed(point)){
                return false;
            }
            if (!checkFreeTiles(point)) {
                return false;
            }
        }
        //check that all Point are adjacent
        return position.size() <= 1 || checkAdjacentTiles(position);

    }

}

package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.server.Exceptions.*;

import java.awt.*;
import java.util.ArrayList;

public class CheckManager {
    private static final int maxNumberOfSelectedTiles=3;
    private static final int numberOfBoardRows=9;
    private static final int numberOfBoardColumns=9;
    private static final int numberOfBookshelColumns=5;

    private Player currPlayer;
    private GameState state;

    private final ArrayList<Tiles> selectedTiles;


    public CheckManager(ArrayList<Tiles> selectedTiles) {
        this.selectedTiles = selectedTiles;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void setState(GameState state) {
        this.state = state;
    }



    //Checks to see the legitimacy of moves


    //REMOVE CHECKS

    /**
     * Checks if the player can remove the selected tiles from the board.
     * @param points The array of points you want to remove
     * @param player The player who wants to remove the tiles
     * @throws MoveNotPossible if the game is not in the right state
     * @throws NotCurrentPlayer if the player is not the current player
     * @throws IllegalArgumentException if the array points is null
     * @throws TilesNotAdjacent if the tiles are not adjacent
     * @throws OutOfDomain if at least one of the points is outside the board
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed
     * @throws TooManySelected if the array points is too long
     */
    public void checkRemoveLegit(ArrayList<Point> points, Player player,Board board) throws MoveNotPossible,IllegalArgumentException {

        if(state.equals(GameState.CHOOSING_TILES)){
            //Check if the player requesting the move is the current player
            if(!player.equals(currPlayer)) throw new NotCurrentPlayer();
                //check if the selected tiles can actually be selected
            else checkPointArrayDomain(points,board);

        }else throw new MoveNotPossible();

    }


    /**
     * Checks if the selected tiles can actually be removed from the board
     * @param points Array of coordinates of the tiles
     * @throws IllegalArgumentException if the array points is null
     * @throws TilesNotAdjacent if the tiles are not adjacent
     * @throws OutOfDomain if at least one of the points is outside the board
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed
     * @throws TooManySelected if the array is too long
     */
    private void checkPointArrayDomain(ArrayList<Point> points,Board board) throws MoveNotPossible, IllegalArgumentException {
        //check if the array is not null
        if(points!=null ){
            //check the length of the array
            if(points.size()>maxNumberOfSelectedTiles) throw new TooManySelected();
            else {  //check if the tiles are adjacent
                if(!Board.checkAdjacentTiles(points)) throw new TilesNotAdjacent();
            }

            //Check if the selected tiles are allowed and not empty
            for(Point p : points){
                checkBoardDomain(p,board);
            }
        }
        else throw new IllegalArgumentException();

    }


    /**
     * Checks if the tiles of coordinates p can be selected (it's in the board, is allowed and not empty)
     * @param p the coordinates of the tiles
     * @throws OutOfDomain if at least one of the points is outside the board
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed
     */
    private void checkBoardDomain(Point p,Board board) throws MoveNotPossible {
        //Check if the point is inside the board
        checkPointBoardDomain(p);
        //Check if there is a tile there
        if(board.getGamesBoard().getTile(p).equals(Tiles.NOTALLOWED) ||
                board.getGamesBoard().getTile(p).equals(Tiles.EMPTY)){
            throw new TilesCannotBeSelected();}
    }



    /**
     * Check if the point is inside the board
     * @param p Coordinates of the point
     * @throws OutOfDomain If the point is outside the board
     */
    private void checkPointBoardDomain(Point p) throws OutOfDomain {
        //Check if the point is inside the board
        if(p.getX()<0 || p.getX()>numberOfBoardRows-1) throw new OutOfDomain();
        else if(p.getY()<0 || p.getY()>numberOfBoardColumns-1) throw new OutOfDomain();
    }


    //ADD CHECKS


    /**
     *  Check if the player can add tiles to his bookshelf
     * @param player The player trying to add tiles to his bookshelf
     * @param col    The column number where to add the tiles
     * @param size   The number of tiles you want to add
     * @throws OutOfDomain if requested column does not exists
     * @throws ColumnIsFull if the requested column is full
     * @throws MoveNotPossible if game is not in the right state
     * @throws NotCurrentPlayer if the player requesting the move is not the current player
     */
    public void checkAddLegit(Player player,int col,int size) throws MoveNotPossible {

        //checks the state of the game
        if(state.equals(GameState.CHOOSING_ORDER)||state.equals(GameState.CHOOSING_COLUMN)){
            //Check if the player requesting the move is the current player
            if(!player.equals(currPlayer)) throw new NotCurrentPlayer();

            //Check if the selected column exists and if there is enough empty space
            checkColumn(col,size);

        }else throw new MoveNotPossible();
    }


    /**
     * Check if the selected column exists and if there is enough empty space
     * @param col The column where you want to put the tiles
     * @param size The number of tiles you want to add
     * @throws OutOfDomain if requested column does not exists
     * @throws ColumnIsFull if the requested column is full
     */
    private void checkColumn(int col,int size) throws OutOfDomain, ColumnIsFull {
        if(col<0||col>numberOfBookshelColumns) throw new OutOfDomain();
        else if(!currPlayer.getBookshelf().checkColumns(size,col)) throw new ColumnIsFull();
    }


    /**
     * Checks if the player can swap the selectedTiles array
     * @param ints Swap array
     * @param player Player
     * @throws MoveNotPossible The game is not in the right state
     * @throws NotCurrentPlayer The player is not the current player
     * @throws IllegalArgumentException The ints array is not of appropriate content
     * @throws TooManySelected if the array is not of appropriate size
     */
    public void swapCheck(ArrayList<Integer> ints,Player player) throws MoveNotPossible,IllegalArgumentException {

        if(!state.equals(GameState.CHOOSING_ORDER)) throw new MoveNotPossible();

        if(!player.equals(currPlayer)) throw new NotCurrentPlayer();

        intsCheck(ints);

    }


    /**
     * Checks if the array is of appropriate size and content
     * @param ints Swap array
     * @throws IllegalArgumentException if the array is not of appropriate content
     * @throws TooManySelected if the array is not of appropriate size
     */
    private void intsCheck(ArrayList<Integer> ints) throws IllegalArgumentException, TooManySelected {
        //Checks if the swap array is the same size as the selectedTiles array
        if(ints.size()!=selectedTiles.size()) throw new TooManySelected();
        //Checks that the integer is between 0 and the array size
        for(Integer i : ints){
            if( i<0 || i>ints.size()) throw new IllegalArgumentException();
        }

    }






}

package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.utils.Tile;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class used to make checks based on the current player and the current state.
 */
public class CheckManager {
    private static final int maxNumberOfSelectedTiles=3;
    private static final int numberOfBoardRows=9;
    private static final int numberOfBoardColumns=9;
    private static final int numberOfBookshelfColumns =5;

    private Player currPlayer;
    private GameState state;

    private final ArrayList<Tile> selectedTiles;

    /**
     * Initialize the selected tiles.
     *
     * @param selectedTiles selected tiles.
     */
    public CheckManager(ArrayList<Tile> selectedTiles) {
        this.selectedTiles = selectedTiles;
    }

    /**
     * <strong>Setter</strong> -> Sets the current player.
     *
     * @param currPlayer current player.
     */
    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * <strong>Setter</strong> -> Sets the current state.
     *
     * @param state state.
     */
    public void setState(GameState state) {
        this.state = state;
    }


    //Checks to see the legitimacy of moves


    //REMOVE CHECKS

    /**
     * Method to check if the player can remove the selected tiles from the board.
     * @param points The array of points you want to remove.
     * @param player The player who wants to remove the tiles.
     * @param board The board of the game.
     * @throws MoveNotPossible if the game is not in the right state.
     * @throws NotCurrentPlayer if the player is not the current player.
     * @throws IllegalArgumentException if the array points is null.
     * @throws TilesNotAdjacent if the tiles are not adjacent.
     * @throws OutOfDomain if at least one of the points is outside the board.
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed.
     * @throws TooManySelected if the points array is too long.
     */
    public void checkRemoveLegit(ArrayList<Point> points, Player player,Board board) throws MoveNotPossible,IllegalArgumentException {

        if(state.equals(GameState.CHOOSING_TILES)){
            //check if the player requesting the move is the current player
            if(!player.equals(currPlayer)) throw new NotCurrentPlayer();


            //check if the selected tiles can actually be selected
            checkPointArrayDomain(points,board,player);

        }else throw new MoveNotPossible();

    }


    /**
     * Method to check if the selected tiles can actually be removed from the board.
     * @param points Array of coordinates of the tiles.
     * @throws IllegalArgumentException if the array points is null.
     * @throws TilesNotAdjacent if the tiles are not adjacent.
     * @throws OutOfDomain if at least one of the points is outside the board.
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed.
     * @throws TooManySelected if the array is too long.
     */
    private void checkPointArrayDomain(ArrayList<Point> points,Board board,Player player) throws MoveNotPossible, IllegalArgumentException {
        //check if the array is not null
        if(points!=null && points.size()>0 ){

            //Check if the bookshelf has enough empty slots to fit the selected tiles
            if(!player.getBookshelf().checkPossibleChoice(points.size())) throw new TooManySelected();


            //check the length of the array
            if(points.size()>maxNumberOfSelectedTiles) throw new TooManySelected();

            //check if the player is trying to pick the same tile more than one
            HashSet<Point> set = new HashSet<>(points);
            if(points.size()!=set.size()) throw new SameElement();

             //check if the tiles are adjacent
            if(points.size()>1) if(!board.checkAdjacentTiles(points)) throw new TilesNotAdjacent();

            //Check if the selected tiles are allowed and not empty
            for(Point p : points){
                checkBoardDomain(p,board);
            }
            //check if tiles are pickable
            if(!board.tilesArePickable(points)) throw  new TilesCannotBeSelected();


        }
        else throw new IllegalArgumentException();
    }


    /**
     * Method to check if the tiles of coordinates p can be selected (it's in the board, is allowed and not empty).
     * @param p the coordinates of the tiles.
     * @throws OutOfDomain if at least one of the points is outside the board.
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed.
     */
    private void checkBoardDomain(Point p,Board board) throws MoveNotPossible {
        //Check if the point is inside the board
        checkPointBoardDomain(p);
        //Check if there is a tile there
        if(board.getGamesBoard().getTile(p).equals(Tiles.NOT_ALLOWED) ||
                board.getGamesBoard().getTile(p).equals(Tiles.EMPTY)){
            throw new TilesCannotBeSelected();}
    }



    /**
     * Method to check if the point is inside the board.
     * @param p Coordinates of the point.
     * @throws OutOfDomain If the point is outside the board.
     */
    private void checkPointBoardDomain(Point p) throws OutOfDomain {
        //Check if the point is inside the board
        if(p.getX()<0 || p.getX()>numberOfBoardRows-1) throw new OutOfDomain();
        else if(p.getY()<0 || p.getY()>numberOfBoardColumns-1) throw new OutOfDomain();
    }


    //ADD CHECKS


    /**
     * Method to check if the player can add tiles to his bookshelf.
     * @param player The player trying to add tiles to his bookshelf.
     * @param col    The column number where to add the tiles.
     * @param size   The number of tiles you want to add.
     * @throws OutOfDomain if requested column does not exists.
     * @throws ColumnIsFull if the requested column is full.
     * @throws MoveNotPossible if game is not in the right state.
     * @throws NotCurrentPlayer if the player requesting the move is not the current player.
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
     * Method to check if the selected column exists and if there is enough empty space.
     * @param col The column where you want to put the tiles.
     * @param size The number of tiles you want to add.
     * @throws OutOfDomain if requested column does not exists.
     * @throws ColumnIsFull if the requested column is full.
     */
    private void checkColumn(int col,int size) throws OutOfDomain, ColumnIsFull {
        if(col<0||col> numberOfBookshelfColumns -1) throw new OutOfDomain();
        else if(!currPlayer.getBookshelf().checkColumns(size,col)) throw new ColumnIsFull();
    }


    /**
     * Method to check if the player can swap the selectedTiles array.
     * @param ints Swap array.
     * @param player Player.
     * @throws MoveNotPossible The game is not in the right state.
     * @throws NotCurrentPlayer The player is not the current player.
     * @throws IllegalArgumentException The ints array is not of appropriate content.
     * @throws TooManySelected if the array is not of appropriate size.
     */
    public void swapCheck(ArrayList<Integer> ints,Player player) throws MoveNotPossible,IllegalArgumentException {

        if(!state.equals(GameState.CHOOSING_ORDER)) throw new MoveNotPossible();

        if(!player.equals(currPlayer)) throw new NotCurrentPlayer();

        //Checks if the array is of appropriate size and content
        intsCheck(ints);

    }


    /**
     * Method to check if the array is of appropriate size and content.
     * @param ints Swap array.
     * @throws IllegalArgumentException if the array is not of appropriate content.
     * @throws TooManySelected if the array is not of appropriate size.
     */
    private void intsCheck(ArrayList<Integer> ints) throws IllegalArgumentException, TooManySelected {
        //Checks if the swap array is the same size as the selectedTiles array
        if(ints.size()>maxNumberOfSelectedTiles) throw new TooManySelected();
        //Checks that the integer is between 0 and the array size
        for(Integer i : ints){
            if( i<1 || i>ints.size()) throw new IllegalArgumentException();
        }

    }



}

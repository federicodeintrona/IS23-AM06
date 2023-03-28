package it.polimi.ingsw.server;

import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Exceptions.MoveNotPossible;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.server.View.View;
import org.javatuples.Pair;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


public class Model  {

    private Board board;
    private ArrayList<Player> players;
    private ArrayList<View> virtualViews;
    private ArrayList<CommonObjective> commonobj = new ArrayList<>();

    private ArrayList<Integer> privatePoints = new ArrayList<>();

    private ArrayList<Integer> publicPoints = new ArrayList<>();

    private Player currPlayer;
    private Player nextPlayer;

    private Player winner;
    private boolean isFinished = false;

    private ArrayList<Tiles> selectedTiles = new ArrayList<>();

    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);


    public Model(ArrayList<Player> players) {
        this.players = players;
    }

    public Model(ArrayList<Player> players, ArrayList<View> views) {
        this.players = players;
        this.virtualViews = views;
        this.currPlayer = players.get(0);
        this.nextPlayer = players.get(1);
    }

    //PUBLIC METHODS : INTERFACE

    /**
     * Initializes the board and the objectives:
     * Create and initialize the board,initialize common and personal objectives,
     * add the views as change listeners,initialize the arrays of points.
     */
    public void initialization()  {
        //Create and initialize the board
        board = new Board(players.size(), new Sachet());
        board.BoardInitialization();

        //Initialize common and personal objectives

        commonobjInit();
        personalobjInit();
        //Initializes the arrays of points
        for(Player p : players){
            privatePoints.add(p.getPrivatePoint());
            publicPoints.add(p.getPublicPoint());
        }

        //Add the views as change listeners
        for (View v : virtualViews){
            notifier.addPropertyChangeListener(v);
        }

    }



    /**
     * Removes an array of tiles from the board if the move is legitimate.
     * Also notifies the views of changes
     * @param points  The position of the tiles
     */
    public void removeTileArray( Player player,ArrayList<Point> points) throws MoveNotPossible{

        //Checks move legitimacy
        if(!checkRemoveLegit(points,player)) throw new MoveNotPossible();

        //Notify the views
        for(int i = 0; i < points.size(); i++){
            Pair<Tiles,Point> p1 = new Pair<>(board.getGamesBoard().getTile(points.get(i)),points.get(i));
            Pair<Tiles,Point> p2 = new Pair<>(Tiles.EMPTY,points.get(i));
            notifier.firePropertyChange( new PropertyChangeEvent(board,"board", p1,p2));

        }
        //Remove the selected tiles
        board.remove(points);

    }


    /**
     * Adds an ordered array of tiles in the player's bookshelf.
     * Since adding tiles to your bookshelf is the last action you can do on your turn,
     * it also calls the nextTurn function
     * @param player  The player who owns the Bookshelf
     * @param tiles   The color of the tiles to add
     * @param column   The column where you want to add the tiles
     */
    public void addToBookShelf(Player player, ArrayList<Tiles> tiles, int column) throws MoveNotPossible {

        //Check for move legitimacy
        if(!checkAddLegit(player,column,tiles.size())) throw new MoveNotPossible();

        //Notifying changes
        ArrayList<Tiles> temp1 = new ArrayList<>(player.getBookshelf().getTiles().getColumn(column));
        Pair<ArrayList<Tiles>, Integer> p1 = new Pair<>(temp1, column);

        player.getBookshelf().addTile(tiles, column);

        ArrayList<Tiles> temp2 = new ArrayList<>(player.getBookshelf().getTiles().getColumn(column));
        Pair<ArrayList<Tiles>, Integer> p2 = new Pair<>(temp2, column);

        notifier.firePropertyChange(new PropertyChangeEvent(player, "bookshelf", p1, p2));


        //Checks if player filled his bookshelf
        if(!isFinished && player.getBookshelf().checkEndGame()){
            isFinished=true;
            player.setWinnerPoint(1);
            PropertyChangeEvent evt1 = new PropertyChangeEvent(player, "points",
                    publicPoints.get(players.indexOf(player)),player.getPublicPoint());

            notifier.firePropertyChange(evt1);

        }

        //Advances turn
        nextTurn();

    }

    //public void saveState(){}




    //Checks

    /**
     * If possible, removes the selected tiles from the board.
     * @param points The array of points you want to remove
     * @param player The player who wants to remove the tiles
     * @return Return true if the move is legitimate, false if it is not.
     */
    private boolean checkRemoveLegit(ArrayList<Point> points, Player player){
        //Check if the player requesting the move is the current player
        if(!player.equals(currPlayer)) return false;
        //check if the selected tiles can actually be selected
        else if(!checkPointArrayDomain(points)) return false;
       else return true;
    }


    /**
     * Checks if the selected tiles can actually be removed from the board
     * @param points Array of coordinates of the tiles
     * @return Returns true if the array of coordinates can actually be removed, false otherwise.
     */
    private boolean checkPointArrayDomain(ArrayList<Point> points){
        //check if the array is not null
        if(points!=null){
            //check the length of the array
            if(points.size()>3) return false;
            else {  //check if the tiles are adjacent
                if(!Board.checkAdjacentTiles(points)) return false;}

            //Check if the selected tiles are allowed and not empty
            for(Point p : points){
                if(!checkBoardDomain(p)) return false;
            }
            return true;}
        else return false;

    }

    /**
     *  Check if the player can add tiles to his bookshelf
     * @param player The player trying to add tiles to his bookshelf
     * @param col    The column number where to add the tiles
     * @param size   The number of tiles you want to add
     * @return  True if the move is possible, false otherwise.
     */
    private boolean checkAddLegit(Player player,int col,int size){
        //Check if the player requesting the move is the current player
        if(!player.equals(currPlayer)) return false;
        //Check if the selected column exists and if there is enough empty space
        if(!checkColumn(col,size)) return false;
        return true;
    }


    /**
     * Check if the selected column exists and if there is enough empty space
     * @param col
     * @param size The number of tiles you want to add
     * @return
     */
    private boolean checkColumn(int col,int size){
        if(col<0||col>5) return false;
        if(!currPlayer.getBookshelf().checkColumns(size,col)) return false;
        return true;}


    /**
     * Checks if the tiles of coordinates p can be selected (it's in the board, is allowed and not empty)
     * @param p the coordinates of the tiles
     * @return Return true if the chosen tile can be selected
     */
    private boolean checkBoardDomain(Point p){
        if(p.getX()<0 || p.getX()>8) return false;
        else if(p.getY()<0 || p.getY()>8) return false;
        else if(board.getGamesBoard().getTile(p).equals(Tiles.NOTALLOWED)) return false;
        else if(board.getGamesBoard().getTile(p).equals(Tiles.EMPTY)) return false;
        else return true;}







    //PRIVATE METHODS : UTILITY

    /**
     * Initializes common objectives
     */
    private void commonobjInit() {
       commonobj = CommonObjective.randomSubclass(2);
    }

    /**
     * Initializes private objectives
     */
    private void personalobjInit() {
        ArrayList<PersonalObjective> temp = PersonalObjective.randomSubclass(players.size());
        for(int i = 0;i<players.size();i++){
            players.get(i).setPersonalObjective(temp.get(i));
        }
    }


    /**
     * Updates and sets the current player's :
     * -Vicinity points
     * -Common objective points
     * -Personal objective points
     */
    private void updatePoints(){


        currPlayer.setVicinityPoint( currPlayer.getBookshelf().checkVicinityPoints());
        currPlayer.getPersonalObjective().personalObjectivePoint(currPlayer);
        for(CommonObjective o : commonobj){
             o.commonObjPointsCalculator(currPlayer,players.size());
        }


        //Notify the changes to the views
        PropertyChangeEvent evt = new PropertyChangeEvent(currPlayer, "points",
                publicPoints.get(players.indexOf(currPlayer)),currPlayer.getPublicPoint());

        notifier.firePropertyChange(evt);

    }


    /**
     * Update the points of the current player,
     * select the next player and calls the endGame function if the last turn has been played.
     * Also resets the board when needed
     * @return Return the player who will play the next turn
     */
    private void nextTurn(){

        //Update the points
        updatePoints();

        //changes current player
        currPlayer=nextPlayer;
        if(currPlayer==players.get(players.size()-1)){ nextPlayer = players.get(0);}
        else nextPlayer = players.get(players.indexOf(currPlayer)+1);


        //checks if the board needs to reset
        if(board.checkBoardReset()) board.boardResetENG();

        //checks if someone completed all their bookshelf
        if(isFinished && currPlayer.equals(players.get(0))) endGame();

    }
    /**
     * Select the winner of the game and ends it
     */
    private void endGame(){
        selectWInner();
    }

    /**
     * Select the player who won the game
     */
    private void selectWInner(){
        int winnerpos=0;
        for( int i = 0; i< players.size(); i++){
            if(players.get(i).getPrivatePoint()>players.get(winnerpos).getPrivatePoint()) winnerpos=i;
        }

        winner = players.get(winnerpos);

    }





    //GETTERS AND SETTERS: USELESS FOR NOW


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * @return Array of all the common objectives
     */
    public ArrayList<CommonObjective> getCommonobj() {
        return commonobj;
    }

    /**
     * @return Array of all the personal objectives of all players in the game
     */
    public ArrayList<PersonalObjective> getPersobj(){
        ArrayList<PersonalObjective> persobj = new ArrayList<>();
        for( Player p : players){
            persobj.add(p.getPersonalObjective());
        }
        return persobj;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public boolean isFinished() {
        return isFinished;
    }




}

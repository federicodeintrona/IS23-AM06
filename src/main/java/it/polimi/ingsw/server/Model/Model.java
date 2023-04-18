package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.server.VirtualView;
import org.javatuples.Pair;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


public class Model  {
    private static final int numberOfCommonObjectives=2;


    private GameState state = GameState.STARTING;
    private  Board board;
    private  ArrayList<Player> players;
    private ArrayList<VirtualView> virtualViews;
    private  ArrayList<CommonObjective> commonObj = new ArrayList<>();

    private Player currPlayer;
    private Player nextPlayer;
    private Player winner;
    private ArrayList<Tiles> selectedTiles = new ArrayList<>();

    private boolean isFinished = false;

    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);

    private final CheckManager checks = new CheckManager(selectedTiles);


    //Probably temporary, just used for notification
    private final ArrayList<Integer> privatePoints = new ArrayList<>();

    private final ArrayList<Integer> publicPoints = new ArrayList<>();



    //Constructors

    public Model(){

    }
    public Model(ArrayList<Player> players) {
        this.players = players;
    }

    public Model(ArrayList<Player> players, ArrayList<VirtualView> views) {
        this.players = players;
        this.virtualViews = views;
        this.currPlayer = players.get(0);
        this.nextPlayer = players.get(1);
    }



    //PUBLIC METHODS

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
        for (VirtualView v : virtualViews){
            notifier.addPropertyChangeListener(v);
        }


        //Change game state
        state = GameState.CHOOSING_TILES;

    }


    /**
     * Removes an array of tiles from the board if the move is legitimate.
     * Also notifies the views of changes
     * @param points  The position of the tiles
     * @throws MoveNotPossible if the game is not in the right state
     * @throws NotCurrentPlayer if the player is not the current player
     * @throws IllegalArgumentException if the array points is null
     * @throws TooManySelected if the array points is too long
     * @throws TilesNotAdjacent if the tiles are not adjacent
     * @throws OutOfDomain if at least one of the points is outside the board
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed
     */
    public void removeTileArray(Player player,ArrayList<Point> points) throws MoveNotPossible{

        //Checks move legitimacy
        updateCheckManager(state,currPlayer);
        checks.checkRemoveLegit(points,player,board);

        //Change game state
        state = GameState.CHOOSING_ORDER;

        //Notify the views and add the removed tiles to the selectedTiles array
        for (Point point : points) {

            //Adding the removed tiles to selectedTiles array
            selectedTiles.add(board.getGamesBoard().getTile(point));

            //notification
            Pair<Tiles, Point> p1 = new Pair<>(board.getGamesBoard().getTile(point), point);
            Pair<Tiles, Point> p2 = new Pair<>(Tiles.EMPTY, point);
            notifier.firePropertyChange(new PropertyChangeEvent(board, "board", p1, p2));

        }

        //Remove the selected tiles
        board.remove(points);


    }


    /**
     * Adds the selected tiles in the player's bookshelf.
     * Since adding tiles to your bookshelf is the last action you can do on your turn,
     * it also calls the nextTurn function
     * @param player  The player who owns the Bookshelf
     * @param column   The column where you want to add the tiles
     * @throws OutOfDomain if requested column does not exists
     * @throws ColumnIsFull if the requested column is full
     * @throws MoveNotPossible if game is not in the right state
     * @throws NotCurrentPlayer if the player requesting the move is not the current player
     */
    public void addToBookShelf(Player player,  int column) throws MoveNotPossible{


        //Check for move legitimacy
        updateCheckManager(state,currPlayer);
        checks.checkAddLegit(player,column,selectedTiles.size());

        //Change game state
        state = GameState.CHOOSING_TILES;

        //Notifying changes (communicating a lot of redundant information for now, I'll change it when we do the views)
        ArrayList<Tiles> temp1 = new ArrayList<>(player.getBookshelf().getTiles().getColumn(column));
        Pair<ArrayList<Tiles>, Integer> p1 = new Pair<>(temp1, column);

        //Add to bookshelf
        player.getBookshelf().addTile(selectedTiles, column);

        //Notifying changes pt.2, (communicating a lot of redundant information for now, I'll change it when we do the views)
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


        //Empties the selected tile array
        selectedTiles.clear();


        //Advances turn
        nextTurn();


    }


    /**
     *  Swap the order of the array of selected tiles to the order describes in the array ints.
     *       ex. oldSelectedTiles[G,B,Y], ints[2,1,3] --> newSelectedTiles[B,G,Y]
     * @param ints  The new order of the array
     * @param player  The player requesting the move
     * @throws MoveNotPossible The game is not in the right state
     * @throws NotCurrentPlayer The player is not the current player
     * @throws IllegalArgumentException The ints array is not of appropriate content
     * @throws TooManySelected if the array is not of appropriate size
     */
    public void swapOrder(ArrayList<Integer> ints,Player player) throws MoveNotPossible,IllegalArgumentException {



        //Check the legitimacy of the move
        updateCheckManager(state,currPlayer);
        checks.swapCheck(ints,player);

        //Swaps the array around
        ArrayList<Tiles> array = new ArrayList<>();
        array.addAll(selectedTiles);
        for (int i = 0; i < ints.size(); i++) {
            selectedTiles.set(i, array.get(ints.get(i)));
        }

        //Change game state
        state = GameState.CHOOSING_COLUMN;

    }









    //PRIVATE METHODS : UTILITY


    /**
     * Initializes common objectives
     */
    private void commonobjInit() {
       commonObj = CommonObjective.randomSubclass(numberOfCommonObjectives);
    }



    /**
     * Initializes private objectives
     */
    private void personalobjInit() {
        PersonalObjective po;
        ArrayList<PersonalObjective> tmp=new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            //check if there are NOT 2 equals PersonalObjective
            do {
                po=new PersonalObjective();
            }while (tmp.contains(po));
            tmp.add(po);
        }


        for(int i = 0;i<players.size();i++){
            players.get(i).setPersonalObjective(tmp.get(i));
        }

    }


    /**
     * Updates and sets the current player's :
     * -Vicinity points
     * -Common objective points
     * -Personal objective points
     */
    private void updatePoints(){

        //Updates vicinity, common objective and personal objective points
        currPlayer.setVicinityPoint( currPlayer.getBookshelf().checkVicinityPoints());
        currPlayer.getPersonalObjective().personalObjectivePoint(currPlayer);
        for(CommonObjective o : commonObj){
             o.commonObjPointsCalculator(currPlayer,players.size());
        }


        //Notify the changes to the views
        PropertyChangeEvent evt = new PropertyChangeEvent(currPlayer, "points",
                publicPoints.get(players.indexOf(currPlayer)),currPlayer.getPublicPoint());

        notifier.firePropertyChange(evt);

    }



    private void updateCheckManager(GameState state,Player current){
        checks .setState(state);
        checks.setCurrPlayer(current);
    }


    /**
     * Update the points of the current player,
     * select the next player and calls the endGame function if the last turn has been played.
     * Also resets the board when needed
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





    //TO BE FINISHED
    /**
     * Select the winner of the game and ends it
     */
    private void endGame(){
        state = GameState.ENDING;
        selectWInner();
    }




    /**
     * Select the player who won the game
     */
    private void selectWInner(){
        int winnerpos=0;
        for( int i = 0; i< players.size(); i++){
            if(players.get(i).getPrivatePoint() >players.get(winnerpos).getPrivatePoint()) winnerpos=i;
        }

        winner = players.get(winnerpos);

    }







    //GETTERS AND SETTERS


    /**
     * Return the array of all players
     * @return  Array of all players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }



    /**
     * Return the board
     * @return the board
     */
    public Board getBoard() {
        return board;
    }


    /**
     *  Returns the current state of the game
     * @return The current state of the game
     */
    public GameState getState() {
        return state;
    }



    /**
     * Sets the state of the game
     * @param state The state you want to set the game to
     */
    public void setState(GameState state) {
        this.state = state;
    }



    /**
     * @return ArrayList of all the common objectives
     */
    public ArrayList<CommonObjective> getCommonObj() {
        return commonObj;
    }


    /**
     * Set the current player (Just for Testing purposes)
     * @param currPlayer The player to set as current player
     */
    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }


    /**
     * Return true if the game is finished, false otherwise
     * @return isFinished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Sets the selectedTiles array
     * @param selectedTile Array you want to set selectedTiles as
     */
    public void setSelectedTiles(ArrayList<Tiles> selectedTile) {
        this.selectedTiles = selectedTile;
    }


    /**
     * Returns the selectedTiles array
     * @return selectedTiles ArrayList
     */
    public ArrayList<Tiles> getSelectedTiles() {
        return selectedTiles;
    }

    public void addPlayers(ArrayList<Player> players){
        this.players.addAll(players);
    }

    /**
     * @return ArrayList of all the personal objectives of all players in the game
     */
  /* public ArrayList<PersonalObjective> getPersobj(){
        ArrayList<PersonalObjective> persobj = new ArrayList<>();
        for( Player p : players){
            persobj.add(p.getPersonalObjective());
        }
        return persobj;
    }*/

}

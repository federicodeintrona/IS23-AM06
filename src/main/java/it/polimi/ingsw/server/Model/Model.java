package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class Model  {
    private int gameID;
    private Board board;
    private ArrayList<Player> players;
    private ArrayList<VirtualView> virtualViews;
    private ArrayList<CommonObjective> commonObj = new ArrayList<>();

    //Utility variables
    private GameState state = GameState.STARTING;
    private Player currPlayer;
    private Player nextPlayer;
    private Player winner;
    private ArrayList<Tiles> selectedTiles = new ArrayList<>();
    private boolean isFinished = false;

    //Utility objects
    private final CheckManager checks = new CheckManager(selectedTiles);
    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);

    /* Notification event pattern:
     (source object to send, receiver name ("all" or username),
     username of the owner of the sent object ("0" if there isn't any ), property name)

      I know it's not the correct way to use PropertyChangeEvent, but it's easier this way, doesn't
      cause any problem and I wanted to use the PropertyChangeSupport object*/



    //Probably temporary, just used for notification
    //Array of the old points values to see if they have changed
    private final ArrayList<Integer> privatePoints = new ArrayList<>();
    private final ArrayList<Integer> publicPoints = new ArrayList<>();

    //Constant value
    private static final int numberOfCommonObjectives=2;

    //Constructors

    public Model(){

    }
    public Model(ArrayList<Player> players) {
        this.players = players;
    }

    public Model(ArrayList<Player> players, ArrayList<VirtualView> views) {
        this.players = players;
        this.virtualViews = views;
    }

    public Model(ArrayList<Player> players, ArrayList<VirtualView> views, Controller controller) {
        this.players = players;
        this.virtualViews = views;
        notifier.addPropertyChangeListener("end", controller);
    }
    public Model(int iD, ArrayList<Player> players, ArrayList<VirtualView> views, Controller controller) {
        this.gameID = iD;
        this.players = players;
        this.virtualViews = views;
        notifier.addPropertyChangeListener("end", controller);
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


        //Add the views as change listeners
        for (VirtualView v : virtualViews){
            System.out.println("vv model init :"+v.getUsername());
            notifier.addPropertyChangeListener("all",v);
            notifier.addPropertyChangeListener(v.getUsername(),v);
        }


        Random random = new Random();
        int index = random.nextInt(players.size());
        currPlayer = players.get(0) ;
        selectNext();


        //Initializes the arrays of points
        for(Player p : players){
            privatePoints.add(p.getPrivatePoint());
            publicPoints.add(p.getPublicPoint());

            System.out.println("po");
            //Notify personal objective
            notifier.firePropertyChange(new PropertyChangeEvent(
                    p.getPersonalObjective().getCard(), p.getUsername(),  p.getUsername(),"personalObj" ));

            System.out.println("book");

            //Notify Bookshelf
            notifier.firePropertyChange(new PropertyChangeEvent(p.getBookshelf().getTiles(),
                    "all", p.getUsername(), "bookshelf"));

            System.out.println("pubp");
            //Notify publicPoints
            notifier.firePropertyChange(new PropertyChangeEvent(p.getPublicPoint(), "all",
                    p.getUsername(), "publicPoints"));

            System.out.println("perp");
            //Notify privatePoints
            notifier.firePropertyChange(new PropertyChangeEvent(p.getPrivatePoint(), p.getUsername(),
                    p.getUsername(), "privatePoints"));

        }

        System.out.println("board");
        //Notify Board
        notifier.firePropertyChange(new PropertyChangeEvent(
                board.getGamesBoard(), "all", "0","board" ));

        //Notify PlayerNames
        notifier.firePropertyChange(new PropertyChangeEvent(
                        players.stream().map(Player::getUsername).toList(), "all", "0","playerNames" ));

        //Notify commonObjectives
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getNum).toList(), "all", "0","commonObj" ));

        //Notify curr and next Player
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getUsername(), "all",
                currPlayer.getUsername(), "currPlayer"));

        notifier.firePropertyChange(new PropertyChangeEvent(nextPlayer.getUsername(), "all",
                currPlayer.getUsername(), "nextPlayer"));

        //Notify game Start
        notifier.firePropertyChange(new PropertyChangeEvent(
               true, "all", "0","start" ));



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


        }

        //Remove the selected tiles
        board.remove(points);


        //Notify Selected Tiles
        notifier.firePropertyChange(new PropertyChangeEvent(
                selectedTiles, "all", "0","selectedTiles" ));

        //Notify Board
        notifier.firePropertyChange(new PropertyChangeEvent(
                board.getGamesBoard(), "all", "0","board" ));


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


        //Add to bookshelf
        player.getBookshelf().addTile(selectedTiles, column);

        //Notify Bookshelf
        notifier.firePropertyChange(new PropertyChangeEvent(player.getBookshelf().getTiles(),
                            "all", player.getUsername(), "bookshelf"));


        //Checks if player filled his bookshelf
        if(!isFinished && player.getBookshelf().checkEndGame()){
            isFinished=true;
            player.setWinnerPoint(1);
            player.setPublicPoint();

            notifier.firePropertyChange(new PropertyChangeEvent(player.getPublicPoint(), "all",
                    player.getUsername(), "publicPoints"));

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
            selectedTiles.set(i, array.get(ints.get(i)-1));
        }

        //Notify Selected Tiles
        notifier.firePropertyChange(new PropertyChangeEvent(
                selectedTiles, "all", "0","selectedTiles" ));



        //Change game state
        state = GameState.CHOOSING_COLUMN;


    }



    //PRIVATE METHODS : UTILITY



    /**
     * Updates and sets the current player's :
     * -Vicinity points
     * -Common objective points
     * -Personal objective points
     */
    private void updatePoints(){

        //Update points array
        publicPoints.set(players.indexOf(currPlayer),currPlayer.getPublicPoint());
        privatePoints.set(players.indexOf(currPlayer), currPlayer.getPrivatePoint());

        //Updates vicinity, common objective and personal objective points
        currPlayer.setVicinityPoint( currPlayer.getBookshelf().checkVicinityPoints());
        currPlayer.setPersonalObjectivePoint(currPlayer.getPersonalObjective().personalObjectivePoint(currPlayer));

        for(CommonObjective o : commonObj){
             o.commonObjPointsCalculator(currPlayer,players.size());
        }

        currPlayer.setPrivatePoint();
        currPlayer.setPublicPoint();

        //Notify publicPoints
        if(currPlayer.getPublicPoint()!=publicPoints.get(players.indexOf(currPlayer))) {

            notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getPublicPoint(), "all",
                    currPlayer.getUsername(), "publicPoints"));

        }

        //Notify privatePoints
        if(currPlayer.getPrivatePoint()!=privatePoints.get(players.indexOf(currPlayer))) {

            notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getPrivatePoint(), currPlayer.getUsername(),
                    currPlayer.getUsername(), "privatePoints"));

        }
    }





    /**
     * Update the points of the current player,
     * select the next player and calls the endGame function if the last turn has been played.
     * Also resets the board when needed
     */
    private void nextTurn(){

        //Update the points
        updatePoints();


        //Update current player
        currPlayer=nextPlayer;
        selectNext();


        //checks if the board needs to reset
        if(board.checkBoardReset()) board.boardResetENG();

        //checks if someone completed all their bookshelf
        if(isFinished && currPlayer.equals(players.get(0))) endGame();


        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getUsername(), "all",
                currPlayer.getUsername(), "currPlayer"));
        notifier.firePropertyChange(new PropertyChangeEvent(nextPlayer.getUsername(), "all",
                currPlayer.getUsername(), "nextPlayer"));

    }



    /**
     * Select the winner of the game and ends it
     */
    private void endGame(){
        state = GameState.ENDING;
        selectWInner();
        //Notify game Start
        notifier.firePropertyChange(new PropertyChangeEvent(true, "all", "0","start" ));
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

        notifier.firePropertyChange(new PropertyChangeEvent(winner.getUsername(),"all","0","winner"));

    }

    private void selectNext() {
        if(currPlayer==players.get(players.size()-1)){ nextPlayer = players.get(0);}
        else nextPlayer = players.get(players.indexOf(currPlayer)+1);
    }

    private void updateCheckManager(GameState state,Player current){
        checks.setState(state);
        checks.setCurrPlayer(current);
    }


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
        ArrayList<PersonalObjective> tmp = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();
        Random rdm = new Random();
        int num;

        for (int i = 0; i < players.size(); i++) {
            //check if there are NOT 2 equals PersonalObjective

            do {
                num = rdm.nextInt(Define.NUMBEROFPERSONALOBJECTIVE.getI());
            } while (numbers.contains(num));

            numbers.add(num);
            players.get(i).setPersonalObjective(new PersonalObjective(num));


        }
    }





  //  ScheduledExecutorService e1 = Executors.newScheduledThreadPool();
 //   ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();




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

    //TEST
    public Player getCurrPlayer() {
        return currPlayer;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public ArrayList<Integer> getPrivatePoints() {
        return privatePoints;
    }

    public ArrayList<Integer> getPublicPoints() {
        return publicPoints;
    }

    public int getGameID() {
        return gameID;
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

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<VirtualView> getVirtualViews() {
        return virtualViews;
    }

    public void setVirtualViews(ArrayList<VirtualView> virtualViews) {
        this.virtualViews = virtualViews;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * @return ArrayList of all the personal objectives of all players in the game
     */
   private ArrayList<PersonalObjective> getPersObj(){
        ArrayList<PersonalObjective> persobj = new ArrayList<>();
        for( Player p : players){
            persobj.add(p.getPersonalObjective());
        }
        return persobj;
    }


    private void updatePlayer(Player p){


        //Notify Board
        notifier.firePropertyChange(new PropertyChangeEvent(board.getGamesBoard(), p.getUsername(), "0","board" ));

        //Notify PlayerNames
        notifier.firePropertyChange(new PropertyChangeEvent(
                players.stream().map(Player::getUsername).toList(), p.getUsername(), "0","playerNames" ));

        //Notify commonObjectives
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getNum).toList(), p.getUsername(), "0","commonObj" ));

        //Notify personal objective
        notifier.firePropertyChange(new PropertyChangeEvent(
                p.getPersonalObjective().getCard(), p.getUsername(),  p.getUsername(),"personalObj" ));

        //Notify currPlayer
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getUsername(), p.getUsername(),
                currPlayer.getUsername(), "currPlayer"));

        //Notify privatePoints
        notifier.firePropertyChange(new PropertyChangeEvent(p.getPrivatePoint(), p.getUsername(),
                p.getUsername(), "privatePoints"));


        for(Player player : players){

            //Notify Bookshelf
            notifier.firePropertyChange(new PropertyChangeEvent(player.getBookshelf().getTiles(),
                    p.getUsername(), player.getUsername(), "bookshelf"));

            //Notify publicPoints
            notifier.firePropertyChange(new PropertyChangeEvent(player.getPublicPoint(), p.getUsername(),
                    player.getUsername(), "publicPoints"));
        }



    }

}

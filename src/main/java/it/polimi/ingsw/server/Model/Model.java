package it.polimi.ingsw.server.Model;
import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimerInterface;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.*;


public class Model implements TimerInterface {
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
    private int connectedPlayers;

    //Timer
    private int time = 0;
    private Timer timer;
    private boolean timerIsOn=false;
    private static final int initialDelay = 50;
    private static final int delta = 5000;

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

    //Constructors

    public Model(){}
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
    public synchronized void  initialization()  {

        System.out.println("Game number: "+gameID+" is starting...");

        //Create and initialize the board
        board = new Board(players.size(), new Sachet());
        board.BoardInitialization();

        //Initialize common and personal objectives
        commonobjInit();
        personalobjInit();


        //Add the views as change listeners
        for (VirtualView v : virtualViews){
            notifier.addPropertyChangeListener("all",v);
            notifier.addPropertyChangeListener(v.getUsername(),v);
        }


        Random random = new Random();
        int index = random.nextInt(players.size());
        currPlayer = players.get(index) ;
        selectNext();


        //Initializes the arrays of points
        for(Player p : players){
            privatePoints.add(p.getPrivatePoint());
            publicPoints.add(p.getPublicPoint());

            if(!p.isDisconnected()) connectedPlayers++;

            //Notify personal objective
            notifier.firePropertyChange(new PropertyChangeEvent(
                    p.getPersonalObjective().getCard(), p.getUsername(),  p.getUsername(),"personalObj" ));


            //Notify Bookshelf
            notifier.firePropertyChange(new PropertyChangeEvent(p.getBookshelf().getTiles(),
                    "all", p.getUsername(), "bookshelf"));

            //Notify publicPoints
            notifier.firePropertyChange(new PropertyChangeEvent(p.getPublicPoint(), "all",
                    p.getUsername(), "publicPoints"));


            //Notify privatePoints
            notifier.firePropertyChange(new PropertyChangeEvent(p.getPrivatePoint(), p.getUsername(),
                    p.getUsername(), "privatePoints"));

        }

        //Notify Board
        notifier.firePropertyChange(new PropertyChangeEvent(
                new Matrix(board.getGamesBoard()), "all", "0","board" ));

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
     * Initializes common objectives
     */
    private void commonobjInit() {
        commonObj = CommonObjective.randomSubclass(Define.NUMBEROFCOMMONOBJECTIVE.getI());
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
    public synchronized void removeTileArray(Player player,ArrayList<Point> points) throws MoveNotPossible{

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
                new Matrix(board.getGamesBoard()), "all", "0","board" ));

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
    public synchronized void addToBookShelf(Player player,  int column) throws MoveNotPossible{

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
        if(connectedPlayers>=1) {
            nextTurn();
        }else state = GameState.STOPPED;

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
    public synchronized void swapOrder(ArrayList<Integer> ints,Player player) throws MoveNotPossible,IllegalArgumentException {

        //Check the legitimacy of the move
        updateCheckManager(state,currPlayer);
        checks.swapCheck(ints,player);

        //Swaps the array around
        ArrayList<Tiles> array = new ArrayList<>(selectedTiles);

        for (int i = 0; i < ints.size(); i++) {
            selectedTiles.set(i, array.get(ints.get(i)-1));
        }

        //Notify Selected Tiles
        notifier.firePropertyChange(new PropertyChangeEvent(
                selectedTiles, "all", "0","selectedTiles" ));

        //Change game state
        state = GameState.CHOOSING_ORDER;
    }



    //PRIVATE METHODS : UTILITY

    /**
     * Updates and sets the current player's points :
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

        currPlayer.updatePoints();

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
     * Updates the CheckManager state with the current game state and the current player.
     * @param state The current game state.
     * @param current The current player.
     */
    private void updateCheckManager(GameState state,Player current){
        checks.setState(state);
        checks.setCurrPlayer(current);
    }


    /** Advances the turn:
     * Update the points of the current player,
     * select the next player and calls the endGame function if the last turn of the game has been played.
     * Also resets the board when needed.
     */
    private void nextTurn(){

        if(connectedPlayers<1){
            endGame();
            return;
        }

        //Update the points
        updatePoints();

        //Change game state
        state = GameState.CHOOSING_TILES;

        //Update current player
        playerSelection();


        //checks if the board needs to reset
            if(board.checkBoardReset()){

                board.boardResetENG();

                //Notify Board
                notifier.firePropertyChange(new PropertyChangeEvent(
                        new Matrix(board.getGamesBoard()), "all", "0","board" ));
            }

        //checks if someone completed all their bookshelf
        if(isFinished && currPlayer.equals(players.get(0))) endGame();

        //Notifies the new current and next players
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getUsername(), "all",
                currPlayer.getUsername(), "currPlayer"));
        notifier.firePropertyChange(new PropertyChangeEvent(nextPlayer.getUsername(), "all",
                currPlayer.getUsername(), "nextPlayer"));

    }


    private void playerSelection(){
            selectCurr();
            selectNext();
    }

    private void selectCurr(){
        if(!nextPlayer.isDisconnected()) {
            currPlayer = nextPlayer;
        }else {

            Optional<Player> player = selectNextNotDisconnected(currPlayer);
            currPlayer = player.orElseGet(this::selectNextPlayer);

        }
    }


    /**
     * Selects the next player in line to play the game.
     * Skips disconnected player.
     * If there is only one connected player, stops the game and starts the end timer
     * (the next player is set to the successive player in line even if it is disconnected
     *  to make sure that if a player reconnects in time the game will select the next player properly)
     */
    private void selectNext() {
        Optional<Player> player=selectNextNotDisconnected(currPlayer);
        nextPlayer = player.orElseGet(this::selectNextPlayer);
    }

    /**
     * Returns the next player in line even if it is disconnected.
     * @return The next player.
     */
    private Player selectNextPlayer(){
        return currPlayer == players.get(players.size()-1)?
                             players.get(0):
                             players.get(players.indexOf(currPlayer)+1);
    }

    /**
     * Returns the next connected player in line.
     *
     * @param current The player of whom you want to select the next
     * @return The Optional of the next connected player.
     * Or an Empty Optional if there is only 1 player connected.
     */
    private Optional<Player> selectNextNotDisconnected(Player current){

        Player player;

        for(int i = 0; i<players.size()-1;i++){
            player=selectNextPlayer();
            if(!player.isDisconnected()){
                return Optional.of(player);
            }
        }

        return Optional.empty();
    }

    /**
     * Disconnects the selected player:
     * starts the end timer if there is only one player left.
     * @param player The player to disconnect.
     */
    public synchronized void disconnectPlayer(Player player,VirtualView view){
        System.out.println(player.getUsername() + " has disconnected");
       //Disconnect the player
       player.setDisconnected(true);

       virtualViews.remove(view);
       notifier.removePropertyChangeListener(view);

       connectedPlayers--;
       if(connectedPlayers<=1&&!timerIsOn) startEndTimer();
       if(player.equals(currPlayer)) nextTurn();
    }


    /**
     * Reconnects the selected player.
     * If the game was stopped due to and insufficient number of players,
     * checks if there are enough players and if so makes the game continue.
     * @param player The player to reconnect.
     */
    public synchronized void playerReconnection(Player player,VirtualView view){
        System.out.println(player.getUsername() + " has reconnected");

        virtualViews.add(view);
        notifier.addPropertyChangeListener("all",view);
        notifier.addPropertyChangeListener(view.getUsername(),view);

        player.setDisconnected(false);
        connectedPlayers++;

        checkRestart(player);

        updatePlayer(player);
    }

    private void checkRestart(Player player){

        if(connectedPlayers==2) {
            timer.cancel();
            timerIsOn=false;
        }

        if(currPlayer.isDisconnected()||state.equals(GameState.STOPPED)) nextTurn();

    }

    private void stopGame(){
        System.out.println("The game has been stopped because too many players have disconnected");
        if(!timerIsOn)startEndTimer();
    }

    private void startEndTimer(){
        System.out.println("start timer");
        timerIsOn=true;
        if(timer!=null) timer.cancel();
        timer = new Timer();
        TimerTask task = new TimerCounter(this);
        timer.schedule(task, initialDelay, delta);
    }

    //Game ending methods

    /**
     * Select the winner of the game and ends it
     */
    private synchronized void endGame(){
        state = GameState.ENDING;
        selectWInner();
        //Notify game Start
        notifier.firePropertyChange
                (new PropertyChangeEvent(true, "all", "0","end" ));
    }


    /**
     * Select the player who won the game
     */
    private void selectWInner(){

        winner = connectedPlayers==1?disconnectionWinner():playerWithMostPoints();
        notifier.firePropertyChange(
                new PropertyChangeEvent(winner.getUsername(),"all","0","winner"));

    }

    private Player disconnectionWinner(){
        Player player;
        int index=0;

        while(index!=players.size()) {
            player = players.get(index);
            if (!player.isDisconnected()) {
                return player;
            }
            index++;
        }
        return playerWithMostPoints();

    }

    private Player playerWithMostPoints(){
        int winnerpos=0;

        for( int i = 0; i< players.size(); i++){
            if(players.get(i).getPrivatePoint() >= players.get(winnerpos).getPrivatePoint())
                winnerpos=i;
        }

        return players.get(winnerpos);
    }

    //TimerInterface Methods

    @Override
    public void disconnect() {
        endGame();
    }

    @Override
    public int updateTime() {
        time++;
        return time;
    }

    @Override
    public String getErrorMessage() {
        return "There is only one player left. Ending game number: " +gameID;
    }





    /**
     * Updates the selected player client state using his virtual view.
     * @param p The player to update
     */
    private void updatePlayer(Player p){
        //Notify Board
        notifier.firePropertyChange(new PropertyChangeEvent(new Matrix(board.getGamesBoard()), p.getUsername(), "0","board" ));

        //Notify PlayerNames
        notifier.firePropertyChange(new PropertyChangeEvent(
                players.stream().map(Player::getUsername).toList(), p.getUsername(), "0","playerNames" ));

        //Notify commonObjectives
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getNum).toList(), p.getUsername(), "0","commonObj" ));

        //Notify personal objective
        notifier.firePropertyChange(new PropertyChangeEvent(
                p.getPersonalObjective().getCard(), p.getUsername(),  p.getUsername(),"personalObj" ));

        //Notify currPlayer and nextPlayer
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getUsername(), p.getUsername(),
                currPlayer.getUsername(), "currPlayer"));

        notifier.firePropertyChange(new PropertyChangeEvent(nextPlayer.getUsername(), "all",
                currPlayer.getUsername(), "nextPlayer"));

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


        //Notify game Start
        notifier.firePropertyChange(new PropertyChangeEvent(
                true, "all", "0","start" ));


    }


    //GETTERS AND SETTERS


    /**
     * Return the array of all players
     * @return  Array of all players
     */
    public  ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Return the board
     * @return the board
     */
    public  Board getBoard() {
        return board;
    }

    /**
     *  Returns the current state of the game
     * @return The current state of the game
     */
    public  GameState getState() {
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
    public synchronized void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
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
    public synchronized void setSelectedTiles(ArrayList<Tiles> selectedTile) {
        this.selectedTiles = selectedTile;
    }


    /**
     * Returns the selectedTiles array
     * @return selectedTiles ArrayList
     */
    public ArrayList<Tiles> getSelectedTiles() {
        return selectedTiles;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setVirtualViews(ArrayList<VirtualView> virtualViews) {
        this.virtualViews = virtualViews;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

}

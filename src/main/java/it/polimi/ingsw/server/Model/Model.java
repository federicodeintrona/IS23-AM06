package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Tile;
import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Chat;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimerInterface;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;


/**
 * <p>Class used to control the evolution of the game.</p>
 * <p>It holds and manages all the information about a single game:</p>
 * <ul>
 *     <li>board;</li>
 *     <li>bookshelves;</li>
 *     <li>players (and their points);</li>
 *     <li>chat logs;</li>
 *     <li>etc.</li>
 * </ul>
 * <p>The model's public methods allow the players to modify the state of game by:
 *      <ul>
 *          <li>removing tiles from the board;</li>
 *          <li>changing the order of the selected tiles;</li>
 *          <li>adding the tiles to their bookshelves.</li>
 *      </ul>
 *
 *     <p>
 *         The model also manages the turns. When a player adds a tile to his bookshelf
 *         the game advances to the next turn since it is the last move a player can do during
 *         his turn.
 *     </p>
 *     <p>
 *         The model selects the next non-disconnected player in line; if there is only one player left
 *         it selects the first disconnected player it finds to stop the game and wait for at least another
 *         player to reconnect or it will end the game after 2 minutes.
 *     </p>
 *     <p>
 *         The model also notifies the virtual views of each player with the necessary information for the
 *         client such as the points, the state of the board, the bookshelves, and the current player.
 *     </p>
 */
public class Model implements TimerInterface {
    private int gameID;
    private Board board;
    private ArrayList<Player> players;
    private ArrayList<VirtualView> virtualViews;
    private ArrayList<CommonObjective> commonObj = new ArrayList<>();

    //Utility variables
    private GameState state = GameState.STARTING;
    private Player chairPlayer;
    private Player currPlayer;
    private Player nextPlayer;
    private ArrayList<Tile> selectedTiles = new ArrayList<>();
    private ArrayList<Point> removedTilesCoord;
    private boolean isFinished = false;
    private int connectedPlayers;
    private final Chat publicChat = new Chat();
    private final HashMap<String, ChatController> allPlayersChats = new HashMap<>();


    //Timer
    private int time = 0;
    private Timer timer;
    private boolean timerIsOn=false;
    private static final int initialDelay = 50;
    private static final int delta = 6000;

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

    /**
     * Main constructor for the model.
     */
    public Model(){}



    //PUBLIC METHODS

    /**
     * <p>Method to initialize the board and the objectives:</p>
     * create and initialize the board,initialize common and personal objectives,
     * add the views as change listeners,initialize the arrays of points.
     */
    public void  initialization()  {

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

        //Randomize the first player
        Random random = new Random();
        int index = random.nextInt(players.size());
        chairPlayer = players.get(index);
        currPlayer = chairPlayer;
        selectNext();

        //Notify data to the virtual views
        notifyCommonData();

        //Initializes the arrays of points and notify data
        for(Player p : players){

            if(!p.isDisconnected()) connectedPlayers++;

            notifyPersonalData(p);

            //Notify Bookshelf
            notifier.firePropertyChange(new PropertyChangeEvent(p.getBookshelf().getTiles(),
                    "all", p.getUsername(), "bookshelf"));

            //Notify publicPoints
            notifier.firePropertyChange(new PropertyChangeEvent(p.getPublicPoint(), "all",
                    p.getUsername(), "publicPoints"));


        }


        //Notify game Start
        notifier.firePropertyChange(new PropertyChangeEvent(
               true, "all", "0","start" ));

        //NotifyCurrPlayer
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getUsername(),"all",
                currPlayer.getUsername(), "currPlayer"));


        //Change game state
        state = GameState.CHOOSING_TILES;

    }

    /**
     * Method to initialize common objectives.
     */
    private void commonobjInit() {
        commonObj = CommonObjective.randomSubclass(Define.NUMBEROFCOMMONOBJECTIVE.getI());
    }

    /**
     * Method to initialize personal objectives.
     */
    private void personalobjInit() {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random rdm = new Random();
        int num;

        for (Player player : players) {
            //check if there are NOT 2 equals PersonalObjective

            do {
                //+1 because personal objective's number is between 1, 12
                num = rdm.nextInt(Define.NUMBEROFPERSONALOBJECTIVE.getI()) + 1;
            } while (numbers.contains(num));

            numbers.add(num);
            player.setPersonalObjective(new PersonalObjective(num));


        }
    }



    /**
     * <p>Method to remove an array of tiles from the board if the move is legitimate.</p>
     * Also notifies the views of changes.
     * @param points  The position of the tiles.
     * @param player The username of the player who wants to remove the tile.
     * @throws MoveNotPossible if the game is not in the right state.
     * @throws NotCurrentPlayer if the player is not the current player.
     * @throws IllegalArgumentException if the array points is null.
     * @throws TooManySelected if the array points is too long.
     * @throws TilesNotAdjacent if the tiles are not adjacent.
     * @throws OutOfDomain if at least one of the points is outside the board.
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed.
     */
    public synchronized void removeTileArray(Player player,ArrayList<Point> points) throws MoveNotPossible{

        //Checks move legitimacy
        updateCheckManager(state,currPlayer);
        checks.checkRemoveLegit(points,player,board);

        //Change game state
        state = GameState.CHOOSING_ORDER;

        //Save removedTiles
        removedTilesCoord = points;

        //Notify the views and add the removed tiles to the selectedTiles array
        for (Point point : points) {
            //Adding the removed tiles to selectedTiles array
            selectedTiles.add(board.getGamesBoard().getFullTile(point));
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
     * <p>Method to add the selected tiles in the player's bookshelf.</p>
     * Since adding tiles to your bookshelf is the last action you can do on your turn, it also makes the game go to the next turn.
     * @param player  The player who owns the Bookshelf.
     * @param column   The column where you want to add the tiles.
     * @throws OutOfDomain if requested column does not exists.
     * @throws ColumnIsFull if the requested column is full.
     * @throws MoveNotPossible if game is not in the right state.
     * @throws NotCurrentPlayer if the player requesting the move is not the current player.
     */
    public synchronized void addToBookShelf(Player player,  int column) throws MoveNotPossible{

        //Check for move legitimacy
        updateCheckManager(state,currPlayer);
        checks.checkAddLegit(player,column,selectedTiles.size());

        //Change game state
        state = GameState.CHOOSING_TILES;

        //Add to bookshelf
        player.getBookshelf().addFullTile(selectedTiles, column);

        //Notify Bookshelf
        notifier.firePropertyChange(new PropertyChangeEvent(player.getBookshelf().getTiles(),
                            "all", player.getUsername(), "bookshelf"));


        //Checks if player filled his bookshelf
        checkFirstToFillBookshelf(player);

        //Empties the selected tile array
        selectedTiles.clear();

        //Advances turn
        if(connectedPlayers>=1) {
            nextTurn();
        }else state = GameState.STOPPED;

    }

    /**
     * Method to check if the player is the first to fill his bookshelf, if so gives him 1 point.
     * @param player The player to check.
     */
    private void checkFirstToFillBookshelf(Player player){
        //Checks if player filled his bookshelf
        if(!isFinished && player.getBookshelf().checkEndGame()){
            isFinished=true;
            player.setWinnerPoint(1);
            player.setPublicPoint();

            notifier.firePropertyChange(new PropertyChangeEvent(player.getPublicPoint(), "all",
                    player.getUsername(), "publicPoints"));

        }
    }

    /**
     * <p>Method to swap the order of the array of selected tiles to the order describes in the array ints.</p>
     *       ex. oldSelectedTiles[G,B,Y], ints[2,1,3] --> newSelectedTiles[B,G,Y].
     * @param ints  The new order of the array.
     * @param player  The player requesting the move.
     * @throws MoveNotPossible The game is not in the right state.
     * @throws NotCurrentPlayer The player is not the current player.
     * @throws IllegalArgumentException The ints array is not of appropriate content.
     * @throws TooManySelected if the array is not of appropriate size.
     */
    public synchronized void swapOrder(ArrayList<Integer> ints,Player player) throws MoveNotPossible,IllegalArgumentException {

        //Check the legitimacy of the move
        updateCheckManager(state,currPlayer);
        checks.swapCheck(ints,player);

        //Swaps the array around
        ArrayList<Tile> array = new ArrayList<>(selectedTiles);

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
     * <p>Method that updates and sets the current player's points.</p>
     * <ul>
     *  <il>Vicinity points;</il>
     *  <il>common objective points;</il>
     *  <il>personal objective points.</il>
     * </ul>
     */
    private void updatePoints(){

        //Update points array
        //publicPoints.set(players.indexOf(currPlayer),currPlayer.getPublicPoint());
        //privatePoints.set(players.indexOf(currPlayer), currPlayer.getPrivatePoint());

        //Updates vicinity, common objective and personal objective points
        currPlayer.setVicinityPoint( currPlayer.getBookshelf().checkVicinityPoints());
        currPlayer.setPersonalObjectivePoint(currPlayer.getPersonalObjective().personalObjectivePoint(currPlayer));

        for(CommonObjective o : commonObj){
            if(o.commonObjPointsCalculator(currPlayer,players.size())){

                //Notify commonObjectivesPoints
                notifier.firePropertyChange(new PropertyChangeEvent(
                        commonObj.stream().map(CommonObjective::getPoints).toList(), "all", "0","commonObjPoints" ));


            }
        }

        currPlayer.updatePoints();

        //Notify publicPoints
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getPublicPoint(), "all",
                currPlayer.getUsername(), "publicPoints"));


        //Notify privatePoints
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getPrivatePoint(), currPlayer.getUsername(),
                currPlayer.getUsername(), "privatePoints"));


    }


    /**
     * Method that updates the CheckManager state with the current game state and the current player.
     * @param state The current game state.
     * @param current The current player.
     */
    private void updateCheckManager(GameState state,Player current){
        checks.setState(state);
        checks.setCurrPlayer(current);
    }


    /** <p>Method that advances the turn.</p>
     * <ul>
     *  <il>Update the points of the current player;</il>
     *  <il>select the next player and calls the endGame function if the last turn of the game has been played;</il>
     *  <il>also resets the board when needed.</il>
     * </ul>
     */
    private void nextTurn(){

        //End the game if there are no players left
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

        //Checks for board reset
        boardResetCheck();

        //checks if someone completed all their bookshelf
        if(isFinished && currPlayer.equals(chairPlayer)) endGame();

        //Notifies the new current and next players
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getUsername(), "all",
                currPlayer.getUsername(), "currPlayer"));
        notifier.firePropertyChange(new PropertyChangeEvent(nextPlayer.getUsername(), "all",
                currPlayer.getUsername(), "nextPlayer"));

    }

    /**
     * Method that checks if the board needs to be reset, if so it resets it and updates the players.
     */
    private void boardResetCheck(){
        //checks if the board needs to reset
        if(board.checkBoardReset()){

            board.boardResetENG();

            //Notify Board
            notifier.firePropertyChange(new PropertyChangeEvent(
                    new Matrix(board.getGamesBoard()), "all", "0","board" ));
        }
    }

    /**
     * Method that selects the current and next player.
     */
    private void playerSelection(){
            selectCurr();
            selectNext();
    }

    /**
     * <p>Method that selects the current player.</p>
     * <ul>
     *     <il>it's the next player if it is connected;</il>
     *     <il>if not the next connected player;</il>
     *     <il>if there are no other connected players it's the next player in line.</il>
     * </ul>
     */
    private void selectCurr(){
        if(!nextPlayer.isDisconnected()) {
            currPlayer = nextPlayer;
        }else{
            Optional<Player> player = selectNextNotDisconnected(currPlayer);
            currPlayer = player.orElseGet(()-> selectNextPlayer(currPlayer));

        }
    }


    /**
     * <p>Method to selects the next player in line to play the game.</p> Skips disconnected player.
     * (if there are no other connected players, the next player is set to the successive player in line
     * even if it is disconnected to make sure that if a player reconnects in time
     * the game will select the next player properly.)
     */
    private void selectNext() {
        Optional<Player> player=selectNextNotDisconnected(currPlayer);
        nextPlayer = player.orElseGet(()->selectNextPlayer(nextPlayer));
    }


    /**
     * Method that returns the next player in line even if it is disconnected.
     * @param player The first player in line.
     * @return The next player.
     */
    private Player selectNextPlayer(Player player){
        return player == players.get(players.size()-1)?
                players.get(0):
                players.get(players.indexOf(player)+1);
    }

    /**
     * Method that returns the next connected player in line.
     *
     * @param current The player of whom you want to select the next.
     * @return The Optional of the next connected player.
     * Or an Empty Optional if there is only 1 player connected.
     */
    private Optional<Player> selectNextNotDisconnected(Player current){

        Player player = current;

        for(int i = 0; i<players.size()-1;i++){
            player=selectNextPlayer(player);
            if(!player.isDisconnected()){
                return Optional.of(player);
            }
        }

        return Optional.empty();
    }

    /**
     * <p>Method to disconnect the selected player.</p>
     * If there is only one player left starts the end timer.
     * @param player The player to disconnect.
     * @param view The virtual view of the player.
     */
    public synchronized void disconnectPlayer(Player player,VirtualView view){
       //Disconnect the player
       player.setDisconnected(true);

       view.setDisconnected(true);
       virtualViews.remove(view);
       notifier.removePropertyChangeListener("all",view);
       notifier.removePropertyChangeListener(player.getUsername(),view);

        //Notify disconnection
        notifier.firePropertyChange(new PropertyChangeEvent(
                player.getUsername(), "all", "disconnection","notification" ));


        connectedPlayers--;
       if(connectedPlayers<=1&&!timerIsOn) startEndTimer();
       if(player.equals(currPlayer)){

           if(state.equals(GameState.CHOOSING_ORDER)||state.equals(GameState.CHOOSING_COLUMN)){
               restoreTiles();

               //Notify Board
               notifier.firePropertyChange(new PropertyChangeEvent(
                       new Matrix(board.getGamesBoard()), "all", "0","board" ));

           }
           nextTurn();
       }
    }


    /**
     *  <p>Method to put back picked tiles in the board.
     *      if a player disconnects in the middle of his turn.</p>
     */
    private void restoreTiles(){
        for(int i = 0; i<selectedTiles.size();i++){
            board.getGamesBoard().setTile(selectedTiles.get(i).getTiles(), removedTilesCoord.get(i));
        }
        selectedTiles.clear();
    }




    /**
     * <p>Method to reconnect the selected player.</p>
     * If the game was stopped due to and insufficient number of players,
     * checks if there are enough players and if so makes the game continue.
     * @param player The player to reconnect.
     * @param view Virtual view of the player.
     */
    public synchronized void playerReconnection(Player player,VirtualView view){
        System.out.println(player.getUsername() + " has reconnected");

        virtualViews.add(view);

        //Notify reconnection
        notifier.firePropertyChange(new PropertyChangeEvent(
                player.getUsername(), "all", "reconnection","notification" ));


        notifier.addPropertyChangeListener("all",view);
        notifier.addPropertyChangeListener(view.getUsername(),view);

        player.setDisconnected(false);
        connectedPlayers++;

        checkRestart();

        updatePlayer(player);

    }

    /**
     * <p>Method to check if there are at least 2 connected players and stops the end timer.</p>
     * Checks if the game is stopped or the current player is disconnected
     * and if so go to the next turn.
     */
    private void checkRestart(){

        if(connectedPlayers==2) {
            timer.cancel();
            timerIsOn=false;
        }

        if(currPlayer.isDisconnected()||state.equals(GameState.STOPPED)) nextTurn();

    }

    /**
     * Method that starts the countdown timer,
     * if it ends before at least 2 players are connected the game ends.
     */
    private void startEndTimer(){
        System.out.println("End game timer started for game number: " + gameID);
        if(timer!=null) timer.cancel();
        timer = new Timer();
        TimerTask task = new TimerCounter(this);
        timerIsOn=true;
        timer.schedule(task, initialDelay, delta);
    }

    //Game ending methods

    /**
     * Method that selects the winner of the game and ends it.
     */
    private synchronized void endGame(){
        state = GameState.ENDING;
        selectWInner();

        if(timerIsOn) timer.cancel();

        for(Player p : players){
            //Notify publicPoints
            notifier.firePropertyChange(new PropertyChangeEvent(p.getPrivatePoint(), "all",
                    p.getUsername(), "publicPoints"));
        }


        //Notify game end
        notifier.firePropertyChange
                (new PropertyChangeEvent(true, "all", "0","end" ));


        for(VirtualView v : virtualViews){
            v.setDisconnected(true);

        }


        isFinished=true;

        notifier.firePropertyChange
                (new PropertyChangeEvent(this, "end", "0","end" ));

    }



    /**
     * Method that selects the player who won the game.
     */
    private void selectWInner(){
        Player winner = connectedPlayers == 1 ?
                disconnectionWinner() :
                playerWithMostPoints();

        notifier.firePropertyChange(
                new PropertyChangeEvent(winner.getUsername(),"all","0","winner"));

    }

    /**
     * Method that returns the first connected player.
     * @return the first connected player.
     */
    private Player disconnectionWinner(){

        for(Player p : players){
            if (!p.isDisconnected()) {

                notifier.firePropertyChange(
                        new PropertyChangeEvent(true,"all","0","disconnectionWinner"));

                return p;
            }
        }

        return playerWithMostPoints();

    }

    /**
     * Method that returns the player with most points.
     * @return The player with most points.
     */
    private Player playerWithMostPoints(){

        Player winner=chairPlayer;
        Player temp = selectNextPlayer(chairPlayer);


        for( int i = 0; i< players.size(); i++){

            if(winner.getPrivatePoint()<=temp.getPrivatePoint())
                winner=temp;

            temp = selectNextPlayer(temp);

        }

        return winner;
    }

    /**
     * Method that forwards a message coming
     * from the chat to all the players.
     *
     * @param playerForwarding      the player that sent the message.
     * @param message       the message to forward.
     */
    public synchronized void sendMessage (String playerForwarding, String message) {
        // Adding message to public chat's history
        publicChat.addMessage(playerForwarding, message);

        List<String> usernames = players.stream()
                                        .filter(x -> !x.isDisconnected())
                                        .map(Player::getUsername)
                                        .toList();

        for (String x: usernames){
            notifier.firePropertyChange(new PropertyChangeEvent(publicChat.getChatMessages().get(0), x, null, "message"));
        }
    }

    /**
     * Method that forwards a message coming
     * from a private chat to a particular player.
     *
     * @param forwardingPlayer      the player that sent the message.
     * @param message       the message to forward.
     * @param receivingPlayer       the player that receives the message.
     */
    public synchronized void sendMessage (String forwardingPlayer, String message, String receivingPlayer) {
        ChatMessage conversation = new ChatMessage(forwardingPlayer, message, receivingPlayer);

        // Adding the conversation to both private chats' history
        allPlayersChats.get(forwardingPlayer).getPrivateChat(receivingPlayer).addMessage(conversation);
        allPlayersChats.get(receivingPlayer).getPrivateChat(forwardingPlayer).addMessage(conversation);

        List<String> usernames = players.stream()
                                        .filter(x -> !x.isDisconnected())
                                        .map(Player::getUsername)
                                        .filter(x -> (x.equals(forwardingPlayer) || x.equals(receivingPlayer)))
                                        .toList();

        for (String x: usernames){
            notifier.firePropertyChange(new PropertyChangeEvent(conversation, x, null, "message"));
        }
    }

    //TimerInterface Methods

    /**
     * Method to ends the game when the timer ends.
     */
    @Override
    public void disconnect() {
        endGame();
    }

    /**
     * Method to update and returns the time counter.
     * @return the updated time.
     */
    @Override
    public int updateTime() {
        time++;
        return time;
    }

    /**
     * Method to return the error message to be printed when the timer ends.
     * @return The error message to be printed when the timer ends.
     */
    @Override
    public String getErrorMessage() {
        return "There is only one player left. Ending game number: " +gameID;
    }





    /**
     * Method to update the selected player client state using his virtual view.
     * @param p The player to update.
     */
    private void updatePlayer(Player p){


        notifyCommonData(p);
        notifyPersonalData(p);

        for(Player player : players){

            //Notify Bookshelf
            notifier.firePropertyChange(new PropertyChangeEvent(player.getBookshelf().getTiles(),
                    p.getUsername(), player.getUsername(), "bookshelf"));

            //Notify publicPoints
            notifier.firePropertyChange(new PropertyChangeEvent(player.getPublicPoint(), p.getUsername(),
                    player.getUsername(), "publicPoints"));
        }

        // Reloading the chats to the player disconnected
        ChatController backup = new ChatController();
        backup.setPublicChat(publicChat);
        backup.setPrivateChats(allPlayersChats.get(p.getUsername()).getPrivateChats());

        notifier.firePropertyChange(new PropertyChangeEvent(backup, p.getUsername(), null, "reloadChats"));


        //Notify game Start
        notifier.firePropertyChange(new PropertyChangeEvent(
                true, p.getUsername(), "0","start" ));


    }

    /**
     * Method to notify the personal objective (and it's number) and private points.
     * @param p The player to update.
     */
    private void notifyPersonalData(Player p){
        //Notify personal objective
        notifier.firePropertyChange(new PropertyChangeEvent(
                p.getPersonalObjective().getCard(), p.getUsername(),  p.getUsername(),"personalObj" ));

        notifier.firePropertyChange(new PropertyChangeEvent(
                p.getPersonalObjective().getPersonalObjectiveNum(), p.getUsername(),  p.getUsername(),"personalObjNum" ));

        //Notify privatePoints
        notifier.firePropertyChange(new PropertyChangeEvent(p.getPrivatePoint(), p.getUsername(),
                p.getUsername(), "privatePoints"));

    }

    /**
     * Method to notify the common objective (and it's points), player names, current, next and chair player and board
     * for a single chosen player.
     * @param p The player to update.
     */
    private void notifyCommonData(Player p){
        //Notify PlayerNames
        notifier.firePropertyChange(new PropertyChangeEvent(
                players.stream().map(Player::getUsername).toList(), p.getUsername(), "0","playerNames" ));

        //Notify Board
        notifier.firePropertyChange(new PropertyChangeEvent(new Matrix(board.getGamesBoard()), p.getUsername(), "0","board" ));

        //Notify commonObjectives
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getNum).toList(), p.getUsername(), "0","commonObj" ));
        //Notify commonObjectivesPoints
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getPoints).toList(), p.getUsername(), "0","commonObjPoints" ));
        //Notify playerWhoCompletedCommonObj
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getPlayersNameCommonObj).toList(), p.getUsername(), "0","commonObjCompleted" ));


        //Notify currPlayer and nextPlayer
        notifier.firePropertyChange(new PropertyChangeEvent(currPlayer.getUsername(), p.getUsername(),
                currPlayer.getUsername(), "currPlayer"));

        notifier.firePropertyChange(new PropertyChangeEvent(nextPlayer.getUsername(), "all",
                currPlayer.getUsername(), "nextPlayer"));

        notifier.firePropertyChange(new PropertyChangeEvent(chairPlayer.getUsername(),"all",
                chairPlayer.getUsername(), "chairPlayer"));

    }
    /**
     * Method to notify the common objective (and it's points), player names, current, next and chair player and board
     * for all players.
     */
    private void notifyCommonData(){
        //Notify PlayerNames
        notifier.firePropertyChange(new PropertyChangeEvent(
                players.stream().map(Player::getUsername).toList(), "all", "0","playerNames" ));

        //Notify Board
        notifier.firePropertyChange(new PropertyChangeEvent(new Matrix(board.getGamesBoard()), "all", "0","board" ));

        //Notify commonObjectives
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getNum).toList(),"all", "0","commonObj" ));

        //Notify commonObjectivesPoints
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getPoints).toList(), "all", "0","commonObjPoints" ));

        //Notify playerWhoCompletedCommonObj
        notifier.firePropertyChange(new PropertyChangeEvent(
                commonObj.stream().map(CommonObjective::getPlayersNameCommonObj).toList(),"all", "0","commonObjCompleted" ));

        //Notify currPlayer, nextPlayer and chair player


        notifier.firePropertyChange(new PropertyChangeEvent(nextPlayer.getUsername(), "all",
                currPlayer.getUsername(), "nextPlayer"));

        notifier.firePropertyChange(new PropertyChangeEvent(chairPlayer.getUsername(),"all",
                chairPlayer.getUsername(), "chairPlayer"));

    }


    //GETTERS AND SETTERS

    /**
     * <strong>Getter</strong>-> Returns the array of all players.
     * @return  Arraylist of all players.
     */
    public  ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * <strong>Getter</strong>-> Returns the board.
     * @return the <i>Board</i>.
     */
    public  Board getBoard() {
        return board;
    }

    /**
     * <strong>Getter</strong>-> Returns the current state of the game.
     * @return The current state of the game.
     */
    public  GameState getState() {
        return state;
    }

    /**
     * <strong>Setter</strong> -> Sets the state of the game.
     * @param state The state you want to set the game to.
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * <strong>Getter</strong>-> Returns the common objective of the game.
     * @return <i>ArrayList</i> of all the common objectives.
     */
    public ArrayList<CommonObjective> getCommonObj() {
        return commonObj;
    }

    /**
     * <strong>Setter</strong> -> Set the current player (Just for Testing purposes).
     * @param currPlayer The player to set as current player.
     */
    public synchronized void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * <strong>Getter</strong> -> Returns the gameID.
     * @return gameId.
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * <strong>Getter</strong> -> Returns true if the game is finished, false otherwise.
     * @return flag isFinished.
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * <strong>Setter</strong> -> Sets the selectedTiles array.
     * @param selectedTile Array you want to set selectedTiles as.
     */
    public synchronized void setSelectedTiles(ArrayList<Tile> selectedTile) {
        this.selectedTiles = selectedTile;
    }


    /**
     * <strong>Getter</strong> -> Returns the selectedTiles array.
     * @return selectedTiles <i>ArrayList</i>.
     */
    public ArrayList<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    /**
     * <strong>Setter</strong> -> Sets the players in a game given an Arraylist of players.
     *
     * @param players       ArrayList of players.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;

        // Initializing for each player a particular version of ChatController specifically designed for Server
        List<String> allUsernames = players.stream()
                .map(Player::getUsername)
                .toList();
        for (String player: allUsernames) {
            allPlayersChats.put(player, new ChatController(true));


            for (String x: allUsernames.stream().filter(y -> !y.equals(player)).toList())
                allPlayersChats.get(player).getPrivateChats().put(x, new Chat());
        }

        selectedTiles.clear();

    }


    /**
     * Method to add the controller as a property change listener of the model.
     * @param controller the controller.
     */
    public void addChangeListener(Controller controller){
        notifier.addPropertyChangeListener("end",controller);
    }

    /**
     * <strong>Setter</strong> -> Sets the virtual views.
     * @param virtualViews virtual views.
     */
    public void setVirtualViews(ArrayList<VirtualView> virtualViews) {
        this.virtualViews = virtualViews;
    }

    /**
     * <strong>Setter</strong> -> Sets the gameID.
     * @param gameID gameId.
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * <strong>Getter</strong> -> Returns current player.
     * @return current player.
     */
    public Player getCurrPlayer() {
        return currPlayer;
    }

    /**
     * <strong>Getter</strong> -> Returns next player.
     * @return next player.
     */
    public Player getNextPlayer() {
        return nextPlayer;
    }

    /**
     * <strong>Getter</strong> -> Returns the flag if the timer is on.
     * @return flag timerIsOn.
     */
    public boolean isTimerIsOn() {
        return timerIsOn;
    }
}

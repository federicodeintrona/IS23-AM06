package it.polimi.ingsw;

import it.polimi.ingsw.CommonObjective.CommonObjective;
import it.polimi.ingsw.Exceptions.MoveNotPossible;
import it.polimi.ingsw.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.View.View;
import org.javatuples.Pair;
import org.javatuples.Tuple;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
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
     * Initializes the board and the objectives
     */
    public void initialization()  {

        board = new Board(players.size(), new Sachet());
        board.BoardInitialization();
        commonobjInit();
        personalobjInit();
        for(Player p : players){
            privatePoints.add(p.getPrivatePoint());
            publicPoints.add(p.getPublicPoint());
        }

        for (View v : virtualViews){
            notifier.addPropertyChangeListener(v);
        }

    }



    /**
     * Removes an array of tiles from the board

     * @param points  The position of the tiles
     */
    public void removeTileArray( ArrayList<Point> points) throws MoveNotPossible{

        //Checks move legitimacy
        if(!checkRemoveLegit(points)) throw new MoveNotPossible();


        for(int i = 0; i < points.size(); i++){
            Pair<Tiles,Point> p1 = new Pair<>(board.getGamesBoard().getTile(points.get(i)),points.get(i));
            Pair<Tiles,Point> p2 = new Pair<>(Tiles.EMPTY,points.get(i));


            notifier.firePropertyChange( new PropertyChangeEvent(board,"board", p1,p2));

        }
        board.remove(points);

    }


    /**
     * Adds an ordered array of tiles in the player's bookshelf.
     * Since adding tiles to your bookshelf is the last action you can do on your turn,
     * it also calls the nextTurn function
     * @param player  Player which owns the Bookshelf
     * @param tiles   The color of the tiles to add
     * @param column   The column where you want to add the tiles
     */
    public void addToBookShelf(Player player, ArrayList<Tiles> tiles, int column) throws MoveNotPossible {

        //Check for move legitimacy
        if(!checkAddLegit(player,column)) throw new MoveNotPossible();

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
        //Advances turns
        nextTurn();

    }

    public void saveState(){

    }




    //Checks
    private boolean checkRemoveLegit(ArrayList<Point> points){return true;};
    private boolean checkAddLegit(Player player,int col){return true;};
    private boolean checkInLine(ArrayList<Point> points){






        return true;};
    private boolean checkAvailable(Point p){return true;}
    private boolean checkDomain(Point p){return true;}
    private boolean checkDomain(int num,int len){return true;}
    private boolean checkPlayerNum(int num){return true;}
    private boolean checkColumn(int i){return true;}










    //PRIVATE METHODS : UTILITY

    /**
     * Initializes common objectives
     *
     */
    private void commonobjInit() {
       commonobj = CommonObjective.randomSubclass(2);
    }

    /**
     * Initializes private objectives
     *
     */
    private void personalobjInit() {
        ArrayList<PersonalObjective> temp = PersonalObjective.randomSubclass(players.size());
        for(int i = 0;i<players.size();i++){
            players.get(i).setPersonalObjective(temp.get(i));
        }
    }




    private void updatePoints(){


        currPlayer.getBookshelf().checkVicinityPoints();
        currPlayer.getPersonalObjective().checkCondition(currPlayer);

        for(CommonObjective o : commonobj){
             o.commonObjPointsCalculator(currPlayer,players.size());
        }



        PropertyChangeEvent evt = new PropertyChangeEvent(currPlayer, "points",
                publicPoints.get(players.indexOf(currPlayer)),currPlayer.getPublicPoint());

        notifier.firePropertyChange(evt);

    }


    /**
     * Select the next player and calls the endGame function if the last turn has been played.
     * Also resets the board when needed
     * @return Return the player who will play the next turn
     */
    private void nextTurn(){

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
     * Calls select winner
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


    public boolean isFinished() {
        return isFinished;
    }




}

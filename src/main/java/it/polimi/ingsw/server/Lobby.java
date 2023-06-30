package it.polimi.ingsw.server;
import it.polimi.ingsw.server.Exceptions.LobbyNotExists;
import it.polimi.ingsw.server.Exceptions.UsernameAlreadyTaken;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import java.util.*;

/**
 * <p>Class used to manage the creation of lobbies and games.</p>
 * <p>It keeps all the information about the lobbies, games, players, and their views.</p>
 * <p>It creates a new lobby if a new player wants to play a game and there aren't any other lobby waiting for players.
 *     It also checks the uniqueness of the nickname before adding "logging in"
 *     (saving his username and associated view) a user.</p>
 *
 * <p> A lobby is waiting room created to store players who will play the same game. It is deleted after the game starts.</p>
 */
public class Lobby {
    private Controller controller;
    private final HashMap<Integer,ArrayList<String>> lobbys = new HashMap<>();
    private final HashMap<Integer,Integer> gamePlayerNumber = new HashMap<>();
    private final Queue<Integer> waitingLobbys = new LinkedList<>();
    private final HashMap<String,Integer> playerToLobby = new HashMap<>();
    private final HashMap<Integer, Model> games;
    private int gameNumber = 0;
    private final HashMap<String,Integer> playerToGame = new HashMap<>();

    private final ArrayList<String> usernames = new ArrayList<>();
    private final HashMap<String , Player> players;
    private final HashMap<String , Player> disconnectedPlayers = new HashMap<>();
    private final HashMap<String , VirtualView> views = new HashMap<>();
    private final HashMap<String,String> nocapUsernames = new HashMap<>();

    /**
     * Main constructor of the class.
     */
    public Lobby() {
        this.games = new HashMap<>();
        this.players = new HashMap<>();
    }

    /**
     * Constructor only used for testing.
     * @param games The HashMap containing all games.
     * @param players The HashMap containing all players.
     */
    public Lobby(HashMap<Integer, Model> games, HashMap<String, Player> players) {
        this.games = games;
        this.players = players;
    }

    /**
     * Method to check if there are lobbies waiting for players.
     * @return <i>True</i> if there are, <i>false</i> otherwise.
     */
    public synchronized boolean waitingLobbies(){
        return !waitingLobbys.isEmpty();
    }

    /**
     * <p>Method to handle new clients.</p>
     * <p>It checks if the username chosen by the player was already taken, if it wasn't
     *     it checks to see if there are lobbies waiting for players (if so adds the player to it),
     *     if there aren't any asks the player to create a new lobby through the controller and the client handlers.</p>
     * @param client The username of the player.
     * @param view The virtual view of the player.
     * @return An optional of Integer which can either be:
     *              <ul>
     *                  <li>the ID of the game the client was added to;</li>
     *                  <li>empty if he needs to create a new lobby himself.</li>
     *              </ul>
     * @throws UsernameAlreadyTaken If the username chosen by the player was already taken.
     */
    public synchronized Optional<Integer> handleClient(String client,String noCap,VirtualView view) throws UsernameAlreadyTaken {

        if(!usernames.contains(client.toLowerCase())) {

            usernames.add(client.toLowerCase());
            nocapUsernames.put(client,noCap);
            controller.addView(view);

            System.out.println(client+ " has logged in successfully");

            if (waitingLobbies()) {//if there are waiting lobbies, add the client to the longest waiting lobby
                try {
                    //return the game number
                    return Optional.of(addClient(client));
                } catch (LobbyNotExists e) {
                    return Optional.empty();
                }
                //if there aren't any, return empty Optional
            } else return Optional.empty();

        }else throw new UsernameAlreadyTaken();
    }


    /**
     * Method to create a new lobby/game with the selected number of players and adds it to the waiting lobbies.
     * @param client The username of the player.
     * @param numplayers The chosen number of players for the game.
     * @return The ID of the game.
     */
    public synchronized int newLobby(String client,int numplayers){
        //Update the game number
        gameNumber+=1;

        System.out.println(client+" has created a new lobby. Number of players: "+ numplayers + " ID: " +gameNumber);
        //create a new lobby and add the player
        ArrayList<String> newLobby = new ArrayList<>();
        newLobby.add(client);

        //add the new lobby to the lobby list
        lobbys.put(gameNumber,newLobby);
        playerToLobby.put(client,gameNumber);

        //record the selected number of player
        gamePlayerNumber.put(gameNumber, numplayers);

        //add it to the waiting lobbies list
        waitingLobbys.add(gameNumber);

        //create the new game
        newGame(gameNumber);
        //return the game number
        return gameNumber;
    }



    /**
     * Method to add a player to a waiting lobby and starts the game when it is full.
     * @param client  The player you want to add.
     * @throws LobbyNotExists If the lobby does not exist (it should never happen in theory).
     * @return The game ID
     */
    public synchronized int addClient(String client) throws LobbyNotExists {

        //Get the ID of the lobby that is waiting for the longest time
        Integer index = waitingLobbys.peek();

        if(index==null) throw new LobbyNotExists();

        System.out.println( client+ " added to lobby number: " + index);
        //Add the client to the lobby and set his lobbyID
        lobbys.get(index).add(client);
        playerToLobby.put(client,index);

        checkStart(index);

        //return the game number
        return index;


    }

    /**
     * Method to check if the selected game has reached the maximum number of players and is ready to start.
     * @param index The ID of the game.
     */
    public void checkStart(int index){
        //If the lobby reached the max number of player, start the game.
        if (lobbys.get(index).size() == gamePlayerNumber.get(index)) {
            waitingLobbys.remove(index);
            startGame(index);
        }
    }

    /**
     * <p> Method to start a game.</p>
     * <p>It adds all the players and virtual views to the game and deletes the associated lobby.</p>
     * @param index The ID of the game.
     */
    private void startGame(int index) {
        //create the model and the array that will contain alla players
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<VirtualView> virtualViews = new ArrayList<>();
        ArrayList<String> myLobby = lobbys.get(index);

        //for every client in the lobby, create his player, add it to the playerToGame map
        // and remove it from the playerToLobby map
        for (String s : myLobby) {
            Player p = new Player(s,nocapUsernames.get(s));

            players.put(s,p);
            playerList.add(p);
            virtualViews.add(views.get(s));
            playerToGame.put(s.toLowerCase(),index);
            playerToLobby.remove(s);
        }

        //Close the lobby
        lobbys.remove(index);

        //Add players and virtual views to the model
        games.get(index).setVirtualViews(virtualViews);
        games.get(index).setPlayers(playerList);

        //start the game
        controller.startGame(index);
    }


    /**
     * Method to create a new empty game.
     * @param num The ID of the game.
     */
    private void newGame(int num){
        Model m = new Model();
        games.put(num, m);
        m.setGameID(num);
        m.addChangeListener(controller);

    }


    /**
     * <p>Method to close a game.</p>
     * <p>Removes all players (even if disconnected), virtual views and the game itself from their respective maps.</p>
     * @param gameID The ID of the game.
     */
    public synchronized void closeGame(int gameID){

        for(String s : games.get(gameID).getPlayers().stream().map(Player::getUsername).toList()){
            usernames.remove(s.toLowerCase());
            disconnectedPlayers.remove(s.toLowerCase());
            players.remove(s);
            playerToLobby.remove(s);
            playerToGame.remove(s.toLowerCase());
            views.remove(s);
        }

        //Remove the model
        games.remove(gameID);

    }

    /**
     * <p>Method to disconnect a player from the game and adds it to the HashMap of all disconnected players.</p>
     * @param username The username of the player.
     */
    public synchronized void playerDisconnection(String username){

        VirtualView view =views.get(username);
        if(view != null) {
            view.setDisconnected(true);
        }


        //If in a lobby remove him
        Integer lobbyID = playerToLobby.get(username);
        if(lobbyID!=null){

            lobbys.get(lobbyID).remove(username);

            if(lobbys.get(lobbyID).size()==0){
                waitingLobbys.remove(lobbyID);
            }
        }

        //If in a game disconnect him
        Integer gameID=playerToGame.get(username.toLowerCase());
        if(gameID!=null){
            if(!games.get(gameID).isFinished()) games.get(gameID).disconnectPlayer(players.get(username), views.get(username));

            if(usernames.contains(username.toLowerCase())){
                disconnectedPlayers.put(username.toLowerCase(), players.get(username));
            }
        }

        //Remove him from the necessary maps
        views.remove(username);
        players.remove(username);
        usernames.remove(username.toLowerCase());

    }


    /**
     * <p>Method to reconnect a player from the game and removes it to the HashMap of all disconnected players.</p>
     * @param username The username of the player.
     * @param view The virtual view of the player.
     * @return The ID of the game.
     */
    public synchronized int playerReconnection(String username,VirtualView view){
        Player player = disconnectedPlayers.get(username.toLowerCase());
        disconnectedPlayers.remove(username.toLowerCase());

        usernames.add(username.toLowerCase());
        players.put(username,player);
        views.put(username,view);
        int index = playerToGame.get(username.toLowerCase());
        games.get(index).playerReconnection(player,view);
        return index;

    }


    /**
     *  <strong>Setter</strong> -> Sets the controller.
     * @param controller controller of the server.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * <strong>Getter</strong> -> Gets the HashMap containing all games.
     * @return The <i>HashMap</i> containing all games.
     */
    public HashMap<Integer,Model> getGames() {
        return games;
    }

    /**
     *  <strong>Getter</strong> -> Gets the HashMap containing all players.
     * @return The <i>HashMap</i> containing all players.
     */
    public HashMap<String, Player> getPlayers() {
        return players;
    }

    /**
     * <strong>Getter</strong> -> Gets the HashMap containing all views.
     * @return The <i>HashMap</i> containing all views.
     */
    public HashMap<String, VirtualView> getViews() {
        return views;
    }

    /**
     * <strong>Getter</strong> -> Gets the HashMap containing all disconnected players.
     * @return The <i>HashMap</i> containing all disconnected players.
     */
    public HashMap<String, Player> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }
}

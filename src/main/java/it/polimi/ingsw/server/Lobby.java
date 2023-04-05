package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Exceptions.UsernameAlreadyTaken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Lobby {
    private Controller controller;

    //Array di array che contiene tutti i cliemt separati in base al gioco a cui appartengono
    //quelle che io chiamo lobby sono i sotto array che contengono tutti i client di un singolo model/game
    private final ArrayList<ArrayList<ServerClientHandler>> lobbys = new ArrayList<>();

    //Coda delle lobbies che stanno ancora aspettando giocatori
    private final Queue<Integer> waitingLobbys = new LinkedList<>();

    //Mappa che contiene il numero di giocatori voluti da ogni game
    private final HashMap<Integer,Integer> gamePlayerNumber = new HashMap<>();

    //Mappa che contiene tutti i model (viene passata anche al controller, la creo qui per
    //evitare problemi di sincronizzazione)
    private final HashMap<Integer,Model> games = new HashMap<>();

    //Mappa che contiene tutti i client (viene passata anche al controller,
    // per fargli recuperare i player che sono dentro al client ed evitare di avere troppe strutture dati,
    //la creo qui per evitare problemi di sincronizzazione)
    private final HashMap<String ,ServerClientHandler > clients = new HashMap<>();

    //Numero delle partite che sono state avviate, viene anche usato come ID del model
    private int gameNumber = 0;

    //Lock per la sincronizzazione, molto probabilmente tutte queste sincronizzazioni sono ridondanti
    //ma le tolgo quando testiamo in caso
    private final Object gameNumberLock = new Object();


    /**
     * @return true if there are games waiting for players, false otherwise
     */
    public synchronized boolean waitingLobbies(){
        return !waitingLobbys.isEmpty();
    }


    /**
     * The lobby handles the new client:
     * if the username is already taken, it asks the client to select a new one,
     * if there are games waiting for players, adds the client to them, and returns true
     * if there aren't any games, it creates a new one and asks the client to
     * select the number of players.
     *
     * @param client the client
     * @return true: if there are games waiting for players , false otherwise
     * @throws UsernameAlreadyTaken if the username selected by the client is already taken
     */
    public synchronized boolean handleClient(ServerClientHandler client) throws UsernameAlreadyTaken {
        if(!clients.containsKey(client.getNickname().toLowerCase())){

            clients.put(client.getNickname().toLowerCase(),client);

            //if there are waiting lobbies, add the client to the longest waiting lobby and return true
            if(waitingLobbies()){
                addClient(client);
                return true;
                //if there aren't any, return false
            }else return false;

        }else throw new UsernameAlreadyTaken();

    }


    /**
     * Adds a client to a waiting lobby and starts the game when it is full
     * @param client The client you want to add
     */
    public synchronized void addClient(ServerClientHandler client){
        //Get the ID of the lobby that is waiting for the longest time
        Integer index = waitingLobbys.peek();

        if(index!=null) {
            //Add the client to the lobby and set his lobbyID
            lobbys.get(index).add(client);
            client.setLobbyID(index);

            //If the lobby reached the max number of player, start the game.
            if (lobbys.get(index).size() == gamePlayerNumber.get(index)) {
                waitingLobbys.remove();
                startGame(index);

            }
        }else throw new RuntimeException("Waiting lobbies is empty");

    }

    /**
     * Creates a new game and adds the client to it
     * @param client the client creating the new game
     * @param numplayers the number of player selected for the game
     */
    public synchronized void newLobby(ServerClientHandler client,int numplayers){
        //create a new lobby and add the player
        ArrayList<ServerClientHandler> newLobby = new ArrayList<>();
        newLobby.add(client);

        //add the new lobby to the lobby list
        lobbys.add(newLobby);

        //record the selected number of player
        gamePlayerNumber.put(lobbys.size()-1, numplayers);

        //add the lobby to the waiting lobbies list
        waitingLobbys.add(lobbys.size()-1);

        //set the client lobbyID
        client.setLobbyID(lobbys.size()-1);
    }


    /**
     * Start a game (creates its players and model and calls for the controller to initialise the game)
     * @param index The index of the game ready to start
     */
    public void startGame(int index) {
        //create the model and the array that will contain all players
        ArrayList<Player> players = new ArrayList<>();
        Model m = new Model(players);
        int tempnum;


        //add the new game and get its ID
        synchronized (gameNumberLock) {
            tempnum = gameNumber;
            games.put(tempnum, m);
            gameNumber += 1;
        }


        //for every client in the lobby, create his player and set the gameID
        for (ServerClientHandler s : lobbys.get(index)) {
            Player p = new Player(s.getNickname());
            s.setPlayer(p);
            s.setGameID(tempnum);
            players.add(p);
        }

        //start the game
        controller.startGame(index);
    }




    //DA FARE
    private void closeLobby(){}
    public void playerDisconnection(){}


    public void setController(Controller controller) {
        this.controller = controller;
    }

    public HashMap<Integer,Model> getGames() {
        return games;
    }


    public HashMap<String, ServerClientHandler> getClients() {
        return clients;
    }
}

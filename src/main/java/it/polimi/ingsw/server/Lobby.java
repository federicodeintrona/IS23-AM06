package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Exceptions.UsernameAlreadyTaken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Lobby {
    private Controller controller;
    private  ArrayList<ArrayList<ServerClientHandler>> lobbys = new ArrayList<>();
    private Queue<Integer> waitingLobbys = new LinkedList<>();

    private HashMap<Integer,Integer> gamePlayerNumber = new HashMap<>();
    private HashMap<Integer,Model> games = new HashMap<>();

     private HashMap<String ,Player > playerIDs = new HashMap<>();

    private HashMap<String ,ServerClientHandler > clients = new HashMap<>();


    private int gameNumber = 0;
    private final Object gameNumberLock = new Object();


    public synchronized boolean waitingLobbies(){
        if(waitingLobbys.isEmpty()){
        return false;}
        else return true;
    }






    public synchronized boolean handleClient(ServerClientHandler client) throws UsernameAlreadyTaken {
        if(!clients.containsKey(client.getNickname())){

            clients.put(client.getNickname(),client);

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
     * @param client    The client you want to add
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
        }

    }


    public synchronized void newLobby(ServerClientHandler client,int numplayers){
        //create a new lobby and add the player
        ArrayList<ServerClientHandler> newLobby = new ArrayList<>();
        newLobby.add(client);

        //add the new lobby to the lobby list
        lobbys.add(newLobby);

        //record the selected number of player
        gamePlayerNumber.put(lobbys.size()-1, numplayers);

        //add i to the waiting lobbies list
        waitingLobbys.add(lobbys.size()-1);

        //set the client lobbyID
        client.setLobbyID(lobbys.size()-1);
    }


    public void startGame(int index) {
        //create the model and the array that will contain alla players
        ArrayList<Player> players = new ArrayList<>();
        Model m = new Model(players);
        int tempnum;

        //add the new game and get its ID
        synchronized (gameNumberLock) {
            gameNumber += 1;
            tempnum = gameNumber;
            games.put(tempnum, m);
        }


        //for every client in the lobby, create his player and set the gameID
        for (ServerClientHandler s : lobbys.get(index)) {
            Player p = new Player(s.getNickname());
            players.add(p);
            s.setGameID(tempnum);
            playerIDs.put(p.getUsername(),p);
        }

        //start the game
        controller.startGame(index);
    }





    private void closeLobby(){}
    public void playerDisconnection(){}


    public void setController(Controller controller) {
        this.controller = controller;
    }

    public HashMap<Integer,Model> getGames() {
        return games;
    }

    public HashMap<String, Player> getPlayerIDs() {
        return playerIDs;
    }
}

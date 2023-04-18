package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Exceptions.LobbyNotExists;
import it.polimi.ingsw.server.Exceptions.UsernameAlreadyTaken;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Lobby {
    private Controller controller;
    private final ArrayList<String> usernames = new ArrayList<>();
    private final HashMap<Integer,ArrayList<String>> lobbys = new HashMap<>();
    private final HashMap<Integer,Integer> gamePlayerNumber = new HashMap<>();
    private final Queue<Integer> waitingLobbys = new LinkedList<>();
    private final HashMap<Integer, Model> games = new HashMap<>();
    private final HashMap<String , Player> players = new HashMap<>();
    private int gameNumber = 0;


    //private final Object gameNumberLock = new Object();


    public synchronized boolean waitingLobbies(){
        return !waitingLobbys.isEmpty();
    }


    public synchronized int handleClient(String client) throws UsernameAlreadyTaken {

        if(!usernames.contains(client.toLowerCase())) {
            usernames.add(client.toLowerCase());

            //if there are waiting lobbies, add the client to the longest waiting lobby
            if (waitingLobbies()) {
                try {
                    //return the game number
                    return addClient(client);
                } catch (LobbyNotExists e) {
                    return -1;
                }

                //if there aren't any, return -1
            } else return -1;
        }else throw new UsernameAlreadyTaken();
    }




    public synchronized int newLobby(String client,int numplayers){

        //Update the game number
        gameNumber+=1;

        //create a new lobby and add the player
        ArrayList<String> newLobby = new ArrayList<>();
        newLobby.add(client);

        //add the new lobby to the lobby list
        lobbys.put(gameNumber,newLobby);

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
     * Adds a client to a waiting lobby and starts the game when it is full
     * @param client    The client you want to add
     */
    public synchronized int addClient(String client) throws LobbyNotExists {

        //Get the ID of the lobby that is waiting for the longest time
        Integer index = waitingLobbys.peek();

        if(index!=null) {
            //Add the client to the lobby and set his lobbyID
            lobbys.get(index).add(client);

            //If the lobby reached the max number of player, start the game.
            if (lobbys.get(index).size() == gamePlayerNumber.get(index)) {
                waitingLobbys.remove();
                startGame(index);

            }

            //return the game number
            return index;

        }else throw new LobbyNotExists();
    }




    public void startGame(int index) {
        //create the model and the array that will contain alla players
        ArrayList<Player> playerList = new ArrayList<>();



        //for every client in the lobby, create his player and add it to the player map
        for (String s : lobbys.get(index)) {
            Player p = new Player(s);

            players.put(s,p);

            playerList.add(p);
        }

        games.get(index).addPlayers(playerList);

        //start the game
        controller.startGame(index);
    }


    public void newGame(int num){
        Model m = new Model();
        games.put(num, m);

    }



    private void closeLobby(){}
    public void playerDisconnection(){}


    public void setController(Controller controller) {
        this.controller = controller;
    }

    public HashMap<Integer,Model> getGames() {
        return games;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }
}

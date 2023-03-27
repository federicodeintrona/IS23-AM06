package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.MoveNotPossible;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageTypes;
import it.polimi.ingsw.View.View;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    Lobby lobby;
    private HashMap<Integer,Model> games;

    private HashMap<String ,Player > playerIDs;

    private ArrayList<ArrayList<View>> views;

    public Controller(Lobby loby, HashMap<Integer,Model> models) {
        lobby = loby;
       games = models;
       views = new ArrayList<>();
       playerIDs = new HashMap<>();
    }




    public void startGame(int ID)  {

        for(Player p : games.get(ID).getPlayers()) {
            playerIDs.put(p.getUsername(),p);
        }
        games.get(ID).initialization();
    }




    public void addToBookshelf(int gameID, String playerID, ArrayList<Tiles> array, int col ){

        try {
            games.get(gameID).addToBookShelf(playerIDs.get(playerID),array,col);
        } catch (MoveNotPossible e) {
            throw new RuntimeException(e);
        }
    }


    public void removeTiles(int gameID,String playerID, ArrayList<Point> points){
        try {
            games.get(gameID).removeTileArray(playerIDs.get(playerID),points);
        } catch (MoveNotPossible e) {
            throw new RuntimeException(e);
        }
    }

    public void saveState(int gameID){
        games.get(gameID).saveState();
    }

    //Lobby methods
    public boolean waitingLobby(){
        return lobby.waitingLobbys();
    }

    public void newLobby(ServerClientHandler client,int players){
        lobby.newLobby(client,players);
    }

    public Message handleNewClient(ServerClientHandler client){
        Message reply = new Message();
        if(lobby.handleClient(client)){
            reply.setType(MessageTypes.WAITING_FOR_PLAYERS);
            reply.setContent("Added to a game. Waiting for other player...");
            return  reply;
        }else {
            reply.setType(MessageTypes.NEW_LOBBY);
            reply.setContent("Select the number of players");
            return reply;
        }
    }


    //Client Side Controller Method
    public void swapOrder(ArrayList<Integer> ints,ArrayList<Tiles> tiles){
        ArrayList<Tiles> array = new ArrayList<>();
        array.addAll(tiles);
        for (int i=0;i<ints.size();i++){
            tiles.set(i,array.get(ints.get(i)-1));
        }

    }


    public void addPlayer(String username, Player player){
        playerIDs.put(username,player);
    }
}

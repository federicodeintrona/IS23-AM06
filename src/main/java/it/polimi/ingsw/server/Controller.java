package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.server.View.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    Lobby lobby;
    private HashMap<Integer,Model> games;

    private HashMap<String,ServerClientHandler> clients;

    private ArrayList<ArrayList<View>> views;

    /**
     * Contructor
     * @param mainLobby The lobby of the server
     * @param models  The hashmap of all current games
     */
    public Controller(Lobby mainLobby, HashMap<Integer,Model> models,HashMap<String ,ServerClientHandler > client) {
        lobby = mainLobby;
        games = models;
        views = new ArrayList<>();
        clients = client;
    }

    public Controller(HashMap<Integer,Model> models,HashMap<String ,ServerClientHandler > client){
        games = models;
        clients = client;

    }


    /**
     * Start the selected game.
     * @param ID The ID of the game you want to start
     */
    public void startGame(int ID)  {
        games.get(ID).initialization();
    }


    /**
     * Adds the selected tiles of the game whose id is 'gameID' in the column 'col'
     * @param gameID The ID of the game
     * @param playerID  The username of the player requesting the move
     * @param col The column where you want to add the tiles
     * @return The reply to be sent to the client
     */
    public Message addToBookshelf(int gameID, String playerID,  int col ){
        Message reply = new Message();

        try {
            games.get(gameID).addToBookShelf(clients.get(playerID).getPlayer(),col);
            reply.setType(MessageTypes.OK);
            reply.setContent("Move successful");

        } catch (OutOfDomain e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("The requested column does not exists");

        }catch (ColumnIsFull e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("The requested column is full");

        }catch (NotCurrentPlayer e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("It's not your turn");

        }catch (MoveNotPossible e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("That move is not allowed at the moment");

        }

        return reply;
    }

    /**
     * Swap the order of the array of selected tiles to the order describes in the array ints.
     * ex. oldSelectedTiles[G,B,Y], ints[2,1,3] --> newSelectedTiles[B,G,Y]
     *
     * @param ints The new order in which you want the array
     * @param gameID ID of the game
     * @param playerID username of the player
     */
    public void swapOrder(ArrayList<Integer> ints, int gameID, String playerID){

        try {
            games.get(gameID).swapOrder(ints,clients.get(playerID).getPlayer());
        } catch (NotCurrentPlayer e) {
            throw new RuntimeException(e);
        } catch (MoveNotPossible e) {
            Message m = new Message();
        }

    }


    /**
     * Removes the tiles of coordinates 'points'
     * @param gameID
     * @param playerID
     * @param points
     */
    public void removeTiles(int gameID,String playerID, ArrayList<Point> points){
        try {
            games.get(gameID).removeTileArray(clients.get(playerID).getPlayer(),points);
        } catch (MoveNotPossible e) {
            throw new RuntimeException(e);
        }
    }


    //public void saveState(int gameID){games.get(gameID).saveState();}

    //Lobby methods

    public void newLobby(ServerClientHandler client,int players){
        lobby.newLobby(client,players);
    }

    public Message handleNewClient(ServerClientHandler client) throws UsernameAlreadyTaken {
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


}

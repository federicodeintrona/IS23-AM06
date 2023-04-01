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
            reply.setContent("You can't do that now");

        }

        return reply;
    }


    /**
     * Swap the order of the array of selected tiles to the order describes in the array ints.
     * ex. oldSelectedTiles[G,B,Y], ints[2,1,3] --> newSelectedTiles[B,G,Y]
     *
     * @param ints The new order in which you want the array
     * @param gameID The ID of the game
     * @param playerID The username of the player
     * @return The reply to be sent to the client
     */
    public Message swapOrder(ArrayList<Integer> ints, int gameID, String playerID){
        Message reply = new Message();

        try {
            games.get(gameID).swapOrder(ints,clients.get(playerID).getPlayer());
            reply.setType(MessageTypes.OK);
            reply.setContent("Move successful");
        } catch (NotCurrentPlayer e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You are not the current player");
        } catch (MoveNotPossible e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You can't fo that now");
        }

        return reply;
    }


    /**
     * Removes the tiles of coordinates 'points'
     * @param gameID The ID of the game
     * @param playerID  The username of the player requesting the move
     * @param points The coordinates of the tiles
     * @return The reply to be sent to the client
     */
    public Message removeTiles(int gameID,String playerID, ArrayList<Point> points){
        Message reply = new Message();


        try {
            games.get(gameID).removeTileArray(clients.get(playerID).getPlayer(),points);
            reply.setType(MessageTypes.OK);
            reply.setContent("Move successful");

        }catch (OutOfDomain e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("Ypu selected a point outside the board");

        }catch (TilesCannotBeSelected e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("One of the tiles cannot be selected");

        }catch (TilesNotAdjacent e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("The tiles are noy adjacent to each other");

        }catch (IllegalArgumentException e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You selected 0 tiles");

        }catch (TooManySelected e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You selected too many tiles");

        }catch (NotCurrentPlayer e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("It's not your turn");

        }catch (MoveNotPossible e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You can't do that now");

        }

        return reply;
    }



    //Lobby methods


    public void newLobby(ServerClientHandler client,int players){
        lobby.newLobby(client,players);
    }



    public Message handleNewClient(ServerClientHandler client) {
        Message reply = new Message();

        try {
            boolean response = lobby.handleClient(client);

            if (response) {
                reply.setType(MessageTypes.WAITING_FOR_PLAYERS);
                reply.setContent("Added to a game. Waiting for other player...");
            } else {
                reply.setType(MessageTypes.NEW_LOBBY);
                reply.setContent("Select the number of players (2 to 4)");
            }
        } catch (UsernameAlreadyTaken e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("Username already taken");
        }


        return reply;
    }


}

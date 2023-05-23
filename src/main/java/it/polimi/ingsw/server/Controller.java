package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller implements PropertyChangeListener {
    private Lobby lobby;
    private  HashMap<String, Player> players;
    private  HashMap<Integer,Model> games;
    private  HashMap<String ,Integer> playerToGame;
    private  HashMap<String, VirtualView> views;



    /**
     * Constructor
     * @param mainLobby The lobby of the server
     */
    public Controller(Lobby mainLobby) {
        lobby = mainLobby;
        this.playerToGame=lobby.getPlayerToGame();
    }

    public Controller(HashMap<Integer,Model> models,HashMap<String ,Player > playerMap){
        games = models;
        players = playerMap;
    }


    /**
     * Start the selected game.
     * @param ID The ID of the game you want to start
     */
    public void startGame(int ID)  {
        lobby.getGames().get(ID).initialization();
    }


    /**
     * Adds the selected tiles of the game whose id is 'gameID' in the column 'col'
     * @param gameID The ID of the game
     * @param playerID  The username of the player requesting the move
     * @param col The column where you want to add the tiles
     * @return The reply to be sent to the client
     */
    public Message addToBookshelf(int gameID, String playerID, int col ){
        System.out.println("Controller: add to game " + gameID + " by " + playerID + " in column number " + col);
        Message reply = new Message();

        try {
            lobby.getGames().get(gameID).addToBookShelf(lobby.getPlayers().get(playerID),col);
            reply.setType(MessageTypes.OK);
            reply.setContent("Move successful add to bookshelf");

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

        System.out.println("Controller: swap in game: " + gameID + " by " + playerID);
        Message reply = new Message();

        try {
            lobby.getGames().get(gameID).swapOrder(ints,lobby.getPlayers().get(playerID));
            reply.setType(MessageTypes.OK);
            reply.setContent("Move successful swap order");
        } catch (NotCurrentPlayer e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You are not the current player");

        } catch (IllegalArgumentException e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You cannot choose these positions");

        }catch (TooManySelected e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("Incorrect number of orders ");

        }catch (MoveNotPossible e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You can't do that now");

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
        System.out.println("Controller: remove from game: " + gameID + " by " + playerID + " in " + points);
        Message reply = new Message();


        try {
            lobby.getGames().get(gameID).removeTileArray(lobby.getPlayers().get(playerID),points);
            reply.setType(MessageTypes.OK);
            reply.setContent("Move successful remove tiles");

        }catch (OutOfDomain e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You selected a point outside the board");

        }catch (TilesCannotBeSelected e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("One of the tiles cannot be selected");

        }catch (TilesNotAdjacent e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("The tiles are not adjacent to each other");

        }catch (IllegalArgumentException e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You selected 0 tiles");

        }catch (TooManySelected e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You selected too many tiles");

        }catch (NotCurrentPlayer e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("It's not your turn");

        }catch (SameElement e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("Tou cannot choose the same tile multiple times");

        }catch (MoveNotPossible e) {
            reply.setType(MessageTypes.ERROR);
            reply.setContent("You can't do that now");

        }

        return reply;
    }



    //Lobby methods


    public IntMessage newLobby(String client, int players){
        IntMessage msg = new IntMessage();
        int gameNum =  lobby.newLobby(client,players);
        msg.setType(MessageTypes.WAITING_FOR_PLAYERS);
        msg.setContent("Lobby created. Waiting for other players...");
        msg.setNum(gameNum);
        return msg;
    }



    public IntMessage handleNewClient(String client,VirtualView view) {

        try {

            int response = lobby.handleClient(client,view);

            if (response == -1) {
                IntMessage reply = new IntMessage();
                reply.setType(MessageTypes.NEW_LOBBY);
                reply.setContent("Select the number of players (2 to 4)");
                return reply;
            } else {
                IntMessage reply = new IntMessage();
                reply.setType(MessageTypes.WAITING_FOR_PLAYERS);
                reply.setContent("Added to a game. Waiting for other player...");
                reply.setNum(response);
                return reply;
            }
        } catch (UsernameAlreadyTaken e) {
            IntMessage reply = new IntMessage();
            reply.setType(MessageTypes.ERROR);
            reply.setContent("Username already taken");
            return reply;
        }
    }

    public void addView(VirtualView view){
        lobby.getViews().put(view.getUsername(),view);
    }
    public void playerDisconnection(String username){
        System.out.println(username+ " was disconnected by the controller");
        lobby.playerDisconnection(username);

    }




    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Game number: " + ((Model)evt.getSource()).getGameID() +" ended");
        lobby.closeGame(((Model)evt.getSource()).getGameID());
    }
}

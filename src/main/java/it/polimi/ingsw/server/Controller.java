package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * <p>Class used to manage multiple games together and the interaction of the players/clients with those games.</p>
 * <p>It is responsible for routing the players' commands to the correct model.
 *    and forwarding the handing of a new client to the Lobby.</p>
 * <p>It formats the proper messages that ought to be sent to the client as a response to their actions.</p>
 *
 */
public class Controller implements PropertyChangeListener {
    private final Lobby lobby;
    private  HashMap<String, Player> players;
    private  HashMap<Integer,Model> games;



    /**
     *  Main constructor of the class.
     * @param mainLobby The lobby of the server.
     */
    public Controller(Lobby mainLobby) {
        lobby = mainLobby;
    }

    /**
     * Constructor used for testing.
     * @param models HashMap of all the games(Models) in the server.
     * @param playerMap Hash map of all the users playing in a game.
     */
    public Controller(HashMap<Integer,Model> models,HashMap<String ,Player > playerMap){
        lobby=new Lobby(models,playerMap);
        lobby.setController(this);
        games = models;
        players = playerMap;
    }

    /**
     * Method to start the selected game.
     * @param ID The ID of the game you want to start.
     */
    public void startGame(int ID)  {
        lobby.getGames().get(ID).initialization();
    }


    /**
     * Method to add the selected tiles of the game whose id is 'gameID' in the column 'col'.
     * @param gameID The ID of the game.
     * @param playerID  The username of the player requesting the move.
     * @param col The column where you want to add the tiles.
     * @return The reply to be sent to the client.
     */
    public Message addToBookshelf(int gameID, String playerID, int col ){
        Message reply = new Message();

        try {
            lobby.getGames().get(gameID).addToBookShelf(lobby.getPlayers().get(playerID.toLowerCase()),col);
            reply.setType(MessageTypes.OK);
            reply.setText("Move successful add to bookshelf");

        } catch (OutOfDomain e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("The requested column does not exists");

        }catch (ColumnIsFull e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("The requested column is full");

        }catch (NotCurrentPlayer e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("It's not your turn");

        }catch (MoveNotPossible e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You can't do that now");

        }

        return reply;
    }


    /**
     * Method to swap the order of the array of selected tiles to the order describes in the array ints.
     * ex. oldSelectedTiles[G,B,Y], ints[2,1,3] --> newSelectedTiles[B,G,Y].
     *
     * @param ints The new order in which you want the array.
     * @param gameID The ID of the game.
     * @param playerID The username of the player.
     * @return The reply to be sent to the client.
     */
    public Message swapOrder(ArrayList<Integer> ints, int gameID, String playerID){

        Message reply = new Message();

        try {
            lobby.getGames().get(gameID).swapOrder(ints,lobby.getPlayers().get(playerID.toLowerCase()));
            reply.setType(MessageTypes.OK);
            reply.setText("Move successful swap order");
        } catch (NotCurrentPlayer e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You are not the current player");

        } catch (IllegalArgumentException e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You cannot choose these positions");

        }catch (TooManySelected e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("Incorrect number of orders");

        }catch (MoveNotPossible e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You can't do that now");

        }

        return reply;
    }


    /**
     * Method to remove tiles of coordinates 'points'.
     * @param gameID The ID of the game.
     * @param playerID  The username of the player requesting the move.
     * @param points The coordinates of the tiles.
     * @return The reply to be sent to the client.
     */
    public Message removeTiles(int gameID,String playerID, ArrayList<Point> points){
        Message reply = new Message();


        try {
            lobby.getGames().get(gameID).removeTileArray(lobby.getPlayers().get(playerID.toLowerCase()),points);
            reply.setType(MessageTypes.OK);
            reply.setText("Move successful remove tiles");

        }catch (OutOfDomain e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You selected a point outside the board");

        }catch (TilesCannotBeSelected e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("One of the tiles cannot be selected");

        }catch (TilesNotAdjacent e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("The tiles are not adjacent to each other");

        }catch (IllegalArgumentException e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You selected 0 tiles");

        }catch (TooManySelected e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You selected too many tiles");

        }catch (NotCurrentPlayer e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("It's not your turn");

        }catch (SameElement e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You cannot choose the same tile multiple times");

        }catch (MoveNotPossible e) {
            reply.setType(MessageTypes.ERROR);
            reply.setText("You can't do that now");

        }

        return reply;
    }



    //Lobby methods


    /**
     * <p>Method used to forward the creation of a new lobby tp the Lobby class.</p>
     * <p>It is used only after the user has already "logged in".</p>
     * @param client The username of the player.
     * @param players The number of players for the game.
     * @return The message to be sent to the client.
     */
    public IntMessage newLobby(String client, int players){
        IntMessage msg = new IntMessage();
        int gameNum =  lobby.newLobby(client.toLowerCase(),players);
        msg.setType(MessageTypes.WAITING_FOR_PLAYERS);
        msg.setText("Lobby created. Waiting for other players...");
        msg.setNum(gameNum);
        return msg;
    }


    /**
     * <p>Method for forwarding a message used for the handling of a new client to the lobby.</p>
     * <p>The controller checks if a player was already connected to game and if reconnects the player.</p>
     * @param client The username of the player.
     * @param view The virtual view associated with the player.
     * @return A message to send to the client which can be:
     * <ul>
     *     <li> an error message if the username was already taken;</li>
     *     <li> a message to inform the player that he reconnected to the game;</li>
     *     <li>a  message to inform the player that he was added to a game;</li>
     *     <li>a  message to inform the player that he needs to create a new lobby.</li>
     * </ul>
     */
    public IntMessage handleNewClient(String client,VirtualView view) {

        try {

            if(lobby.getDisconnectedPlayers().containsKey(client.toLowerCase())) {
                int idx = lobby.playerReconnection(client.toLowerCase(),view);
                IntMessage reply = new IntMessage();
                reply.setType(MessageTypes.RECONNECT);
                reply.setNum(idx);
                reply.setText("Reconnected to the game");
                return reply;
            }

            Optional<Integer> response = lobby.handleClient(client.toLowerCase(),client,view);

            if (response.isEmpty()) {
                IntMessage reply = new IntMessage();
                reply.setType(MessageTypes.NEW_LOBBY);
                reply.setText("Select the number of players (2 to 4)");
                return reply;
            } else {
                IntMessage reply = new IntMessage();
                reply.setType(MessageTypes.WAITING_FOR_PLAYERS);
                reply.setText("Added to a game. Waiting for other player...");
                reply.setNum(response.get());
                return reply;
            }
        } catch (UsernameAlreadyTaken e) {
            IntMessage reply = new IntMessage();
            reply.setType(MessageTypes.ERROR);
            reply.setText("Username already taken");
            return reply;
        }
    }

    /**
     * Method to add a virtual view to the HashMap of all views.
     * @param view The virtual view of the player.
     */
    public void addView(VirtualView view){
        lobby.getViews().put(view.getUsername().toLowerCase(),view);
    }


    /**
     * Method to forward to the lobby the handling of the disconnection of a player.
     * @param username The username of the player.
     */
    public void playerDisconnection(String username){
        System.out.println(username.toLowerCase()+ " was disconnected by the controller");

        lobby.playerDisconnection(username.toLowerCase());

    }


    /**
     * Method to forward to the lobby the closing of a game when it ends.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Game number: " + ((Model)evt.getSource()).getGameID() +" ended");
        lobby.closeGame(((Model)evt.getSource()).getGameID());
    }

    /**
     * Method that creates a ChatMessage destined to
     * PublicChat with playerForwarding and message
     * and thanks to gameId forwards it to the correct game.
     *
     * @param gameId        Key to access the correct game in the HashMap games.
     * @param playerForwarding      Player who wrote the message.
     * @param message       Message to forward.
     * @return      <i>ChatMessage</i> created.
     */
    public ChatMessage sendMessage (int gameId, String playerForwarding, String message) {

        lobby.getGames().get(gameId).sendMessage(playerForwarding.toLowerCase(), message);

        ChatMessage messageOut = new ChatMessage(playerForwarding.toLowerCase(), message);
        messageOut.setType(MessageTypes.CHAT);

        return messageOut;
    }

    /**
     * Method that creates a ChatMessage destined to
     * PrivateChat with playerForwarding and message
     * and thanks to gameId forwards it to the correct game.
     *
     * @param gameId        Key to access the correct game in the HashMap games.
     * @param playerForwarding      Player who wrote the message.
     * @param message       Message to forward.
     * @param receivingPlayer       Player who the message is destined to.
     * @return      <i>ChatMessage</i> created.
     */
    public ChatMessage sendMessage (int gameId, String playerForwarding, String message, String receivingPlayer) {

        lobby.getGames().get(gameId).sendMessage(playerForwarding.toLowerCase(), message, receivingPlayer);

        ChatMessage messageOut = new ChatMessage(playerForwarding.toLowerCase(), message ,receivingPlayer);
        messageOut.setType(MessageTypes.CHAT);

        return messageOut;
    }

    /**
     * <strong>Getter</strong> -> Gets the lobby.
     *
     * @return      <i>Lobby</i>.
     */
    public Lobby getLobby () {
        return lobby;
    }
}

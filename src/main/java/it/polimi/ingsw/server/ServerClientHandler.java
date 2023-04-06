package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Exceptions.UsernameAlreadyTaken;
import it.polimi.ingsw.server.Messages.IntMessage;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.server.Messages.PointsMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientHandler implements Runnable  {

    private String username;
    private int lobbyID;
    private int gameID;
    private Player player;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private Message messageIn;
    private Message messageOut;

    private Controller controller;

    boolean disconnected = false;
    public ServerClientHandler(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller=controller;
    }
    public ServerClientHandler(Controller controller) {
        this.controller=controller;
    }


    public void run() {
        try {
            //Object streams: used for communicating between client and server,
            // they pass an object between the two.
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            while (socket.isConnected() /* !disconnected*/) {

                //Get the object (message in this case) coming from the client
                messageIn= (Message) ois.readObject();
                //process the message and send the response
                processMessage(messageIn);

            }

            //Closing socket and object stream when the client disconnects
            socket.close();
            ois.close();
            oos.close();

        }
        catch (IOException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setNickname(String nickname) {
        this.username = nickname;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public String getNickname() {
        return username;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }






    /*
    * Mancano ancora i casi per:
    * -Annullare la mossa appena fatta
    * -Anche altri che ora non ricordo ma probabilmente scriverò qui
    *
    */

    private void processMessage(Message incomingMsg) throws IOException {
        if(incomingMsg != null)
        {
            //Print just for debugging purposes
            System.out.println("Server received " + incomingMsg + "from: " + username);

            //Gets the type of the message and calls gor the appropriate controller method
            switch (incomingMsg.getType()) {

                //First time client connections (or for clients whose selected username was already taken)
                case CONNECT -> {

                    System.out.println("Server: connect message received");


                    //Check if there are waiting rooms or the client has to start another game
                    synchronized (this){

                        /*Controller calls for the lobby to handle the new client:
                          if the username is already taken, it asks the client to select a new one,
                          if there are games waiting for players, adds the client to them,
                          if there aren't any games, it creates a new one and asks the client to
                          select the number of players.
                        */
                        messageOut = controller.handleNewClient(this);

                    }

                    //Sends the response to the client
                    this.oos.writeObject(messageOut);

                }
                //When a client has decided the number of players they want in the game
                case NUM_OF_PLAYERS -> {

                    //Class cast to a specific subclass to use its methods
                    IntMessage message = (IntMessage) incomingMsg;

                    //Creates a new game with the selected number of players
                    messageOut = controller.newLobby(this, message.getNum());

                    //Sends the response to the client
                    this.oos.writeObject(messageOut);

                }
                case DISCONNECT -> { //Completamente da fare, mancano anche i relativi metodi nelle altre classi
                    disconnected = true;

                }
                case REMOVE_FROM_BOARD -> {  //Solo accennato, da finire

                    //Class cast to a specific subclass to use its methods
                    PointsMessage message = (PointsMessage) incomingMsg;

                    messageOut = controller.removeTiles(this.gameID,this.username, message.getTiles());

                    //Sends the response to the client
                    this.oos.writeObject(messageOut);

                }
                case SWITCH_PLACE -> {
                   // messageOut = controller.swapOrder();

                    this.oos.writeObject(messageOut);
                }
                case ADD_TO_BOOKSHELF -> {
                    //messageOut = controller.addToBookshelf();

                    this.oos.writeObject(messageOut);
                }
                default -> {
                    System.out.println("Server received: " + incomingMsg.toString());
                }
            }
        }
    }
}

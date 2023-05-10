package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Messages.IntMessage;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.server.Messages.PointsMessage;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.TCPVirtualView;

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
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            while (socket.isConnected() /* !disconnected*/) {

                messageIn= (Message) ois.readObject();
                processMessage(messageIn);

            }

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

    private void processMessage(Message incomingMsg) throws IOException {
        if(incomingMsg != null) {
            System.out.println("Server received " + incomingMsg.toString() + "from: " + username);
            switch (incomingMsg.getType()) {
                case USERNAME -> {

                    System.out.println("Server: connect message received");

                    //Check if there are waiting rooms or the client has to start another game
                    synchronized (this){
                        messageOut = controller.handleNewClient(incomingMsg.getUsername());
                    }

                    if(!messageOut.getType().equals(MessageTypes.ERROR)){
                        this.username=incomingMsg.getUsername();
                        controller.addView(new TCPVirtualView(username,this.socket));
                    }

                }

                case NUM_OF_PLAYERS -> {
                    IntMessage message = (IntMessage) incomingMsg;
                    messageOut = controller.newLobby(this.username, message.getNum());

                }
                case DISCONNECT -> {
                    disconnected = true;

                }
                case REMOVE_FROM_BOARD -> {
                    PointsMessage temp = (PointsMessage) incomingMsg;
                    messageOut = controller.removeTiles(this.gameID,this.username, temp.getTiles());
                }
                case SWITCH_PLACE -> {
                    //controller.swapOrder();
                }
                case ADD_TO_BOOKSHELF -> {
                    //controller.addToBookshelf();
                }
                default -> {
                    System.out.println("Server received: " + incomingMsg.toString());
                }
            }
            this.oos.writeObject(messageOut);
        }
    }
}

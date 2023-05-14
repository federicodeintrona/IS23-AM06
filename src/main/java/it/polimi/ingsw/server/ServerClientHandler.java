package it.polimi.ingsw.server;


import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.TCPVirtualView;
import it.polimi.ingsw.utils.Messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientHandler implements Runnable  {

    private String username;
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

            while (!disconnected) {

                if(!socket.isConnected()){
                    disconnected=false;
                }
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

    private void processMessage(Message incomingMsg) throws IOException {
        if(incomingMsg != null) {
            System.out.println("Server received " + incomingMsg.getType()+ incomingMsg.getUsername() + "from: " + username);
            switch (incomingMsg.getType()) {
                case USERNAME -> {
                    //Check if there are waiting rooms or the client has to start another game
                    synchronized (this){
                        messageOut = controller.handleNewClient(incomingMsg.getUsername(),
                                new TCPVirtualView(incomingMsg.getUsername(),this.socket));
                    }

                    if(!messageOut.getType().equals(MessageTypes.ERROR)){
                        this.gameID = ((IntMessage) messageOut).getNum();
                        this.username=incomingMsg.getUsername();
                    }

                }
                case NUM_OF_PLAYERS -> {
                    messageOut = controller.newLobby(this.username,((IntMessage) incomingMsg).getNum());
                    this.gameID = ((IntMessage) messageOut).getNum();
                }
                case REMOVE_FROM_BOARD -> {
                    messageOut = controller.removeTiles(this.gameID,this.username, ((PointsMessage) incomingMsg).getTiles());
                }
                case SWITCH_PLACE -> {
                    controller.swapOrder(((IntArrayMessage) incomingMsg).getIntegers(),gameID,username);
                }
                case ADD_TO_BOOKSHELF -> {
                    controller.addToBookshelf(gameID,username,((IntMessage)incomingMsg).getNum());
                }
                case DISCONNECT -> {
                    controller.playerDisconnection(username);
                    disconnected = true;
                }
                default -> {
                    System.out.println("Server received: " + incomingMsg.toString());
                }
            }
            this.oos.writeObject(messageOut);
        }
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setNickname(String nickname) {
        this.username = nickname;
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


}

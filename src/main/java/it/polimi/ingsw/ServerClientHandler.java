package it.polimi.ingsw;

import it.polimi.ingsw.Messages.IntMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageTypes;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.lang.InterruptedException;
public class ServerClientHandler implements Runnable  {

    String nickname;
    int gameID;
    int playerID;

    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    Message messageIn;
    Message messageOut;

    Controller controller;

    public ServerClientHandler(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller=controller;
    }

    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            while (socket.isConnected()) {
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

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    private void processMessage(Message incomingMsg) throws IOException {
        if(incomingMsg != null)
        {
            System.out.println("Server received " + incomingMsg.toString() + "from: " + nickname);
            switch (incomingMsg.getType()) {
                case CONNECT -> {
                    System.out.println("Server: connect message received");

                    synchronized(this){
                    if (!controller.waitingLobby()) {
                        messageOut.setType(MessageTypes.NEW_LOBBY);
                        messageOut.setContent("Select the number of players");
                    } else {
                        controller.addClient(this);
                        messageOut.setType(MessageTypes.WAITING_FOR_PLAYERS);
                        messageOut.setContent("Added to a game. Waiting for other player...");
                    }}
                    this.oos.writeObject(messageOut);

                    }

                case NUM_OF_PLAYERS -> {
                    IntMessage message = (IntMessage) incomingMsg;
                    controller.newLobby(this, message.getNum());

                    messageOut.setType(MessageTypes.WAITING_FOR_PLAYERS);
                    messageOut.setContent("Lobby created. Waiting for other players...");

                }
                case DISCONNECT -> {

                }
                default -> {
                    System.out.println("Server received: " + incomingMsg.toString());
                }
            }
        }
    }
}

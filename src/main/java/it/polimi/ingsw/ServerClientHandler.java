package it.polimi.ingsw;

import it.polimi.ingsw.Messages.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.lang.InterruptedException;
public class ServerClientHandler implements Runnable  {

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
            while (socket.isConnected()) {
                ois = new ObjectInputStream(socket.getInputStream());
                //convert ObjectInputStream object to String
                messageIn= (Message) ois.readObject();
                messageOut=(Message) controller.processMessage(messageIn,playerID,gameID);
                //create ObjectOutputStream object
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(messageOut);
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
}

package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.server.View.View;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkerTcp implements Networker{
    private static int port; //da sistemare
    Socket socket ;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String username;
    private int lobbyID;
    private int gameID;
    private Message messageOut;
    private View view;

    public NetworkerTcp() throws IOException, ParseException {
        JsonReader config;
        config = new JsonReader("src/main/java/it/polimi/ingsw/server/config/Server.json");
        port=config.getInt("port");
    }

    public void initializeConnection() {
        try {
            socket = new Socket("127.0.0.1", 9876);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Created TCP connection with Server");
    }

    public void firstConnection (){
        Scanner scanner = new Scanner(System.in);
        String username;
        boolean validUsername = false;

        while (!validUsername) {
            System.out.print("Inserisci un username: ");
            username = scanner.nextLine();
            try {
                oos.writeObject(username);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                messageOut= (Message) ois.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (!messageOut.getType().equals(MessageTypes.ERROR)) validUsername = true;

            System.out.println(messageOut);
        }
    };
    public void numberOfPlayersSelection(int numberOfPlayers){
        try {
            oos.writeObject(numberOfPlayers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            messageOut= (Message) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(messageOut);
    };
    public void removeTilesFromBoard(ArrayList<Point> tiles){
        try {
            oos.writeObject(tiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            view= (View) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
    public void switchTilesOrder(ArrayList<Integer> ints){
        try {
            oos.writeObject(ints);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            view= (View) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    public void addTilesToBookshelf (int column){
        try {
            oos.writeObject(column);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            view= (View) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
}

package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.utils.JsonReader;
import org.json.simple.parser.ParseException;

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

    public NetworkerTcp()  {
        JsonReader config;
        try {
            config = new JsonReader("src/main/java/it/polimi/ingsw/server/config/Server.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

    public Message firstConnection (Message username){
        try {
            oos.writeObject(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return (Message) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
    public Message numberOfPlayersSelection(Message numberOfPlayers){
        try {
            oos.writeObject(numberOfPlayers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (Message) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
    public Message removeTilesFromBoard(Message tiles){
        try {
            oos.writeObject(tiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (Message) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
    public Message switchTilesOrder(Message ints){
        try {
            oos.writeObject(ints);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (Message) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    public Message addTilesToBookshelf (Message column){
        try {
            oos.writeObject(column);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (Message) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
}

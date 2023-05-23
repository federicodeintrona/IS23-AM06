package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.JsonReader;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;


public class NetworkerTcp implements Networker{
    private static int port;
    Socket socket ;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private View view;

    public NetworkerTcp()  {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("NetworkerTcp.json");
            config=new JsonReader(is);
//            config = new JsonReader("src/main/resources/NetworkerTcp.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        port=config.getInt("port");
    }

    public void initializeConnection() {
        try {
            socket = new Socket("127.0.0.1", port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Created TCP connection with Server");
    }

    public void firstConnection (Message username){
        try {
            oos.writeObject(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            view.receivedMessage( (Message) ois.readObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
    public void numberOfPlayersSelection(Message numberOfPlayers){
        try {
            oos.writeObject(numberOfPlayers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            view.receivedMessage( (Message) ois.readObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
    public void removeTilesFromBoard(Message tiles){
        try {
            oos.writeObject(tiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            view.receivedMessage( (Message) ois.readObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };
    public void switchTilesOrder(Message ints){
        try {
            oos.writeObject(ints);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            view.receivedMessage( (Message) ois.readObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    public void addTilesToBookshelf (Message column){
        try {
            oos.writeObject(column);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            view.receivedMessage( (Message) ois.readObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setView(View view) {
        this.view=view;
    }

}

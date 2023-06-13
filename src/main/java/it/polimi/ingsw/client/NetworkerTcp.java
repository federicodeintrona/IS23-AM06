package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.JsonReader;
import org.json.simple.parser.ParseException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;


public class NetworkerTcp implements Networker, PropertyChangeListener {
    private static int port;
    private static String host;
    private Socket socket ;
    private ObjectOutputStream oos;
    private View view;
    private ClientState clientState;

    public NetworkerTcp(ClientState clientState,String host) {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config=new JsonReader(is);
            this.clientState = clientState;
            NetworkerTcp.host =host;
            port=config.getInt("tcpPort");
        } catch (IOException e) {
            System.out.println("Input/Output problems. Try to close and reopen the program");
            e.printStackTrace();
            close();
        } catch (ParseException e) {
            System.out.println("Problem with tha parsing of file json. Try to close and reopen the program");
            e.printStackTrace();
            close();
        }
    }

    public void initializeConnection() {
        Reader reader;
        try {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            reader=new Reader(socket,oos,this, clientState);
            reader.start();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
            close();
        }


    }

    private void close(){
        try {
            socket.close();
            oos.close();
        } catch (IOException e) {
            System.out.println( "Error: unable to close the socket...");
            e.printStackTrace();
        }

    }

    /**
     * Sends to server the username selected by player
     * @param username a message which contains the username selected by the palyer
     */
    public void firstConnection (Message username){
        try {
            oos.writeObject(username);
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }

    }

    /**
     * Sends to server the number of player selected
     * @param numberOfPlayers a message which contains the number of player
     */
    public void numberOfPlayersSelection(Message numberOfPlayers){
        try {
            oos.writeObject(numberOfPlayers);
            oos.flush();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }

    }

    /**
     * Sends to the server the positions of the tiles to remove from board
     * @param tiles a message which contains the positions of the tiles to remove from board
     */
    public void removeTilesFromBoard(Message tiles){
        try {
            oos.writeObject(tiles);
            oos.flush();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }
    }

    /**
     * Sends to the server the order of the tiles
     * @param ints a message which contains the order of the tiles
     */
    public void switchTilesOrder(Message ints){
        try {
            oos.writeObject(ints);
            oos.flush();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }
    }

    /**
     * Send to the server the column chosen from the player
     * @param column a message which contains the column chosen by the player
     */
    public void addTilesToBookshelf (Message column){
        try {
            oos.writeObject(column);
            oos.flush();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }
    }

    /**
     * Send to the view the message received
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        view.receivedMessage((Message) evt.getNewValue());
    }

    /**
     * Sets the view
     * @param view The view crated by the client base
     */
    @Override
    public void setView(View view) {
        this.view=view;
    }

    /**
     * Send to the server the chat message written by the player
     * @param message a message which contains the chat message by the player
     */
    @Override
    public void chat(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

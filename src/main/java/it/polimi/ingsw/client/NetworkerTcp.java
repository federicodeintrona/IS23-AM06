package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.CLI.CLIMain;
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
    private final ClientState clientState;

    public NetworkerTcp(ClientState clientState,String host) {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config=new JsonReader(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        this.clientState = clientState;
        NetworkerTcp.host =host;
        port=config.getInt("tcpPort");
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

    public void firstConnection (Message username){
        try {
            oos.writeObject(username);
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }

    }
    public void numberOfPlayersSelection(Message numberOfPlayers){
        try {
            oos.writeObject(numberOfPlayers);
            oos.flush();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }

    }
    public void removeTilesFromBoard(Message tiles){
        try {
            oos.writeObject(tiles);
            oos.flush();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }
    }
    public void switchTilesOrder(Message ints){
        try {
            oos.writeObject(ints);
            oos.flush();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }
    }

    public void addTilesToBookshelf (Message column){
        try {
            oos.writeObject(column);
            oos.flush();
        } catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        view.receivedMessage((Message) evt.getNewValue());
    }

    @Override
    public void setView(View view) {
        this.view=view;
    }

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

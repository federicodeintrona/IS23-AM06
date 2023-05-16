package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.JsonReader;
import org.json.simple.parser.ParseException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;



public class NetworkerTcp implements Networker, PropertyChangeListener {
    private static int port;
    Socket socket ;
    private ObjectOutputStream oos;
    private final ClientState clientState;
    private CLIMain cliMain;

    public NetworkerTcp(ClientState clientState) {
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
        this.clientState = clientState;
        port=config.getInt("port");
    }

    public void initializeConnection() {
        Reader reader;
        ObjectInputStream ois;
        try {
            socket = new Socket("127.0.0.1", port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reader=new Reader(ois,this, clientState);
        reader.run();
    }

    public void setUserInterface(CLIMain cliMain) {
        this.cliMain= cliMain;
    }

    public void firstConnection (Message username){
        try {
            oos.writeObject(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void numberOfPlayersSelection(Message numberOfPlayers){
        try {
            oos.writeObject(numberOfPlayers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void removeTilesFromBoard(Message tiles){
        try {
            oos.writeObject(tiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void switchTilesOrder(Message ints){
        try {
            oos.writeObject(ints);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTilesToBookshelf (Message column){
        try {
            oos.writeObject(column);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setCli(CLIMain cli) {
        this.cliMain=cli;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        cliMain.receivedMessage((Message) evt.getNewValue());
    }
}

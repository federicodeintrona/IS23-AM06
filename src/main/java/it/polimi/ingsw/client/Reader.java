package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Messages.ViewMessage;
import it.polimi.ingsw.utils.Tiles;
import it.polimi.ingsw.utils.Matrix;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.out;

public class Reader implements Runnable{
    private final ObjectInputStream client;
    private final NetworkerTcp networkerTcp;
    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);
    private final ClientState clientState;


    public Reader(ObjectInputStream client, NetworkerTcp networkerTcp, ClientState clientState) {
        this.client = client;
        this.networkerTcp = networkerTcp;
        this.clientState = clientState;
    }

    public void run() {
        notifier.addPropertyChangeListener(networkerTcp);
        out.println("Sono nel reader");
        Message newMessage;
        Message oldMessage = null;
        while(true){
            try {
                newMessage = (Message) client.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if(newMessage.getType()== MessageTypes.VIEW){
                ViewMessage message= (ViewMessage) newMessage;
                switch (message.getObjectName()) {
                    case "board":
                        Matrix board=(Matrix ) message.getContent();
                        clientState.setBoard(board);
                    case "bookshelf":
                        Matrix bookshelf=(Matrix ) message.getContent();
                        String Username= message.getUsername();
                        clientState.setAllBookshelf(Username, bookshelf);
                    case "currPlayer":
                        String currPlayer=(String) message.getContent();
                        clientState.setCurrentPlayer(currPlayer);
                    case "playerNames":
                        ArrayList<String> playerNames = (ArrayList<String>) message.getContent();
                        clientState.setAllUsername(playerNames);
                    case "commonObj":
                        ArrayList<Integer> commonObj = (ArrayList<Integer>) message.getContent();
                        clientState.setGameCommonObjective(commonObj);
                    case "publicPoints":
                        int publicPoints = (int) message.getContent();
                        String username = message.getUsername();
                        clientState.setAllPublicPoints(username, publicPoints);
                    case "selectedTiles":
                        ArrayList<Tiles> selectedTiles = (ArrayList<Tiles>) message.getContent();
                        clientState.setSelectedTiles(selectedTiles);
                    case "personalObj":
                        HashMap<Point, Tiles> personalObj = (HashMap<Point, Tiles>) message.getContent();
                        clientState.setMyPersonalObjective(personalObj);
                    case "privatePoints":
                        int privatePoints = (int) message.getContent();
                        clientState.setMyPoints(privatePoints);
                }
            }
            else {
                notifier.firePropertyChange(new PropertyChangeEvent(newMessage,
                        newMessage.getType().toString(),oldMessage,newMessage));
                oldMessage= newMessage;
            }
        }
    }
}
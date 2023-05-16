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
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;

public class Reader extends Thread{
    private Socket socket;
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
                out.println(message.getObjectName());
                switch (message.getObjectName()) {
                    case ("board") -> {
                        out.println("board");
                        Matrix board = (Matrix) message.getContent();
                        clientState.setBoard(board);
                    }
                    case ("bookshelf") -> {
                        out.println("bookshelf");
                        Matrix bookshelf = (Matrix) message.getContent();
                        String Username = message.getUsername();
                        clientState.setAllBookshelf(Username, bookshelf);
                    }
                    case ("currPlayer") -> {
                        out.println("currPlayer");
                        String currPlayer = (String) message.getContent();
                        clientState.setCurrentPlayer(currPlayer);
                    }
                    case ("playerNames") -> {
                        out.println("playerNames");
                        List <String> playerNames = (List<String>) message.getContent();
                        ArrayList pNames= new ArrayList<>(playerNames);
                        clientState.setAllUsername(pNames);
                    }
                    case ("commonObj") -> {
                        out.println("commonObj");
                        List<Integer> commonObj = (List<Integer>) message.getContent();
                        ArrayList cObj =new ArrayList<>(commonObj);
                        clientState.setGameCommonObjective(cObj);
                    }
                    case ("publicPoints") -> {
                        out.println("publicPoints");
                        int publicPoints = (int) message.getContent();
                        String username = message.getUsername();
                        clientState.setAllPublicPoints(username, publicPoints);
                    }
                    case ("selectedTiles") -> {
                        out.println("selectedTiles");
                        ArrayList<Tiles> selectedTiles = (ArrayList<Tiles>) message.getContent();
                        clientState.setSelectedTiles(selectedTiles);
                    }
                    case ("personalObj") -> {
                        out.println("personalObj");
                        HashMap<Point, Tiles> personalObj = (HashMap<Point, Tiles>) message.getContent();
                        clientState.setMyPersonalObjective(personalObj);
                    }
                    case ("privatePoints") -> {
                        out.println("privatePoints");
                        int privatePoints = (int) message.getContent();
                        clientState.setMyPoints(privatePoints);
                    }case ("nextPlayer") -> {
                        out.println("nextPlayer");
                        String nextPlayer= (String) message.getContent();
                        clientState.setNextPlayer(nextPlayer);
                    }
                    case("winner")->{
                        out.println("winner");
                        String winner= (String) message.getContent();
                        clientState.setWinnerPlayer(winner);
                    }
                    case("start")->{
                        out.println("start");
                        Boolean start=(Boolean) message.getContent();
                        clientState.setGameHasStarted(start);
                    }
                    case("end")->{
                        out.println("end");
                        Boolean end =(Boolean) message.getContent();
                        clientState.setGameIsEnded(end);
                    }
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
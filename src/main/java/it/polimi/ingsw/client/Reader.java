package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Messages.ViewMessage;
import it.polimi.ingsw.utils.Tiles;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimerInterface;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class Reader extends Thread implements TimerInterface {
    private Socket socket;
    private final ObjectInputStream client;
    private final ObjectOutputStream oos;
    private final NetworkerTcp networkerTcp;
    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);
    private final ClientState clientState;
    private boolean disconnected = false;
    private ScheduledExecutorService e;

    //Timer

    private  Timer timer;
    private int time = 0;
    private static final int initialDelay = 50;
    private static final int delta = 2000;

    public Reader(Socket socket,ObjectOutputStream oos, NetworkerTcp networkerTcp, ClientState clientState) throws IOException {
        this.client = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));;
        this.oos = oos;
        this.socket=socket;
        this.networkerTcp = networkerTcp;
        this.clientState = clientState;
    }

    public void run() {
        notifier.addPropertyChangeListener(networkerTcp);
        pingPong();
        Message newMessage = null;
        Message oldMessage = null;

        try {
            while(!disconnected){

                if(socket.isConnected()) newMessage = (Message) client.readUnshared();

                if(newMessage!=null) {
                    if (newMessage.getType() == MessageTypes.VIEW) {
                        processMessage(newMessage);
                    }
                    else if (newMessage.getType().equals(MessageTypes.PING)) {
                        time=0;
                    }
                    else {
                        notifier.firePropertyChange(new PropertyChangeEvent(newMessage,
                                newMessage.getType().toString(), oldMessage, newMessage));

                        oldMessage = newMessage;
                    }
                }
            }

        }
        catch(SocketException e){
            if(!disconnected) disconnect();
            else out.println("The game is about to close! Have fun! :)");

        }
        catch (IOException e) {
            System.out.println( "Server is not responding...");
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            System.out.println( "Unable to identify the received object...");

        }
    }



    private void pingPong(){
        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{
            Message msg = new Message();
            msg.setType(MessageTypes.PONG);
            try {
                oos.writeObject(msg);
                oos.flush();
            } catch (IOException e) {
                if(!disconnected)
                    System.out.println( "Server is not responding...");
            }
        },10,500, TimeUnit.MILLISECONDS);

        timer = new Timer();
        TimerTask task = new TimerCounter(this);
        timer.schedule(task,initialDelay,delta);
    }


    private void processMessage(Message newMessage){

        ViewMessage message = (ViewMessage) newMessage;
        switch (message.getObjName()) {
            case ("board") -> {
                Matrix board = (Matrix) message.getContent();
                clientState.setBoard(board);
            }
            case ("bookshelf") -> {
                Matrix bookshelf = (Matrix) message.getContent();
                String Username = message.getText();
                clientState.setAllBookshelf(Username, bookshelf);
            }
            case ("currPlayer") -> {
                String currPlayer = (String) message.getContent();
                clientState.setCurrentPlayer(currPlayer);
            }
            case ("playerNames") -> {
                List<String> playerNames = (List<String>) message.getContent();
                ArrayList pNames = new ArrayList<>(playerNames);
                clientState.setAllUsername(pNames);
            }
            case ("commonObj") -> {
                List<Integer> commonObj = (List<Integer>) message.getContent();
                ArrayList cObj = new ArrayList<>(commonObj);
                clientState.setGameCommonObjective(cObj);
            }
            case ("commonObjPoints") -> {
                clientState.setCommonObjectivePoints(new ArrayList<>((List<Integer>) message.getContent()));
            }
            case ("publicPoints") -> {
                int publicPoints = (int) message.getContent();
                String username = message.getText();
                clientState.setAllPublicPoints(username, publicPoints);
            }
            case ("selectedTiles") -> {
                ArrayList<Tiles> selectedTiles = (ArrayList<Tiles>) message.getContent();
                clientState.setSelectedTiles(selectedTiles);
            }
            case ("personalObj") -> {
                HashMap<Point, Tiles> personalObj = (HashMap<Point, Tiles>) message.getContent();
                clientState.setMyPersonalObjective(personalObj);
            }
            case ("personalObjNum")->{
                clientState.setMyPersonalObjectiveInt((int) message.getContent());
            }
            case ("privatePoints") -> {
                int privatePoints = (int) message.getContent();
                clientState.setMyPoints(privatePoints);
            }
            case ("nextPlayer") -> {
                String nextPlayer = (String) message.getContent();
                clientState.setNextPlayer(nextPlayer);
            }
            case ("chairPlayer")->{
                clientState.setChair((String) message.getContent());
            }
            case ("winner") -> {
                String winner = (String) message.getContent();
                clientState.setWinnerPlayer(winner);
            }
            case ("disconnectionWinner") ->{
                clientState.setDisconnectionWinner(true);
            }
            case ("start") -> {
                Boolean start = (Boolean) message.getContent();
                clientState.setGameHasStarted(start);
            }
            case ("message") -> {
                clientState.newMessageHandler((ChatMessage) message.getContent());
            }
            case ("reloadChats") -> {
                clientState.reloadChats((ChatController) message.getContent());
            }
            case ("end") -> {
                Boolean end = (Boolean) message.getContent();
                clientState.setGameIsEnded(end);
                disconnected=true;
            }
        }
    }

    @Override
    public void disconnect() {
        disconnected=true;
        e.shutdown();
        timer.cancel();

        try {
            socket.close();
            oos.close();
            client.close();
            System.exit(0);

        } catch (IOException ex) {
            System.exit(0);
        }
    }

    @Override
    public int updateTime() {
        time++;
        return time;
    }

    @Override
    public String getErrorMessage() {
        return "Server is not responding. Retry later";
    }
}
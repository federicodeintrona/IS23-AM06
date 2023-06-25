package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.server.VirtualView.TCPVirtualView;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.server.Model.GameState;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    public Controller controller;
    public HashMap<String,Player> playermap = new HashMap<>();
    public HashMap<Integer,Model> modelmap = new HashMap<>();

    public ArrayList<ArrayList<Player>> players = new ArrayList<>();
    public ArrayList<ArrayList<VirtualView>> views = new ArrayList<>();
    public Lobby lobby = new Lobby(modelmap,playermap);

    public int gameNumber=2;
    public int playerNumber=3;
    @BeforeEach
    void setUp() {

        modelmap.put(0,new Model());
        modelmap.put(1,new Model());

        //j = number of games
        for(int j = 0; j<gameNumber; j++) {

            //Add a game
            players.add(new ArrayList<>());
            views.add(new ArrayList<>());

            for (int i = 0; i < playerNumber; i++) {

                players.get(j).add(new Player("User"+ j + i));
                playermap.put(players.get(j).get(i).getUsername(), players.get(j).get(i));
                try {
                    views.get(j).add(new RMIVirtualView("User" + j + i,
                                    new ClientState("User" + j + i,new Object())));
                } catch (RemoteException e) {
                    System.out.println("tanto non succede");
                }
            }

            modelmap.get(j).setPlayers(players.get(j));
            modelmap.get(j).setVirtualViews(views.get(j));

        }

        controller=new Controller(lobby);
        lobby.setController(controller);
        controller.startGame(0);
        controller.startGame(1);
    }

    @Test
    void startGame() {

        assertEquals(modelmap.get(0).getState(), GameState.CHOOSING_TILES);
        assertEquals(modelmap.get(1).getState(), GameState.CHOOSING_TILES);

    }



    @Test
    void removeTiles() {
        ArrayList<Point> points = new ArrayList<>();
        Message msg;


        //OutOfDomain
        points.add(new Point(45,20));
        modelmap.get(0).setState(GameState.CHOOSING_TILES);
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("You selected a point outside the board",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //TilesCannotBeSelected
        points.add(new Point(5,5));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("One of the tiles cannot be selected",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //TilesNotAdjacent
        points.add(new Point(5,5));
        points.add(new Point(4,4));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("The tiles are not adjacent to each other",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //IllegalArgumentException
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),null);
        assertEquals("You selected 0 tiles",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //TooManySelected
        points.add(new Point(3,3));
        points.add(new Point(2,2));
        points.add(new Point(1,1));
        points.add(new Point(3,4));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("You selected too many tiles",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);


        //SameElement
        points.add(new Point(7,4));
        points.add(new Point(7,4));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("You cannot choose the same tile multiple times",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);


        //NotCurrentPlayer
        points.add(new Point(3,5));
        msg = controller.removeTiles(0,modelmap.get(0).getNextPlayer().getUsername(),points);
        assertEquals("It's not your turn",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //MoveNotPossible
        points.add(new Point(1,5));
        msg = controller.addToBookshelf(0,modelmap.get(0).getCurrPlayer().getUsername(),2);
        assertEquals("You can't do that now",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);


        //Normal
        points.add(new Point(7,4));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("Move successful remove tiles",msg.getText());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);


    }

    @Test
    void swapOrder() {
        ArrayList<Tiles> selected = new ArrayList<>();
        selected.add(Tiles.GREEN);
        selected.add(Tiles.BLUE);
        selected.add(Tiles.YELLOW);
        modelmap.get(0).setSelectedTiles(selected);
        modelmap.get(0).setState(GameState.CHOOSING_ORDER);
        Message msg;

        ArrayList<Integer> order = new ArrayList<>();



        //IllegalArgument
        order.add(0);
        order.add(23);
        msg = controller.swapOrder(order,0,modelmap.get(0).getCurrPlayer().getUsername());
        assertEquals("You cannot choose these positions",msg.getText());
        order.removeAll(order);

        //NotCurrentPlayer
        order.add(1);
        order.add(2);
        msg = controller.swapOrder(order,0,modelmap.get(0).getNextPlayer().getUsername());
        assertEquals("You are not the current player",msg.getText());
        order.removeAll(order);

        //TooManySelected
        order.add(0);
        order.add(1);
        order.add(2);
        order.add(2);
        msg = controller.swapOrder(order,0,modelmap.get(0).getCurrPlayer().getUsername());
        assertEquals("Incorrect number of orders",msg.getText());
        order.removeAll(order);

        //MoveNotPossible
        order.add(0);
        order.add(1);
        order.add(2);
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),new ArrayList<>());
        assertEquals("You can't do that now",msg.getText());
        order.removeAll(order);


        //Normal
        order.add(1);
        order.add(3);
        order.add(2);
        msg = controller.swapOrder(order,0,modelmap.get(0).getCurrPlayer().getUsername());
        assertEquals("Move successful swap order",msg.getText());

    }


    @Test
    void addToBookshelf() {
        Message msg;
        //Remove to change state and take some tiles
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(7,4));
        controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals(1,modelmap.get(0).getSelectedTiles().size());
        String player = modelmap.get(0).getCurrPlayer().getUsername();
        Player curr = modelmap.get(0).getCurrPlayer();

        msg = controller.addToBookshelf(0,modelmap.get(0).getCurrPlayer().getUsername(),7);
        modelmap.get(0).setState(GameState.CHOOSING_COLUMN);
        assertEquals("The requested column does not exists",msg.getText());

        ArrayList<Integer> order = new ArrayList<>();
        order.add(1);
        msg = controller.swapOrder(order,0,modelmap.get(0).getCurrPlayer().getUsername());
        modelmap.get(0).setState(GameState.CHOOSING_COLUMN);
        assertEquals("You can't do that now",msg.getText());

        msg = controller.addToBookshelf(0,modelmap.get(0).getNextPlayer().getUsername(),1);
        modelmap.get(0).setState(GameState.CHOOSING_COLUMN);
        assertEquals("It's not your turn",msg.getText());

        msg = controller.addToBookshelf(0,modelmap.get(0).getCurrPlayer().getUsername(),1);
        assertEquals("Move successful add to bookshelf",msg.getText());
        modelmap.get(0).setCurrPlayer(curr);

        points.removeAll(points);
        points.add(new Point(7,5));
        points.add(new Point(8,5));
        controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        points.removeAll(points);
        controller.addToBookshelf(0,modelmap.get(0).getCurrPlayer().getUsername(),1);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);
        modelmap.get(0).setCurrPlayer(curr);

        points.add(new Point(0,3));
        controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        points.removeAll(points);
        controller.addToBookshelf(0,modelmap.get(0).getCurrPlayer().getUsername(),1);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);
        modelmap.get(0).setCurrPlayer(curr);

        points.add(new Point(1,3));
        points.add(new Point(1,4));
        controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        points.removeAll(points);
        controller.addToBookshelf(0,modelmap.get(0).getCurrPlayer().getUsername(),1);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);
        modelmap.get(0).setCurrPlayer(curr);


        points.add(new Point(2,6));
        controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        msg = controller.addToBookshelf(0,modelmap.get(0).getCurrPlayer().getUsername(),1);
        modelmap.get(0).setState(GameState.CHOOSING_COLUMN);
        assertEquals("The requested column is full",msg.getText());
    }

    @Test
    void newLobby() {

        Message msg = controller.newLobby("ciao",2);
        assertEquals("Lobby created. Waiting for other players...",msg.getText());

    }

    @Test
    void handleNewClient() {

        RMIVirtualView view = new RMIVirtualView("ciao");
        view.setDisconnected(true);
        controller.handleNewClient("ciao",view);
        controller.newLobby("ciao",2);

        RMIVirtualView view1 = new RMIVirtualView("1");
        view1.setDisconnected(true);
        Message msg = controller.handleNewClient("1",view1);
        assertEquals("Added to a game. Waiting for other player...",msg.getText());
        controller.playerDisconnection("ciao");

        RMIVirtualView view2 = new RMIVirtualView("ciao");
        view2.setDisconnected(true);
        msg=controller.handleNewClient("ciao",view2);
        assertEquals("Reconnected to the game",msg.getText());

        msg=controller.handleNewClient("ciao",new RMIVirtualView("ciao"));
        assertEquals("Username already taken",msg.getText());

        msg=controller.handleNewClient("gennaro",new RMIVirtualView("gennaro"));
        assertEquals("Select the number of players (2 to 4)",msg.getText());


    }
}
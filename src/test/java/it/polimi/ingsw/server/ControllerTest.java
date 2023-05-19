package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientState;
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
                    throw new RuntimeException(e);
                }
            }

            modelmap.get(j).setPlayers(players.get(j));
            modelmap.get(j).setVirtualViews(views.get(j));

        }

        controller=new Controller(modelmap,playermap);
        controller.startGame(0);
        controller.startGame(1);
    }

    @Test
    void startGame() {

        assertEquals(modelmap.get(0).getState(), GameState.CHOOSING_TILES);
        assertEquals(modelmap.get(1).getState(), GameState.CHOOSING_TILES);

    }



    //I punti specifici devono ancora essere settati

    @Test
    void removeTiles() {
        ArrayList<Point> points = new ArrayList<>();
        Message msg;
/*

        //OutOfDomain
        points.add(new Point(45,20));
        modelmap.get(0).setState(GameState.CHOOSING_TILES);
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("You selected a point outside the board",msg.getUsername());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //TilesCannotBeSelected
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("One of the tiles cannot be selected",msg.getUsername());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //TilesNotAdjacent
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("The tiles are not adjacent to each other",msg.getUsername());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //IllegalArgumentException
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("You selected 0 tiles",msg.getUsername());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //TooManySelected
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("You selected too many tiles",msg.getUsername());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //NotCurrentPlayer
        points.add(new Point(3,5));
        msg = controller.removeTiles(0,modelmap.get(0).getNextPlayer().getUsername(),points);
        assertEquals("It's not your turn",msg.getUsername());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);

        //MoveNotPossible
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("You can't do that now",msg.getUsername());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);


        //Normal
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,modelmap.get(0).getCurrPlayer().getUsername(),points);
        assertEquals("Move successful",msg.getUsername());
        points.removeAll(points);
        modelmap.get(0).setState(GameState.CHOOSING_TILES);
        */

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
        /*
        ArrayList<Integer> order = new ArrayList<>();

        //NotCurrentPlayer
        order.add(0);
        order.add(1);
        order.add(2);
        msg = controller.swapOrder(order,0,modelmap.get(0).getNextPlayer().getUsername());
        assertEquals("You are not the current player",msg.getUsername());
        order.removeAll(order);

        //IllegalArgument
        order.add(0);
        order.add(23);
        order.add(2);
        msg = controller.swapOrder(order,0,modelmap.get(0).getCurrPlayer().getUsername());
        assertEquals("You cannot choose these positions",msg.getUsername());
        order.removeAll(order);

        //TooManySelected
        order.add(0);
        order.add(1);
        order.add(2);
        order.add(3);
        msg = controller.swapOrder(order,0,modelmap.get(0).getCurrPlayer().getUsername());
        assertEquals("Incorrect number of orders",msg.getUsername());
        order.removeAll(order);

        //MoveNotPossible
        order.add(0);
        order.add(1);
        order.add(2);
        msg = controller.swapOrder(order,0,modelmap.get(0).getCurrPlayer().getUsername());
        assertEquals("You can't do that now",msg.getUsername());
        order.removeAll(order);

        //Normal
        order.add(0);
        order.add(2);
        order.add(1);
        msg = controller.swapOrder(order,0,modelmap.get(0).getCurrPlayer().getUsername());
        assertEquals("Move successful",msg.getUsername());
        */
    }


    @Test
    void addToBookshelf() {

    }

    @Test
    void newLobby() {
    }

    @Test
    void handleNewClient() {
    }
}
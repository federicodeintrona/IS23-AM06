package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Model.GameState;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.Model.Tiles;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.server.VirtualView.TCPVirtualView;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    public Controller controller;
    public Model model0 = new Model();
    public Model model1 = new Model();
    public ArrayList<Player> players0 = new ArrayList<>();
    public ArrayList<Player> players1 = new ArrayList<>();
    public ArrayList<VirtualView> views0 = new ArrayList<>();
    public ArrayList<VirtualView> views1 = new ArrayList<>();
    public HashMap<String,Player> playermap = new HashMap<>();
    public HashMap<Integer,Model> modelmap = new HashMap<>();

    @BeforeEach
    void setUp() {
        modelmap.put(0,model0);
        modelmap.put(1,model1);

        //Model0
        for(int i=0;i<3;i++){
            players0.add(new Player("User"+i));
            playermap.put(players0.get(i).getUsername(),players0.get(i));
            views0.add(new RMIVirtualView());
        }
        model0.setPlayers(players0);
        model0.setVirtualViews(views0);

        //Model1
        for(int i=0;i<3;i++){
            players1.add(new Player("User"+i));
            playermap.put(players1.get(i).getUsername(),players1.get(i));
            views1.add(new RMIVirtualView());
        }
        model1.setPlayers(players1);
        model1.setVirtualViews(views1);

        controller=new Controller(modelmap,playermap);
    }

    @Test
    void startGame() {

        controller.startGame(0);
        assertEquals(model0.getState(), GameState.CHOOSING_TILES);

        controller.startGame(1);
        assertEquals(model1.getState(), GameState.CHOOSING_TILES);

    }

    @Test
    void removeTiles() {
        ArrayList<Point> points = new ArrayList<>();
        Message msg;

        //OutOfDomain
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,model0.getCurrPlayer().getUsername(),points);
        assertEquals(msg.getUsername(),"You selected a point outside the board");
        points.removeAll(points);

        //TilesCannotBeSelected
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,model0.getCurrPlayer().getUsername(),points);
        assertEquals(msg.getUsername(),"One of the tiles cannot be selected");
        points.removeAll(points);

        //TilesNotAdjacent
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,model0.getCurrPlayer().getUsername(),points);
        assertEquals(msg.getUsername(),"The tiles are not adjacent to each other");
        points.removeAll(points);

        //IllegalArgumentException
        msg = controller.removeTiles(0,model0.getCurrPlayer().getUsername(),points);
        assertEquals(msg.getUsername(),"You selected 0 tiles");
        points.removeAll(points);

        //TooManySelected
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,model0.getCurrPlayer().getUsername(),points);
        assertEquals(msg.getUsername(),"You selected too many tiles");
        points.removeAll(points);

        //NotCurrentPlayer
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,model0.getCurrPlayer().getUsername(),points);
        assertEquals(msg.getUsername(),"It's not your turn");
        points.removeAll(points);

        //MoveNotPossible
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,model0.getCurrPlayer().getUsername(),points);
        assertEquals(msg.getUsername(),"You can't do that now");
        points.removeAll(points);


        //Normal
        points.add(new Point(45,20));
        msg = controller.removeTiles(0,model0.getCurrPlayer().getUsername(),points);
        assertEquals(msg.getUsername(),"Move successful");
        points.removeAll(points);
    }

    @Test
    void swapOrder() {
        ArrayList<Tiles> selected = new ArrayList<>();
        selected.add(Tiles.GREEN);
        selected.add(Tiles.BLUE);
        selected.add(Tiles.YELLOW);
        model0.setSelectedTiles(selected);
        Message msg;

        ArrayList<Integer> order = new ArrayList<>();

        //NotCurrentPlayer
        order.add(0);
        order.add(1);
        order.add(2);
        msg = controller.swapOrder(order,0,model0.getNextPlayer().getUsername());
        assertEquals(msg.getUsername(),"You are not the current player");
        order.removeAll(order);

        //IllegalArgument
        order.add(0);
        order.add(23);
        order.add(2);
        msg = controller.swapOrder(order,0,model0.getCurrPlayer().getUsername());
        assertEquals(msg.getUsername(),"You cannot choose these positions");
        order.removeAll(order);

        //TooManySelected
        order.add(0);
        order.add(1);
        order.add(2);
        order.add(3);
        msg = controller.swapOrder(order,0,model0.getCurrPlayer().getUsername());
        assertEquals(msg.getUsername(),"Incorrect number of orders ");
        order.removeAll(order);

        //MoveNotPossible
        order.add(0);
        order.add(1);
        order.add(2);
        msg = controller.swapOrder(order,0,model0.getCurrPlayer().getUsername());
        assertEquals(msg.getUsername(),"You can't do that now");
        order.removeAll(order);

        //Normal
        order.add(0);
        order.add(2);
        order.add(1);
        msg = controller.swapOrder(order,0,model0.getCurrPlayer().getUsername());
        assertEquals(msg.getUsername(),"Move successful");

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
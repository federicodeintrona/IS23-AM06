package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.server.Exceptions.MoveNotPossible;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Tiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    public Model m;

    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<VirtualView> views = new ArrayList<>();

    @BeforeEach
    void setUp(){
        try {
            Player p0 = new Player("p0");
            Player p1 = new Player("p1");
            Player p2 = new Player("p2");

            players.add(p0);
            players.add(p1);
            players.add(p2);

            views.add(new RMIVirtualView("p0", new ClientState("p0",new Object())));
            views.add(new RMIVirtualView("p1", new ClientState("p1",new Object())));
            views.add(new RMIVirtualView("p2", new ClientState("p2",new Object())));

            m = new Model(players,views);
            m.initialization();
            m.setCurrPlayer(players.get(0));

        } catch (RemoteException e) {
            System.out.println("Perchè???");
        }
    }

    @Test
    void initialization() {
        assertNotEquals(Tiles.EMPTY,m.getBoard().getGamesBoard().getTile(4,4));
        assertEquals(Tiles.NOT_ALLOWED,m.getBoard().getGamesBoard().getTile(5,7));
        for (Player p : m.getPlayers()) {
            assertEquals(Tiles.EMPTY, p.getBookshelf().getTiles().getTile(getRandomPointInBookshelf()));
            assertEquals(0,p.getPersonalObjective().personalObjectivePoint(p));
        }
        assertEquals(8,m.getCommonObj().get(0).getPoints());

    }

    @Test
    void removeTileArray() {
        ArrayList<Point> array = new ArrayList<>();

        array.add(new Point(0,3));
        array.add(new Point(0,4));
        try {
            //La casella 0,4 è NOT_ALLOWED con solo 3 giocatori
            assertThrows(MoveNotPossible.class,()-> m.removeTileArray(players.get(0),array));
            m.setCurrPlayer(players.get(0));
            array.set(1,new Point(1,3));
            m.removeTileArray(players.get(0),array);

        } catch (MoveNotPossible e) {
            throw new RuntimeException(e);
        }
        for(Point p : array) {
            assertEquals(Tiles.EMPTY, m.getBoard().getGamesBoard().getTile(p),"Expected empty but got:" +m.getBoard().getGamesBoard().getTile(p));
        }

    }

    @Test
    void addToBookShelf() {

        ArrayList<Tiles> array = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            array.add(Tiles.BLUE);
        }
        m.getSelectedTiles().addAll(array);
        assertEquals(Tiles.BLUE,array.get(2));
        for (int j =0;j<5;j++) {



            try {
                //Add 1
                m.setState(GameState.CHOOSING_COLUMN);
                m.addToBookShelf(players.get(0), j);
                m.setCurrPlayer(players.get(0));
                m.getSelectedTiles().addAll(array);
                //Add 2
                m.setState(GameState.CHOOSING_COLUMN);
                m.addToBookShelf(players.get(0), j);
                m.setCurrPlayer(players.get(0));
                m.getSelectedTiles().addAll(array);

            } catch (MoveNotPossible e) {
                throw new RuntimeException(e);
            }

            assertEquals(Tiles.BLUE,m.getPlayers().get(0).getBookshelf().getTiles().getTile(4,j),"blue check");
            assertEquals(Tiles.EMPTY,m.getPlayers().get(1).getBookshelf().getTiles().getTile(2,j));
        }
        assertTrue(m.isFinished());

    }


    @Test
    void swapOrder() {
        try {
            m.getSelectedTiles().add(Tiles.BLUE);
            m.getSelectedTiles().add(Tiles.WHITE);
            m.getSelectedTiles().add(Tiles.GREEN);


            Tiles[] array = {Tiles.WHITE,Tiles.GREEN,Tiles.BLUE};

            ArrayList<Integer> ints = new ArrayList<>();
            ints.add(2);
            ints.add(3);
            ints.add(1);

            m.setState(GameState.CHOOSING_ORDER);


            m.swapOrder(ints,players.get(0));

            assertArrayEquals(array,m.getSelectedTiles().toArray());

        } catch (MoveNotPossible e) {
            System.out.println("Swap test catch: non succederà mai spero...");

        }
    }


    @Test
    void gameTest(){
        try {
            //player 0's turn
            ArrayList<Point> remove = new ArrayList<>();
            remove.add(new Point(0,3));
            remove.add(new Point(1,3));
            ArrayList<Tiles> add = new ArrayList<>();
            for (Point point : remove) {
                add.add(m.getBoard().getGamesBoard().getTile(point));
            }

            m.setSelectedTiles(add);


            m.removeTileArray(players.get(0), remove);
            assertThrows(MoveNotPossible.class ,()-> m.removeTileArray(players.get(0), remove));
            m.addToBookShelf(players.get(0), 0);
            assertNotEquals(Tiles.EMPTY, players.get(0).getBookshelf().getTiles().getColumn(0).get(5));
            assertThrows(MoveNotPossible.class ,()-> m.addToBookShelf(players.get(0), 0));
            assertThrows(MoveNotPossible.class ,()-> m.removeTileArray(players.get(0), remove));
            assertThrows(MoveNotPossible.class ,()-> m.removeTileArray(players.get(1), remove));
            m.setCurrPlayer(players.get(1));
            assertThrows(IllegalArgumentException.class ,()-> m.removeTileArray(players.get(1), null));
            assertThrows(MoveNotPossible.class ,()-> m.swapOrder(new ArrayList<>(),players.get(1)));


            remove.set(0,new Point(5,0));
            remove.set(1,new Point(5,1));
            m.removeTileArray(players.get(1), remove);
            m.addToBookShelf(players.get(1), 0);
            assertNotEquals(Tiles.EMPTY, players.get(1).getBookshelf().getTiles().getColumn(0).get(5));

        } catch (MoveNotPossible e) {
            System.out.println("Game test catch: non succederà mai spero...");

        }
    }


    @Test
    void disconnectAndReconnectPlayer(){
        m.disconnectPlayer(players.get(0),views.get(0));
        m.disconnectPlayer(players.get(1),views.get(1));
        assertTrue(players.get(0).isDisconnected());

        m.playerReconnection(players.get(0),views.get(0));

        assertFalse(players.get(0).isDisconnected());

    }

    @Test
    void endingDisconnect(){
        m.disconnectPlayer(players.get(0),views.get(0));
        m.disconnectPlayer(players.get(0),views.get(0));

        assertTrue(m.isTimerIsOn());

    }

    @Test
    void endGame(){
        try {
            Player curr = m.getCurrPlayer();
            ArrayList<Point> remove = new ArrayList<>();
            remove.add(new Point(0,3));
            remove.add(new Point(1,3));
            ArrayList<Tiles> add = new ArrayList<>();
            add.add(Tiles.GREEN);
            add.add(Tiles.GREEN);

            m.setState(GameState.CHOOSING_COLUMN);

            for(int j=0; j<Define.NUMBEROFROWS_BOOKSHELF.getI()/2;j++) {
                for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                    m.getSelectedTiles().addAll(add);
                    m.addToBookShelf(curr, i);
                    m.setCurrPlayer(curr);
                    m.setState(GameState.CHOOSING_COLUMN);
                }
            }

            assertTrue(m.isFinished());

        } catch (MoveNotPossible e) {
            System.out.println("End game test catch: non succederà mai spero...");

        }

    }


    @Test
    void restoreTiles(){
        try {

            ArrayList<Point> remove = new ArrayList<>();
            remove.add(new Point(5,0));
            remove.add(new Point(5,1));

            m.removeTileArray(m.getCurrPlayer(),remove);
            assertEquals(Tiles.EMPTY,m.getBoard().getGamesBoard().getTile(5,0));
            m.disconnectPlayer(m.getCurrPlayer(),views.get(0));
            assertNotEquals(Tiles.EMPTY,m.getBoard().getGamesBoard().getTile(5,0));

        } catch (MoveNotPossible e) {
            System.out.println("ollare");
        }




    }

    @Test
    void boardReset(){
        try {
        ArrayList<Point> points = new ArrayList<>();
        Player curr = m.getCurrPlayer();
        for(int j=0; j<Define.NUMBEROFROWS_BOOKSHELF.getI()/2;j++) {
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                points.removeAll(points);
                points.add(new Point(i,j));

                    m.removeTileArray(curr,points);
                    m.addToBookShelf(curr,i);


                m.setCurrPlayer(curr);
                m.setState(GameState.CHOOSING_TILES);

            }
        }
        } catch (MoveNotPossible e) {
            System.out.println("Board reset test catch: non succederà mai spero...");
        }
    }

    public static Point getRandomPointInBoard() {
        Random rdm = new Random();
        return new Point(rdm.nextInt(9),rdm.nextInt(9));

    }



    public static Point getRandomPointInBookshelf() {
        Random rdm = new Random();
        return new Point(rdm.nextInt(6),rdm.nextInt(5));

    }

}
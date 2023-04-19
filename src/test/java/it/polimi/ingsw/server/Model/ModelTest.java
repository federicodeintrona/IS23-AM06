package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.server.*;
import it.polimi.ingsw.server.Exceptions.MoveNotPossible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    public Model m;

    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<VirtualView> views = new ArrayList<>();

    @BeforeEach
    void setUp(){
        Player p0 = new Player("p0",true);
        Player p1 = new Player("p1",false);
        Player p2 = new Player("p2",false);
        players.add(p0);
        players.add(p1);
        players.add(p2);
        views.add(new VirtualView());
        views.add(new VirtualView());
        views.add(new VirtualView());
        m = new Model(players,views);
        m.initialization();

    }

    @Test
    void initialization() {
        assertNotEquals(Tiles.EMPTY,m.getBoard().getGamesBoard().getTile(4,4));
        assertEquals(Tiles.NOTALLOWED,m.getBoard().getGamesBoard().getTile(5,7));
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
        assertEquals(true,m.isFinished());

    }


    @Test
    void swapOrder() {
        m.getSelectedTiles().add(Tiles.BLUE);
        m.getSelectedTiles().add(Tiles.WHITE);
        m.getSelectedTiles().add(Tiles.GREEN);


        Tiles[] array = {Tiles.WHITE,Tiles.GREEN,Tiles.BLUE};

        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(0);

        m.setState(GameState.CHOOSING_ORDER);

        try {
            m.swapOrder(ints,players.get(0));

            assertArrayEquals(array,m.getSelectedTiles().toArray());

        } catch (MoveNotPossible e) {
            throw new RuntimeException(e);
        }



    }

    @Test
    void gameTest(){

        //player 0's turn
        ArrayList<Point> remove = new ArrayList<>();
        remove.add(new Point(0,3));
        remove.add(new Point(1,3));
        ArrayList<Tiles> add = new ArrayList<>();
        for (Point point : remove) {
            add.add(m.getBoard().getGamesBoard().getTile(point));
        }

        m.setSelectedTiles(add);
        try {

            m.removeTileArray(players.get(0), remove);
            assertThrows(MoveNotPossible.class ,()-> m.removeTileArray(players.get(0), remove));
            m.addToBookShelf(players.get(0), 0);
            assertNotEquals(Tiles.EMPTY, players.get(0).getBookshelf().getTiles().getColumn(0).get(5));
            assertThrows(MoveNotPossible.class ,()-> m.addToBookShelf(players.get(0), 0));
            assertThrows(MoveNotPossible.class ,()-> m.removeTileArray(players.get(0), remove));
            assertThrows(MoveNotPossible.class ,()-> m.removeTileArray(players.get(1), remove));
            assertThrows(IllegalArgumentException.class ,()-> m.removeTileArray(players.get(1), null));
            assertThrows(MoveNotPossible.class ,()-> m.swapOrder(new ArrayList<>(),players.get(1)));


            remove.set(0,new Point(5,0));
            remove.set(1,new Point(5,1));
            m.removeTileArray(players.get(1), remove);
            m.addToBookShelf(players.get(1), 0);
            assertNotEquals(Tiles.EMPTY, players.get(1).getBookshelf().getTiles().getColumn(0).get(5));




        } catch (MoveNotPossible e) {
            throw new RuntimeException(e);
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
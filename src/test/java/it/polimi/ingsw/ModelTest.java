package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.MoveNotPossible;
import it.polimi.ingsw.View.CLIView;
import it.polimi.ingsw.View.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    public Model m;
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<View> views = new ArrayList<>();

    @BeforeEach
    void setUp(){
        Player p0 = new Player("p0",true);
        Player p1 = new Player("p1",false);
        Player p2 = new Player("p2",false);
        players.add(p0);
        players.add(p1);
        players.add(p2);
        views.add(new CLIView());
        views.add(new CLIView());
        views.add(new CLIView());
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
            assertEquals(8,m.getCommonobj().get(0).getPoints());
    }

    @Test
    void removeTileArray() {
        ArrayList<Point> array = new ArrayList<>();

        array.add(new Point(0,3));
        array.add(new Point(0,4));
        try {
            //La casella 0,4 è NOT_ALLOWED con solo 3 giocatori
            assertThrows(MoveNotPossible.class,()-> m.removeTileArray(array));

            array.set(1,new Point(1,3));
            m.removeTileArray(array);

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
        assertEquals(Tiles.BLUE,array.get(2));
        for (int j =0;j<5;j++) {



            try {
                m.addToBookShelf(players.get(0), array, j);
                m.setCurrPlayer(players.get(0));
                m.addToBookShelf(players.get(0), array, j);
                m.setCurrPlayer(players.get(0));
            } catch (MoveNotPossible e) {
                throw new RuntimeException(e);
            }

            assertEquals(Tiles.BLUE,m.getPlayers().get(0).getBookshelf().getTiles().getTile(2,j));
            assertEquals(Tiles.EMPTY,m.getPlayers().get(1).getBookshelf().getTiles().getTile(2,j));
        }
       assertEquals(true,m.isFinished());

    }

    /*
    @Test
    void saveState() {
    }*/




    public static Point getRandomPointInBoard() {
        Random rdm = new Random();
        return new Point(rdm.nextInt(9),rdm.nextInt(9));

    }



    public static Point getRandomPointInBookshelf() {
        Random rdm = new Random();
        return new Point(rdm.nextInt(6),rdm.nextInt(5));

    }

}
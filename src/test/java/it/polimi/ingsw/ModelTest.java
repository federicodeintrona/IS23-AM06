package it.polimi.ingsw;

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
    }

    @Test
    void initialization() {
            m.initialization();
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
    }

    @Test
    void addToBookShelf() {
    }

    @Test
    void saveState() {
    }

    @Test
    void isFinished() {
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
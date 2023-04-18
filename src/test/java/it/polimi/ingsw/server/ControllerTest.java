package it.polimi.ingsw.server;


import it.polimi.ingsw.server.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    public HashMap<Integer, Model> games = new HashMap<>();
    public HashMap<String,Player> clients = new HashMap<>();
    public Controller controller = new Controller(games,clients);;
    public ArrayList<Player> players0 = new ArrayList<>();
    public ArrayList<Player> players1 = new ArrayList<>();
    public ArrayList<VirtualView> views0 = new ArrayList<>();
    public ArrayList<VirtualView> views1 = new ArrayList<>();

    @BeforeEach
    void setUp(){


        controller.startGame(0);
        controller.startGame(1);
    }

    @Test
    void startGame() {

        for(int i=0;i<2;i++) {
            assertNotEquals(Tiles.EMPTY, games.get(i).getBoard().getGamesBoard().getTile(4, 4));
            assertEquals(Tiles.NOTALLOWED, games.get(i).getBoard().getGamesBoard().getTile(5, 7));
            for (Player p : games.get(i).getPlayers()) {
                assertEquals(Tiles.EMPTY, p.getBookshelf().getTiles().getTile(getRandomPointInBookshelf()));
                assertEquals(0, p.getPersonalObjective().personalObjectivePoint(p));
            }
            assertEquals(8, games.get(i).getCommonObj().get(0).getPoints());

        }

    }


    @Test
    void addToBookshelf() {



    }

    @Test
    void swapOrder() {



    }

    @Test
    void removeTiles() {


    }

    @Test
    void newLobby() {
    }

    @Test
    void handleNewClient() {
    }



    public static Point getRandomPointInBookshelf() {
        Random rdm = new Random();
        return new Point(rdm.nextInt(6),rdm.nextInt(5));

    }
}




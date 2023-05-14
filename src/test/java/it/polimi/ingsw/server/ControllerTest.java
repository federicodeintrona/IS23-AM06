package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Model.GameState;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    public Controller controller;
    public HashMap<String,Player> playermap = new HashMap<>();
    public HashMap<Integer,Model> modelmap = new HashMap<>();

    public ArrayList<ArrayList<Player>> players = new ArrayList<>();
    public ArrayList<ArrayList<VirtualView>> views = new ArrayList<>();

    private static final int gameNumber=2;
    private static final int  playerNumber=3;

    @BeforeEach
    void setUp() {


        for(int j = 0; j<gameNumber; j++) {

            modelmap.put(j,new Model());
            players.add(new ArrayList<>());
            views.add(new ArrayList<>());

            for (int i = 0; i < playerNumber; i++) {

                players.get(j).add(new Player("User" + j + i));
                playermap.put(players.get(j).get(i).getUsername(), players.get(j).get(i));
                views.get(j).add(new RMIVirtualView("User" + j + i));
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

    }

    @Test
    void swapOrder() {


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
package it.polimi.ingsw.server;

import it.polimi.ingsw.server.CommonObjective.CommonObjective;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

import static it.polimi.ingsw.server.CommonObjective.CommonObjective.randomSubclass;

public class Game {
    int turns;
    int numOfPlayers;
    Set<Player> players;
    ArrayList<CommonObjective> commonObjectives;
    Board board;

    // method to update the turns counter
    public void updateTurn(){
        turns++;
    }

    public Game(Set<Player> players) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        this.players = players;
        numOfPlayers = players.size();
        turns = 0;

        // initializing the board
        board = new Board(numOfPlayers);

        // initializing 2 random commonObjectives
        commonObjectives = randomSubclass(2);
    }
}

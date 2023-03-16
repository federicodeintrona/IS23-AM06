package it.polimi.ingsw;

import it.polimi.ingsw.CommonObjective.CommonObjective;

import java.util.HashSet;
import java.util.Set;

public class Game {
    int turns = 0;
    int numOfPlayers = 0;
    int poitntsCommonObj1 = 8;
    int poitntsCommonObj2 = 8;
    Set<Player> players = new HashSet<>();
    Set<CommonObjective> commonObjectives= new HashSet<>();
    Board board;

    // method to update the turns counter
    public void updateTurn(){
        turns++;
    }

}

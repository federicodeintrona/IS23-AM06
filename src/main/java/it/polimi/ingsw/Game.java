package it.polimi.ingsw;

import it.polimi.ingsw.CommonObjective.CommonObjective;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static it.polimi.ingsw.CommonObjective.CommonObjective.randomSubclass;

public class Game {
    int turns;
    int numOfPlayers;
    int poitntsCommonObj1 = 8;
    int poitntsCommonObj2 = 8;
    Set<Player> players;
    Set<Player> playersWhoComplitedComObj1 = new HashSet<>();
    Set<Player> playersWhoComplitedComObj2 = new HashSet<>();
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

    public void commonObj1PointsCalculator(Player player){
        if (!playersWhoComplitedComObj1.contains(player)){

            // adding the player to the set of players who already received the pointsCommonObj1
            playersWhoComplitedComObj1.add(player);

            // for a 2 players game the first to complete a commonObj gets 8 points and the second to do so 4
            if (numOfPlayers == 2){
                player.setCommonObjectivePoint(poitntsCommonObj1);
                poitntsCommonObj1 =- 4;
            }

            // in case there are more than 2 players each time a commonObj is completed its points decrease by 2
            else {
                player.setCommonObjectivePoint(poitntsCommonObj1);
                poitntsCommonObj1 =- 2;
            }

        }
    }

    public void commonObj2PointsCalculator(Player player){
        if (!playersWhoComplitedComObj2.contains(player)){

            // adding the player to the set of players who already received the pointsCommonObj2
            playersWhoComplitedComObj2.add(player);

            // for a 2 players game the first to complete a commonObj gets 8 points and the second to do so 4
            if (numOfPlayers == 2){
                player.setCommonObjectivePoint(poitntsCommonObj2);
                poitntsCommonObj2 =- 4;
            }

            // in case there are more than 2 players each time a commonObj is completed its points decrease by 2
            else {
                player.setCommonObjectivePoint(poitntsCommonObj2);
                poitntsCommonObj2 =- 2;
            }

        }
    }
}

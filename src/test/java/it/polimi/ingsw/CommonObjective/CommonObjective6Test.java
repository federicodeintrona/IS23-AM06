package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonObjective6Test {

    /**
     * Testing for success all method's branches with a
     * bookshelf completely empty except for 8 same colored tiles
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually programming the only 8 blue tiles
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 0);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 0);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 4);


        // Creation of an instance for CommonObjective6
        CommonObjective6 obj = new CommonObjective6();

        // Checking that the checkCondition method returns true
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing
     */
    @Test
    void checkConditionFailure1() {
    }

    /**
     * Testing the method in the case of 2 players game
     * when points need to be added
     */
    @Test
    void commonObjPointsCalculatorTwoPlayers() {
    }

    /**
     * Testing the method in the case of 4 players game
     * when points need to be added
     */
    @Test
    void commonObjPointsCalculatorFourPlayers() {
    }

    /**
     * Testing first if statement for failure in case player's
     * bookshelf does not meet the obj6 condition criteria
     */
    @Test
    void commonObjPointsCalculatorFailure1() {
    }

    /**
     * Testing the first if statement for failure in case the player
     * already received the commonObjectivePoints
     */
    @Test
    void commonObjPointsCalculatorFailure2() {
    }
}
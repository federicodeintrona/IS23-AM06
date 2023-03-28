package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonObjective12Test {

    /**
     * Testing all method's branches for success using a
     * bookshelf with no same colored tile next to each other
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player("Jhon", true);
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 5) x = 0;
            }
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing the case of full bookshelf with the first
     * diagonal programmed to have same colored tiles
     */
    @Test
    void checkConditionSuccess2() {
        Player player = new Player("Jhon", true);
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 5) x = 0;
            }
        }

        // Manually programming the diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 4);

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns false
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
     * bookshelf does not meet the obj12 condition criteria
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
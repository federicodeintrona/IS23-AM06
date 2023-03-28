package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.server.CommonObjective.CommonObjective12;
import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonObjective12Test {

    /**
     * Testing all method's branches for success using a
     * bookshelf that has only the boxes under the first
     * diagonal full of no same colored tiles next to each other
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;
        Tiles[] values = Tiles.values();
        int x = 0;
        int buffer = 1;
        int j;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        for (int i=1; i<6; i++){
            j = 0;
            while (j < buffer){
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                j++;
                if (x == 6) x = 0;
            }
            buffer++;
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
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;
        int buffer = 1;
        int j;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        for (int i=1; i<6; i++){
            j = 0;
            while (j < buffer){
                player.getBookshelf().getTiles().setTile(Tiles.GREEN, i, j);
                j++;
            }
            buffer++;
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
     * Testing for failure first if statement
     * with a bookshelf completely empty
     */
    @Test
    void checkConditionFailure1() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing for failure first if statement
     * with a bookshelf completely full of green tiles
     */
    @Test
    void checkConditionFailure2() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
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
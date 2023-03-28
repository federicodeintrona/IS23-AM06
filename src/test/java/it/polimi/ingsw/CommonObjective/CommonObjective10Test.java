package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonObjective10Test {

    /**
     * Testing all method's branches with
     * an empty bookshelf except for 2
     * rows that meet the criteria
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

        // Manually programming the first 2 rows
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 5, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);
        player.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 5, 4);

        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 1);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 4, 4);

        // Creation of an instance for CommonObjective10
        CommonObjective10 obj = new CommonObjective10();

        // Checking that the checkCondition method returns true
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

        // Creation of an instance for CommonObjective10
        CommonObjective10 obj = new CommonObjective10();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing the count of same colored tiles per
     * column with a bookshelf full of green colored tiles
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

        // Creation of an instance for CommonObjective10
        CommonObjective10 obj = new CommonObjective10();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing the method in the case of 2 players game
     * when points need to be added
     */
    @Test
    void commonObjPointsCalculatorTwoPlayers() {
        Player player1 = new Player( "Jhon", true);
        Player player2 = new Player( "Obi", false);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player1.getBookshelf().getTiles().setTile(tiles, i, j);
                player2.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually programming the first 2 rows for player 1
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 5, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 5, 4);

        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 4, 4);

        // Manually programming the first 2 rows for player 2
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 5, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 5, 4);

        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 4, 4);

        // Creation of an instance for CommonObjective10
        CommonObjective10 obj = new CommonObjective10();

        // Testing method for player1
        obj.commonObjPointsCalculator(player1, 2);
        assertEquals(8, player1.getCommonObjectivePoint());
        assertEquals(4, obj.points);

        // Testing method for player2
        obj.commonObjPointsCalculator(player2, 2);
        assertEquals(4, player2.getCommonObjectivePoint());
        assertEquals(0, obj.points);
    }

    /**
     * Testing the method in the case of 4 players game
     * when points need to be added
     */
    @Test
    void commonObjPointsCalculatorFourPlayers() {
        Player player1 = new Player( "Jhon", true);
        Player player2 = new Player( "Obi", false);
        Player player3 = new Player( "Pablo", false);
        Player player4 = new Player( "Felipe", false);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player1.getBookshelf().getTiles().setTile(tiles, i, j);
                player2.getBookshelf().getTiles().setTile(tiles, i, j);
                player3.getBookshelf().getTiles().setTile(tiles, i, j);
                player4.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually programming the first 2 rows for player 1
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 5, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 5, 4);

        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 4, 4);

        // Manually programming the first 2 rows for player 2
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 5, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 5, 4);

        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 4, 4);

        // Manually programming the first 2 rows for player 3
        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);
        player3.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player3.getBookshelf().getTiles().setTile(Tiles.PINK, 5, 2);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);
        player3.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 5, 4);

        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);
        player3.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 1);
        player3.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player3.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 4, 4);

        // Manually programming the first 2 rows for player 4
        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);
        player4.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player4.getBookshelf().getTiles().setTile(Tiles.PINK, 5, 2);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);
        player4.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 5, 4);

        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);
        player4.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 1);
        player4.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player4.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 4, 4);

        // Creation of an instance for CommonObjective10
        CommonObjective10 obj = new CommonObjective10();

        // Testing method for player1
        obj.commonObjPointsCalculator(player1, 4);
        assertEquals(8, player1.getCommonObjectivePoint());
        assertEquals(6, obj.points);

        // Testing method for player2
        obj.commonObjPointsCalculator(player2, 4);
        assertEquals(6, player2.getCommonObjectivePoint());
        assertEquals(4, obj.points);

        // Testing method for player3
        obj.commonObjPointsCalculator(player3, 4);
        assertEquals(4, player3.getCommonObjectivePoint());
        assertEquals(2, obj.points);

        // Testing method for player4
        obj.commonObjPointsCalculator(player4, 4);
        assertEquals(2, player4.getCommonObjectivePoint());
        assertEquals(0, obj.points);
    }

    /**
     * Testing first if statement for failure in case player's
     * bookshelf does not meet the obj10 condition criteria
     */
    @Test
    void commonObjPointsCalculatorFailure1() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective10
        CommonObjective10 obj = new CommonObjective10();

        // Testing method for player
        obj.commonObjPointsCalculator(player, 2);
        assertEquals(0, player.getCommonObjectivePoint());
        assertEquals(8, obj.points);
    }

    /**
     * Testing the first if statement for failure in case the player
     * already received the commonObjectivePoints
     */
    @Test
    void commonObjPointsCalculatorFailure2() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually programming the first 2 rows
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 5, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);
        player.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 5, 4);

        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 1);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 4, 4);

        // Creation of an instance for CommonObjective10
        CommonObjective10 obj = new CommonObjective10();

        // Testing method for player first time
        obj.commonObjPointsCalculator(player, 3);
        assertEquals(8, player.getCommonObjectivePoint());
        assertEquals(6, obj.points);

        // Testing method for player second time
        obj.commonObjPointsCalculator(player, 3);
        assertEquals(8, player.getCommonObjectivePoint());
        assertEquals(6, obj.points);
    }
}
package it.polimi.ingsw.server.common;

import it.polimi.ingsw.server.CommonObjective.CommonObjective5;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for CommonObjective 5
 */
class CommonObjective5Test {

    /**
     * Testing all method's branches with a bookshelf
     * completely full of green colored tiles
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Checking that the checkCondition method returns true
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing the minimum case for the method to return true:
     * only 3 columns selectable composed exactly by 3 different
     * colors each
     */
    @Test
    void checkConditionSuccess2() {
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        player.getBookshelf().getTiles().setTile(Tiles.EMPTY, 0, 0);
        player.getBookshelf().getTiles().setTile(Tiles.EMPTY, 0, 1);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 2);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 2);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 3);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 3);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 4);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);

        // Creation of an instance for CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Checking that the checkCondition method returns true
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing for failure the first if statement:
     * no column is full
     */
    @Test
    void checkConditionFailure1(){
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Adding a row of empty tiles so that there are no full columns
        for (int i=0; i<5; i++){
            player.getBookshelf().getTiles().setTile(Tiles.EMPTY,0, i);
        }

        // Creation of an instance for CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Checking that the checkCondition method returns false
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing for failure the third if statement:
     * all columns are composed by 4 different colors
     */
    @Test
    void checkConditionFailure2(){
        Player player = new Player( "Jhon");

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {

                // Manually programming each column to have 4 different colors
                switch (i) {
                    case 0:
                        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 0, j);
                    case 1:
                        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, j);
                    case 2:
                        player.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 2, j);
                    case 3:
                        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, j);
                    case 4:
                        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, j);
                    case 5:
                        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, j);
                }
            }
        }

        // Creation of an instance for CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Checking that the checkCondition method returns false
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing the method in the case of 2 players game
     * when points need to be added
     */
    @Test
    void commonObjPointsCalculatorTwoPlayers() {
        Player player1 = new Player( "Jhon");
        Player player2 = new Player( "Obi");
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player1.getBookshelf().getTiles().setTile(tiles, i, j);
                player2.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Testing method for player1
        obj.commonObjPointsCalculator(player1, 2);
        assertEquals(8, player1.getCommonObjectivePoint());
        assertEquals(4, obj.getPoints());

        // Testing method for player2
        obj.commonObjPointsCalculator(player2, 2);
        assertEquals(4, player2.getCommonObjectivePoint());
        assertEquals(0, obj.getPoints());
    }

    /**
     * Testing the method in the case of 4 players game
     * when points need to be added
     */
    @Test
    void commonObjPointsCalculatorFourPlayers() {
        Player player1 = new Player( "Jhon");
        Player player2 = new Player( "Obi");
        Player player3 = new Player( "Pablo");
        Player player4 = new Player( "Felipe");
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player1.getBookshelf().getTiles().setTile(tiles, i, j);
                player2.getBookshelf().getTiles().setTile(tiles, i, j);
                player3.getBookshelf().getTiles().setTile(tiles, i, j);
                player4.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Testing method for player1
        obj.commonObjPointsCalculator(player1, 4);
        assertEquals(8, player1.getCommonObjectivePoint());
        assertEquals(6, obj.getPoints());

        // Testing method for player2
        obj.commonObjPointsCalculator(player2, 4);
        assertEquals(6, player2.getCommonObjectivePoint());
        assertEquals(4, obj.getPoints());

        // Testing method for player3
        obj.commonObjPointsCalculator(player3, 4);
        assertEquals(4, player3.getCommonObjectivePoint());
        assertEquals(2, obj.getPoints());

        // Testing method for player4
        obj.commonObjPointsCalculator(player4, 4);
        assertEquals(2, player4.getCommonObjectivePoint());
        assertEquals(0, obj.getPoints());
    }

    /**
     * Testing first if statement for failure in case player's
     * bookshelf does not meet the obj5 condition criteria
     */
    @Test
    void commonObjPointsCalculatorFailure1(){
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        for (int i=0; i<5; i++){
            player.getBookshelf().getTiles().setTile(Tiles.EMPTY,0, i);
        }

        // Creation of an instance for CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Testing method for player
        obj.commonObjPointsCalculator(player, 2);
        assertEquals(0, player.getCommonObjectivePoint());
        assertEquals(8, obj.getPoints());
    }

    /**
     * Testing the first if statement for failure in case the player
     * already received the commonObjectivePoints
     */
    @Test
    void commonObjPointsCalculatorFailure2(){
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Testing method for player first time
        obj.commonObjPointsCalculator(player, 3);
        assertEquals(8, player.getCommonObjectivePoint());
        assertEquals(6, obj.getPoints());

        // Testing method for player second time
        obj.commonObjPointsCalculator(player, 3);
        assertEquals(8, player.getCommonObjectivePoint());
        assertEquals(6, obj.getPoints());
    }
}
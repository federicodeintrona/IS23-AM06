package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.server.CommonObjective.CommonObjective12;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.Model.Tiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        int buffer = 1;
        int j;

        // Initializing the bookshelf
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
        Player player1 = new Player( "Jhon", true);
        Player player2 = new Player( "Obi", false);
        Tiles tiles = Tiles.EMPTY;
        int buffer = 1;
        int j;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (j = 0; j < 5; j++) {
                player1.getBookshelf().getTiles().setTile(tiles, i, j);
                player2.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        for (int i=1; i<6; i++){
            j = 0;
            while (j < buffer){
                player1.getBookshelf().getTiles().setTile(Tiles.GREEN, i, j);
                player2.getBookshelf().getTiles().setTile(Tiles.GREEN, i, j);
                j++;
            }
            buffer++;
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

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
        Player player1 = new Player( "Jhon", true);
        Player player2 = new Player( "Obi", false);
        Player player3 = new Player( "Pablo", false);
        Player player4 = new Player( "Felipe", false);
        int buffer = 1;
        int j;

        // Initializing the bookshelf
        for (int i=1; i<6; i++){
            j = 0;
            while (j < buffer){
                player1.getBookshelf().getTiles().setTile(Tiles.GREEN, i, j);
                player2.getBookshelf().getTiles().setTile(Tiles.GREEN, i, j);
                player3.getBookshelf().getTiles().setTile(Tiles.GREEN, i, j);
                player4.getBookshelf().getTiles().setTile(Tiles.GREEN, i, j);
                j++;
            }
            buffer++;
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

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
     * bookshelf does not meet the obj12 condition criteria
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

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

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
    void commonObjPointsCalculatorFailure2() {
        Player player = new Player( "Jhon", true);
        int buffer = 1;
        int j;

        // Initializing the bookshelf
        for (int i=1; i<6; i++){
            j = 0;
            while (j < buffer){
                player.getBookshelf().getTiles().setTile(Tiles.GREEN, i, j);
                j++;
            }
            buffer++;
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

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
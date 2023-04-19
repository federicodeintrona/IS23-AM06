package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.Model.Tiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonObjective7Test {

    /**
     * Testing for success the first diagonal
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 6) x = 0;
            }
        }

        // Manually programming the diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 4);

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing for success the second diagonal
     */
    @Test
    void checkConditionSuccess2() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 6) x = 0;
            }
        }

        // Manually programming the diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 4);

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing for success the first inverse-diagonal
     */
    @Test
    void checkConditionSuccess3() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 6) x = 0;
            }
        }

        // Manually programming the diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 4);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing for success the second inverse-diagonal
     */
    @Test
    void checkConditionSuccess4() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 6) x = 0;
            }
        }

        // Manually programming the diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 4);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing the case of multiple diagonals
     */
    @Test
    void checkConditionSuccess5() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 6) x = 0;
            }
        }

        // Manually programming the first diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 4);

        // Manually programming the second diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 4);

        // Manually programming the first inverse-diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 4);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 0);

        // Manually programming the second inverse-diagonal
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 4);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 2);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 0);

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing result in case of bookshelf completely empty
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

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing the case of bookshelf completely full but
     * without diagonals that meet the criteria
     */
    @Test
    void checkConditionFailure2() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 5) x = 0;
            }
        }

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

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
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player1.getBookshelf().getTiles().setTile(tiles, i, j);
                player2.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

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

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

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
     * bookshelf does not meet the obj7 condition criteria
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

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

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
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective7
        CommonObjective7 obj = new CommonObjective7();

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
package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonObjective4Test {

    /**
     * Testing for success alla method's branches
     * with a bookshelf full of green tiles
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.GREEN;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

        // Checking that the checkCondition method returns true
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing the case of exactly 2 groups that meet the
     * commonObjective4 criteria in a bookshelf full of
     * tiles with no homonyms next to each others
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

        // Manually programming the only 2 groups in the bookshelf
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 1);

        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 4);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 5, 4);

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

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

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing the particular case of a group of 6 tiles
     * to make sure that the method recognizes it as 1 amd not 2
     */
    @Test
    void checkConditionFailure2() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually programming the group of 6 tiles
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 3);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 3);

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing the case of a bookshelf completely full
     * and no same colored tiles next to each other
     */
    @Test
    void checkConditionFailure3() {
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

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing the case of a bookshelf completely
     * empty except for two groups that however
     * don't have the same color
     */
    @Test
    void checkConditionFailure4() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually programming 2 groups with different colors
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);

        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 4);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 4);

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

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

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

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

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

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
     * bookshelf does not meet the obj4 condition criteria
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

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

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

        // Creation of an instance for CommonObjective4
        CommonObjective4 obj = new CommonObjective4();

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
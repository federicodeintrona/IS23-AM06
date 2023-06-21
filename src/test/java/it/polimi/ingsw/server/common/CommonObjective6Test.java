package it.polimi.ingsw.server.common;

import it.polimi.ingsw.server.CommonObjective.CommonObjective6;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonObjective6Test {

    /**
     * Testing for success all method's branches with a
     * bookshelf completely empty except for 8 same colored tiles
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player( "Jhon");
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
     * Testing successful case of bookshelf completely
     * full of tiles of the same color
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

        // Creation of an instance for CommonObjective6
        CommonObjective6 obj = new CommonObjective6();

        // Checking that the checkCondition method returns true
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing result in case of bookshelf completely empty
     */
    @Test
    void checkConditionFailure1() {
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective6
        CommonObjective6 obj = new CommonObjective6();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing failure case of only 7 tiles that meet the criteria
     */
    @Test
    void checkConditionFailure2() {
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually programming the only 8 blue tiles
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
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player1.getBookshelf().getTiles().setTile(tiles, i, j);
                player2.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually programming the only 8 blue tiles for player 1
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 4);

        // Manually programming the only 8 blue tiles for player 2
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 4);

        // Creation of an instance for CommonObjective6
        CommonObjective6 obj = new CommonObjective6();

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

        // Manually programming the only 8 blue tiles for player 1
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 4);

        // Manually programming the only 8 blue tiles for player 2
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 4);

        // Manually programming the only 8 blue tiles for player 3
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 0);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 0);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 2);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 4);

        // Manually programming the only 8 blue tiles for player 4
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 0);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 0);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 2);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 4);

        // Creation of an instance for CommonObjective6
        CommonObjective6 obj = new CommonObjective6();

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
     * bookshelf does not meet the obj6 condition criteria
     */
    @Test
    void commonObjPointsCalculatorFailure1() {
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective6
        CommonObjective6 obj = new CommonObjective6();

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
        Player player = new Player( "Jhon");
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
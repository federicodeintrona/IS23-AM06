package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CommonObjective1Test {

    /**
     * Testing all method's branches for success, programming the matrix
     * to have at least 6 groups of 2 same colored tiles.
     * Testing both Threads synchronization by programming particular tiles
     * of the bookshelf so that they have to communicate with each other
     */
    @Test
    void checkConditionSuccess() {
        Player player = new Player( "Jhon", true);

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                Tiles[] values = Tiles.values();
                Random random = new Random();

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                Tiles randomValue = values[index];
                player.getBookshelf().getTiles().setTile(randomValue, i, j);
            }
        }

        // Programming the specific boxes to manually get 6 groups of two same colored tiles
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);

        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 0, 4);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 1, 4);

        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 3);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 4);

        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 2);

        player.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 3);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);


        // Creation of an instance for CommonObjective1
        CommonObjective1 obj = new CommonObjective1();

        // Checking that the checkCondition method returns true
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing first if statement of run() for success:
     * bookshelf completely empty
     */
    @Test
    void checkConditionFailure() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective1
        CommonObjective1 obj = new CommonObjective1();

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

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                Tiles[] values = Tiles.values();
                Random random = new Random();

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                Tiles randomValue = values[index];
                player1.getBookshelf().getTiles().setTile(randomValue, i, j);
                player2.getBookshelf().getTiles().setTile(randomValue, i, j);
            }
        }

        // Programming the specific boxes to manually get 6 groups of two same colored tiles for player 1
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);

        player1.getBookshelf().getTiles().setTile(Tiles.GREEN, 0, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.GREEN, 1, 4);

        player1.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 4);

        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 2);

        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 3);

        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);


        // Programming the specific boxes to manually get 6 groups of two same colored tiles for player 2
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);

        player2.getBookshelf().getTiles().setTile(Tiles.GREEN, 0, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.GREEN, 1, 4);

        player2.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 4);

        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 2);

        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 3);

        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);


        // Creation of an instance for CommonObjective1
        CommonObjective1 obj = new CommonObjective1();

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

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                Tiles[] values = Tiles.values();
                Random random = new Random();

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                Tiles randomValue = values[index];
                player1.getBookshelf().getTiles().setTile(randomValue, i, j);
                player2.getBookshelf().getTiles().setTile(randomValue, i, j);
                player3.getBookshelf().getTiles().setTile(randomValue, i, j);
                player4.getBookshelf().getTiles().setTile(randomValue, i, j);
            }
        }

        // Programming the specific boxes to manually get 6 groups of two same colored tiles for player 1
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);

        player1.getBookshelf().getTiles().setTile(Tiles.GREEN, 0, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.GREEN, 1, 4);

        player1.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 4);

        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 2);

        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 3);

        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);


        // Programming the specific boxes to manually get 6 groups of two same colored tiles for player 2
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);

        player2.getBookshelf().getTiles().setTile(Tiles.GREEN, 0, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.GREEN, 1, 4);

        player2.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 4);

        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 2);

        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 3);

        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);


        // Programming the specific boxes to manually get 6 groups of two same colored tiles for player 3
        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 1);
        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);

        player3.getBookshelf().getTiles().setTile(Tiles.GREEN, 0, 4);
        player3.getBookshelf().getTiles().setTile(Tiles.GREEN, 1, 4);

        player3.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 3);
        player3.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 4);

        player3.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player3.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 2);

        player3.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player3.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 3);

        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);


        // Programming the specific boxes to manually get 6 groups of two same colored tiles for player 4
        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 0);
        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 0, 1);
        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 1, 0);

        player4.getBookshelf().getTiles().setTile(Tiles.GREEN, 0, 4);
        player4.getBookshelf().getTiles().setTile(Tiles.GREEN, 1, 4);

        player4.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 3);
        player4.getBookshelf().getTiles().setTile(Tiles.GREEN, 2, 4);

        player4.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 1);
        player4.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 2);

        player4.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 2);
        player4.getBookshelf().getTiles().setTile(Tiles.PINK, 4, 3);

        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);


        // Creation of an instance for CommonObjective1
        CommonObjective1 obj = new CommonObjective1();

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
}
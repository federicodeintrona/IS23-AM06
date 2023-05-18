package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CommonObjective12Test {

    /**
     * Testing case of ascending stairs that starts with a step of zero tiles
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 0;
        int j;

        // Initializing the bookshelf
        for (int i=0; i<5; i++){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
            }
            buffer++;
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing case of ascending stairs that starts with a step of one tile
     */
    @Test
    void checkConditionSuccess2() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 1;
        int j;

        // Initializing the bookshelf
        for (int i=0; i<5; i++){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
            }
            buffer++;
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing case of ascending stairs that starts with a step of two tiles
     */
    @Test
    void checkConditionSuccess3() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 2;
        int j;

        // Initializing the bookshelf
        for (int i=0; i<5; i++){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
            }
            buffer++;
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing case of descending stairs that starts with a step of zero tiles
     */
    @Test
    void checkConditionSuccess4() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 0;
        int j;

        // Initializing the bookshelf
        for (int i=4; i>=0; i--){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
            }
            buffer++;
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing case of descending stairs that starts with a step of one tile
     */
    @Test
    void checkConditionSuccess5() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 1;
        int j;

        // Initializing the bookshelf
        for (int i=4; i>=0; i--){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
            }
            buffer++;
        }

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns false
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing case of descending stairs that starts with a step of two tiles
     */
    @Test
    void checkConditionSuccess6() {
        Player player = new Player( "Jhon", true);
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 2;
        int j;

        // Initializing the bookshelf
        for (int i=4; i>=0; i--){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
            }
            buffer++;
        }

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

        // Manually setting the number of tiles within the bookshelf
        player.getBookshelf().setNum_of_tiles(30);

        // Creation of an instance for CommonObjective12
        CommonObjective12 obj = new CommonObjective12();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing for failure first if statement with a bookshelf
     * completely empty but two tiles in the middle
     */
    @Test
    void checkConditionFailure3() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Manually setting the only 2 tiles within the bookshelf
        ArrayList<Tiles> tile = new ArrayList<>();
        tile.add(Tiles.GREEN);
        tile.add(Tiles.WHITE);
        player.getBookshelf().addTile(tile, 3);

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
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 0;
        int j;

        // Initializing the bookshelf
        for (int i=0; i<5; i++){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player1.getBookshelf().addTile(tile, i);
                player2.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
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
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 0;
        int j;

        // Initializing the bookshelf
        for (int i=0; i<5; i++){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player1.getBookshelf().addTile(tile, i);
                player2.getBookshelf().addTile(tile, i);
                player3.getBookshelf().addTile(tile, i);
                player4.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
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
        Tiles[] values = Tiles.values();
        Random random = new Random();
        ArrayList<Tiles> tile = new ArrayList<>();
        int buffer = 0;
        int j;

        // Initializing the bookshelf
        for (int i=0; i<5; i++){
            j = 0;
            while (j < buffer){

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                tile.add(values[index]);

                // Adding the tile to the bookshelf
                player.getBookshelf().addTile(tile, i);

                j++;
                tile.clear();
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
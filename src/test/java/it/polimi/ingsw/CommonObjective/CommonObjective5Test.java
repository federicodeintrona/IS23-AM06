package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonObjective5Test {

    @Test
    void checkConditionSuccessOneColor() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.GREEN;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creazione di un'istanza di CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Verifica che il metodo checkCondition restituisca true
        assertTrue(obj.checkCondition(player));
    }

    @Test
    void checkConditionSuccessMoreColors() {
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.GREEN;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 1, 2);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 2);

        // Creazione di un'istanza di CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Verifica che il metodo checkCondition restituisca true
        assertTrue(obj.checkCondition(player));
    }

    @Test
    void checkConditionFalseAllColumnsEmpty(){
        Player player = new Player( "Jhon", true);
        Tiles tiles = Tiles.GREEN;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        for (int i=0; i<5; i++){
            player.getBookshelf().getTiles().setTile(Tiles.EMPTY,0, i);
        }

        // Creazione di un'istanza di CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Verifica che il metodo checkCondition restituisca true
        assertFalse(obj.checkCondition(player));
    }

    @Test
    void checkConditionFalseAllColumnsFourColors(){
        Player player = new Player( "Jhon", true);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
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

        // Creazione di un'istanza di CommonObjective5
        CommonObjective5 obj = new CommonObjective5();

        // Verifica che il metodo checkCondition restituisca true
        assertFalse(obj.checkCondition(player));
    }

    @Test
    void commonObjPointsCalculator() {
    }
}
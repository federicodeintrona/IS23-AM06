package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonObjective5Test {

    @Test
    void checkConditionSuccess() {
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
        assertEquals(true, obj.checkCondition(player));
    }

    @Test
    void commonObjPointsCalculator() {
    }
}
package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.server.Bookshelf;
import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective1;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonalObjective1Test {

    @Test
    void checkCondition() {
        Player player=new Player("Ale");
        Bookshelf bookshelf=new Bookshelf();
        ArrayList<Tiles> list=new ArrayList<>();
        PersonalObjective1 po=new PersonalObjective1();
        player.setPersonalObjective(po);

        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(bookshelf));
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE, 5,2);
        assertEquals(1, po.checkCondition(bookshelf));
        assertEquals(1, po.personalObjectivePoint(bookshelf));
        bookshelf.getTiles().setTile(Tiles.GREEN, 5,2);
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @Test
    void testCheckCondition() {
    }

    @Test
    void personalObjectivePoint() {
    }

    @Test
    void testPersonalObjectivePoint() {
    }
}
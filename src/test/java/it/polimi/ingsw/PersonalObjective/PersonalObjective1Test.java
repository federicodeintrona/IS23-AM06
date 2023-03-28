package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.Bookshelf;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
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
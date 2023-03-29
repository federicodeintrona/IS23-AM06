package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.server.Bookshelf;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective1;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective8;
import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonalObjective8Test {

    @DisplayName("Checking the correct positions")
    @Test
    void checkCondition1() {
        Player player=new Player("Ale");
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective8 po=new PersonalObjective8();
        player.setPersonalObjective(po);

        //controllo con bookshelf vuota
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE, 2,2);
        player.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 2,2);
        assertEquals(1, po.checkCondition(player));
        assertEquals(1, po.checkCondition(bookshelf));
        assertEquals(1, po.personalObjectivePoint(player));
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 5,3);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5,3);
        assertEquals(2, po.checkCondition(player));
        assertEquals(2, po.checkCondition(bookshelf));
        assertEquals(2, po.personalObjectivePoint(player));
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 4,3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4,3);
        assertEquals(3, po.checkCondition(player));
        assertEquals(3, po.checkCondition(bookshelf));
        assertEquals(4, po.personalObjectivePoint(player));
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,1);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 1,1);
        assertEquals(4, po.checkCondition(player));
        assertEquals(4, po.checkCondition(bookshelf));
        assertEquals(6, po.personalObjectivePoint(player));
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 0,4);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 0,4);
        assertEquals(5, po.checkCondition(player));
        assertEquals(5, po.checkCondition(bookshelf));
        assertEquals(9, po.personalObjectivePoint(player));
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 3,0);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 3,0);
        assertEquals(6, po.checkCondition(player));
        assertEquals(6, po.checkCondition(bookshelf));
        assertEquals(12, po.personalObjectivePoint(player));
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions")
    @Test
    void checkCondition2() {
        Player player=new Player("Ale");
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective8 po=new PersonalObjective8();
        player.setPersonalObjective(po);

        //controllo con bookshelf vuota
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN, 2,2);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 2,2);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,3);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 5,3);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,3);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 4,3);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 1,1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 1,1);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,4);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 0,4);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,0);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 3,0);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

}
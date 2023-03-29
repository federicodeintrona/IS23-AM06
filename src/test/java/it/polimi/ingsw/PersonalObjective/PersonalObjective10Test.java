package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.server.Bookshelf;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective1;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective10;
import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonalObjective10Test {

    @DisplayName("Checking the correct positions")
    @Test
    void checkCondition1() {
        Player player=new Player("Ale");
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective10 po=new PersonalObjective10();
        player.setPersonalObjective(po);

        //controllo con bookshelf vuota
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE, 0,4);
        player.getBookshelf().getTiles().setTile(Tiles.LIGHT_BLUE, 0,4);
        assertEquals(1, po.checkCondition(player));
        assertEquals(1, po.checkCondition(bookshelf));
        assertEquals(1, po.personalObjectivePoint(player));
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 1,1);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 1,1);
        assertEquals(2, po.checkCondition(player));
        assertEquals(2, po.checkCondition(bookshelf));
        assertEquals(2, po.personalObjectivePoint(player));
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 2,0);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2,0);
        assertEquals(3, po.checkCondition(player));
        assertEquals(3, po.checkCondition(bookshelf));
        assertEquals(4, po.personalObjectivePoint(player));
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,3);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 3,3);
        assertEquals(4, po.checkCondition(player));
        assertEquals(4, po.checkCondition(bookshelf));
        assertEquals(6, po.personalObjectivePoint(player));
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 4,1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4,1);
        assertEquals(5, po.checkCondition(player));
        assertEquals(5, po.checkCondition(bookshelf));
        assertEquals(9, po.personalObjectivePoint(player));
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 5,3);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 5,3);
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
        PersonalObjective10 po=new PersonalObjective10();
        player.setPersonalObjective(po);

        //controllo con bookshelf vuota
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN, 0,4);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 0,4);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,1);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 1,1);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,0);
        player.getBookshelf().getTiles().setTile(Tiles.GREEN, 2,0);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 3,3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3,3);
        assertEquals(0, po.checkCondition(player));
        assertEquals(0, po.checkCondition(bookshelf));
        assertEquals(0, po.personalObjectivePoint(player));
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 4,1);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4,1);
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
    }

}
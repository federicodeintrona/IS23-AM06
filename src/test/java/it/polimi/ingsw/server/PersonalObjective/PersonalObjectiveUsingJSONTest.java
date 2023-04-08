package it.polimi.ingsw.server.PersonalObjective;

import it.polimi.ingsw.server.Bookshelf;
import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonalObjectiveUsingJSONTest {

    @DisplayName("Checking the correct positions of PersonalObjective1")
    @Test
    void personalObjectivePoint(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjectiveUsingJSON po=new PersonalObjectiveUsingJSON(1);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE, 5,2);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 3,1);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 2,3);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,4);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 0,2);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 0,0);
        assertEquals(12, po.personalObjectivePoint(bookshelf));

    }
    @DisplayName("Checking the wrong positions of PersonalObjective1")
    @Test
    void personalObjectivePoint2(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjectiveUsingJSON po=new PersonalObjectiveUsingJSON(1);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN, 5,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 1,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

    }




}
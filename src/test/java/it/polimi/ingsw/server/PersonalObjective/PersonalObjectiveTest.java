package it.polimi.ingsw.server.PersonalObjective;

import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.Model.Tiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalObjectiveTest {

    @DisplayName("Checking the correct positions of PersonalObjective1")
    @Test
    void personalObjectivePoint11(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(1);

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
    void personalObjectivePoint12(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(1);

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

    @DisplayName("Checking the correct positions of PersonalObjective2")
    @Test
    void personalObjectivePoint21(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(2);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE, 4,3);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 2,2);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 3,4);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,0);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 5,4);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 1,1);
        assertEquals(12, po.personalObjectivePoint(bookshelf));

    }
    @DisplayName("Checking the wrong positions of PersonalObjective2")
    @Test
    void personalObjectivePoint22(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(2);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN, 4,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 2,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

    }

    @DisplayName("Checking the correct positions of PersonalObjective3")
    @Test
    void personalObjectivePoint31(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(3);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE, 3,4);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 1,3);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 5,0);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,1);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 1,0);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 2,2);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective3")
    @Test
    void personalObjectivePoint32(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(3);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN, 3,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 3,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective4")
    @Test
    void personalObjectivePoint41(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(4);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE, 2,0);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 0,4);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 4,1);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,2);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 2,2);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 3,3);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective4")
    @Test
    void personalObjectivePoint42(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(4);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN, 2,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 4,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective5")
    @Test
    void personalObjectivePoint51(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(5);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE, 1,1);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 5,0);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 3,2);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,3);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 3,1);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 4,4);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective5")
    @Test
    void personalObjectivePoint52(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(5);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN, 1,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 5,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective6")
    @Test
    void personalObjectivePoint61(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(6);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE,0,2);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 4,1);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 2,3);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,4);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 4,3);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 5,0);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective6")
    @Test
    void personalObjectivePoint62(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(6);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN,0,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 0,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective7")
    @Test
    void personalObjectivePoint71(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(7);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE,3,0);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 4,4);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 5,2);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,0);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 1,3);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 2,1);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective7")
    @Test
    void personalObjectivePoint72(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(7);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN,3,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 0,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective8")
    @Test
    void personalObjectivePoint81(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(8);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE,2,2);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 5,3);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 4,3);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,1);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 0,4);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 3,0);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective8")
    @Test
    void personalObjectivePoint82(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(8);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN,2,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 1,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective9")
    @Test
    void personalObjectivePoint91(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(9);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE,4,1);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 0,2);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 3,4);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,2);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 5,0);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 4,4);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective9")
    @Test
    void personalObjectivePoint92(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(9);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN,4,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 2,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective10")
    @Test
    void personalObjectivePoint101(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(10);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE,0,4);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 1,1);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 2,0);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,3);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 4,1);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 5,0);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective10")
    @Test
    void personalObjectivePoint102(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(10);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN,0,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 3,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective11")
    @Test
    void personalObjectivePoint111(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(11);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE,5,3);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 2,0);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 1,1);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,4);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 3,2);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 0,2);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective11")
    @Test
    void personalObjectivePoint112(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(11);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN,5,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 4,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 3,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }

    @DisplayName("Checking the correct positions of PersonalObjective12")
    @Test
    void personalObjectivePoint121(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(12);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.LIGHT_BLUE,3,3);
        assertEquals(1, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.YELLOW, 4,4);
        assertEquals(2, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 0,2);
        assertEquals(4, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 5,0);
        assertEquals(6, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.BLUE, 2,2);
        assertEquals(9, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.PINK, 1,1);
        assertEquals(12, po.personalObjectivePoint(bookshelf));
    }
    @DisplayName("Checking the wrong positions of PersonalObjective12")
    @Test
    void personalObjectivePoint122(){
        Bookshelf bookshelf=new Bookshelf();
        PersonalObjective po=new PersonalObjective(12);

        //controllo con bookshelf vuota
        assertEquals(0, po.personalObjectivePoint(bookshelf));


        //controllo tutti i possibili casi corretti
        bookshelf.getTiles().setTile(Tiles.GREEN,3,3);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 4,4);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 0,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.WHITE, 5,0);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 2,2);
        assertEquals(0, po.personalObjectivePoint(bookshelf));

        bookshelf.getTiles().setTile(Tiles.GREEN, 1,1);
        assertEquals(0, po.personalObjectivePoint(bookshelf));
    }


}
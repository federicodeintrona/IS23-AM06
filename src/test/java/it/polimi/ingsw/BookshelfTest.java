package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookshelfTest {
    public Bookshelf b;

    @BeforeEach
    void setUp(){
        b=new Bookshelf();
    }
    /**
     * Test construct of the class bookshelf
     */
    @Test
    void CheckConstructor(){
        assertEquals(b.getTiles().getTile(0,0), Tiles.EMPTY,"1,1 expected empty");
        assertEquals(b.getTiles().getTile(0,4), Tiles.EMPTY,"1,5 expected Empty");
        assertEquals(b.getTiles().getTile(5,0), Tiles.EMPTY,"6,1 expected empty");
        assertEquals(b.getTiles().getTile(5,4), Tiles.EMPTY,"6,5 expected empty");
    }
    /**
     * Test the operation addTile from the class bookshelf
     */
    @Test
    void CheckAddTile(){
        ArrayList <Tiles> scelta= new ArrayList<>();
        scelta.add(Tiles.BLUE);
        b.addTile( scelta,3 );
        assertEquals(b.getTiles().getTile(5,3),Tiles.BLUE,"0,3 expected blue");
        scelta.add(Tiles.PINK);
        b.addTile( scelta,3 );
        assertEquals(b.getTiles().getTile(4,3),Tiles.BLUE, "1,3 expected blue");
        assertEquals(b.getTiles().getTile(3,3),Tiles.PINK, "2,3 expected pink");
        scelta.add(Tiles.LIGHT_BLUE);
        b.addTile( scelta,1 );
        assertEquals(b.getTiles().getTile(5,1),Tiles.BLUE, "0,1 expected blue");
        assertEquals(b.getTiles().getTile(4,1),Tiles.PINK, "1,1 expected pink");
        assertEquals(b.getTiles().getTile(3,1),Tiles.LIGHT_BLUE, "2,1 expected light_blue");
    }

    /**
     * Test the operation checkEndGame from the class bookshelf
     */
    @Test
    void checkCheckEndGame(){
        ArrayList <Tiles> scelta= new ArrayList<>();
        scelta.add(Tiles.BLUE);
        scelta.add(Tiles.BLUE);
        scelta.add(Tiles.BLUE);
        assertEquals(b.checkEndGame(),false,"La matrice non è ancora piena");
        b.addTile( scelta,0 );
        b.addTile( scelta,0 );
        b.addTile( scelta,1 );
        b.addTile( scelta,1 );
        b.addTile( scelta,2 );
        b.addTile( scelta,2 );
        b.addTile( scelta,3 );
        b.addTile( scelta,3 );
        b.addTile( scelta,4 );
        b.addTile( scelta,4 );
        assertEquals(b.checkEndGame(),true,"La matrice è piena");
    }
    /**
     * Test the operation checkColumns from the class bookshelf
     */
    @Test
    void checkCheckColumns(){
        ArrayList <Tiles> scelta= new ArrayList<>();
        scelta.add(Tiles.BLUE);
        scelta.add(Tiles.BLUE);
        scelta.add(Tiles.BLUE);
        assertEquals(b.checkColumns(1,0),true,"Ci dovrebbe essere spazio nella matrice 0 try(1)");
        assertEquals(b.checkColumns(2,0),true,"Ci dovrebbe essere spazio nella matrice 0 try(2)");
        assertEquals(b.checkColumns(3,0),true,"Ci dovrebbe essere spazio nella matrice 0 try(3)");
        b.addTile(scelta,0);
        scelta.remove(2);
        scelta.remove(1);
        b.addTile(scelta,0);
        assertEquals(b.checkColumns(1,0),true,"Ci dovrebbe essere spazio nella matrice 1 try(1)");
        assertEquals(b.checkColumns(2,0),true,"Ci dovrebbe essere spazio nella matrice 1 try(2)");
        assertEquals(b.checkColumns(3,0),false,"Non ci dovrebbe essere spazio nella matrice 1 try(3)");
        b.addTile(scelta,0);
        assertEquals(b.checkColumns(1,0),true,"Ci dovrebbe essere spazio nella matrice 2 try(1)");
        assertEquals(b.checkColumns(2,0),false,"Non ci dovrebbe essere spazio nella matrice 2 try(2)");
        assertEquals(b.checkColumns(3,0),false,"Non ci dovrebbe essere spazio nella matrice 2 try(3)");
        b.addTile(scelta,0);
        assertEquals(b.checkColumns(1,0),false,"Non ci dovrebbe essere spazio nella matrice 3 try(1)");
        assertEquals(b.checkColumns(2,0),false,"Non ci dovrebbe essere spazio nella matrice 3 try(2)");
        assertEquals(b.checkColumns(3,0),false,"Non ci dovrebbe essere spazio nella matrice 3 try(3)");

    }
    @Test
    void checkCheckVicinityPoints(){
        ArrayList <Tiles> scelta= new ArrayList<>();
        scelta.add(Tiles.BLUE);
        scelta.add(Tiles.BLUE);
        scelta.add(Tiles.BLUE);
        ArrayList <Tiles> scelta2= new ArrayList<>();
        scelta2.add(Tiles.GREEN);
        scelta2.add(Tiles.GREEN);
        scelta2.add(Tiles.GREEN);
        ArrayList <Tiles> scelta3= new ArrayList<>();
        scelta3.add(Tiles.PINK);
        scelta3.add(Tiles.PINK);
        scelta3.add(Tiles.PINK);
        ArrayList <Tiles> scelta4= new ArrayList<>();
        scelta4.add(Tiles.YELLOW);
        scelta4.add(Tiles.YELLOW);
        scelta4.add(Tiles.YELLOW);
        assertEquals(b.checkVicinityPoints(),0,"I punti non sono zero");
        b.addTile( scelta,0 );
        b.addTile( scelta2,0 );
        b.addTile( scelta,1 );
        b.addTile( scelta2,1 );
        b.addTile( scelta3,2 );
        b.addTile( scelta,2 );
        b.addTile( scelta3,3 );
        b.addTile( scelta,3 );
        b.addTile( scelta4,4 );
        b.addTile( scelta4,4 );
        assertEquals(b.checkVicinityPoints(),40,"I punti non sono 32");
    }
}

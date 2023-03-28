package it.polimi.ingsw;

import it.polimi.ingsw.server.Matrix;
import it.polimi.ingsw.server.Tiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
        public Matrix m;

        @BeforeEach
        void setUp(){
             m = new Matrix(3,3);
            for(int i=0;i<m.getNumCols();i++) {
                m.setTile(Tiles.GREEN, 0,i);
                m.setTile(Tiles.BLUE, 1,i);
                m.setTile(Tiles.YELLOW, 2,i);
            }
        }



    @Test
    void GetaandSetCoordTile() {


        assertThrows(IndexOutOfBoundsException.class,()->m.getTile(2,4));
        assertEquals(m.getTile(0,0), Tiles.GREEN,"0,0 expected Green");
        assertEquals(m.getTile(1,2), Tiles.BLUE,"1,2 expected Blue");
        assertEquals(m.getTile(1,0), Tiles.BLUE,"1,0 expected Blue");
        assertEquals(m.getTile(2,1), Tiles.YELLOW,"2,1 expected Yellow");
    }



    @Test
    void GetandSetPointsTile() {

        for(int i=0;i<m.getNumCols();i++) {
            Point p0 = new Point(0,i);
            Point p1 = new Point(1,i);
            Point p2 = new Point(2,i);
            m.setTile(Tiles.GREEN, p0);
            m.setTile(Tiles.BLUE, p1);
            m.setTile(Tiles.YELLOW, p2);
        }

        assertEquals(m.getTile(0,0), Tiles.GREEN,"0,0 expected Green");
        assertEquals(m.getTile(1,2), Tiles.BLUE,"1,2 expected Blue");
        assertEquals(m.getTile(1,0), Tiles.BLUE,"1,0 expected Blue");
        assertEquals(m.getTile(2,1), Tiles.YELLOW,"2,1 expected Yellow");

    }


    @Test
    void getColumn() {
        Tiles[] t = {Tiles.GREEN,Tiles.BLUE,Tiles.YELLOW};
        assertArrayEquals(t,m.getColumn(1).toArray());

    }



    @Test
    void remove() {

        Point p = new Point(1,2);
        m.remove(2,2);
        m.remove(p);
        assertEquals(Tiles.EMPTY,m.getTile(2,2));
        assertEquals(Tiles.EMPTY,m.getTile(p));
        assertEquals(Tiles.GREEN,m.getTile(0,1));

    }



    @Test
    void columnIsFull() {
       m.remove(2,1);

       assertEquals(true,m.columnIsFull(2));
       assertEquals(false,m.columnIsFull(1));



    }

    @Test
    void rowIsFull() {
        m.remove(0,0);

        assertEquals(true,m.rowIsFull(2));
        assertEquals(false,m.rowIsFull(0));




    }
}
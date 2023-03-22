package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {




    @Test
    void GetaandSetCoordTile() {
        Matrix m = new Matrix(3,3);
        for(int i=0;i<m.getNumCols();i++) {
            m.setTile(Tiles.GREEN, 0,i);
            m.setTile(Tiles.BLUE, 1,i);
            m.setTile(Tiles.YELLOW, 2,i);
        }

        assertThrows(IndexOutOfBoundsException.class,()->m.getTile(2,4));
        assertEquals(m.getTile(0,0), Tiles.GREEN,"0,0 expected Green");
        assertEquals(m.getTile(1,2), Tiles.BLUE,"1,2 expected Blue");
        assertEquals(m.getTile(1,0), Tiles.BLUE,"1,2 expected Blue");
        assertEquals(m.getTile(2,1), Tiles.YELLOW,"2,1 expected Yellow");
    }



    @Test
    void GetandSetPointsTile() {
        Matrix m = new Matrix(3,3);

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
        assertEquals(m.getTile(1,0), Tiles.BLUE,"1,2 expected Blue");
        assertEquals(m.getTile(2,1), Tiles.YELLOW,"2,1 expected Yellow");

    }


    @Test
    void getColumn() {
        Matrix m = new Matrix(3,3);
        for(int i=0;i<m.getNumCols();i++) {
            m.setTile(Tiles.GREEN, 0,i);
            m.setTile(Tiles.BLUE, 1,i);
            m.setTile(Tiles.YELLOW, 2,i);
        }
        Tiles[] t = {Tiles.GREEN,Tiles.BLUE,Tiles.YELLOW};
        assertArrayEquals(t,m.getColumn(1).toArray());

    }





/*


    @Test
    void remove() {
    }

    @Test
    void testRemove() {
    }


    @Test
    void testGetTile() {
    }



    @Test
    void columnIsFull() {
    }

    @Test
    void rowIsFull() {
    }*/
}
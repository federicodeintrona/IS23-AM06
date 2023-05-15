package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.Tiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    //TEST Constructor and Getter method
    @DisplayName("Testing the Constructor and Getter method")
    @Test
    void constructorTest(){
        Board board=new Board(2);
        assertEquals(board.getNumberOfPlayer(), 2);
        Board board1=new Board(3);
        assertEquals(board1.getNumberOfPlayer(), 3);
        Board board2=new Board(4);
        assertEquals(board2.getNumberOfPlayer(), 4);

        Board board4=new Board(2, new Sachet());
        assertEquals(board4.getNumberOfPlayer(), 2);
        Board board5=new Board(3, new Sachet());
        assertEquals(board5.getNumberOfPlayer(), 3);
        Board board6=new Board(4, new Sachet());
        assertEquals(board6.getNumberOfPlayer(), 4);

        //check the NOTALLOWED position
        assertEquals(board.getGamesBoard().getTile(0,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,8), Tiles.NOTALLOWED);

        assertEquals(board1.getGamesBoard().getTile(0,0), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(0,1), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(0,2), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(0,5), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(0,6), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(0,7), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(0,8), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(1,0), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(1,1), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(1,2), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(1,6), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(1,7), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(1,8), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(2,0), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(2,1), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(2,7), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(2,8), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(3,0), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(5,8), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(6,0), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(6,1), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(6,7), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(6,8), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(7,0), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(7,1), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(7,2), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(7,6), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(7,7), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(7,8), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(8,0), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(8,1), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(8,2), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(8,3), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(8,6), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(8,7), Tiles.NOTALLOWED);
        assertEquals(board1.getGamesBoard().getTile(8,8), Tiles.NOTALLOWED);

        assertEquals(board2.getGamesBoard().getTile(0,0), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(0,1), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(0,2), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(0,5), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(0,6), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(0,7), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(0,8), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(1,0), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(1,1), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(1,2), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(1,6), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(1,7), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(1,8), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(2,0), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(2,1), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(2,7), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(2,8), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(3,0), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(5,8), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(6,0), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(6,1), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(6,7), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(6,8), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(7,0), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(7,1), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(7,2), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(7,6), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(7,7), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(7,8), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(8,0), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(8,1), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(8,2), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(8,3), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(8,6), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(8,7), Tiles.NOTALLOWED);
        assertEquals(board2.getGamesBoard().getTile(8,8), Tiles.NOTALLOWED);

        assertEquals(board4.getGamesBoard().getTile(0,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,1), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,2), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,3), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,5), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,6), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,7), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,8), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,1), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,2), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,6), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,7), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,8), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,1), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,2), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,6), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,7), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,8), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(3,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(3,8), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(5,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(5,8), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,1), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,2), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,6), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,7), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,8), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,1), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,2), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,6), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,7), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,8), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,0), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,1), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,2), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,3), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,5), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,6), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,7), Tiles.NOTALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,8), Tiles.NOTALLOWED);

        assertEquals(board5.getGamesBoard().getTile(0,0), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,1), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,2), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,5), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,6), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,7), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,8), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,0), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,1), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,2), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,6), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,7), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,8), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(2,0), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(2,1), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(2,7), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(2,8), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(3,0), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(5,8), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(6,0), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(6,1), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(6,7), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(6,8), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,0), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,1), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,2), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,6), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,7), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,8), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,0), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,1), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,2), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,3), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,6), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,7), Tiles.NOTALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,8), Tiles.NOTALLOWED);

        assertEquals(board6.getGamesBoard().getTile(0,0), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,1), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,2), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,5), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,6), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,7), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,8), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,0), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,1), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,2), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,6), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,7), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,8), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(2,0), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(2,1), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(2,7), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(2,8), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(3,0), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(5,8), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(6,0), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(6,1), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(6,7), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(6,8), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,0), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,1), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,2), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,6), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,7), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,8), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,0), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,1), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,2), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,3), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,6), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,7), Tiles.NOTALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,8), Tiles.NOTALLOWED);
    }

    //TEST boardInitialization()
    @DisplayName("Board initiliazation for 2 players do NOT put EMPTY tiles")
    @Test
    void boardInitialization1() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOTALLOWED);

    }
    @DisplayName("Board initiliazation for 3 players do NOT put EMPTY tiles")
    @Test
    void boardInitialization2() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
    }
    @DisplayName("Board initiliazation for 3 players do NOT put EMPTY tiles")
    @Test
    void boardInitialization3() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
    }

    //TEST checkBoardReset
    @DisplayName("Condition to board reset is verified - 2 players' game")
    @Test
    void checkBoardReset1() {
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 4,4);
        board.placeTiles(Tiles.GREEN, 3,6);
        boolean result=board.checkBoardReset();
        assertTrue(result);

    }
    @DisplayName("Condition to board reset is verified - 3 players' game")
    @Test
    void checkBoardReset2() {
        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 4,4);
        board.placeTiles(Tiles.GREEN, 3,6);
        boolean result=board.checkBoardReset();
        assertTrue(result);

    }
    @DisplayName("Condition to board reset is verified - 4 players' game")
    @Test
    void checkBoardReset3() {
        Sachet sachet=new Sachet();
        Board board=new Board(4, sachet);
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 4,4);
        board.placeTiles(Tiles.GREEN, 3,6);
        boolean result=board.checkBoardReset();
        assertTrue(result);

    }
    @DisplayName("Condition to board reset is NOT verified - 2 players' game")
    @Test
    void checkBoardReset4() {
        boolean result;
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //we are NOT on the edge board --> left adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3,2);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 3,2);

        //we are NOT on the edge board --> right adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3,4);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 3,4);

        //we are NOT on the edge board --> upper adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 2,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 2,3);

        //we are NOT on the edge board --> bottom adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 4,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 4,3);
    }
    @DisplayName("Condition to board reset is NOT verified - 3 players' game")
    @Test
    void checkBoardReset5() {
        boolean result;
        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        //we are NOT on the edge board --> left adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3,2);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 3,2);

        //we are NOT on the edge board --> right adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3,4);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 3,4);

        //we are NOT on the edge board --> upper adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 2,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 2,3);

        //we are NOT on the edge board --> bottom adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 4,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 4,3);


        //we are on the upper edge --> bottom adjacency
        board.placeTiles(Tiles.GREEN, 0,3);
        board.placeTiles(Tiles.GREEN, 1,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 0,3);
        board.placeTiles(Tiles.EMPTY, 1,3);


        //we are on the bottom edge --> up adjacency
        board.placeTiles(Tiles.GREEN, 8,5);
        board.placeTiles(Tiles.GREEN, 7,5);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 8,5);
        board.placeTiles(Tiles.EMPTY, 7,5);


        //we are on the left edge --> right adjacency
        board.placeTiles(Tiles.GREEN, 5,0);
        board.placeTiles(Tiles.GREEN, 5,1);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 5,0);
        board.placeTiles(Tiles.EMPTY, 5,1);


        //we are on the right edge --> left adjacency
        board.placeTiles(Tiles.GREEN, 3,8);
        board.placeTiles(Tiles.GREEN, 3,7);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,8);
        board.placeTiles(Tiles.EMPTY, 3,7);
    }
    @DisplayName("Condition to board reset is NOT verified - 4 players' game")
    @Test
    void checkBoardReset6() {
        boolean result;
        Sachet sachet=new Sachet();
        Board board=new Board(4, sachet);
        //we are NOT on the edge board --> left adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3,2);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 3,2);

        //we are NOT on the edge board --> right adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3,4);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 3,4);

        //we are NOT on the edge board --> upper adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 2,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 2,3);

        //we are NOT on the edge board --> bottom adjacency
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 4,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,3);
        board.placeTiles(Tiles.EMPTY, 4,3);


        //we are on the upper edge --> bottom adjacency
        board.placeTiles(Tiles.GREEN, 0,3);
        board.placeTiles(Tiles.GREEN, 1,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 0,3);
        board.placeTiles(Tiles.EMPTY, 1,3);

        //we are on the upper edge --> right adjacency
        board.placeTiles(Tiles.GREEN, 0,3);
        board.placeTiles(Tiles.GREEN, 0,4);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 0,3);
        board.placeTiles(Tiles.EMPTY, 0,4);

        //we are on the upper edge --> left adjacency
        board.placeTiles(Tiles.GREEN, 0,4);
        board.placeTiles(Tiles.GREEN, 0,3);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 0,4);
        board.placeTiles(Tiles.EMPTY, 0,3);


        //we are on the bottom edge --> up adjacency
        board.placeTiles(Tiles.GREEN, 8,5);
        board.placeTiles(Tiles.GREEN, 7,5);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 8,5);
        board.placeTiles(Tiles.EMPTY, 7,5);

        //we are on the bottom edge --> left adjacency
        board.placeTiles(Tiles.GREEN, 8,5);
        board.placeTiles(Tiles.GREEN, 8,4);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 8,5);
        board.placeTiles(Tiles.EMPTY, 8,4);

        //we are on the bottom edge --> right adjacency
        board.placeTiles(Tiles.GREEN, 8,4);
        board.placeTiles(Tiles.GREEN, 8,5);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 8,4);
        board.placeTiles(Tiles.EMPTY, 8,5);


        //we are on the left edge --> right adjacency
        board.placeTiles(Tiles.GREEN, 5,0);
        board.placeTiles(Tiles.GREEN, 5,1);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 5,0);
        board.placeTiles(Tiles.EMPTY, 5,1);

        //we are on the left edge --> upper adjacency
        board.placeTiles(Tiles.GREEN, 5,0);
        board.placeTiles(Tiles.GREEN, 4,0);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 5,0);
        board.placeTiles(Tiles.EMPTY, 4,0);

        //we are on the left edge --> bottom adjacency
        board.placeTiles(Tiles.GREEN, 4,0);
        board.placeTiles(Tiles.GREEN, 5,0);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 4,0);
        board.placeTiles(Tiles.EMPTY, 5,0);


        //we are on the right edge --> left adjacency
        board.placeTiles(Tiles.GREEN, 3,8);
        board.placeTiles(Tiles.GREEN, 3,7);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,8);
        board.placeTiles(Tiles.EMPTY, 3,7);

        //we are on the right edge --> upper adjacency
        board.placeTiles(Tiles.GREEN, 4,8);
        board.placeTiles(Tiles.GREEN, 3,8);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 4,8);
        board.placeTiles(Tiles.EMPTY, 3,8);

        //we are on the right edge --> bottom adjacency
        board.placeTiles(Tiles.GREEN, 3,8);
        board.placeTiles(Tiles.GREEN, 4,8);
        result=board.checkBoardReset();
        assertFalse(result);
        board.placeTiles(Tiles.EMPTY, 3,8);
        board.placeTiles(Tiles.EMPTY, 4,8);
    }

    //TEST boardResetENG()
    @DisplayName("Board is reset according to the English rules - 2 players")
    @Test
    void boardResetENG1() {
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        board.getGamesBoard().setTile(Tiles.GREEN, 4,4);
        board.boardResetENG();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOTALLOWED);

    }
    @DisplayName("Board is reset according to the English rules - 3 players")
    @Test
    void boardResetENG2() {
        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        board.getGamesBoard().setTile(Tiles.GREEN, 4,4);
        board.boardResetENG();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);

    }
    @DisplayName("Board is reset according to the English rules - 4 players")
    @Test
    void boardResetENG3() {
        Sachet sachet=new Sachet();
        Board board=new Board(4, sachet);
        board.getGamesBoard().setTile(Tiles.GREEN, 4,4);
        board.boardResetENG();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }


    }

    //TEST boardResetITA()
    @DisplayName("Board is reset according to the Italian rules - 2 players")
    @Test
    void boardResetITA1() {
        //arrange - setup our test objects
        Board board=new Board(2, new Sachet());
        //act - do the actual calc or method run
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3, 6);
        board.placeTiles(Tiles.GREEN, 4, 4);
        board.boardResetITA();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        assertEquals(board.getGamesBoard().getTile(3,3), Tiles.GREEN);
        assertEquals(board.getGamesBoard().getTile(3,6), Tiles.GREEN);
        assertEquals(board.getGamesBoard().getTile(4,4), Tiles.GREEN);

        assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOTALLOWED);

    }
    @DisplayName("Board is reset according to the Italian rules - 3 players")
    @Test
    void boardResetITA2() {
        //arrange - setup our test objects
        Board board=new Board(3, new Sachet());
        //act - do the actual calc or method run
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3, 6);
        board.placeTiles(Tiles.GREEN, 4, 4);
        board.boardResetITA();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        assertEquals(board.getGamesBoard().getTile(3,3), Tiles.GREEN);
        assertEquals(board.getGamesBoard().getTile(3,6), Tiles.GREEN);
        assertEquals(board.getGamesBoard().getTile(4,4), Tiles.GREEN);

        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);

    }
    @DisplayName("Board is reset according to the Italian rules - 4 players")
    @Test
    void boardResetITA3() {
        //arrange - setup our test objects
        Board board=new Board(4, new Sachet());
        //act - do the actual calc or method run
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 3, 6);
        board.placeTiles(Tiles.GREEN, 4, 4);
        board.boardResetITA();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        assertEquals(board.getGamesBoard().getTile(3,3), Tiles.GREEN);
        assertEquals(board.getGamesBoard().getTile(3,6), Tiles.GREEN);
        assertEquals(board.getGamesBoard().getTile(4,4), Tiles.GREEN);

    }

    //TEST placeTiles(Tiles, int, int)
    @DisplayName("Place tile on the board in specified position")
    @Test
    void placeTiles() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        Tiles tiles=sachet.draw();
        //act - do the actual calc or method run
        board.placeTiles(tiles, 3,3);

        //assert - check if actual val is equal to expected val
        assertEquals(board.getGamesBoard().getTile(3,3), tiles);
    }

    //TEST remove(List<Point>)
    @DisplayName("Remove tile on the board in specified position")
    @Test
    void remove() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        ArrayList<Point> points=new ArrayList<>();
        points.add(new Point(3,3));
        points.add(new Point(3,5));
        points.add(new Point(6,3));
        board.remove(points);
        //assert - check if actual val is equal to expected val
        for (Point point : points) {
            assertEquals(board.getGamesBoard().getTile(point.x, point.y), Tiles.EMPTY);
        }
    }

    //TEST checkFreeTiles(Point)
    @DisplayName("Tile in specified position is NOT free")
    @Test
    void checkFreeTiles1() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        //assert - check if actual val is equal to expected val
        assertFalse(board.checkFreeTiles(new Point(3, 3)));
    }
    @DisplayName("Tile in specified position is free")
    @Test
    void checkFreeTiles2() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        ArrayList<Point> points=new ArrayList<>();
        points.add(new Point(2,3));
        points.add(new Point(4,3));
        points.add(new Point(3,2));
        points.add(new Point(3,4));
        board.remove(points);
        //assert - check if actual val is equal to expected val
        assertTrue(board.checkFreeTiles(new Point(3, 3)));
    }
    @DisplayName("Tile in specified position is free")
    @Test
    void checkFreeTiles3() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        //assert - check if actual val is equal to expected val
        assertTrue(board.checkFreeTiles(new Point(0, 3)));
    }

    //TEST freeTiles()
    @DisplayName("Tiles that are free now")
    @Test
    void freeTiles() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(4, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        ArrayList<Point> result=board.freeTiles();
        //assert - check if actual val is equal to expected val
        assertEquals(20, result.size());
        assertTrue(result.contains(new Point(0,3)));
        assertTrue(result.contains(new Point(0,4)));
        assertTrue(result.contains(new Point(1,3)));
        assertTrue(result.contains(new Point(1,5)));
        assertTrue(result.contains(new Point(2,2)));
        assertTrue(result.contains(new Point(2,6)));
        assertTrue(result.contains(new Point(3,1)));
        assertTrue(result.contains(new Point(3,7)));
        assertTrue(result.contains(new Point(3,8)));
        assertTrue(result.contains(new Point(4,0)));
        assertTrue(result.contains(new Point(4,8)));
        assertTrue(result.contains(new Point(5,0)));
        assertTrue(result.contains(new Point(5,1)));
        assertTrue(result.contains(new Point(5,7)));
        assertTrue(result.contains(new Point(6,2)));
        assertTrue(result.contains(new Point(6,6)));
        assertTrue(result.contains(new Point(7,3)));
        assertTrue(result.contains(new Point(7,5)));
        assertTrue(result.contains(new Point(8,4)));
        assertTrue(result.contains(new Point(8,5)));
    }

    //TEST checkAdjacentTiles(List<Point>)
    @DisplayName("Tiles are adjacent because in the same row - 2 players' game")
    @Test
    void checkAdjacentTiles1() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        arr.add(new Point(3, 2));
        arr.add(new Point(3, 3));
        arr.add(new Point(3, 4));
        assertTrue(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are adjacent because in the same row - 3 players' game")
    @Test
    void checkAdjacentTiles2() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(3);
        //x,y
        arr.add(new Point(3, 2));
        arr.add(new Point(3, 3));
        arr.add(new Point(3, 4));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //x,0
        arr.add(new Point(4,0));
        arr.add(new Point(4,2));
        arr.add(new Point(4,1));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //x,8
        arr.add(new Point(3,6));
        arr.add(new Point(3,7));
        arr.add(new Point(3,8));
        assertTrue(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are adjacent because in the same row - 4 players' game")
    @Test
    void checkAdjacentTiles3() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(4);
        //x,y
        arr.add(new Point(3, 2));
        arr.add(new Point(3, 3));
        arr.add(new Point(3, 4));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //x,0
        arr.add(new Point(4,0));
        arr.add(new Point(4,2));
        arr.add(new Point(4,1));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //x,8
        arr.add(new Point(3,6));
        arr.add(new Point(3,7));
        arr.add(new Point(3,8));
        assertTrue(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are adjacent because in the same column - 2 players' game")
    @Test
    void checkAdjacentTiles4() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //x,y
        arr.add(new Point(3, 3));
        arr.add(new Point(4, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are adjacent because in the same column - 3 players' game")
    @Test
    void checkAdjacentTiles5() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(3);
        //x,y
        arr.add(new Point(3, 3));
        arr.add(new Point(4, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //0,y
        arr.add(new Point(0, 3));
        arr.add(new Point(1, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //8,y
        arr.add(new Point(8, 5));
        arr.add(new Point(7, 5));
        arr.add(new Point(6, 5));
        assertTrue(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are adjacent because in the same column - 4 players' game")
    @Test
    void checkAdjacentTiles6() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(4);
        //x,y
        arr.add(new Point(3, 3));
        arr.add(new Point(4, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //0,y
        arr.add(new Point(0, 3));
        arr.add(new Point(1, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //8,y
        arr.add(new Point(8, 5));
        arr.add(new Point(7, 5));
        arr.add(new Point(6, 5));
        assertTrue(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are NOT adjacent but in the same column - 2 players' game")
    @Test
    void checkAdjacentTiles7() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //x,y
        arr.add(new Point(1, 3));
        arr.add(new Point(3, 3));
        arr.add(new Point(5, 3));
        assertFalse(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are NOT adjacent but in the same column - 3 players' game")
    @Test
    void checkAdjacentTiles8() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(3);
        //x,y
        arr.add(new Point(1, 3));
        arr.add(new Point(3, 3));
        arr.add(new Point(5, 3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //0,y
        arr.add(new Point(0,3));
        arr.add(new Point(2,3));
        arr.add(new Point(3,3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //8,y
        arr.add(new Point(8,5));
        arr.add(new Point(6,5));
        arr.add(new Point(5,5));
        assertFalse(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are NOT adjacent but in the same column - 4 players' game")
    @Test
    void checkAdjacentTiles9() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(4);
        //x,y
        arr.add(new Point(1, 3));
        arr.add(new Point(3, 3));
        arr.add(new Point(5, 3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //0,y
        arr.add(new Point(0,3));
        arr.add(new Point(2,3));
        arr.add(new Point(3,3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //8,y
        arr.add(new Point(8,5));
        arr.add(new Point(6,5));
        arr.add(new Point(5,5));
        assertFalse(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are NOT adjacent but in the same row - 2 players' game")
    @Test
    void checkAdjacentTiles10() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //x,y
        arr.add(new Point(4, 1));
        arr.add(new Point(4, 3));
        arr.add(new Point(4, 5));
        assertFalse(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are NOT adjacent but in the same row - 3 players' game")
    @Test
    void checkAdjacentTiles11() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(3);
        //x,y
        arr.add(new Point(4, 1));
        arr.add(new Point(4, 3));
        arr.add(new Point(4, 5));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //x,0
        arr.add(new Point(5,0));
        arr.add(new Point(5,2));
        arr.add(new Point(5,3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //x,8
        arr.add(new Point(3,8));
        arr.add(new Point(3,6));
        arr.add(new Point(3,5));
        assertFalse(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are NOT adjacent but in the same row - 4 players' game")
    @Test
    void checkAdjacentTiles12() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(4);
        //x,y
        arr.add(new Point(4, 1));
        arr.add(new Point(4, 3));
        arr.add(new Point(4, 5));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //x,0
        arr.add(new Point(5,0));
        arr.add(new Point(5,2));
        arr.add(new Point(5,3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.removeAll(arr);
        //x,8
        arr.add(new Point(3,8));
        arr.add(new Point(3,6));
        arr.add(new Point(3,5));
        assertFalse(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are NOT adjacent")
    @Test
    void checkAdjacentTiles13() {
        //arrange - setup our test objects
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //act - do the actual calc or method run
        arr.add(new Point(3, 3));
        arr.add(new Point(4, 4));
        arr.add(new Point(5, 5));
        boolean result=board.checkAdjacentTiles(arr);
        //assert - check if actual val is equal to expected val
        assertFalse(result);
    }
    @DisplayName("List consist of only 1 elemets")
    @Test
    void checkAdjacentTiles14() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        arr.add(new Point(3, 3));
        assertFalse(board.checkAdjacentTiles(arr));
    }

    //TEST adjacentTiles(Point)
    @DisplayName("Tiles adjacent to that date")
    @Test
    void adjacentTiles1() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        ArrayList<Point> result;
        //act - do the actual calc or method run
        board.BoardInitialization();
        result=board.adjacentTiles(new Point(3,3));
        //assert - check if actual val is equal to expected val
        assertEquals(4, result.size());
        assertTrue(result.contains(new Point(2, 3)));
        assertTrue(result.contains(new Point(4, 3)));
        assertTrue(result.contains(new Point(3, 2)));
        assertTrue(result.contains(new Point(3, 4)));
    }
    @DisplayName("Tiles adjacent to that date - upper edge")
    @Test
    void adjacentTiles2() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(4, sachet);
        ArrayList<Point> result;
        //act - do the actual calc or method run
        board.BoardInitialization();
        result=board.adjacentTiles(new Point(0,3));
        //assert - check if actual val is equal to expected val
        assertEquals(2, result.size());
        assertTrue(result.contains(new Point(1, 3)));
        assertTrue(result.contains(new Point(0, 4)));
    }
    @DisplayName("Tiles adjacent to that date - bottom edge")
    @Test
    void adjacentTiles3() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(4, sachet);
        ArrayList<Point> result;
        //act - do the actual calc or method run
        board.BoardInitialization();
        result=board.adjacentTiles(new Point(8,4));
        //assert - check if actual val is equal to expected val
        assertEquals(2, result.size());
        assertTrue(result.contains(new Point(8, 5)));
        assertTrue(result.contains(new Point(7, 4)));
    }

    //TEST checkSameColumn(List<Point)
    @DisplayName("Point in the same row")
    @Test
    void checkSameRow1() {
        //arrange - setup our test objects
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //act - do the actual calc or method run
        arr.add(new Point(0, 0));
        arr.add(new Point(0, 1));
        arr.add(new Point(0, 5));
        boolean result=board.checkSameRow(arr);
        //assert - check if actual val is equal to expected val
        assertTrue(result);
    }
    @DisplayName("Point NOT in the same row")
    @Test
    void checkSameRow2() {
        //arrange - setup our test objects
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //act - do the actual calc or method run
        arr.add(new Point(0, 0));
        arr.add(new Point(2, 1));
        arr.add(new Point(0, 5));
        boolean result=board.checkSameRow(arr);
        //assert - check if actual val is equal to expected val
        assertFalse(result);
    }

    //TEST checkSameColumn(List<Point)
    @DisplayName("Point in the same column")
    @Test
    void checkSameColumn1() {
        //arrange - setup our test objects
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //act - do the actual calc or method run
        arr.add(new Point(0, 0));
        arr.add(new Point(1, 0));
        arr.add(new Point(5, 0));
        boolean result=board.checkSameColumn(arr);
        //assert - check if actual val is equal to expected val
        assertTrue(result);
    }
    @DisplayName("Point NOT in the same column")
    @Test
    void checkSameColumn2() {
        //arrange - setup our test objects
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //act - do the actual calc or method run
        arr.add(new Point(0, 0));
        arr.add(new Point(1, 3));
        arr.add(new Point(5, 0));
        boolean result=board.checkSameColumn(arr);
        //assert - check if actual val is equal to expected val
        assertFalse(result);
    }

    @DisplayName("Add tile to the board")
    @Test
    void addTile(){
        Board board=new Board(2);
        board.addTile(Tiles.GREEN, 3,3);
        assertEquals(board.getGamesBoard().getTile(3,3), Tiles.GREEN);
    }
}






/*
    checkBoardReset():  il check a sx e sopra NON lo fa mai
                        perchè return false prima --> a sx ritorna la posizione prima (dx prima)
                                                  --> a up ritorna la posizione sotto (down prima)

                    -->
                   |
                   |
                   V
                        xx    si chiede: dx è occupata? Sì
                        x     si chiede: dw è occupata? Sì

                        li lascio o li tolgo??????
 */




//arrange - setup our test objects

//act - do the actual calc or method run

//assert - check if actual val is equal to expected val

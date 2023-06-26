package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Tiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private void placeTiles(Board gamesBoard, Sachet boardSachet, Tiles tile, int row, int col){
        gamesBoard.getGamesBoard().setTile(tile, row, col);
        boardSachet.removeTiles(tile);
    }

    //TEST Constructor and Getter method
    @DisplayName("Testing the Constructor and Getter method")
    @Test
    void constructorTest(){

        Board board4=new Board(2, new Sachet());
        Board board5=new Board(3, new Sachet());
        Board board6=new Board(4, new Sachet());

        //check the NOTALLOWED position
        assertEquals(board4.getGamesBoard().getTile(0,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,1), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,2), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,3), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,4), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,5), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,6), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,7), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(0,8), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,1), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,2), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,5), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,6), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,7), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(1,8), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,1), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,2), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,6), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,7), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(2,8), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(3,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(3,1), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(3,8), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(4,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(4,8), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(5,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(5,7), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(5,8), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,1), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,2), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,6), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,7), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(6,8), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,1), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,2), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,3), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,6), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,7), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(7,8), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,0), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,1), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,2), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,3), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,4), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,5), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,6), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,7), Tiles.NOT_ALLOWED);
        assertEquals(board4.getGamesBoard().getTile(8,8), Tiles.NOT_ALLOWED);

        assertEquals(board5.getGamesBoard().getTile(0,0), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,1), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,2), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,4), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,5), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,6), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,7), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(0,8), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,0), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,1), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,2), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,5), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,6), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,7), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(1,8), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(2,0), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(2,1), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(2,7), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(2,8), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(3,0), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(3,1), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(4,0), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(4,8), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(5,7), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(5,8), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(6,0), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(6,1), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(6,7), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(6,8), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,0), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,1), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,2), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,3), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,6), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,7), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(7,8), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,0), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,1), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,2), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,3), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,4), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,6), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,7), Tiles.NOT_ALLOWED);
        assertEquals(board5.getGamesBoard().getTile(8,8), Tiles.NOT_ALLOWED);

        assertEquals(board6.getGamesBoard().getTile(0,0), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,1), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,2), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,5), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,6), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,7), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(0,8), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,0), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,1), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,2), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,6), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,7), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(1,8), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(2,0), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(2,1), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(2,7), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(2,8), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(3,0), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(5,8), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(6,0), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(6,1), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(6,7), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(6,8), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,0), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,1), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,2), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,6), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,7), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(7,8), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,0), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,1), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,2), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,3), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,6), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,7), Tiles.NOT_ALLOWED);
        assertEquals(board6.getGamesBoard().getTile(8,8), Tiles.NOT_ALLOWED);
    }

    //TEST boardInitialization()
    @DisplayName("Board initialization for 2 players do NOT put EMPTY tiles")
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
        assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOT_ALLOWED);

    }
    @DisplayName("Board initialization for 3 players do NOT put EMPTY tiles")
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
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOT_ALLOWED);
    }
    @DisplayName("Board initialization for 3 players do NOT put EMPTY tiles")
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
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 4,4);
        placeTiles(board, sachet, Tiles.GREEN, 3,6);
        boolean result=board.checkBoardReset();
        assertTrue(result);

        Board board1=new Board(4, sachet);

        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                board1.getGamesBoard().setTile(Tiles.EMPTY, i, j);
            }
        }
        assertTrue(board1.checkBoardReset());

    }
    @DisplayName("Condition to board reset is verified - 3 players' game")
    @Test
    void checkBoardReset2() {
        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 4,4);
        placeTiles(board, sachet, Tiles.GREEN, 3,6);
        boolean result=board.checkBoardReset();
        assertTrue(result);

    }
    @DisplayName("Condition to board reset is verified - 4 players' game")
    @Test
    void checkBoardReset3() {
        Sachet sachet=new Sachet();
        Board board=new Board(4, sachet);
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 4,4);
        placeTiles(board, sachet, Tiles.GREEN, 3,6);
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
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 3,2);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 3,2);

        //we are NOT on the edge board --> right adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 3,4);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 3,4);

        //we are NOT on the edge board --> upper adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 2,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 2,3);

        //we are NOT on the edge board --> bottom adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 4,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 4,3);
    }
    @DisplayName("Condition to board reset is NOT verified - 3 players' game")
    @Test
    void checkBoardReset5() {
        boolean result;
        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        //we are NOT on the edge board --> left adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 3,2);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 3,2);

        //we are NOT on the edge board --> right adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 3,4);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 3,4);

        //we are NOT on the edge board --> upper adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 2,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 2,3);

        //we are NOT on the edge board --> bottom adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 4,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 4,3);


        //we are on the upper edge --> bottom adjacency
        placeTiles(board, sachet, Tiles.GREEN, 0,3);
        placeTiles(board, sachet, Tiles.GREEN, 1,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 0,3);
        placeTiles(board, sachet, Tiles.EMPTY, 1,3);


        //we are on the bottom edge --> up adjacency
        placeTiles(board, sachet, Tiles.GREEN, 8,5);
        placeTiles(board, sachet, Tiles.GREEN, 7,5);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 8,5);
        placeTiles(board, sachet, Tiles.EMPTY, 7,5);


        //we are on the left edge --> right adjacency
        placeTiles(board, sachet, Tiles.GREEN, 5,0);
        placeTiles(board, sachet, Tiles.GREEN, 5,1);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 5,0);
        placeTiles(board, sachet, Tiles.EMPTY, 5,1);


        //we are on the right edge --> left adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,8);
        placeTiles(board, sachet, Tiles.GREEN, 3,7);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,8);
        placeTiles(board, sachet, Tiles.EMPTY, 3,7);
    }
    @DisplayName("Condition to board reset is NOT verified - 4 players' game")
    @Test
    void checkBoardReset6() {
        boolean result;
        Sachet sachet=new Sachet();
        Board board=new Board(4, sachet);
        //we are NOT on the edge board --> left adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 3,2);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 3,2);

        //we are NOT on the edge board --> right adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 3,4);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 3,4);

        //we are NOT on the edge board --> upper adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 2,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 2,3);

        //we are NOT on the edge board --> bottom adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,3);
        placeTiles(board, sachet, Tiles.GREEN, 4,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,3);
        placeTiles(board, sachet, Tiles.EMPTY, 4,3);


        //we are on the upper edge --> bottom adjacency
        placeTiles(board, sachet, Tiles.GREEN, 0,3);
        placeTiles(board, sachet, Tiles.GREEN, 1,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 0,3);
        placeTiles(board, sachet, Tiles.EMPTY, 1,3);

        //we are on the upper edge --> right adjacency
        placeTiles(board, sachet, Tiles.GREEN, 0,3);
        placeTiles(board, sachet, Tiles.GREEN, 0,4);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 0,3);
        placeTiles(board, sachet, Tiles.EMPTY, 0,4);

        //we are on the upper edge --> left adjacency
        placeTiles(board, sachet, Tiles.GREEN, 0,4);
        placeTiles(board, sachet, Tiles.GREEN, 0,3);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 0,4);
        placeTiles(board, sachet, Tiles.EMPTY, 0,3);


        //we are on the bottom edge --> up adjacency
        placeTiles(board, sachet, Tiles.GREEN, 8,5);
        placeTiles(board, sachet, Tiles.GREEN, 7,5);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 8,5);
        placeTiles(board, sachet, Tiles.EMPTY, 7,5);

        //we are on the bottom edge --> left adjacency
        placeTiles(board, sachet, Tiles.GREEN, 8,5);
        placeTiles(board, sachet, Tiles.GREEN, 8,4);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 8,5);
        placeTiles(board, sachet, Tiles.EMPTY, 8,4);

        //we are on the bottom edge --> right adjacency
        placeTiles(board, sachet, Tiles.GREEN, 8,4);
        placeTiles(board, sachet, Tiles.GREEN, 8,5);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 8,4);
        placeTiles(board, sachet, Tiles.EMPTY, 8,5);


        //we are on the left edge --> right adjacency
        placeTiles(board, sachet, Tiles.GREEN, 5,0);
        placeTiles(board, sachet, Tiles.GREEN, 5,1);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 5,0);
        placeTiles(board, sachet, Tiles.EMPTY, 5,1);

        //we are on the left edge --> upper adjacency
        placeTiles(board, sachet, Tiles.GREEN, 5,0);
        placeTiles(board, sachet, Tiles.GREEN, 4,0);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 5,0);
        placeTiles(board, sachet, Tiles.EMPTY, 4,0);

        //we are on the left edge --> bottom adjacency
        placeTiles(board, sachet, Tiles.GREEN, 4,0);
        placeTiles(board, sachet, Tiles.GREEN, 5,0);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 4,0);
        placeTiles(board, sachet, Tiles.EMPTY, 5,0);


        //we are on the right edge --> left adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,8);
        placeTiles(board, sachet, Tiles.GREEN, 3,7);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,8);
        placeTiles(board, sachet, Tiles.EMPTY, 3,7);

        //we are on the right edge --> upper adjacency
        placeTiles(board, sachet, Tiles.GREEN, 4,8);
        placeTiles(board, sachet, Tiles.GREEN, 3,8);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 4,8);
        placeTiles(board, sachet, Tiles.EMPTY, 3,8);

        //we are on the right edge --> bottom adjacency
        placeTiles(board, sachet, Tiles.GREEN, 3,8);
        placeTiles(board, sachet, Tiles.GREEN, 4,8);
        result=board.checkBoardReset();
        assertFalse(result);
        placeTiles(board, sachet, Tiles.EMPTY, 3,8);
        placeTiles(board, sachet, Tiles.EMPTY, 4,8);
    }
    @Test
    public void checkBoardReset7(){
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        placeTiles(board, sachet, Tiles.GREEN, 4, 4);
        placeTiles(board, sachet, Tiles.BLUE, 5, 5);

        assertTrue(board.checkBoardReset());
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
        assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOT_ALLOWED);

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
        assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOT_ALLOWED);
        assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOT_ALLOWED);

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

    //TEST placeTiles(Tiles, int, int)
    @DisplayName("Place tile on the board in specified position")
    @Test
    void placeTiles() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        Tiles tiles=sachet.draw();
        //act - do the actual calc or method run
        placeTiles(board, sachet, tiles, 3,3);

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


    //TEST checkAdjacentTiles(List<Point>)
    @DisplayName("Tiles are adjacent because in the same row - 2 players' game")
    @Test
    void checkAdjacentTiles1() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2, new Sachet());
        arr.add(new Point(3, 2));
        arr.add(new Point(3, 3));
        arr.add(new Point(3, 4));
        assertTrue(board.checkAdjacentTiles(arr));
    }
    @DisplayName("Tiles are adjacent because in the same row - 3 players' game")
    @Test
    void checkAdjacentTiles2() {
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(3, new Sachet());
        //x,y
        arr.add(new Point(3, 2));
        arr.add(new Point(3, 3));
        arr.add(new Point(3, 4));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.clear();
        //x,0
        arr.add(new Point(4,0));
        arr.add(new Point(4,2));
        arr.add(new Point(4,1));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.clear();
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
        Board board=new Board(4, new Sachet());
        //x,y
        arr.add(new Point(3, 2));
        arr.add(new Point(3, 3));
        arr.add(new Point(3, 4));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.clear();
        //x,0
        arr.add(new Point(4,0));
        arr.add(new Point(4,2));
        arr.add(new Point(4,1));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.clear();
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
        Board board=new Board(2, new Sachet());
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
        Board board=new Board(3, new Sachet());
        //x,y
        arr.add(new Point(3, 3));
        arr.add(new Point(4, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.clear();
        //0,y
        arr.add(new Point(0, 3));
        arr.add(new Point(1, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.clear();
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
        Board board=new Board(4, new Sachet());
        //x,y
        arr.add(new Point(3, 3));
        arr.add(new Point(4, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.clear();
        //0,y
        arr.add(new Point(0, 3));
        arr.add(new Point(1, 3));
        arr.add(new Point(2, 3));
        assertTrue(board.checkAdjacentTiles(arr));
        arr.clear();
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
        Board board=new Board(2, new Sachet());
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
        Board board=new Board(3, new Sachet());
        //x,y
        arr.add(new Point(1, 3));
        arr.add(new Point(3, 3));
        arr.add(new Point(5, 3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.clear();
        //0,y
        arr.add(new Point(0,3));
        arr.add(new Point(2,3));
        arr.add(new Point(3,3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.clear();
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
        Board board=new Board(4, new Sachet());
        //x,y
        arr.add(new Point(1, 3));
        arr.add(new Point(3, 3));
        arr.add(new Point(5, 3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.clear();
        //0,y
        arr.add(new Point(0,3));
        arr.add(new Point(2,3));
        arr.add(new Point(3,3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.clear();
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
        Board board=new Board(2, new Sachet());
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
        Board board=new Board(3, new Sachet());
        //x,y
        arr.add(new Point(4, 1));
        arr.add(new Point(4, 3));
        arr.add(new Point(4, 5));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.clear();
        //x,0
        arr.add(new Point(5,0));
        arr.add(new Point(5,2));
        arr.add(new Point(5,3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.clear();
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
        Board board=new Board(4, new Sachet());
        //x,y
        arr.add(new Point(4, 1));
        arr.add(new Point(4, 3));
        arr.add(new Point(4, 5));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.clear();
        //x,0
        arr.add(new Point(5,0));
        arr.add(new Point(5,2));
        arr.add(new Point(5,3));
        assertFalse(board.checkAdjacentTiles(arr));
        arr.clear();
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
        Board board=new Board(2, new Sachet());
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
        Board board=new Board(2, new Sachet());
        arr.add(new Point(3, 3));
        assertFalse(board.checkAdjacentTiles(arr));
    }


    //TEST tilesArePickable(ArrayList)
    @DisplayName("Tiles Not Pickable")
    @Test
    void tilesArePickable(){
        Board board=new Board(2, new Sachet());
        board.BoardInitialization();

        ArrayList<Point> arrayList=new ArrayList<>();

        //tiles not free
        arrayList.add(new Point(3,3));
        assertFalse(board.tilesArePickable(arrayList));

        //tiles not adjacent
        arrayList.add(new Point(5,5));
        assertFalse(board.tilesArePickable(arrayList));

        //selected Empty tiles
        board.getGamesBoard().remove(3, 3);
        ArrayList<Point> arrayList1=new ArrayList<>();
        arrayList1.add(new Point(3,3));
        assertFalse(board.tilesArePickable(arrayList1));

        //selected NotAllowed tiles
        ArrayList<Point> arrayList2=new ArrayList<>();
        arrayList2.add(new Point(0,0));
        assertFalse(board.tilesArePickable(arrayList2));

        //selected a number over the max number of tiles  pickable
        ArrayList<Point> arrayList3=new ArrayList<>();
        arrayList3.add(new Point(0,0));
        arrayList3.add(new Point(0,0));
        arrayList3.add(new Point(0,0));
        arrayList3.add(new Point(0,0));
        arrayList3.add(new Point(0,0));
        arrayList3.add(new Point(0,0));
        arrayList3.add(new Point(0,0));
        assertFalse(board.tilesArePickable(arrayList3));

        //selected one tiles pickable
        ArrayList<Point> arrayList4=new ArrayList<>();
        arrayList4.add(new Point(1,3));
        assertTrue(board.tilesArePickable(arrayList4));

    }

    @DisplayName("test the adjacentTiles method, but since it is private I have to go by checkFreeTiles which is called by tilesArePickable")
    @Test
    void tilesArePickable1(){
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                board.getGamesBoard().setTile(Tiles.EMPTY, i, j);
            }
        }

        //upper left corner
        placeTiles(board, sachet, Tiles.GREEN, 0,0);
        placeTiles(board, sachet, Tiles.GREEN, 0, 1);
        placeTiles(board, sachet, Tiles.GREEN, 1, 0);
        ArrayList<Point> arrayList=new ArrayList<>();
        arrayList.add(new Point(0, 0));
        assertTrue(board.tilesArePickable(arrayList));

        //upper right corner
        placeTiles(board, sachet, Tiles.GREEN, 0,Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        placeTiles(board, sachet, Tiles.GREEN, 0, Define.NUMBEROFCOLUMNS_BOARD.getI()-1-1);
        placeTiles(board, sachet, Tiles.GREEN, 1, Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        ArrayList<Point> arrayList1=new ArrayList<>();
        arrayList1.add(new Point(0, Define.NUMBEROFCOLUMNS_BOARD.getI()-1));
        assertTrue(board.tilesArePickable(arrayList1));

        //bottom left corner
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1,0);
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1, 1);
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1-1, 0);
        ArrayList<Point> arrayList2=new ArrayList<>();
        arrayList2.add(new Point(Define.NUMBEROFCOLUMNS_BOARD.getI()-1, 0));
        assertTrue(board.tilesArePickable(arrayList2));

        //bottom right corner
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1,Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1, Define.NUMBEROFCOLUMNS_BOARD.getI()-1-1);
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1-1, Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        ArrayList<Point> arrayList3=new ArrayList<>();
        arrayList3.add(new Point(Define.NUMBEROFCOLUMNS_BOARD.getI()-1, Define.NUMBEROFCOLUMNS_BOARD.getI()-1));
        assertTrue(board.tilesArePickable(arrayList3));


        //upper edge
        placeTiles(board, sachet, Tiles.GREEN, 0,4);
        placeTiles(board, sachet, Tiles.GREEN, 0, 3);
        placeTiles(board, sachet, Tiles.GREEN, 0, 5);
        placeTiles(board, sachet, Tiles.GREEN, 1, 4);
        ArrayList<Point> arrayList4=new ArrayList<>();
        arrayList4.add(new Point(0, 4));
        assertTrue(board.tilesArePickable(arrayList4));

        //bottom edge
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1,4);
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1, 3);
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1, 5);
        placeTiles(board, sachet, Tiles.GREEN, Define.NUMBEROFCOLUMNS_BOARD.getI()-1-1, 4);
        ArrayList<Point> arrayList5=new ArrayList<>();
        arrayList5.add(new Point(Define.NUMBEROFCOLUMNS_BOARD.getI()-1, 4));
        assertTrue(board.tilesArePickable(arrayList5));

        //left edge
        placeTiles(board, sachet, Tiles.GREEN, 4,0);
        placeTiles(board, sachet, Tiles.GREEN, 3, 0);
        placeTiles(board, sachet, Tiles.GREEN, 5, 0);
        placeTiles(board, sachet, Tiles.GREEN, 4, 1);
        ArrayList<Point> arrayList6=new ArrayList<>();
        arrayList6.add(new Point(4, 0));
        assertTrue(board.tilesArePickable(arrayList6));

        //right edge
        placeTiles(board, sachet, Tiles.GREEN, 4,Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        placeTiles(board, sachet, Tiles.GREEN, 3, Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        placeTiles(board, sachet, Tiles.GREEN, 5, Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        placeTiles(board, sachet, Tiles.GREEN, 4, Define.NUMBEROFCOLUMNS_BOARD.getI()-1-1);
        ArrayList<Point> arrayList7=new ArrayList<>();
        arrayList7.add(new Point(4, Define.NUMBEROFCOLUMNS_BOARD.getI()-1));
        assertTrue(board.tilesArePickable(arrayList7));
    }


    @DisplayName("testing of all edge cases that are not inherent to the game but to how we decided to structure the board")
    @Test
    void checkBoardResetLimitCaseNotMyShelfie() {
        //upper left corner
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                board.getGamesBoard().setTile(Tiles.EMPTY, i, j);
            }
        }
        placeTiles(board, sachet, Tiles.GREEN, 0, 0);
        assertTrue(board.checkBoardReset());

        //upper right corner
        Sachet sachet1=new Sachet();
        Board board1=new Board(2, sachet);
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                board1.getGamesBoard().setTile(Tiles.EMPTY, i, j);
            }
        }
        placeTiles(board1, sachet1, Tiles.GREEN, 0, Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        assertTrue(board1.checkBoardReset());


        //bottom left corner
        Sachet sachet3=new Sachet();
        Board board3=new Board(2, sachet);
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                board3.getGamesBoard().setTile(Tiles.EMPTY, i, j);
            }
        }
        placeTiles(board3, sachet3, Tiles.GREEN, Define.NUMBEROFROWS_BOARD.getI()-1, 0);
        assertTrue(board3.checkBoardReset());

        //bottom right corner
        Sachet sachet2=new Sachet();
        Board board2=new Board(2, sachet);
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                board2.getGamesBoard().setTile(Tiles.EMPTY, i, j);
            }
        }
        placeTiles(board2, sachet2, Tiles.GREEN, Define.NUMBEROFROWS_BOARD.getI()-1, Define.NUMBEROFCOLUMNS_BOARD.getI()-1);
        assertTrue(board2.checkBoardReset());
    }

}

package it.polimi.ingsw;

import it.polimi.ingsw.PersonalObjective.PersonalObjective;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

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
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        Assertions.assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOTALLOWED);

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
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        Assertions.assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
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
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
    }

    //TEST checkBoardReset
    @DisplayName("Condition to board reset is verified")
    @Test
    void checkBoardReset1() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 4,4);
        board.placeTiles(Tiles.GREEN, 3,6);
        boolean result=board.checkBoardReset();
        //assert - check if actual val is equal to expected val
        Assertions.assertEquals(result, true);
    }
    @DisplayName("Condition to board reset is NOT verified")
    @Test
    void checkBoardReset2() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.placeTiles(Tiles.GREEN, 3,3);
        board.placeTiles(Tiles.GREEN, 4,3);
        board.placeTiles(Tiles.GREEN, 3,6);
        boolean result=board.checkBoardReset();
        //assert - check if actual val is equal to expected val
        Assertions.assertEquals(result, false);
    }

    //TEST boardResetENG()
    @DisplayName("Board is reset according to the English rules - 2 players")
    @Test
    void boardResetENG1() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        Assertions.assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOTALLOWED);

    }
    @DisplayName("Board is reset according to the English rules - 3 players")
    @Test
    void boardResetENG2() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        Assertions.assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);

    }
    @DisplayName("Board is reset according to the English rules - 4 players")
    @Test
    void boardResetENG3() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        //act - do the actual calc or method run
        board.BoardInitialization();
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
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
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        Assertions.assertEquals(board.getGamesBoard().getTile(3,3), Tiles.GREEN);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,6), Tiles.GREEN);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,4), Tiles.GREEN);

        Assertions.assertEquals(board.getGamesBoard().getTile(0,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(2,2), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(2,6), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(6,2), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(6,6), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,5), Tiles.NOTALLOWED);

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
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        Assertions.assertEquals(board.getGamesBoard().getTile(3,3), Tiles.GREEN);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,6), Tiles.GREEN);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,4), Tiles.GREEN);

        Assertions.assertEquals(board.getGamesBoard().getTile(0,4), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(1,5), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,1), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,0), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,8), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(5,7), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(7,3), Tiles.NOTALLOWED);
        Assertions.assertEquals(board.getGamesBoard().getTile(8,4), Tiles.NOTALLOWED);

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
                Assertions.assertNotEquals(board.getGamesBoard().getTile(i,j), Tiles.EMPTY);
            }
        }
        Assertions.assertEquals(board.getGamesBoard().getTile(3,3), Tiles.GREEN);
        Assertions.assertEquals(board.getGamesBoard().getTile(3,6), Tiles.GREEN);
        Assertions.assertEquals(board.getGamesBoard().getTile(4,4), Tiles.GREEN);

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
        Assertions.assertEquals(board.getGamesBoard().getTile(3,3), tiles);
    }

    //TEST remove(List<Point>)
    @DisplayName("Remove tile on the board in specified position")
    @Test
    void remove() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        Tiles tiles=sachet.draw();
        //act - do the actual calc or method run
        board.BoardInitialization();
        ArrayList<Point> points=new ArrayList<>();
        points.add(new Point(3,3));
        points.add(new Point(3,5));
        points.add(new Point(6,3));
        board.remove(points);
        //assert - check if actual val is equal to expected val
        for (int i = 0; i < points.size(); i++) {
            Assertions.assertEquals(board.getGamesBoard().getTile(points.get(i).x, points.get(i).y), Tiles.EMPTY);
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
        Assertions.assertEquals(board.checkFreeTiles(new Point(3,3)), false);
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
        Assertions.assertEquals(board.checkFreeTiles(new Point(3,3)), true);
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
        Assertions.assertEquals(board.checkFreeTiles(new Point(0,3)), true);
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
    @DisplayName("Tiles are adjacent because in the same row")
    @Test
    void checkAdjacentTiles1() {
        //arrange - setup our test objects
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //act - do the actual calc or method run
        arr.add(new Point(0, 0));
        arr.add(new Point(0, 1));
        arr.add(new Point(0, 2));
        boolean result=board.checkAdjacentTiles(arr);
        //assert - check if actual val is equal to expected val
        Assertions.assertEquals(result, true);
    }
    @DisplayName("Tiles are adjacent because in the same column")
    @Test
    void checkAdjacentTiles2() {
        //arrange - setup our test objects
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //act - do the actual calc or method run
        arr.add(new Point(1, 0));
        arr.add(new Point(0, 0));
        arr.add(new Point(2, 0));
        boolean result=board.checkAdjacentTiles(arr);
        //assert - check if actual val is equal to expected val
        Assertions.assertEquals(result, true);
    }
    @DisplayName("Tiles are NOT adjacent")
    @Test
    void checkAdjacentTiles3() {
        //arrange - setup our test objects
        ArrayList<Point> arr=new ArrayList<>();
        Board board=new Board(2);
        //act - do the actual calc or method run
        arr.add(new Point(1, 0));
        arr.add(new Point(0, 0));
        arr.add(new Point(5, 0));
        boolean result=board.checkAdjacentTiles(arr);
        //assert - check if actual val is equal to expected val
        Assertions.assertEquals(result, false);
    }

    //TEST adjacentTiles(Point)
    @DisplayName("Tiles adjacent to that date")
    @Test
    void adjacentTiles1() {
        //arrange - setup our test objects
        Sachet sachet=new Sachet();
        Board board=new Board(2, sachet);
        ArrayList<Point> result=new ArrayList<>();
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
        ArrayList<Point> result=new ArrayList<>();
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
        ArrayList<Point> result=new ArrayList<>();
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
        Assertions.assertEquals(result, true);
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
        Assertions.assertEquals(result, false);
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
        Assertions.assertEquals(result, true);
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
        Assertions.assertEquals(result, false);
    }
}






//arrange - setup our test objects

//act - do the actual calc or method run

//assert - check if actual val is equal to expected val

package it.polimi.ingsw.utils;

import java.awt.*;
import java.util.HashMap;

public interface CliPrintBackBone {
    void printBoard(Matrix board);
    void printBookshelf(Matrix bookshelf);
    void printAllBookshelf(HashMap<String, Matrix> allMatrix);
    void printPersonalObjective(HashMap<Point, Tiles> personalObjective);
    void printBookshelfPersonalObjective(Matrix bookshelf, HashMap<Point, Tiles> personalObjective);
    void help();

    void printChat(String username, boolean b);

    void helpForChat();

    void printChat(boolean b);
    boolean isWorking();
}

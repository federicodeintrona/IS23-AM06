package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.CliPrintBackBone;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.util.HashMap;

public class MockCliPrint implements CliPrintBackBone {
    private boolean isWorking = false;

    @Override
    public void printBoard(Matrix board) {

    }

    @Override
    public void printBookshelf(Matrix bookshelf) {

    }

    @Override
    public void printAllBookshelf(HashMap<String, Matrix> allMatrix) {

    }

    @Override
    public void printPersonalObjective(HashMap<Point, Tiles> personalObjective) {

    }

    @Override
    public void printBookshelfPersonalObjective(Matrix bookshelf, HashMap<Point, Tiles> personalObjective) {

    }

    @Override
    public void help() {

    }

    @Override
    public void printChat(String username, boolean b) {

    }

    @Override
    public void helpForChat() {

    }

    @Override
    public void printChat(boolean b) {
        isWorking = true;
    }

    public boolean isWorking() {
        return isWorking;
    }
}

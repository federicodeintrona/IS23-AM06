package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.utils.Matrix;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class VirtualView implements PropertyChangeListener {



    private ArrayList<String> playerNames;
    private Matrix board;
    private ArrayList<Matrix> bookshelves;
    private ArrayList<Integer> privatePoints ;
    private ArrayList<Integer> publicPoints ;




    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()){
            case "board":
                break;


            case "bookshelf" :
                break;


            case "points" :
                break;
        }
    }


    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public Matrix getBoard() {
        return board;
    }

    public void setBoard(Matrix board) {
        this.board = board;
    }

    public ArrayList<Matrix> getBookshelves() {
        return bookshelves;
    }

    public void setBookshelves(ArrayList<Matrix> bookshelves) {
        this.bookshelves = bookshelves;
    }

    public ArrayList<Integer> getPrivatePoints() {
        return privatePoints;
    }

    public void setPrivatePoints(ArrayList<Integer> privatePoints) {
        this.privatePoints = privatePoints;
    }

    public ArrayList<Integer> getPublicPoints() {
        return publicPoints;
    }

    public void setPublicPoints(ArrayList<Integer> publicPoints) {
        this.publicPoints = publicPoints;
    }
}

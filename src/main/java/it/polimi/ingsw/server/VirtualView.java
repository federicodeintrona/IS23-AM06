package it.polimi.ingsw.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class VirtualView implements PropertyChangeListener {

    private ArrayList<String> playerNames;
    private Board board;
    private ArrayList<Bookshelf> bookshelves;
    private ArrayList<Integer> points ;



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
}

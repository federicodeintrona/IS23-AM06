package it.polimi.ingsw;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.View.View;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    private ArrayList<Model> games;
    private ArrayList<ArrayList<View>> views;

    private HashMap<Integer, Player> playerIDs;


    public Controller() {
       games = new ArrayList<>();
       views = new ArrayList<>();
       playerIDs = new HashMap<>();
    }

    public void addGame(Model model, ArrayList<View> view){
        games.add(model);
        views.add(view);
    }

    public void startGame(int ID) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        games.get(ID).initialization();
    }




    public void addToBookshelf(int gameID, int playerID, ArrayList<Tiles> array, int col ){

        games.get(gameID).addToBookShelf(playerIDs.get(playerID),array,col);
    }


    public void removeTiles(int gameID, ArrayList<Point> points){
        games.get(gameID).removeTileArray(points);
    }

    public void saveState(int gameID){
        games.get(gameID).saveState();
    }


    //TO BE COMPLETED: just for UML purposes


    public Message processMessage(Message m, int playerID, int gameID){return new Message();};

    //Client Side Controller Method
    public void swapOrder(ArrayList<Integer> ints,ArrayList<Tiles> tiles){
        ArrayList<Tiles> array = new ArrayList<>();
        array.addAll(tiles);
        for (int i=0;i<ints.size();i++){
            tiles.set(i,array.get(ints.get(i)-1));
        }

    }
}

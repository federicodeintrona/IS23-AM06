package it.polimi.ingsw;

import it.polimi.ingsw.View.View;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Model> games;
    private ArrayList<ArrayList<View>> views;



    public void addGame(Model model, ArrayList<View> view){
        games.add(model);
        views.add(view);
    }

    

}

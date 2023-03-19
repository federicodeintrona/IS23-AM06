package it.polimi.ingsw.View;

import java.beans.PropertyChangeEvent;

public class CLIView extends View{


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

package it.polimi.ingsw.client.View;

import java.beans.PropertyChangeEvent;

public class GUIView extends View{

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

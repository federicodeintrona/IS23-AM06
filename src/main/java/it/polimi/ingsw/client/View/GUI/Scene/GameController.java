package it.polimi.ingsw.client.View.GUI.Scene;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class GameController {
    @FXML
    Label bookshelfUsername1;
    @FXML
    Label BookshelfUsername2;
    @FXML
    Label BookshelfUsername3;

    @FXML
    public void prova(){
        bookshelfUsername1.setText("ale");
        BookshelfUsername2.setText("pluto");
        BookshelfUsername3.setText("pippo");

    }

}

package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGameForDisconnectionController implements SceneController, Initializable {
    private final GUIController guiController = GUIControllerStatic.getGuiController();

    @FXML
    private Label winnerPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guiController.setSceneController(this);
        winnerPlayer();
    }

    private void winnerPlayer(){
        String winner = guiController.getState().getWinnerPlayer();
        winnerPlayer.setText(winner+" is the last player so...\nThe winner is: "+winner);
        winnerPlayer.setAlignment(Pos.CENTER);
    }
}

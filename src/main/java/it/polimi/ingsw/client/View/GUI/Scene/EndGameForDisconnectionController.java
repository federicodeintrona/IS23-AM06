package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class to manage the final scene (End Game For Disconnection scene)
 * <p>
 * Show who is the last player still connected to the game, this is the winner.
 */
public class EndGameForDisconnectionController implements SceneController, Initializable {

    /**
     * Attribute used to know the instance of GUIController.
     */
    private final GUIController guiController = GUIControllerStatic.getGuiController();


//GRAPHIC ELEMENTS OF THE SCENE
    /**
     * Label of the winner player.
     */
    @FXML
    private Label winnerPlayer;

    /**
     * Default constructor
     */
    public EndGameForDisconnectionController() {
    }

    /**
     * Method called to initialize the scene.
     *
     * @param url the URL used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set the current scene
        guiController.setSceneController(this);

        //print the winner player
        winnerPlayer();
    }



    /**
     * Method to print the username of the winner player.
     */
    private void winnerPlayer(){
        String winner = guiController.getState().getWinnerPlayer();
        winnerPlayer.setText(winner+" is the last player so...\nThe winner is: "+winner);
        winnerPlayer.setAlignment(Pos.CENTER);
    }
}

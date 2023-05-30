package it.polimi.ingsw.client.View.GUI.Scene;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public interface SceneController {
    default void showError(String error, Stage stage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
        alert.initOwner(stage);
        alert.showAndWait();
    };

}

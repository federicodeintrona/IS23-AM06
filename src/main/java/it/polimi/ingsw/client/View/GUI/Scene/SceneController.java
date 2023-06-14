package it.polimi.ingsw.client.View.GUI.Scene;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

//interfaccia delle scene
//mostra gli errori
public interface SceneController {
    default void showError(String error, Stage stage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.getDialogPane().setStyle( "-fx-font-weight: bold;" +
                                        "-fx-font-size: 18px;" +
                                        "-fx-font-style: italic;"+
                                        "-fx-text-fill: #070707;"+
                                        "-fx-background-color: #f70000;");
        alert.initOwner(stage);
        alert.showAndWait();
    };

}

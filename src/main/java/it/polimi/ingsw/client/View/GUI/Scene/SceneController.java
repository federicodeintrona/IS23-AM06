package it.polimi.ingsw.client.View.GUI.Scene;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

//interfaccia delle scene
//mostra gli errori

/**
 * Interface of common things in scene controller.
 * <ul>
 *     <li>Show error received from the server.</li>
 * </ul>
 */
public interface SceneController {

    /**
     * Default method that showing the error received from the server.
     *
     * @param error the error message.
     * @param stage the stage where to show it.
     */
    default void showError(String error, Stage stage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(error);
        //set style
        alert.getDialogPane().setStyle( "-fx-font-weight: bold;" +
                                        "-fx-font-size: 18px;" +
                                        "-fx-font-style: italic;"+
                                        "-fx-text-fill: #070707;"+
                                        "-fx-background-color: #f70000;");
        alert.initOwner(stage);
        alert.showAndWait();
    }

    default void showNotification(String notification, Stage stage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText("Notification");
        alert.setContentText(notification);
        //set style
        alert.getDialogPane().setStyle( "-fx-font-weight: bold;" +
                "-fx-font-size: 18px;" +
                "-fx-font-style: italic;"+
                "-fx-text-fill: #070707;"+
                "-fx-background-color: #f7d600;");
        alert.initOwner(stage);
        alert.showAndWait();

    }

}

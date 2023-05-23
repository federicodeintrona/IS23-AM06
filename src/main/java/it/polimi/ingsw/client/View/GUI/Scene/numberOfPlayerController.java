package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class numberOfPlayerController implements Initializable {

    private GUIController guiController = GUIControllerStatic.getGuiController();
    @FXML
    private Label firstUsernameLabel;
    @FXML
    private ChoiceBox<Integer> numberOfPlayerBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstUsernameLabel.setText(", you have to create a lobby");
        Integer[] choice={2, 3, 4};
        numberOfPlayerBox.getItems().addAll(choice);
        numberOfPlayerBox.setOnAction(this::setNumberOfPlayerBox);
    }

    public void setNumberOfPlayerBox(ActionEvent actionEvent){
        Integer choice=numberOfPlayerBox.getValue();
    }
}

package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javax.swing.plaf.basic.BasicButtonUI;
import java.net.URL;
import java.util.ResourceBundle;

public class numberOfPlayerController implements Initializable {

    private GUIController guiController = GUIControllerStatic.getGuiController();
    @FXML
    private Label firstUsernameLabel;
    @FXML
    private ChoiceBox<Integer> numberOfPlayerBox;
    @FXML
    private Button sendButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username=guiController.getState().getMyUsername();
        firstUsernameLabel.setText(username+", you must to create a lobby");
        Integer[] choice={2, 3, 4};
        numberOfPlayerBox.getItems().addAll(choice);
        numberOfPlayerBox.setOnAction(this::setNumberOfPlayerBox);
    }

    public void setNumberOfPlayerBox(ActionEvent actionEvent){
        Integer choice=numberOfPlayerBox.getValue();

        IntMessage message=new IntMessage();
        message.setUsername(guiController.getState().getMyUsername());
        message.setNum(choice);
        message.setType(MessageTypes.NUM_OF_PLAYERS);
        guiController.sendMessage(message);
    }

}

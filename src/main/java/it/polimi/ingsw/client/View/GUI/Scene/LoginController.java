package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginController {
    private final GUIController guiController = GUIControllerStatic.getGuiController();
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Label usernameStatus;



    @FXML
    private void initialize() {
        // Add an action for the "Open Layout2" button
        loginButton.setOnAction(this::loginClick);

    }



    @FXML
    public void loginClick(ActionEvent actionEvent) {
        String username=usernameField.getText();

        if (username.isEmpty()){
            usernameStatus.setText("Inserire username");
        }
        else {
            guiController.getState().setMyUsername(username);
            Message message=new Message();
            message.setUsername(username);
            message.setType(MessageTypes.USERNAME);
            guiController.sendMessage(message);
        }
    }
}

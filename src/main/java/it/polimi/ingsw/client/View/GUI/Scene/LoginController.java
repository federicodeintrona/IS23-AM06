package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements SceneController, Initializable {
    private final GUIController guiController = GUIControllerStatic.getGuiController();
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Label usernameStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guiController.setSceneController(this);

    }

    private void login(){
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
    @FXML
    private void loginClick(ActionEvent actionEvent) {
        login();
    }
    @FXML
    private void loginEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER) {
            login();
        }
    }

    @Override
    public void showError(String error, Stage stage) {
        usernameStatus.setText(error);
    }


}
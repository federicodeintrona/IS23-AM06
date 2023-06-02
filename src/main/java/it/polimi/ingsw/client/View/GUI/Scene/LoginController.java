package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

//controller della scena di login
public class LoginController implements SceneController, Initializable {
    private final GUIController guiController = GUIControllerStatic.getGuiController();

    @FXML
    private TextField usernameField; //username inserito

    //inizializzazione
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guiController.setSceneController(this);

    }

    //leggi l'username e invia il messaggio al server
    private void login(){
        String username=usernameField.getText();
        usernameField.clear();

        if (username.isEmpty()){
            showError("Insert username", guiController.getStage());
        }
        else {
            guiController.getState().setMyUsername(username);
            Message message=new Message();
            message.setUsername(username);
            message.setType(MessageTypes.USERNAME);
            guiController.sendMessage(message);
        }
    }
    //al click del bottone login
    @FXML
    private void loginClick(ActionEvent actionEvent) {
        login();
    }
    //quando premi invio invii l'username
    @FXML
    private void loginEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER) {
            login();
        }
    }

    //mostra gli errori - username già preso
    @Override
    public void showError(String error, Stage stage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
        alert.getDialogPane().setStyle( "-fx-font-weight: bold;" +
                                        "-fx-font-size: 18px;" +
                                        "-fx-font-style: italic;"+
                                        "-fx-text-fill: #070707;"+
                                        "-fx-background-color: #f70000;");

        alert.initOwner(stage);
        alert.showAndWait();
    }


}

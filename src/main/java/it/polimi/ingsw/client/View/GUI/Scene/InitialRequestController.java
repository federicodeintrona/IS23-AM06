package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.application.Application;


import java.net.URL;
import java.util.ResourceBundle;


public class InitialRequestController implements SceneController, Initializable {
    private final GUIController guiController = GUIControllerStatic.getGuiController();

    @FXML
    private Button RMIButton;
    @FXML
    private Button TCPButton;
    @FXML
    private TextField hostField;
    @FXML
    private Button hostButton;
    @FXML
    private Label requestLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guiController.setSceneController(this);

        requestLabel.setText("Which connection protocol do you choose?");
        hostField.setPromptText("127.0.0.1");
        hostField.setText("username");

    }

    @FXML
    private void RMIClick(ActionEvent event){
        /*
            TODO
             è stato scelto RMI
         */
        showHost();
    }

    private void showHost(){
        RMIButton.setDisable(true);
        RMIButton.setVisible(false);

        TCPButton.setDisable(true);
        TCPButton.setVisible(false);

        hostField.setDisable(false);
        hostField.setVisible(true);
        hostField.setText("Which host do you use?");

        hostButton.setDisable(false);
        hostButton.setVisible(true);
    }

    @FXML
    private void TCPClick(ActionEvent event){
        /*
            TODO
             è stato scelto TCP
         */
        showHost();
    }

    private void chooseHost(){
        String host=hostField.getText();
        hostField.clear();

        if (host.isEmpty()){
            showError("Insert host", guiController.getStage());
        }
        else {
            //TODO manda l'host
        }

    }
    @FXML
    private void hostEnter(KeyEvent event){
        chooseHost();
    }
    @FXML
    private void hostClick(ActionEvent event){
        chooseHost();
    }



}

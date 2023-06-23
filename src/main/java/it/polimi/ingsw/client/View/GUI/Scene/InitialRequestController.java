package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.NetworkerRmi;
import it.polimi.ingsw.client.NetworkerTcp;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
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
import javafx.application.Application;


import java.net.URL;
import java.util.ResourceBundle;


public class InitialRequestController implements SceneController, Initializable {
    private final GUIController guiController = GUIControllerStatic.getGuiController();

    private String connectionType;

    @FXML
    private Button RMIButton;
    @FXML
    private Button TCPButton;
    @FXML
    private TextField hostField;
    @FXML
    private Button hostButton;
    @FXML
    private Label requestLabel1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        requestLabel1.setText("Which connection protocol do you choose?");
        guiController.setSceneController(this);

        hostField.setPromptText("127.0.0.1");


    }

    @FXML
    private void RMIClick(ActionEvent event){
        connectionType="RMI";

        showHost();
    }

    private void showHost(){
        requestLabel1.setText("Which server IP do you use?");
        RMIButton.setDisable(true);
        RMIButton.setVisible(false);

        TCPButton.setDisable(true);
        TCPButton.setVisible(false);

        hostField.setDisable(false);
        hostField.setVisible(true);

        hostButton.setDisable(false);
        hostButton.setVisible(true);
    }

    @FXML
    private void TCPClick(ActionEvent event){
        connectionType="TCP";


        showHost();
    }

    private void chooseHost(){
        String host=hostField.getText();
        if(host.isEmpty()) {
            host = "localhost";
        }

        hostField.clear();

//        guiController.selectNetworker(connectionType,host);

        Networker networker;
        if(connectionType.equals("RMI")){
            networker = new NetworkerRmi(guiController.getState(),host);
        }else{
            networker = new NetworkerTcp(guiController.getState(),host);
        }

        boolean connected=networker.initializeConnection();
        if (!connected){
            showError("Failed to connect with server\nTry again", guiController.getStage());
        }
        else {
            networker.setView(guiController);
            guiController.setNetworker(networker);
            guiController.changeScene(Scenes.Login);
        }

    }

    @FXML
    private void hostEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER) {
            chooseHost();
        }
    }
    @FXML
    private void hostClick(ActionEvent event){
        chooseHost();
    }



}

package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIFactory;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;



public class LoginController {
    private GUIController guiController = GUIFactory.getGuiController();
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Label usernameStatus;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setGuiController(GUIController guiController) {
        this.guiController = guiController;
    }


    @FXML
    private void initialize() {
        // Add an action for the "Open Layout2" button
        loginButton.setOnAction(this::loginClick);

    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    @FXML
    public void loginClick(ActionEvent actionEvent) {
        String username=usernameField.getText();

        if (username.isEmpty()){
            usernameStatus.setText("Inserire username");
        }
        else {
            Message message=new Message();
            message.setUsername(username);
            message.setType(MessageTypes.USERNAME);
            guiController.sendMessage(message);
        }
    }
}

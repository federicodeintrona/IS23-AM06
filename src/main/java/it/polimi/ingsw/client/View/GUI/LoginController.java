package it.polimi.ingsw.client.View.GUI;

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
    private static GUIMain guiMain;

    public static GUIMain getGuiMain() {
        return guiMain;
    }

    public static void setGuiMain(GUIMain guiMain) {
        LoginController.guiMain = guiMain;
    }

    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Label usernameStatus;

    private Stage stage;
    private Scene scene;
    private Parent root;

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
    public void loginClick(ActionEvent actionEvent) throws IOException {
        String username=usernameField.getText();

        if (username.isEmpty()){
            usernameStatus.setText("Inserire username");
        }
        else if (guiMain.getClientState().isUsername()){
            usernameStatus.setText("Username Corretto");
            Parent root= FXMLLoader.load(getClass().getResource("/fxml/numberOfPlayer.fxml"));
            stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene=new Scene(root);

            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setTitle("Waiting page");
            stage.show();
        }
        else {
            usernameStatus.setText("Username NON Corretto");
        }
    }
}

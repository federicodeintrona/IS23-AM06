package it.polimi.ingsw.client.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Label usernameStatus;

    @FXML
    public void loginClick(ActionEvent actionEvent) {
        String username=usernameField.getText();

        if (username.compareTo("ale")==0){
            usernameStatus.setText("username va bene");
        }
        else {
            usernameStatus.setText("username NON va bene");
        }
    }
}

package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


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

        /*
            if username.lenght()==0
                insert username
            if username corretto
                login
            if username sbagliato
                Username already taken
         */
        if (username.length()==0){
            usernameStatus.setText("Inserire username");
        }
        else if (username.compareTo("ale")==0){
            usernameStatus.setText("Username Corretto");
        }
        else {
            usernameStatus.setText("Username NON Corretto");
        }




    }
}

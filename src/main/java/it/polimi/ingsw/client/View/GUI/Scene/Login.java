package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.app.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Login {


    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader(Login.class.getResource("/fxml/loginGriglia.fxml"));
//        LoginController loginController=new LoginController();


        Parent root=FXMLLoader.load(getClass().getResource("/fxml/loginGriglia.fxml"));
        Scene scene=new Scene(root);


        stage.setFullScreen(true);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();


    }


}

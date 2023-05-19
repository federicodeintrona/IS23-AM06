package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.app.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader(Login.class.getResource("/fxml/loginGriglia.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
//        stage.setWidth(1920);
//        stage.setHeight(1080);
        stage.setFullScreen(true);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}

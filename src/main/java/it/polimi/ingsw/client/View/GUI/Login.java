package it.polimi.ingsw.client.View.GUI;

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

public class Login extends Application {
    private static GUIMain guiMain;

    public static GUIMain getGuiMain() {
        return guiMain;
    }

    public static void setGuiMain(GUIMain guiMain) {
        Login.guiMain = guiMain;
    }

    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader=new FXMLLoader(Login.class.getResource("/fxml/loginGriglia.fxml"));
//        LoginController loginController=new LoginController();

        Parent root=FXMLLoader.load(getClass().getResource("/fxml/loginGriglia.fxml"));
        Scene scene=new Scene(root);


        stage.setFullScreen(true);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        LoginController.setGuiMain(guiMain);
        launch();
    }
}

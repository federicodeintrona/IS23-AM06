package it.polimi.ingsw.client.View.GUI.prova;

import it.polimi.ingsw.client.View.GUI.Scene.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try{
            FXMLLoader fxmlLoader=new FXMLLoader(Login.class.getResource("/fxml/prova/Main.fxml"));
            Parent root=fxmlLoader.load();

            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.client.View.GUI.Scene.Scenes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.Scanner;

public class ClientBase extends Application{

    public static void main( String[] args ) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Which User Interface do you choose? (CLI/GUI): ");
            String decision = scanner.nextLine();
            if (decision.equalsIgnoreCase("CLI")) {
                CLIMain cli = new CLIMain();
                cli.runUI();
            } else {
                launch();
            }
        } catch (RemoteException e) {
                e.printStackTrace();
        }


    }


    @Override
    public void start(Stage stage) throws Exception {
        try {

            ClientState clientState = new ClientState(new Object());
            GUIControllerStatic.setGuiController(new GUIController(clientState));


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Scenes.InitialRequest.getName())));
            Scene scene=new Scene(root);

            GUIController guiController= GUIControllerStatic.getGuiController();
            guiController.setStage(stage);
            guiController.setRoot(root);
            guiController.setScene(scene);

            //aggiunta icona
            InputStream s=getClass().getResourceAsStream("/images/Publisher_material/Icon 50x50px.png");
            stage.getIcons().add(new Image(s));

            stage.setTitle(Scenes.InitialRequest.getTitle());
            stage.setScene(scene);
//            stage.setFullScreen(true);
//            stage.setFullScreenExitHint(""); //TODO NON esce più la scritta per uscire fai exit - NON funziona più :(
            stage.setWidth(1300);
            stage.setHeight(750);
            stage.setResizable(false);
//            stage.toFront();
            stage.show();

            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });
        } catch (IOException e) {
            System.out.println("GUI failed to launch...");
            e.printStackTrace();
        }
    }

    public void logout(Stage stage){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Are you sure you want logout?");

        if (alert.showAndWait().get()== ButtonType.OK){
            System.out.println("You successfully logged out");
            stage.close();
        }
    }
}

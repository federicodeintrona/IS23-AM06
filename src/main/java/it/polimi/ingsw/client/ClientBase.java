package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.client.View.GUI.Scene.Scenes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
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


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Scenes.Login.getName())));
            Scene scene=new Scene(root);

            GUIController guiController= GUIControllerStatic.getGuiController();
            guiController.setStage(stage);
            guiController.setRoot(root);
            guiController.setScene(scene);

            stage.setTitle(Scenes.Login.getTitle());
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint(""); //TODO NON esce più la scritta per uscire fai exit - NON funziona più :(
            stage.show();
        } catch (IOException e) {
            System.out.println("GUI failed to launch...");
            e.printStackTrace();
        }
    }



}

package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.client.View.GUI.Scene.Scenes;
import it.polimi.ingsw.client.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientBase extends Application{

    public static void main( String[] args ) {
        try {
            Scanner scanner = new Scanner(System.in);
            Object lock = new Object();
            ClientState state = new ClientState(lock);

            System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
            String decision = scanner.nextLine();
            decision=decision.toUpperCase();

            System.out.println("Which host do you use?");
            String host = scanner.nextLine();

            if (host == null) {
                System.out.printf("You selected the default host: localhost");
                host = "localhost";
            }
            Networker networker;

            if (decision.equalsIgnoreCase("RMI")) {
                  networker = new NetworkerRmi(state,host);
            }else networker = new NetworkerTcp(state,host);


            System.out.print("Which User Interface do you choose? (CLI/GUI): ");
            decision = scanner.nextLine();
            decision=decision.toUpperCase();
            if (decision.equalsIgnoreCase("CLI")) {
                CLIMain cli = new CLIMain(lock, state, networker);
                networker.setView(cli);
                networker.initializeConnection();
                cli.runUI();
            } else {
                GUIControllerStatic.setGuiController(new GUIController(networker, state));
                networker.setView(GUIControllerStatic.getGuiController());
                networker.initializeConnection();
                launch();

            }
        } catch (RemoteException e) {
                e.printStackTrace();
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Scenes.Login.getName()));
            Scene scene=new Scene(root);

            GUIController guiController= GUIControllerStatic.getGuiController();
            guiController.setStage(stage);
            guiController.setRoot(root);
            guiController.setScene(scene);

            stage.setTitle(Scenes.Login.getTitle());
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

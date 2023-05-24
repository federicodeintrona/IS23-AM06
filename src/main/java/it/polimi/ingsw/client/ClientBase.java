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
import java.util.Scanner;

public class ClientBase extends Application{


private static GUIController contr;
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        String decision ;
        System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
        decision = scanner.nextLine();
        decision=decision.toUpperCase();
        Object lock = new Object();
        ClientState state;

        try {
            state = new ClientState(lock);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Which host do you use?");
        String host = scanner.nextLine();
        Networker networker = switch (decision.toUpperCase()) {
            case "RMI" -> new NetworkerRmi(state,host);
            case "TCP" -> new NetworkerTcp(state,host);
            default -> new NetworkerTcp(state,host);
        };
        networker.initializeConnection();
        System.out.print("Which User Interface do you choose? (CLI/GUI): ");
        decision = scanner.nextLine();
        switch (decision.toUpperCase()){
            case "CLI" -> {
                CLIMain cli = new CLIMain(lock, state, networker);
                networker.setView(cli);
                cli.runUI();
            }
            case "GUI" -> {
                GUIControllerStatic.setGuiController(new GUIController(networker,state));
                networker.setView(GUIControllerStatic.getGuiController());
                launch();
            }
            default -> {
                CLIMain cli = new CLIMain(lock, state, networker);
                networker.setView(cli);
                networker.initializeConnection();
                cli.runUI();
            }
        }



    }


    @Override
    public void start(Stage stage) throws Exception {

        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource(Scenes.Login.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene=new Scene(root);

        GUIController guiController= GUIControllerStatic.getGuiController();
        guiController.setStage(stage);
        guiController.setRoot(root);
        guiController.setScene(scene);

        stage.setFullScreen(true);
        stage.setTitle(Scenes.Login.getTitle());
        stage.setScene(scene);
        stage.show();
    }
}

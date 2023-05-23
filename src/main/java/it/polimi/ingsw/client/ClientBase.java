package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIFactory;
import it.polimi.ingsw.client.View.GUI.GUIMain;
import it.polimi.ingsw.client.View.GUI.Scene.Login;
import it.polimi.ingsw.client.View.GUI.Scene.LoginController;
import it.polimi.ingsw.client.View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientBase extends Application{

    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        String decision = null;

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

        Networker networker = switch (decision) {
            case "RMI" -> new NetworkerRmi(state);
            case "TCP" -> new NetworkerTcp();
            default -> null;
        };

        System.out.print("Which User Interface do you choose? (CLI/GUI): ");
        decision = scanner.nextLine();
        decision=decision.toUpperCase();
        switch (decision){
            case "CLI" -> {
                CLIMain cli = new CLIMain(lock, state, networker);
                networker.setView(cli);
                networker.initializeConnection();
                cli.runUI();
            }
            case "GUI" -> {
                GUIController guiController=new GUIController(networker);
                GUIFactory.setGuiController(guiController);
                networker.setView(guiController);
                launch();
            }
        }

    }


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader=new FXMLLoader(Login.class.getResource("/fxml/loginGriglia.fxml"));
        fxmlLoader.setController(new LoginController());
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/loginGriglia.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene=new Scene(root);

        stage.setFullScreen(true);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }
}

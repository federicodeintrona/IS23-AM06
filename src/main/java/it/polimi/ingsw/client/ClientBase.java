package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.client.View.GUI.Scene.Scenes;
import javafx.application.Application;
import javafx.application.Platform;
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

/**
 *  <p>Class to manage the Client.</p>
 *
 * <p>The client manages: </p>
 * <ul>
 *     <li>the choice of which User Interface you want to play in (CLI or GUI), if you do not choose which one you prefer the GUI starts automatically;</li>
 *     <li>the logout from the User Interface.</li>
 * </ul>
 *
 */
public class ClientBase extends Application{

    /**
     * Default constructor.
     */
    public ClientBase() {
    }

    /**
     * Static method to launch the Client.
     *
     * @param args the arguments that you insert on input.
     */
    public static void main( String[] args ) {
        try {
            //choice of which UI you want to play in
            Scanner scanner = new Scanner(System.in);
            System.out.print("Which User Interface do you choose? (CLI/GUI): ");
            String decision = scanner.nextLine();

            //if you choose CLI
            if (decision.equalsIgnoreCase("CLI")) {
                CLIMain cli = new CLIMain();
                cli.runUI();
            }
            //if you choose GUI or
            //if you do not choose any UI
            else {
                launch();
            }
        }
        catch (RemoteException e) {
            System.out.println("The game has failed to start properly. Retry again.");
            System.exit(1);
        }
    }



    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned, and after the system is ready for the application to begin running.
     *
     * @param stage the stage for this application, onto which the application scene can be set.
     * @throws Exception Constructs a new exception with null as its detail message.
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            //instance of ClientState and GUIControllerStatic
            ClientState clientState = new ClientState(new Object());
            GUIControllerStatic.setGuiController(new GUIController(clientState));

            //show the InitialRequest's scene
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Scenes.InitialRequest.getPath())));
            Scene scene=new Scene(root);

            //set all the information in scene's controller
            GUIController guiController= GUIControllerStatic.getGuiController();
            guiController.setStage(stage);
            guiController.setRoot(root);
            guiController.setScene(scene);

            //set Icon
            InputStream s=getClass().getResourceAsStream("/images/Publisher_material/Icon 50x50px.png");
            assert s != null;
            stage.getIcons().add(new Image(s));

            //set title
            stage.setTitle(Scenes.InitialRequest.getTitle());

            //set scene's dimensione
            stage.setScene(scene);
            stage.setWidth(1300);
            stage.setHeight(750);
            stage.setResizable(false);

            //show the setting scene
            stage.show();

            //set logout action
            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });
        }
        catch (RemoteException e){
            System.out.println("The game has failed to start properly. Retry again.");
            System.exit(1);

        }
        catch (IOException e) {
            System.out.println("GUI failed to launch...");
            System.exit(1);
        }
    }

    /**
     * Method to log out from the Graphic User Interface.
     *
     * @param stage the stage for this application.
     */
    public void logout(Stage stage){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);

        //set alert information
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Are you sure you want logout?");

        //if you want to logged out
        if (alert.showAndWait().get()== ButtonType.OK){
            System.out.println("You successfully logged out");
            stage.close();
            Platform.exit();
            GUIController guicontroller= GUIControllerStatic.getGuiController();
            guicontroller.close();
        }
    }

}

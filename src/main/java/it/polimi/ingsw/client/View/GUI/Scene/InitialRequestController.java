package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.NetworkerRmi;
import it.polimi.ingsw.client.NetworkerTcp;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class to manage the initial request scene.
 * <ul>
 *     <li>Choice of connection protocol;</li>
 *     <li>choice the server IP.</li>
 * </ul>
 */
public class InitialRequestController implements SceneController, Initializable {

    /**
     * Attribute used to know the instance of GUIController.
     */
    private final GUIController guiController = GUIControllerStatic.getGuiController();
    /**
     * Attribute used to save the connection protocol choice.
     */
    private String connectionType;


//GRAPHIC ELEMENTS OF THE SCENE
    /**
     * Button that select the RMI connection.
     */
    @FXML
    private Button RMIButton;
    /**
     * Button that select the TCP connection.
     */
    @FXML
    private Button TCPButton;
    /**
     * TextField used to write the server IP.
     */
    @FXML
    private TextField hostField;
    /**
     * Button to send the hostField.
     */
    @FXML
    private Button hostButton;
    /**
     * Label used to request the connection and server IP.
     */
    @FXML
    private Label requestLabel1;

    /**
     * Default constructor
     */
    public InitialRequestController() {
    }


    //INITIALIZE
    /**
     * Method called to initialize the scene.
     *
     * @param url the URL used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Which connection protocol do you choose?
        requestLabel1.setText("Which connection protocol do you choose?");
        guiController.setSceneController(this);

        hostField.setPromptText("127.0.0.1");
    }



//ACTION
    /**
     * Method that manages the selection of the RMI protocol.
     */
    @FXML
    private void RMIClick(){
        connectionType="RMI";

        //show the request of server IP
        showServerIP();
    }

    /**
     * Method that manages the selection of the TCP protocol.
     */
    @FXML
    private void TCPClick(){
        connectionType="TCP";

        //show the request of server IP
        showServerIP();
    }


    /**
     * Method that manages the selection of server IP (clicked the button).
     */
    @FXML
    private void serverIPClick(){
        choseServerIP();
    }

    /**
     * Method that manages the selection of Server IP (enter key pressed).
     *
     * @param event the event, enter key has been pressed.
     */
    @FXML
    private void serverIPEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER) {
            choseServerIP();
        }
    }



//ANCILLARY METHODS
    /**
     * Method to show the request of server IP.
     */
    private void showServerIP(){
        requestLabel1.setText("Which server IP do you use?");
        RMIButton.setDisable(true);
        RMIButton.setVisible(false);

        TCPButton.setDisable(true);
        TCPButton.setVisible(false);

        hostField.setDisable(false);
        hostField.setVisible(true);

        hostButton.setDisable(false);
        hostButton.setVisible(true);
    }

    /**
     * Method that create the connection with the server.
     */
    private void choseServerIP(){
        //catch the server IP
        String host=hostField.getText();
        if(host.isEmpty()) {
            host = "localhost";
        }
        hostField.clear();

        //create the Networker
        Networker networker;
        if(connectionType.equals("RMI")){
            networker = new NetworkerRmi(guiController.getState(),host);
        }else{
            networker = new NetworkerTcp(guiController.getState(),host);
        }

        //create the connection with the server
        boolean connected=networker.initializeConnection();
        //connection error
        if (!connected){
            showError("Failed to connect with server\nTry again", guiController.getStage());
        }
        else {
            networker.setView(guiController);
            guiController.setNetworker(networker);
            guiController.changeScene(Scenes.Login);
        }

    }

}
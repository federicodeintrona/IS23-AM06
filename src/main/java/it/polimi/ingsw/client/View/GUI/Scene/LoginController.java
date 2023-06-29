package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class to manage the login scene.
 * <ul>
 *     <li>Insert the username.</li>
 * </ul>
 */
public class LoginController implements SceneController, Initializable {

    /**
     * Attribute used to know the instance of GUIController.
     */
    private final GUIController guiController = GUIControllerStatic.getGuiController();


//GRAPHIC ELEMENTS OF THE SCENE
    /**
     * TextField used to write the username
     */
    @FXML
    private TextField usernameField;


    /**
     * Default constructor
     */
    public LoginController() {
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
        guiController.setSceneController(this);

        usernameField.setPromptText("username");
    }



//ACTION
    /**
     * Method that manages the login's button click.
     */
    @FXML
    private void loginClick() {
        login();
    }

    /**
     * Method that manages the enter key pressed.
     *
      * @param event the event, enter key pressed.
     */
    @FXML
    private void loginEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER) {
            login();
        }
    }



//ANCILLARY METHODS
    /**
     * Method that read the username entered and send it to the server.
     */
    private void login(){
        //catch the username
        String username=usernameField.getText();
        usernameField.clear();

        //if no username has been entered
        if (username.isEmpty()){
            showError("Insert username", guiController.getStage());
        }
        //create the message to send to the server and send it out
        else {
            //set the client's username
            guiController.getState().setMyUsername(username.toLowerCase());
            //create the message to send to the server
            Message message=new Message();
            //set the message
            message.setText(username);
            message.setType(MessageTypes.USERNAME);
            //send the message to the server
            guiController.sendMessage(message);
        }
    }




}

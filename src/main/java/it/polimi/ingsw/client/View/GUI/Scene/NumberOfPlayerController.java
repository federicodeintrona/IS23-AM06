package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that manages the number of player scene.
 * <ul>
 *     <li>Insert the number of players you want to play with.</li>
 * </ul>
 */
public class NumberOfPlayerController implements Initializable,SceneController {

    /**
     * Attribute used to know the instance of GUIController.
     */
    private final GUIController guiController = GUIControllerStatic.getGuiController();


//GRAPHIC ELEMENTS OF THE SCENE
    /**
     * Label used to request to enter the number of players because it is the first players.
     */
    @FXML
    private Label firstUsernameLabel;
    /**
     * ChoiceBox used to show the number of players you can play with.
     */
    @FXML
    private ChoiceBox<Integer> numberOfPlayerBox;


    /**
     * Default constructor
     */
    public NumberOfPlayerController() {
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
        String username=guiController.getState().getMyUsername();
        guiController.setSceneController(this);

        //set the request
        firstUsernameLabel.setText(username+", you must to create a lobby");

        //initialize the ChoiceBox
        Integer[] choice={2, 3, 4};
        //set default value
        numberOfPlayerBox.setValue(choice[0]);
        numberOfPlayerBox.getItems().addAll(choice);
        numberOfPlayerBox.setOnAction(this::setNumberOfPlayerBoxClick);
    }



//ACTION
    /**
     * Method that manage the selection number of player.
     * <p>
     * Selection from ChoiceBox.
     *
     * @param actionEvent the event, ChoiceBox close click.
     */
    public void setNumberOfPlayerBoxClick(ActionEvent actionEvent){
        setNumberOfPlayerBox();
    }

    /**
     * Method that manage the selection number of player.
     * <p>
     * Clicked the enter button.
     */
    public void setNumberOfPlayerEnterButtonClick(){
        setNumberOfPlayerBox();
    }

    /**
     * Method that manage the selection number of player.
     *
     * @param event the event, enter key pressed.
     */
    public void setNumberOfPlayerBoxEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER){
            setNumberOfPlayerBox();
        }
    }



//ANCILLARY METHODS
    /**
     * Method that create the number of player's message to send to the server.
     */
    private void setNumberOfPlayerBox(){
        //catch number of player
        Integer choice=numberOfPlayerBox.getValue();

        //create IntMessage
        IntMessage message=new IntMessage();
        //set message
        message.setText(guiController.getState().getMyUsername());
        message.setNum(choice);
        message.setType(MessageTypes.NUM_OF_PLAYERS);
        //send message
        guiController.sendMessage(message);
    }

}

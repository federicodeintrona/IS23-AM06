package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.GUI.Scene.*;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * Class to manage the GUI
 * <ul>
 *     <li>Start the GUI;</li>
 *     <li>close the GUI;</li>
 *     <li>change GUI's scene;</li>
 *     <li>send message to the server;</li>
 *     <li>received message from the server.</li>
 * </ul>
 */
public class GUIController implements View, SceneController {

    /**
     * Attribute that instance the stage.
     */
    private  Stage stage;
    /**
     * Attribute that instance the current scene.
     */
    private  Scene scene;
    /**
     * Attribute that instance the root.
     */
    private  Parent root;

    /**
     * Attribute that instantiates the correct Networker to send and receive messages from.
     */
    private  Networker networker;
    /**
     * Attribute that instance the correct ClientState.
     */
    private final ClientState state;
    /**
     * Attribute used to know which scene controller is currently instantiated.
     */
    private SceneController sceneController;



    /**
     * Initialize the ClientState and adds itself as a listener to receive notifications from the client state
     *
     * @param state the reference ClientState.
     */
    public GUIController(ClientState state) {
        this.state=state;
        state.addListener(this,"start");
        state.addListener(this,"end");
        state.addListener(this,"notification");
    }



    /**
     * <strong>Getter</strong> -> Returns the ClientState.
     *
     * @return the ClientState.
     */
    public ClientState getState() {
        return state;
    }

    /**
     * <strong>Getter</strong> -> Returns the Stage.
     *
     * @return the Stage.
     */
    public Stage getStage() {
        return stage;
    }



    /**
     * <strong>Setter</strong> -> Sets the current SceneController.
     *
     * @param sceneController the current SceneController.
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /**
     * <strong>Setter</strong> -> Sets the Stage where to show the Scene.
     *
     * @param stage the Stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * <strong>Setter</strong> -> Sets the current Scene shown.
     *
     * @param scene the current Scene shown.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * <strong>Setter</strong> -> Sets the Root.
     *
     * @param root the Root.
     */
    public void setRoot(Parent root) {
        this.root = root;
    }

    /**
     * <strong>Setter</strong> -> Sets the Networker (RMI or TCP) to which to send messages and from which to receive server messages.
     *
     * @param networker the correct Networker (RMI or TCP).
     */
    public void setNetworker(Networker networker) {
        this.networker = networker;
    }



    /**
     * Method to send the message to the server.
     *
     * @param message the message to send to the server.
     */
    public void sendMessage(Message message){
        switch (message.getType()){
            case REMOVE_FROM_BOARD -> networker.removeTilesFromBoard(message);
            case SWITCH_PLACE -> networker.switchTilesOrder(message);
            case ADD_TO_BOOKSHELF -> networker.addTilesToBookshelf(message);
            case USERNAME -> networker.firstConnection(message);
            case NUM_OF_PLAYERS -> networker.numberOfPlayersSelection(message);
            case CHAT -> networker.chat(message);
        }
    }

    /**
     * Method to receive the message from the server.
     *
     * @param message that received from the server.
     */
    @Override
    public void receivedMessage(Message message) {
        switch (message.getType()){
            case NEW_LOBBY ->  changeScene(Scenes.NumOfPlayers);
            case WAITING_FOR_PLAYERS -> {
                if(state.gameHasStarted()) changeScene(Scenes.Waiting);
            }
            case ERROR -> showError(message);
        }
    }

    /**
     * Method for managing scene change.
     *
     * @param scene to be shown now.
     */
    public void changeScene(Scenes scene){
        Platform.runLater(()->{
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(scene.getPath())));
            } catch (IOException e) {
                System.out.println("Create client error");
                close();
            }
            this.scene.setRoot(root);
            stage.setTitle(scene.getTitle());
        });
    }

    /**
     * Method to close the Graphic User Interface.
     */
    @Override
    public void close() {
        Message msg = new Message();
        msg.setType(MessageTypes.DISCONNECT);
        if(networker!=null) networker.closeProgram(msg);
        else System.exit(0);
    }

    /**
     * Method to show all the error received from server.
     *
     * @param message the error message received from the server.
     */
    private void showError(Message message){
        Platform.runLater(()-> sceneController.showError(message.getText(),stage));
    }


    /**
     * Receives notification from the client state to pass on to the fxml controllers.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case "start" ->
                    changeScene(Scenes.Game);
            case "notification" ->{
                String username = (String)evt.getSource() ;
                String message = evt.getNewValue().equals("disconnection")?
                                                (username+" has disconnected"):
                                                (username+" has reconnected");

                Platform.runLater(()-> sceneController.showNotification(message,stage));
            }
            case "end" -> {
                if(state.isDisconnectionWinner()){
                    changeScene(Scenes.DisconnectionEnd);
                }
                else{
                    changeScene(Scenes.Endgame);
                }
                networker.stopTimer();
            }
            default -> System.out.println("Unexpected value: " + evt.getPropertyName());
        }
    }

}

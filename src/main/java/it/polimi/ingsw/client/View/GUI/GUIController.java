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
 *     <li>start the GUI</li>
 *     <li>close the GUI</li>
 *     <li>change GUI's scene</li>
 *     <li>send message to the server</li>
 *     <li>received message from the server</li>
 * </ul>
 */
public class GUIController implements View, SceneController {
    private  Stage stage;
    private  Scene scene;
    private  Parent root;
    private  Networker networker;
    private final ClientState state;
    private SceneController sceneController;



    //TODO and... addListener FLA
    /**
     * Initialize the ClientState and
     *
     * @param state the reference ClientState
     */
    public GUIController(ClientState state) {
        this.state=state;
        state.addListener(this,"start");
        state.addListener(this,"end");
    }


    /**
     * <strong>Getter</strong> -> Returns the ClientState
     *
     * @return the ClientState
     */
    public ClientState getState() {
        return state;
    }

    /**
     * <strong>Getter</strong> -> Returns the Stage
     *
     * @return the Stage
     */
    public Stage getStage() {
        return stage;
    }


    /**
     * <strong>Setter</strong> -> Sets the current SceneController
     *
     * @param sceneController the current SceneController
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /**
     * <strong>Setter</strong> -> Sets the Stage where to show the Scene
     *
     * @param stage the Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * <strong>Setter</strong> -> Sets the current Scene shown
     *
     * @param scene the current Scene shown
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * <strong>Setter</strong> -> Sets the Root
     *
     * @param root the Root
     */
    public void setRoot(Parent root) {
        this.root = root;
    }

    /**
     * <strong>Setter</strong> -> Sets the Networker (RMI or TCP) to which to send messages and from which to receive server messages
     *
     * @param networker the correct Networker (RMI or TCP)
     */
    public void setNetworker(Networker networker) {
        this.networker = networker;
    }



    //TODO finire javadoc
    //invia i messaggi dal client al server
    //client -> networker -> server
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


    //riceve i messaggi dal server
    //server -> networker -> client
    @Override
    public void receivedMessage(Message message) {
        switch (message.getType()){
            case NEW_LOBBY ->  changeScene(Scenes.NumOfPlayers);
            case WAITING_FOR_PLAYERS -> {
                if(!state.gameHasStarted()) changeScene(Scenes.Waiting);
            }
            case ERROR -> showError(message);
        }
    }

    @Override
    public void close() {
        Message msg = new Message();
        msg.setType(MessageTypes.DISCONNECT);
        if(networker!=null) networker.closeProgram(msg);
        else System.exit(0);
    }

    //mostra gli errori che arrivano dal server
    private void showError(Message message){
        Platform.runLater(()-> sceneController.showError(message.getText(),stage));
    }

    //Qui arrivano le notifiche dal client state
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case "start" -> changeScene(Scenes.Game);
            case "end" -> {
                if(state.isDisconnectionWinner()){
                    changeScene(Scenes.DisconnectionEnd);
                } else changeScene(Scenes.Endgame);

            }
            default -> throw new IllegalStateException("Unexpected value: " + evt.getPropertyName());
        }
    }


    //si occupa del cambiamento di scena
    public void changeScene(Scenes scenes){
        Platform.runLater(()->{
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(scenes.getName())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.scene.setRoot(root);
            stage.setTitle(scenes.getTitle());
        });
    }




}

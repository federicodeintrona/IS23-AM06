package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.GUI.Scene.*;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.Message;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Objects;

public class GUIController implements View {
    private  Stage stage;
    private  Scene scene;
    private  Parent root;
    private  final Networker networker;
    private final ClientState state;
    private SceneController sceneController;


    public ClientState getState() {
        return state;
    }

    public GUIController(Networker networker, ClientState state) {
        this.networker = networker;
        this.state=state;
        state.addListener(this,"start");
        state.addListener(this,"end");
    }


    //invia i messaggi dal client al server
    //client -> networker -> server
    public void sendMessage(Message message){
        switch (message.getType()){
            case REMOVE_FROM_BOARD -> networker.removeTilesFromBoard(message);
            case SWITCH_PLACE -> networker.switchTilesOrder(message);
            case ADD_TO_BOOKSHELF -> networker.addTilesToBookshelf(message);
            case USERNAME -> networker.firstConnection(message);
            case NUM_OF_PLAYERS -> networker.numberOfPlayersSelection(message);
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
        //TODO chiudere la GUI
    }

    //mostra gli errori che arrivano dal server
    private void showError(Message message){
        Platform.runLater(()->{
            sceneController.showError(message.getUsername(),stage);
        });
    }

    //Qui arrivano le notifiche dal client state
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case "start" ->{
                changeScene(Scenes.Game);;
            }
            case "end" -> {
                changeScene(Scenes.Endgame);
            }
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

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Stage getStage() {
        return stage;
    }

    public Parent getRoot() {
        return root;
    }
}

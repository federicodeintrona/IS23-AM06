package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.ClientBase;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.GUI.Scene.LoginController;
import it.polimi.ingsw.client.View.GUI.Scene.SceneController;
import it.polimi.ingsw.client.View.GUI.Scene.Scenes;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Tiles;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
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
        state.addListener(this);
    }


    public void sendMessage(Message message){
        switch (message.getType()){
            case REMOVE_FROM_BOARD -> networker.removeTilesFromBoard(message);
            case SWITCH_PLACE -> networker.switchTilesOrder(message);
            case ADD_TO_BOOKSHELF -> networker.addTilesToBookshelf(message);
            case USERNAME -> networker.firstConnection(message);
            case NUM_OF_PLAYERS -> networker.numberOfPlayersSelection(message);
        }
    }





    //Qui arrivano le notifiche dal client state
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case "start" ->{
                changeScene(Scenes.Game);;
            }
            //Bisogna fare tutti i casi
            case "bookshelf" ->{
                updateBookshelf((String)evt.getNewValue(),(Matrix) evt.getSource());
            }
            case "end" -> {
                changeScene(Scenes.Endgame);
            }
        }
    }


    private void updateBookshelf(String username,Matrix matrix) {
    }

    private void moveToEndScene() {
    }

    private void moveToGameScene() {
    }




    //Per ora ho copiato quello della cli per avere un punto di partenza, tanto i messagi che arrivano sono gli stessi

    @Override
    public void receivedMessage(Message message) {
        switch (message.getType()){
            case NEW_LOBBY ->  changeScene(Scenes.NumOfPlayers);
            case WAITING_FOR_PLAYERS -> {
                if(!state.gameHasStarted()) changeScene(Scenes.Waiting);
            }
            case ERROR -> {

                sceneController.showError(message.getUsername());

            }
            case OK -> {
                /*
                if (message.getUsername().equals("Move successful remove tiles")||
                        message.getUsername().equals("Move successful swap order")){
                     printOrderTiles(state.getSelectedTiles());
                }*/
            }
        }
    }

    private void showError(String error) {
    }

    private void showErrorLoginPage() {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/loginGriglia.fxml"));
        LoginController loginController=loader.getController();

        loginController.errorUsername();


    }




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
}

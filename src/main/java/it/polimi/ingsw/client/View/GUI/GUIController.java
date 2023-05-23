package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Tiles;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GUIController implements View {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private final Networker networker;
    private final ClientState state;

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
        switch ((String)evt.getSource()){
            case "start" ->{
                moveToGameScene();
            }
            //Bisogna fare tutti i casi
            case "bookshelf" ->{
                updateBookshelf();

            }
            case "end" -> {
                moveToEndScene();
            }
        }
    }

    private void updateBookshelf() {
    }

    private void moveToEndScene() {
    }

    private void moveToGameScene() {
    }




    //Per ora ho copiato quello della cli per avere un punto di partenza, tanto i messagi che arrivano sono gli stessi

    @Override
    public void receivedMessage(Message message) {
        switch (message.getType()){
            case NEW_LOBBY -> goToNumberOfPlayerPage();
            case WAITING_FOR_PLAYERS -> goToWaitingPage();
            case ERROR -> {

                showError(message.getUsername());

                if (message.getUsername().equals("Username already taken")){
                   showErrorLoginPage();
                }
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
    }



    private void goToWaitingPage() {

    }

    private void goToNumberOfPlayerPage() {

        Platform.runLater(()->{

            FXMLLoader fxmlLoader=new FXMLLoader();
            numberOfPlayerController controller=new numberOfPlayerController();
            fxmlLoader.setController(controller);
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/numberOfPlayer.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Scene scene=new Scene(root);

            stage.setFullScreen(true);
            stage.setTitle("Player Number Page");
            stage.setScene(scene);
            stage.show();

        });

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

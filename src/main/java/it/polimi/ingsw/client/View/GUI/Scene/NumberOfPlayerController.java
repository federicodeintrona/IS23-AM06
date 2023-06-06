package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Messages.IntMessage;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

//controller della scelta del numero di giocatori
public class NumberOfPlayerController implements Initializable,SceneController {

    private GUIController guiController = GUIControllerStatic.getGuiController();
    @FXML
    private Label firstUsernameLabel; //username dell'utente che deve creare la partita
    @FXML
    private ChoiceBox<Integer> numberOfPlayerBox; //box che mostra il numero dei giocatori
    @FXML
    private Button enterButton;


    //inizializzazione
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username=guiController.getState().getMyUsername();
        guiController.setSceneController(this);
        firstUsernameLabel.setText(username+", you must to create a lobby");
        //box per il numero di giocatori
        Integer[] choice={2, 3, 4};
        numberOfPlayerBox.setValue(choice[0]);
        numberOfPlayerBox.getItems().addAll(choice);
        numberOfPlayerBox.setOnAction(this::setNumberOfPlayerBoxClick);
    }

    //quando hai finito la selezione nel box invia il messaggio al server
    public void setNumberOfPlayerBoxClick(ActionEvent actionEvent){
        setNumberOfPlayerBox();
    }
    public void setNumberOfPlayerBoxEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER){
            setNumberOfPlayerBox();
        }
    }
    public void setNumberOfPlayerEnterButtonClick(ActionEvent event){
        setNumberOfPlayerBox();
    }

    private void setNumberOfPlayerBox(){
        Integer choice=numberOfPlayerBox.getValue();

        IntMessage message=new IntMessage();
        message.setUsername(guiController.getState().getMyUsername());
        message.setNum(choice);
        message.setType(MessageTypes.NUM_OF_PLAYERS);
        guiController.sendMessage(message);
    }


}

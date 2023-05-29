package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class EndGameController implements Initializable{

    private GUIController guiController = GUIControllerStatic.getGuiController();
    private ClientState clientState;
    private ArrayList<String> orderPlayer;

    @FXML
    private Label firstPlayer;
    @FXML
    private Label secondPlayer;
    @FXML
    private Label thirdPlayer;
    @FXML
    private Label fourthPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientState = guiController.getState();
//        clientState.addListener(this);
//        guiController.setSceneController(this);
        printEndGame();
    }

    public void printEndGame(){
        sortPlayer();
        firstPlayer.setText("1. "+orderPlayer.get(0)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(0)));
        secondPlayer.setText("2. "+orderPlayer.get(1)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(1)));

        switch (orderPlayer.size()){
            case 3 -> {
                thirdPlayer.setText("3. "+orderPlayer.get(2)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(2)));
            }
            case 4 -> {
                thirdPlayer.setText("3. "+orderPlayer.get(2)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(2)));
                fourthPlayer.setText("4. "+orderPlayer.get(3)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(3)));
            }
        }
    }

    private void sortPlayer(){
        ArrayList<Integer> sortPoint=new ArrayList<>();

        //creazione array dei punti
        for (String st: clientState.getAllPublicPoints().keySet()){
            sortPoint.add(clientState.getAllPublicPoints().get(st));
        }
        //ordinamento
        sortPoint.sort(Comparator.reverseOrder());

        //crea l'ArrayList dei nomi ordinati
        ArrayList<String> result=new ArrayList<>(sortPoint.size());

        for (Integer integer : sortPoint) {
            for (String st : clientState.getAllPublicPoints().keySet()) {
                if (Objects.equals(clientState.getAllPublicPoints().get(st), integer)) {
                    result.add(st);
                }
            }
        }

        //salva l'ArrayList in orderPlayer
        orderPlayer=result;


    }

}

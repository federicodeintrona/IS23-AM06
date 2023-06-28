package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.*;

/**
 * Class to manage the final scene (End Game scene).
 * <p>
 * Show the final ranking.
 */
public class EndGameController implements Initializable, SceneController{

    /**
     * Attribute used to know the instance of GUIController.
     */
    private final GUIController guiController = GUIControllerStatic.getGuiController();
    /**
     * Attribute that instance the correct ClientState.
     */
    private ClientState clientState;
    /**
     * Attribute used to sort players by points made in the game - final ranking (1st to last).
     */
    private ArrayList<String> orderPlayer;


//GRAPHIC ELEMENTS OF THE SCENE
    /**
     * Label of the 1st player on ranking.
     */
    @FXML
    private Label firstPlayer;
    /**
     * Label of the 2nd player on ranking.
     */
    @FXML
    private Label secondPlayer;
    /**
     * Label of the 3rd player on ranking.
     */
    @FXML
    private Label thirdPlayer;
    /**
     * Label of the 4th player on ranking.
     */
    @FXML
    private Label fourthPlayer;
    /**
     * Label of the winner player.
     */
    @FXML
    private Label winnerPlayer;

    /**
     * Default constructor
     */
    public EndGameController() {
    }

    /**
     * Method called to initialize the scene.
     *
     * @param url the URL used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize the ClientState
        clientState = guiController.getState();
        //set the current scene
        guiController.setSceneController(this);

        //print the ranking
        printEndGame();
    }



    /**
     * Method to print the podium of players with their scores.
     */
    private void printEndGame(){
        //order players by how many points they scored during the game
        sortPlayer();

        //set the label's ranking
        firstPlayer.setText("1. "+orderPlayer.get(0)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(0)));
        secondPlayer.setText("2. "+orderPlayer.get(1)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(1)));
        switch (orderPlayer.size()){
            case 3 ->
                    thirdPlayer.setText("3. "+orderPlayer.get(2)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(2)));
            case 4 -> {
                thirdPlayer.setText("3. "+orderPlayer.get(2)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(2)));
                fourthPlayer.setText("4. "+orderPlayer.get(3)+" POINTS: "+clientState.getAllPublicPoints().get(orderPlayer.get(3)));
            }
        }

        //set the label of the winner player
        printWinner("The winner is: "+orderPlayer.get(0));
    }

    /**
     * Method to print the username of the winner player.
     *
     * @param player the username of the winner player.
     */
    private void printWinner(String player){
        winnerPlayer.setText(player);
        winnerPlayer.setWrapText(true);
    }

    /**
     * Method used to reorder players based on how many points they scored - increasing.
     */
    private void sortPlayer(){
        ArrayList<Integer> sortPoint=new ArrayList<>(clientState.getAllPublicPoints().size());

        //players' point ArrayList creation
        for (String st: clientState.getAllPublicPoints().keySet()){
            sortPoint.add(clientState.getAllPublicPoints().get(st));
        }
        //sorting of players' points
        sortPoint.sort(Comparator.reverseOrder());

        //create ArrayList of sorted names
        ArrayList<String> result=new ArrayList<>(sortPoint.size());

        //create the ranking
        for (Integer integer : sortPoint) {
            for (String st : clientState.getAllPublicPoints().keySet()) {
                if (Objects.equals(clientState.getAllPublicPoints().get(st), integer) && !result.contains(st)) {
                    result.add(st);
                }
            }
        }

        //save result (the ranking's ArrayList) into orderPlayer's ArrayList
        orderPlayer=result;
    }

}

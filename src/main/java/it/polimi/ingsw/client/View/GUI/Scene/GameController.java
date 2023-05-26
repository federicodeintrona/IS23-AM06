package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Messages.PointsMessage;
import it.polimi.ingsw.utils.Tiles;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;


public class GameController implements Initializable, PropertyChangeListener,SceneController {
    private GUIController guiController = GUIControllerStatic.getGuiController();
    private ClientState clientState;
    private ArrayList<Point> removeTiles=new ArrayList<>();
    @FXML
    private GridPane boardGrid;
    @FXML
    private GridPane commonGrid;
    @FXML
    private GridPane myBookshelfGrid;
    @FXML
    private Label otherPlayerLabel;
    @FXML
    private GridPane otherPlayerBookshelfGrid;
    @FXML
    private ImageView personalObjectiveImageView;
    @FXML
    private Label myPointsLabel;
    @FXML
    private Label otherPlayerPointsLabel;
    @FXML
    private Label turnLabel; //TODO se viene mostrato non funziona la removetiles - se funziona la remove non si legge
    @FXML
    private Button confirmationButtons=new Button(); //TODO capire come disabilitare & rendere invisibile il bottone fino a che non lo dico io

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientState = guiController.getState();
        clientState.addListener(this);
        guiController.setSceneController(this);
//        confirmationButtons.setOpacity(0);
        initializeBoardGrid();
        initializeCommonGrid();
        try {
            initializePersonalObjectiveImageView();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        initializeotherPlayerLabel();
        initializeMyPointsLabel();
        initializeOtherPlayerPointsLabel();
        updateCurrPlayer();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch ((String) evt.getPropertyName()) {
            case ("board") -> {
                Platform.runLater(this::updateBoard);
            }
            case ("selectedTiles") -> {
                updateSelectedTiles();
            }
            case ("bookshelf") -> {
                updateBookshelf();
            }
            case ("publicPoints") -> {
                updatePublicPoints();
            }
            case ("privatePoints") -> {
                updatePrivatePoints();
            }
            case ("currPlayer") -> {
                Platform.runLater(this::updateCurrPlayer);
            }
        }

    }

    //setta le tessere - dal colore all'immagine
    private ImageView setTiles(Tiles tile){
        Random rand = new Random();
        String[] titles = tile.getImage();
        String title = titles==null?null:titles[rand.nextInt(Define.NUMBEROFTILEIMAGES.getI())];

        Image image = new Image(title);
        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        return imageView;
    }
    private Image getImage(String path){
        try {
            FileInputStream fileInputStream= new FileInputStream(path);
            Image image = new Image(fileInputStream);
            return image;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //la metrice è 11x11 ==> getTile -1
    public void initializeBoardGrid(){
        Matrix matrix=clientState.getBoard();
        for (int i = 1; i < Define.NUMBEROFROWS_BOARD.getI()+1; i++) {
            for (int j = 1; j < Define.NUMBEROFCOLUMNS_BOARD.getI()+1; j++) {
                if (!matrix.getTile(i-1,j-1).equals(Tiles.NOT_ALLOWED) && !matrix.getTile(i-1,j-1).equals(Tiles.EMPTY)) {
                    boardGrid.add(setTiles(matrix.getTile(i-1, j-1)), j, i); //lavora colonna - riga
                }
            }
        }
    }

    //inizializza i common objective
    public void initializeCommonGrid(){ArrayList<Integer> commonGoal= clientState.getGameCommonObjective();
        for(int i=0; i<2;i++){
            String path = "css/images/common_goal_cards/Common_Goal_png/Common_Goal_"+commonGoal.get(i)+".png";
            ImageView imageview=new ImageView(getImage(path));
            imageview.setFitHeight(100);
            imageview.setFitWidth(250);
            commonGrid.add(imageview,i,0);
        }
    }

    //ritorna il nome dell'altro giocatore in partita
    private String catchOtherPlayerName(){
        for (int i = 0; i < clientState.getAllUsername().size(); i++) {
            if (!clientState.getAllUsername().get(i).equals(clientState.getMyUsername())){
                return clientState.getAllUsername().get(i);
            }
        }
        return null;
    }

    //inizializza il nome dell'altro giocatore
    public void initializeotherPlayerLabel(){
        otherPlayerLabel.setText(catchOtherPlayerName());

    }

    //inizializza il personal objective
    public void initializePersonalObjectiveImageView() throws FileNotFoundException {
        String path="css/images/personal_goal_cards/Personal_Goals1.png";
        personalObjectiveImageView.setImage(getImage(path));
        personalObjectiveImageView.setPreserveRatio(true);
        personalObjectiveImageView.setFitWidth(152);
        personalObjectiveImageView.setFitHeight(229);
    }

    //inizializza i tuoi punti
    public void initializeMyPointsLabel(){
        String myPoints="My Points are: "+clientState.getMyPoints();

        myPointsLabel.setText(myPoints);
    }

    //inizializza i punti dell'altro giocatore
    public void initializeOtherPlayerPointsLabel(){
        String otherPlayer=catchOtherPlayerName();
        int points=clientState.getAllPublicPoints().get(otherPlayer);

        otherPlayerPointsLabel.setText(otherPlayer+" points are: "+points);
    }



    private void updateBoard() {
        Matrix matrix=clientState.getBoard();
        for (int i = 1; i < Define.NUMBEROFROWS_BOARD.getI()+1; i++) {
            for (int j = 1; j < Define.NUMBEROFCOLUMNS_BOARD.getI()+1; j++) {
                if (matrix.getTile(i-1, j-1).equals(Tiles.EMPTY)){
                    int finalJ = j;
                    int finalI = i;
                    boardGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node)== finalJ && GridPane.getRowIndex(node)== finalI);
                }
            }
        }
    }

    private void updateSelectedTiles() {
    }

    private void updateBookshelf() {
    }

    private void updatePublicPoints() {
    }

    private void updateCurrPlayer() {
        if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
            String string="It is YOUR turn";
            turnLabel.setText(string);
        }
        else {
            String string="It is "+clientState.getCurrentPlayer()+" turn";
            turnLabel.setText(string);
        }
    }

    private void updatePrivatePoints() {
    }

    @Override
    public void showError(String error) {

    }

    //TODO update board, mybookshelf, otherplayerbookshelf, mypoints, otherplayerpoints

    //TODO click on boardGrid



    //rimuove solo 1 tile per volta
    @FXML
    public void removeTilesClick(MouseEvent event){
        Node click=event.getPickResult().getIntersectedNode();
        Integer colmnIndex=GridPane.getColumnIndex(click);
        Integer rowIndex=GridPane.getRowIndex(click);
//
//        ArrayList<Point> arrayList=new ArrayList<>();
//        arrayList.add(new Point(rowIndex-1, colmnIndex-1));
//
        removeTiles.add(new Point(rowIndex-1, colmnIndex-1));
        //TODO abilitare il bottone
//        confirmationButtons.setVisible(true);
    }

    //conferma le tessere selezionate
    @FXML
    public void confirmClick(ActionEvent actionEvent){
        if (removeTiles.isEmpty()){
            //TODO inviare errore devi selezionare almeno una tile
        }
        else{
            if (removeTiles.size()>3){
                //TODO inviare errore hai selezionato N tiles, puoi selezionare solo 1,2,3 tiles
                // riseleziona
                removeTiles=new ArrayList<>();
            }
            else {
                PointsMessage pointsMessage=new PointsMessage();

                pointsMessage.setUsername(clientState.getMyUsername());
                pointsMessage.setType(MessageTypes.REMOVE_FROM_BOARD);
                pointsMessage.setTiles(removeTiles);
                guiController.sendMessage(pointsMessage);

                removeTiles=new ArrayList<>();
                //TODO disabilitare bottone
//              confirmationButtons.setVisible(false);
            }
        }

    }

}

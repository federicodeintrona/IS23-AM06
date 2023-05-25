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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientState = guiController.getState();
        clientState.addListener(this);
        guiController.setSceneController(this);
        initializeBoardGrid();
//        initializeCommonGrid();
        initializeotherPlayerLabel();
        initializeMyPointsLabel();
        initializeOtherPlayerPointsLabel();
//        initializePersonalObjectiveImageView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch ((String) evt.getNewValue()) {
            case ("board") -> {
                updateBoard();
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
                updateCurrPlayer();
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

    //TODO inizializza i common objective
    public void initializeCommonGrid(){

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

    //TODO inizializza il personal objective
    public void initializePersonalObjectiveImageView(){

        try {

            Image image = new Image(new FileInputStream("17_MyShelfie_BGA/common goal cards/1.jpg"));
            personalObjectiveImageView.setImage(image);
            personalObjectiveImageView.setPreserveRatio(true);
            personalObjectiveImageView.setFitWidth(50);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

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
    }

    private void updateSelectedTiles() {
    }

    private void updateBookshelf() {
    }

    private void updatePublicPoints() {
    }

    private void updateCurrPlayer() {
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

        ArrayList<Point> arrayList=new ArrayList<>();
        arrayList.add(new Point(rowIndex-1, colmnIndex-1));
        PointsMessage pointsMessage=new PointsMessage();

        pointsMessage.setUsername(clientState.getMyUsername());
        pointsMessage.setType(MessageTypes.REMOVE_FROM_BOARD);
        pointsMessage.setTiles(arrayList);

        guiController.sendMessage(pointsMessage);
    }

}

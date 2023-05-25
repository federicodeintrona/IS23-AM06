package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        initializeCommonGrid();
        try {
            initializePersonalObjectiveImageView();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    //TODO inizializza i common objective
    public void initializeCommonGrid(){ArrayList<Integer> commonGoal= clientState.getGameCommonObjective();
        for(int i=0; i<2;i++){
            String path = "css/images/common_goal_cards/Common_Goal_png/Common_Goal_"+commonGoal.get(i)+".png";
            ImageView imageview=new ImageView(getImage(path));
            imageview.setFitHeight(100);
            imageview.setFitWidth(250);
            commonGrid.add(imageview,i,0);
        }
    }

    //TODO inizializza il nome dell'altro giocatore
    public void initializeotherPlayerLabel(){
    }

    //TODO inizializza il personal objective
    public void initializePersonalObjectiveImageView() throws FileNotFoundException {
        String path="css/images/personal_goal_cards/Personal_Goals1.png";
        personalObjectiveImageView.setImage(getImage(path));
        personalObjectiveImageView.setPreserveRatio(true);
        personalObjectiveImageView.setFitWidth(152);
        personalObjectiveImageView.setFitHeight(229);
    }

    //TODO inizializza i tuoi punti
    public void initializeMyPointsLabel(){
        //my points are: ...
    }

    //TODO inizializza i punti dell'altro giocatore
    public void initializeOtherPlayerPointsLabel(){}



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


}

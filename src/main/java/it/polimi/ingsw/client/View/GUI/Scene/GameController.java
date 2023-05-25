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
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class GameController implements Initializable {
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
        initializeBoardGrid();
        initializeCommonGrid();
        initializeotherPlayerLabel();
        initializeMyPointsLabel();
        initializeOtherPlayerPointsLabel();
        initializePersonalObjectiveImageView();
    }

    //setta le tessere - dal colore all'immagine
    private ImageView setTiles(Tiles tile){

//
//        double cellWidth = boardGrid.getColumnConstraints().get(j).getPercentWidth() / 100.0 * boardGrid.getWidth();
//        double cellHeight = boardGrid.getRowConstraints().get(i).getPercentHeight() / 100.0 * boardGrid.getHeight();
//        System.out.println(cellHeight+ cellWidth);

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

    //TODO update board, mybookshelf, otherplayerbookshelf, mypoints, otherplayerpoints

    //TODO click on boardGrid


}

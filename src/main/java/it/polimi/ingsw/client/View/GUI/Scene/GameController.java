package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.Tiles;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
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
    private ArrayList<Point> removeTiles = new ArrayList<>();
    private ArrayList<Integer> orderTiles=new ArrayList<>();
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
    private Button confirmationButton;
    @FXML
    private ImageView selectedTiles1;
    @FXML
    private ImageView selectedTiles2;
    @FXML
    private ImageView selectedTiles3;
    @FXML
    private Button confirmSelected;
    @FXML
    private DialogPane selectedTilesDialog;
    @FXML
    private Button endSwitch;
    @FXML
    private Button column1;
    @FXML
    private Button column2;
    @FXML
    private Button column3;
    @FXML
    private Button column4;
    @FXML
    private Button column5;



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
        initializeotherPlayerLabel();
        updateMyPointsLabel();
        updateOtherPlayerPointsLabel();
        updateCurrPlayer();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case ("board") -> {
                Platform.runLater(this::updateBoard);
            }
            case ("selectedTiles") -> {
                Platform.runLater(this::updateSelectedTiles);
            }
            case ("bookshelf") -> {
                Platform.runLater(this::updateBookshelf);
            }
            case ("publicPoints") -> {
                Platform.runLater(this::updateAllPlayerPoints);
            }
            case ("privatePoints") -> {
                Platform.runLater(this::updateMyPointsLabel);
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

    //aggiorna i tuoi punti
    public void updateMyPointsLabel(){
        String myPoints="My Points are: "+clientState.getMyPoints();

        myPointsLabel.setText(myPoints);
    }

    //aggiorna i punti dell'altro giocatore
    private void updateOtherPlayerPointsLabel(){
        String otherPlayer=catchOtherPlayerName();
        int points=clientState.getAllPublicPoints().get(otherPlayer);

        otherPlayerPointsLabel.setText(otherPlayer+" points are: "+points);
    }

    //aggiorna i punti di tutti i giocatori
    public void updateAllPlayerPoints(){
        for (String player : clientState.getAllPublicPoints().keySet()){
            if (!player.equals(clientState.getMyUsername())){
                updateOtherPlayerPointsLabel();
            }
        }
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
        if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())) {
            selectedTilesDialog.setVisible(true);
            selectedTilesDialog.setDisable(false);

            switch (clientState.getSelectedTiles().size()) {
                case 1 -> {
                    selectedTiles1.setImage(new Image(clientState.getSelectedTiles().get(0).getImage()[0]));
                    selectedTiles1.setDisable(true);
                    selectedTiles1.setVisible(false);
                }
                case 2 -> {
                    selectedTiles1.setImage(new Image(clientState.getSelectedTiles().get(0).getImage()[0]));
                    selectedTiles2.setImage(new Image(clientState.getSelectedTiles().get(1).getImage()[0]));
                    selectedTiles3.setDisable(true);
                    selectedTiles3.setVisible(false);
                }
                case 3 -> {
                    selectedTiles1.setImage(new Image(clientState.getSelectedTiles().get(0).getImage()[0]));
                    selectedTiles2.setImage(new Image(clientState.getSelectedTiles().get(1).getImage()[0]));
                    selectedTiles3.setImage(new Image(clientState.getSelectedTiles().get(2).getImage()[0]));
                }
            }
        }
    }

    private void updateBookshelf() {
        HashMap<String,Matrix> allBookshelf=clientState.getAllBookshelf();
        for(String username: allBookshelf.keySet()) {
            for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    if (!allBookshelf.get(username).getTile(i , j ).equals(Tiles.EMPTY)) {
                        if (username.equals(clientState.getMyUsername())) {
                            myBookshelfGrid.add(setTiles(allBookshelf.get(username).getTile(i, j)), j, i);
                        }
                        else{
                            ImageView tile = setTiles(allBookshelf.get(username).getTile(i, j));
                            tile.setFitWidth(20);
                            tile.setFitHeight(20);
                            otherPlayerBookshelfGrid.add(tile, j, i);
                        }
                    }
                }
            }
        }
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



    //TODO update mybookshelf, otherplayerbookshelf




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
        if(colmnIndex!=null&&rowIndex!=null) {
            if(clientState.getCurrentPlayer().equals(clientState.getMyUsername())||removeTiles.size()>3) {
                removeTiles.add(new Point(rowIndex - 1, colmnIndex - 1));
                confirmationButton.setVisible(true);
                confirmationButton.setDisable(false);
            }
        }

    }

    //conferma le tessere selezionate
    @FXML
    public void confirmClick(ActionEvent actionEvent){
        if (removeTiles.isEmpty()){
           showError("Select at least 1 tile",guiController.getStage());
        }
        else{

                PointsMessage pointsMessage=new PointsMessage();

                pointsMessage.setUsername(clientState.getMyUsername());
                pointsMessage.setType(MessageTypes.REMOVE_FROM_BOARD);
                pointsMessage.setTiles(removeTiles);
                guiController.sendMessage(pointsMessage);

                removeTiles=new ArrayList<>();

                confirmationButton.setVisible(false);
                confirmationButton.setDisable(true);
                boardGrid.setDisable(true);

        }
    }

    //conferma l'ordine dello switch
    @FXML
    public void confirmsSelectedClick(ActionEvent actionEvent){
        confirmSelected.setVisible(false);
        confirmSelected.setDisable(true);

        selectedTilesDialog.setVisible(false);
        selectedTilesDialog.setDisable(true);

        IntArrayMessage intArrayMessage=new IntArrayMessage();

        //setto il messaggio
        intArrayMessage.setUsername(clientState.getMyUsername());
        intArrayMessage.setType(MessageTypes.SWITCH_PLACE);
        intArrayMessage.setIntegers(orderTiles);

        //invia il messaggio
        guiController.sendMessage(intArrayMessage);
        orderTiles=new ArrayList<>();

        endSwitch.setVisible(true);
        endSwitch.setDisable(false);
    }
    //conferma la fine di tutti gli switch
    @FXML
    public void endSwitchClick(ActionEvent actionEvent){
        endSwitch.setVisible(false);
        endSwitch.setDisable(true);

        selectedTilesDialog.setVisible(false);
        selectedTilesDialog.setDisable(true);

        column1.setVisible(true);
        column1.setDisable(false);

        column2.setVisible(true);
        column2.setDisable(false);

        column3.setVisible(true);
        column3.setDisable(false);

        column4.setVisible(true);
        column4.setDisable(false);

        column5.setVisible(true);
        column5.setDisable(false);
    }

    //aggiungo tile nell'ordine corretto
    @FXML
    public void selectTile1(MouseEvent event){
        orderTiles.add(1);
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }
    //aggiungo tile nell'ordine corretto
    @FXML
    public void selectTile2(MouseEvent event){
        orderTiles.add(2);
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }
    //aggiungo tile nell'ordine corretto
    @FXML
    public void selectTile3(MouseEvent event){
        orderTiles.add(3);
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }

    //aggiungo le tiles nella colonna corretta
    @FXML
    public void addToColumn1(ActionEvent actionEvent){
        column1.setVisible(false);
        column1.setDisable(true);

        column2.setVisible(false);
        column2.setDisable(true);

        column3.setVisible(false);
        column3.setDisable(true);

        column4.setVisible(false);
        column4.setDisable(true);

        column5.setVisible(false);
        column5.setDisable(true);

        IntMessage intMessage=new IntMessage();

        //setta il messaggio
        intMessage.setUsername(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(0);

        //invia il messaggio
        guiController.sendMessage(intMessage);
    }
    //aggiungo le tiles nella colonna corretta
    @FXML
    public void addToColumn2(ActionEvent actionEvent){
        column1.setVisible(false);
        column1.setDisable(true);

        column2.setVisible(false);
        column2.setDisable(true);

        column3.setVisible(false);
        column3.setDisable(true);

        column4.setVisible(false);
        column4.setDisable(true);

        column5.setVisible(false);
        column5.setDisable(true);

        IntMessage intMessage=new IntMessage();

        //setta il messaggio
        intMessage.setUsername(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(1);

        //invia il messaggio
        guiController.sendMessage(intMessage);
    }
    //aggiungo le tiles nella colonna corretta
    @FXML
    public void addToColumn3(ActionEvent actionEvent){
        column1.setVisible(false);
        column1.setDisable(true);

        column2.setVisible(false);
        column2.setDisable(true);

        column3.setVisible(false);
        column3.setDisable(true);

        column4.setVisible(false);
        column4.setDisable(true);

        column5.setVisible(false);
        column5.setDisable(true);

        IntMessage intMessage=new IntMessage();

        //setta il messaggio
        intMessage.setUsername(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(2);

        //invia il messaggio
        guiController.sendMessage(intMessage);
    }
    //aggiungo le tiles nella colonna corretta
    @FXML
    public void addToColumn4(ActionEvent actionEvent){
        column1.setVisible(false);
        column1.setDisable(true);

        column2.setVisible(false);
        column2.setDisable(true);

        column3.setVisible(false);
        column3.setDisable(true);

        column4.setVisible(false);
        column4.setDisable(true);

        column5.setVisible(false);
        column5.setDisable(true);

        IntMessage intMessage=new IntMessage();

        //setta il messaggio
        intMessage.setUsername(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(3);

        //invia il messaggio
        guiController.sendMessage(intMessage);
    }
    //aggiungo le tiles nella colonna corretta
    @FXML
    public void addToColumn5(ActionEvent actionEvent){
        column1.setVisible(false);
        column1.setDisable(true);

        column2.setVisible(false);
        column2.setDisable(true);

        column3.setVisible(false);
        column3.setDisable(true);

        column4.setVisible(false);
        column4.setDisable(true);

        column5.setVisible(false);
        column5.setDisable(true);

        IntMessage intMessage=new IntMessage();

        //setta il messaggio
        intMessage.setUsername(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(4);

        //invia il messaggio
        guiController.sendMessage(intMessage);
    }
}

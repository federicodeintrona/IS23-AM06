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
import javafx.stage.Stage;

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
    private Point checkResetPoint=null;
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
    private Button rollbackButton;
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

    private State state = State.REMOVE;

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
        List<String> username = clientState.getAllUsername();
        for(String x: username){
            updateBookshelf(x);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case ("board") -> {
                Platform.runLater(() -> {
                    if (checkResetPoint==null ){
                        updateBoard();
                    }else if(clientState.getBoard().getTile(checkResetPoint).equals(Tiles.EMPTY)){
                        updateBoard();
                    }
                    else {
                        updateBoard();
                        initializeBoardGrid();
                    }
                });
            }
            case ("selectedTiles") -> {
                Platform.runLater(this::updateSelectedTiles);
            }
            case ("bookshelf") -> {
                Platform.runLater(()->{
                    updateBookshelf((String) evt.getOldValue());
                });
            }
            case ("publicPoints") -> {
                Platform.runLater(this::updateAllPlayerPoints);
            }
            case ("privatePoints") -> {
                Platform.runLater(this::updateMyPointsLabel);
            }
            case ("currPlayer") -> {
//                Platform.runLater(this::updateCurrPlayer);
                Platform.runLater(() -> {
                    updateCurrPlayer();
                    if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
                        boardGrid.setDisable(false);
                    }
                });

            }
        }
    }

    @Override
    public void showError(String error, Stage stage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
        alert.getDialogPane().setStyle( "-fx-font-weight: bold;" +
                                        "-fx-font-size: 18px;" +
                                        "-fx-font-style: italic;"+
                                        "-fx-text-fill: #070707;"+
                                        "-fx-background-color: #f70000;");

        alert.initOwner(stage);
        alert.showAndWait();

        revert();
    }

    private void revert(){

        switch (state){
            case REMOVE -> {
                //riabilitiamo tutti i bottoni di selezione delle colonne
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
                //setto lo stato ad add
                state=State.ADD;
            }

            case SWITCH -> {
                //puliamo le tessere selezionate
                removeTiles=new ArrayList<>();
                //riabilitiamo la board
                boardGrid.setDisable(false);
                //disabilitiamo il bottone di conferma
                confirmationButton.setVisible(false);
                confirmationButton.setDisable(true);
                //togliamo l'opacità a tutte le tessere selezionate
                boardGrid.getChildren().forEach(node -> node.setStyle("-fx-opacity: 1"));
                //setto lo stato a remove
                state=State.REMOVE;
            }
            case ADD -> {
                //puliamo lo switch selezionato
                orderTiles=new ArrayList<>();
                //riabilito la finestra di switch delle tile
                selectedTilesDialog.setVisible(true);
                selectedTilesDialog.setDisable(false);
                //setto lo stato a switch
                state=State.SWITCH;
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
    private void initializeBoardGrid(){
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
    private void initializeCommonGrid(){ArrayList<Integer> commonGoal= clientState.getGameCommonObjective();
        for(int i=0; i<2;i++){
            String path = "css/images/common_goal_cards/Common_Goal_png/Common_Goal_"+commonGoal.get(i)+".png";
            ImageView imageview=new ImageView(getImage(path));
            imageview.setPreserveRatio(true);
            imageview.setFitWidth(248);
//            imageview.setFitHeight(100);
//            imageview.setFitWidth(250);
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
    private void initializeotherPlayerLabel(){
        otherPlayerLabel.setText(catchOtherPlayerName());
    }

    //inizializza il personal objective
    private void initializePersonalObjectiveImageView() throws FileNotFoundException {
        String path="css/images/personal_goal_cards/Personal_Goals"+clientState.getMyPersonalObjectiveInt()+".png";
        personalObjectiveImageView.setImage(getImage(path));
        personalObjectiveImageView.setPreserveRatio(true);
        personalObjectiveImageView.setFitWidth(152);
        personalObjectiveImageView.setFitHeight(229);
    }

    //aggiorna i tuoi punti
    private void updateMyPointsLabel(){
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
    private void updateAllPlayerPoints(){
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
            selectedTilesDialog.setStyle("-fx-background-color: null");
            //rendi visibile il bottone per finire lo switch e andare nella selezione della colonna
            endSwitch.setVisible(true);
            endSwitch.setDisable(false);
            //rendi visibili tutte le tile
            selectedTiles1.setDisable(false);
            selectedTiles1.setVisible(true);
            selectedTiles2.setDisable(false);
            selectedTiles2.setVisible(true);
            selectedTiles3.setDisable(false);
            selectedTiles3.setVisible(true);
            //rendi invisibile la board
            boardGrid.setDisable(true);
            boardGrid.setVisible(false);
            switch (clientState.getSelectedTiles().size()) {
                case 1 -> {
                    selectedTiles1.setImage(new Image(clientState.getSelectedTiles().get(0).getImage()[0]));
                    selectedTiles2.setDisable(true);
                    selectedTiles2.setVisible(false);
                    selectedTiles3.setDisable(true);
                    selectedTiles3.setVisible(false);
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

    private void updateBookshelf(String username) {
        Matrix bookshelf=clientState.getAllBookshelf().get(username);
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                if (!bookshelf.getTile(i , j ).equals(Tiles.EMPTY)) {
                    if (username.equals(clientState.getMyUsername())) {
                        myBookshelfGrid.add(setTiles(bookshelf.getTile(i, j)), j, i);
                    }
                    else{
                        ImageView tile = setTiles(bookshelf.getTile(i, j));
                        tile.setFitWidth(20);
                        tile.setFitHeight(20);
                        otherPlayerBookshelfGrid.add(tile, j, i);
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
    private void removeTilesClick(MouseEvent event){
        Node click=event.getPickResult().getIntersectedNode();
        Integer colmnIndex=GridPane.getColumnIndex(click);
        Integer rowIndex=GridPane.getRowIndex(click);
//
//        ArrayList<Point> arrayList=new ArrayList<>();
//        arrayList.add(new Point(rowIndex-1, colmnIndex-1));
//
        if(colmnIndex!=null&&rowIndex!=null) {
            if(clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
                if(removeTiles.size()<3){
                    removeTiles.add(new Point(rowIndex - 1, colmnIndex - 1));
                    //abilita il bottone della conferma
                    confirmationButton.setVisible(true);
                    confirmationButton.setDisable(false);
                    //abilita il bottone dell'annullamento
                    rollbackButton.setVisible(true);
                    rollbackButton.setDisable(false);
                    ImageView imageView=(ImageView) event.getTarget();
                    imageView.setStyle("-fx-opacity: 0.5");
                }
            }
        }

    }

    //conferma le tessere selezionate
    @FXML
    private void confirmClick(ActionEvent actionEvent){
        if (removeTiles.isEmpty()){
           showError("Select at least 1 tile",guiController.getStage());
        }
        else{
                PointsMessage pointsMessage=new PointsMessage();

                pointsMessage.setUsername(clientState.getMyUsername());
                pointsMessage.setType(MessageTypes.REMOVE_FROM_BOARD);
                pointsMessage.setTiles(removeTiles);
                guiController.sendMessage(pointsMessage);

                //mi salvo una posizione che so essere vuota
                checkResetPoint=removeTiles.get(0);

                removeTiles=new ArrayList<>();

                //disabilita il bottone di conferma
                confirmationButton.setVisible(false);
                confirmationButton.setDisable(true);
                //disabilita il bottone di annullamento
                rollbackButton.setVisible(false);
                rollbackButton.setDisable(true);
                boardGrid.setDisable(true);
                state = State.SWITCH;

        }
    }

    //annulla la selezione delle tessere selezionate
    @FXML
    private void rollbackClick(ActionEvent actionEvent){
        removeTiles=new ArrayList<>();
        boardGrid.getChildren().forEach(node -> node.setStyle("-fx-opacity: 1"));
        rollbackButton.setVisible(false);
        rollbackButton.setDisable(true);
        confirmationButton.setVisible(false);
        confirmationButton.setDisable(true);
    }

    //conferma l'ordine dello switch
    @FXML
    private void confirmsSelectedClick(ActionEvent actionEvent){
        //riabilitiamo le tessere per essere invertite
        selectedTiles1.setDisable(false);
        selectedTiles1.setVisible(true);
        selectedTiles1.setStyle("-fx-opacity: 1");
        selectedTiles2.setDisable(false);
        selectedTiles2.setVisible(true);
        selectedTiles2.setStyle("-fx-opacity: 1");
        selectedTiles3.setDisable(false);
        selectedTiles3.setVisible(true);
        selectedTiles3.setStyle("-fx-opacity: 1");

        //nascondiamo il bottone di conferma della selezione
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

        //rendi visibile il bottone per finire lo switch e andare nella selezione della colonna
        endSwitch.setVisible(true);
        endSwitch.setDisable(false);
    }
    //conferma la fine di tutti gli switch
    @FXML
    private void endSwitchClick(ActionEvent actionEvent){
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

        boardGrid.setVisible(true);
        state = State.ADD;
    }

    //aggiungo tile nell'ordine corretto
    @FXML
    private void selectTile1(MouseEvent event){
        orderTiles.add(1);
        //disabilita la selezione di questa tessera
        selectedTiles1.setDisable(true);
        selectedTiles1.setStyle("-fx-opacity: 0.5");
        //disabilita la fine dello switch perchè hai selezionato un'altra tile
        endSwitch.setVisible(false);
        endSwitch.setDisable(true);
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }
    //aggiungo tile nell'ordine corretto
    @FXML
    private void selectTile2(MouseEvent event){
        orderTiles.add(2);
        //disabilita la selezione di questa tessera
        selectedTiles2.setDisable(true);
        selectedTiles2.setStyle("-fx-opacity: 0.5");
        //disabilita la fine dello switch perchè hai selezionato un'altra tile
        endSwitch.setVisible(false);
        endSwitch.setDisable(true);
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }
    //aggiungo tile nell'ordine corretto
    @FXML
    private void selectTile3(MouseEvent event){
        orderTiles.add(3);
        //disabilita la selezione di questa tessera
        selectedTiles3.setDisable(true);
        selectedTiles3.setStyle("-fx-opacity: 0.5");
        //disabilita la fine dello switch perchè hai selezionato un'altra tile
        endSwitch.setVisible(false);
        endSwitch.setDisable(true);
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }

    //aggiungo le tiles nella colonna corretta
    @FXML
    private void addToColumn1(ActionEvent actionEvent){
        addToColumn();

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
    private void addToColumn2(ActionEvent actionEvent){
        addToColumn();

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
    private void addToColumn3(ActionEvent actionEvent){
        addToColumn();

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
    private void addToColumn4(ActionEvent actionEvent){
        addToColumn();

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
    private void addToColumn5(ActionEvent actionEvent){

        addToColumn();

        IntMessage intMessage=new IntMessage();

        //setta il messaggio
        intMessage.setUsername(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(4);

        //invia il messaggio
        guiController.sendMessage(intMessage);

    }


    private void addToColumn(){
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

        state = State.REMOVE;
    }
}

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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Class to manage the game scene.
 * <ul>
 *     <li>Set common objective;</li>
 *     <li>set personal objective;</li>
 *     <li>update board;</li>
 *     <li>update all players' bookshelf;</li>
 *     <li>update all players' points;</li>
 *     <li>update public chat;</li>
 *     <li>update private chat (chat between 2 players);</li>
 *     <li>show error.</li>
 * </ul>
 */
public class GameControllerChat implements Initializable, PropertyChangeListener,SceneController {

    /**
     * Attribute used to know the instance of GUIController.
     */
    private final GUIController guiController = GUIControllerStatic.getGuiController();
    /**
     * Attribute that instance the correct ClientState.
     */
    private ClientState clientState;
    /**
     * Attribute used to know the tiles remove from the board.
     */
    private ArrayList<Point> removeTiles = new ArrayList<>();
    /**
     * Attribute used to know the order of the tiles remove from the board.
     */
    private ArrayList<Integer> orderTiles=new ArrayList<>();
    /**
     * Attribute used to know whether the board should be reset or not.
     */
    private Point checkResetPoint=null;
    /**
     * Attribute used to know what state I am in (Remove, Switch or Add).
     * What move is the client making?
     */
    private State state = State.REMOVE;


    //graphic elements of the scene
    /**
     * Label use to show the name of the current player.
     */
    @FXML
    private Label turnLabel;
    /**
     * Label used to show the current ranking - only the public points.
     */
    @FXML
    private Label classification;
    /**
     * GridPane of the board.
     */
    @FXML
    private GridPane boardGrid;
    /**
     * ImageView of the 1st common objective.
     */
    @FXML
    private ImageView commonObjectiveImage1;
    /**
     * ImageView of the 2nd common objective.
     */
    @FXML
    private ImageView commonObjectiveImage2;
    /**
     * ImageView of the points of the 1st common objective.
     */
    @FXML
    private ImageView commonObjectivePoint1;
    /**
     * ImageView of the points of the 2nd common objective.
     */
    @FXML
    private ImageView commonObjectivePoint2;

    /**
     * GridPane of my bookshelf.
     */
    @FXML
    private GridPane myBookshelfGrid;
    /**
     * ImageView of my personal objective.
     */
    @FXML
    private ImageView personalObjectiveImageView;
    /**
     * Label with my points (public + private points).
     */
    @FXML
    private Label myPointsLabel;
    /**
     * ImageView used to show that I have the chair.
     */
    @FXML
    private ImageView myChair;
    /**
     * ImageView used to show how many points I received from the 1st common objective.
     */
    @FXML
    private ImageView myCommonPointImage1;
    /**
     * ImageView used to show how many points I received from the 2nd common objective.
     */
    @FXML
    private ImageView myCommonPointImage2;

    /**
     * Label with the username of the other player.
     */
    @FXML
    private Label otherPlayerLabel1;
    /**
     * Label with the username of the other player.
     */
    @FXML
    private Label otherPlayerLabel2;
    /**
     * Label with the username of the other player.
     */
    @FXML
    private Label otherPlayerLabel3;

    /**
     * GridPane of the other player bookshelf.
     */
    @FXML
    private GridPane otherPlayerBookshelfGrid1;
    /**
     * GridPane of the other player bookshelf.
     */
    @FXML
    private GridPane otherPlayerBookshelfGrid2;
    /**
     * GridPane of the other player bookshelf.
     */
    @FXML
    private GridPane otherPlayerBookshelfGrid3;

    /**
     * Label with the points of the other player.
     */
    @FXML
    private Label otherPlayerPointsLabel1;
    /**
     * Label with the points of the other player.
     */
    @FXML
    private Label otherPlayerPointsLabel2;
    /**
     * Label with the points of the other player.
     */
    @FXML
    private Label otherPlayerPointsLabel3;

    /**
     * ImageView of the other player's bookshelf.
     */
    @FXML
    private ImageView otherPlayerImage1;
    /**
     * ImageView of the other player's bookshelf.
     */
    @FXML
    private ImageView otherPlayerImage2;
    /**
     * ImageView of the other player's bookshelf.
     */
    @FXML
    private ImageView otherPlayerImage3;

    /**
     * ImageView of the other player's personal objective.
     */
    @FXML
    private ImageView personal1;
    /**
     * ImageView of the other player's personal objective.
     */
    @FXML
    private ImageView personal2;
    /**
     * ImageView of the other player's personal objective.
     */
    @FXML
    private ImageView personal3;

    /**
     * ImageView used to show that the other player has the chair.
     */
    @FXML
    private ImageView otherChair1;
    /**
     * ImageView used to show that the other player has the chair.
     */
    @FXML
    private ImageView otherChair2;
    /**
     * ImageView used to show that the other player has the chair.
     */
    @FXML
    private ImageView otherChair3;

    /**
     * ImageView used to show how many points the other player received from the 1st common objective.
     */
    @FXML
    private ImageView otherPlayer1CommonPoint1;
    /**
     * ImageView used to show how many points the other player received from the 1st common objective.
     */
    @FXML
    private ImageView otherPlayer2CommonPoint1;
    /**
     * ImageView used to show how many points the other player received from the 1st common objective.
     */
    @FXML
    private ImageView otherPlayer3CommonPoint1;

    /**
     * ImageView used to show how many points the other player received from the 2nd common objective.
     */
    @FXML
    private ImageView otherPlayer1CommonPoint2;
    /**
     * ImageView used to show how many points the other player received from the 2nd common objective.
     */
    @FXML
    private ImageView otherPlayer2CommonPoint2;
    /**
     * ImageView used to show how many points the other player received from the 2nd common objective.
     */
    @FXML
    private ImageView otherPlayer3CommonPoint2;

    /**
     * Button used to confirm the tiles selected from board.
     */
    @FXML
    private Button confirmationButton;
    /**
     * Button used to roll back the tiles selected from board.
     */
    @FXML
    private Button rollbackButton;

    /**
     * DialogPane where to show and switch the tiles removed from the board.
     */
    @FXML
    private DialogPane selectedTilesDialog;
    /**
     * ImageView of the 1st selected tile.
     */
    @FXML
    private ImageView selectedTiles1;
    /**
     * ImageView of the 2nd selected tile.
     */
    @FXML
    private ImageView selectedTiles2;
    /**
     * ImageView of the 3rd selected tile.
     */
    @FXML
    private ImageView selectedTiles3;
    /**
     * Button used to confirm the selected order of tiles.
     */
    @FXML
    private Button confirmSelected;
    /**
     * Button used to confirm the end of switch.
     */
    @FXML
    private Button endSwitch;

    /**
     * Button used to choose the 1st column of the bookshelf where put the  selected tiles ordered.
     */
    @FXML
    private Button column1;
    /**
     * Button used to choose the 2nd column of the bookshelf where put the  selected tiles ordered.
     */
    @FXML
    private Button column2;
    /**
     * Button used to choose the 3rd column of the bookshelf where put the  selected tiles ordered.
     */
    @FXML
    private Button column3;
    /**
     * Button used to choose the 4th column of the bookshelf where put the  selected tiles ordered.
     */
    @FXML
    private Button column4;
    /**
     * Button used to choose the 5th column of the bookshelf where put the  selected tiles ordered.
     */
    @FXML
    private Button column5;

    /**
     * VBox used to show the public chat.
     */
    @FXML
    private VBox publicChatBox;
    /**
     * VBox used to show the private chat.
     */
    @FXML
    private VBox otherPlayerChatBox1;
    /**
     * VBox used to show the private chat.
     */
    @FXML
    private VBox otherPlayerChatBox2;
    /**
     * VBox used to show the private chat.
     */
    @FXML
    private VBox otherPlayerChatBox3;
    /**
     * TextField used to write the message to send in the shown chat.
     */
    @FXML
    private TextField sendMessage;
    /**
     * ChoiceBox used to show the chat that want show.
     */
    @FXML
    private ChoiceBox<String> selectChat;
    /**
     * ScrollPane used to scroll the shown chat.
     */
    @FXML
    private ScrollPane chatScroll;



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
        clientState.addListener(this);
        clientState.setOldCommonObjectivePoints(clientState.getCommonObjectivePoints());
        guiController.setSceneController(this);

        //initialize all graphics element
        //board
        initializeBoardGrid();
        //common objective
        initializeCommonGrid();
        //common objective's points
        updateCommonObjectivePoints();
        //personal objective
        try {
            initializePersonalObjectiveImageView();
        }
        catch (FileNotFoundException e) {
            System.out.println("The image was not found");
        }
        //all players' username
        initializeotherPlayerLabel();
        //my points
        updateMyPointsLabel();
        //all players' points
        updateAllPlayerPoints();
        //current player
        updateCurrPlayer();
        //chair
        initializeChair();
        //ranking
        updateClassification();
        //chat choice
        initializeChatChoice();
        //initialize all chat
        initializeChat();
        //bookshelf
        List<String> username = clientState.getAllUsername();
        for(String x: username){
            updateBookshelf(x);
        }
    }

    /**
     * Method to initialize the board.
     * <p>
     * The BoardGrid has a size of 11x11, this was done in order to arrange the position of the tiles to be inserted on top of it.
     * This implies that I had to do getTile -1.
     */
    private void initializeBoardGrid(){
        Matrix matrix=clientState.getBoard();

        //add tiles for each position different to EMPTY and NOT ALLOWED
        for (int i = 1; i < Define.NUMBEROFROWS_BOARD.getI()+1; i++) {
            for (int j = 1; j < Define.NUMBEROFCOLUMNS_BOARD.getI()+1; j++) {
                if (!matrix.getTile(i-1,j-1).equals(Tiles.NOT_ALLOWED) && !matrix.getTile(i-1,j-1).equals(Tiles.EMPTY)) {
                    boardGrid.add(setTiles(matrix.getTile(i-1, j-1)), j, i); //column, row
                }
            }
        }
    }

    /**
     * Method to initialize the common objective.
     */
    private void initializeCommonGrid(){
        ArrayList<Integer> commonGoal= clientState.getGameCommonObjective();

        //catch the path of the 1st common objective image and set it
        String path = "/images/common_goal_cards/Common_Goal_png/Common_Goal_"+commonGoal.get(0)+".png";
        commonObjectiveImage1.setImage(getImage(path));
        //catch the path of the 2nd common objective image and set it
        String path1 = "/images/common_goal_cards/Common_Goal_png/Common_Goal_"+commonGoal.get(1)+".png";
        commonObjectiveImage2.setImage(getImage(path1));
    }

    //TODO finire javadoc
    //inizializza il personal objective
    private void initializePersonalObjectiveImageView() throws FileNotFoundException {
        String path= "/images/personal_goal_cards/Personal_Goals"+clientState.getMyPersonalObjectiveInt()+".png";
        personalObjectiveImageView.setImage(getImage(path));
        personalObjectiveImageView.setPreserveRatio(true);
        personalObjectiveImageView.setFitWidth(152);
        personalObjectiveImageView.setFitHeight(229);
    }

    //inizializza il nome dell'altro giocatore
    private void initializeotherPlayerLabel(){
        //lista con gli username degli altri giocatori
        ArrayList<String> otherPlayerList=catchOtherPlayerName(clientState.getAllUsername());

        otherPlayerLabel1.setVisible(true);
        otherPlayerLabel1.setDisable(false);
        otherPlayerPointsLabel1.setVisible(true);
        otherPlayerPointsLabel1.setDisable(false);
        otherPlayerBookshelfGrid1.setVisible(true);
        otherPlayerBookshelfGrid1.setDisable(false);
        otherPlayerImage1.setVisible(true);
        otherPlayerImage1.setDisable(false);
        personal1.setVisible(true);
        personal1.setDisable(false);
        otherPlayer1CommonPoint1.setVisible(true);
        otherPlayer1CommonPoint1.setDisable(false);
        otherPlayer1CommonPoint2.setVisible(true);
        otherPlayer1CommonPoint2.setDisable(false);

        otherPlayerLabel1.setText(otherPlayerList.get(0));

        switch (clientState.getAllUsername().size()){
            case 3 -> {
                otherPlayerLabel2.setVisible(true);
                otherPlayerLabel2.setDisable(false);
                otherPlayerPointsLabel2.setVisible(true);
                otherPlayerPointsLabel2.setDisable(false);
                otherPlayerBookshelfGrid2.setVisible(true);
                otherPlayerBookshelfGrid2.setDisable(false);
                otherPlayerImage2.setVisible(true);
                otherPlayerImage2.setDisable(false);
                personal2.setVisible(true);
                personal2.setDisable(false);
                otherPlayer2CommonPoint1.setVisible(true);
                otherPlayer2CommonPoint1.setDisable(false);
                otherPlayer2CommonPoint2.setVisible(true);
                otherPlayer2CommonPoint2.setDisable(false);

                otherPlayerLabel2.setText(otherPlayerList.get(1));
            }
            case 4 -> {
                otherPlayerLabel2.setVisible(true);
                otherPlayerLabel2.setDisable(false);
                otherPlayerPointsLabel2.setVisible(true);
                otherPlayerPointsLabel2.setDisable(false);
                otherPlayerBookshelfGrid2.setVisible(true);
                otherPlayerBookshelfGrid2.setDisable(false);
                otherPlayerImage2.setVisible(true);
                otherPlayerImage2.setDisable(false);
                personal2.setVisible(true);
                personal2.setDisable(false);
                otherPlayer2CommonPoint1.setVisible(true);
                otherPlayer2CommonPoint1.setDisable(false);
                otherPlayer2CommonPoint2.setVisible(true);
                otherPlayer2CommonPoint2.setDisable(false);

                otherPlayerLabel2.setText(otherPlayerList.get(1));

                otherPlayerLabel3.setVisible(true);
                otherPlayerLabel3.setDisable(false);
                otherPlayerPointsLabel3.setVisible(true);
                otherPlayerPointsLabel3.setDisable(false);
                otherPlayerBookshelfGrid3.setVisible(true);
                otherPlayerBookshelfGrid3.setDisable(false);
                otherPlayerImage3.setVisible(true);
                otherPlayerImage3.setDisable(false);
                personal3.setVisible(true);
                personal3.setDisable(false);
                otherPlayer3CommonPoint1.setVisible(true);
                otherPlayer3CommonPoint1.setDisable(false);
                otherPlayer3CommonPoint2.setVisible(true);
                otherPlayer3CommonPoint2.setDisable(false);

                otherPlayerLabel3.setText(otherPlayerList.get(2));
            }
        }
    }

    /**
     * Method to initialize the chair (username of the player who started the game).
     */
    private void initializeChair(){
        if (clientState.getChair().equals(clientState.getMyUsername())){
            myChair.setVisible(true);
            myChair.setDisable(false);
        }
        else if (clientState.getChair().equals(otherPlayerLabel1.getText())){
            otherChair1.setVisible(true);
            otherChair1.setDisable(false);
        }
        else if (clientState.getChair().equals(otherPlayerLabel2.getText())){
            otherChair2.setVisible(true);
            otherChair2.setDisable(false);
        }
        else if (clientState.getChair().equals(otherPlayerLabel3.getText())){
            otherChair3.setVisible(true);
            otherChair3.setDisable(false);
        }
    }

    /**
     * Method to initialize the chat choice.
     * <p>
     * ALL and with the other player.
     */
    private void initializeChatChoice(){
        //username of the other players
        ArrayList<String> otherPlayer=catchOtherPlayerName(clientState.getAllUsername());

        //initialize the choice
        selectChat.setValue("ALL");
        selectChat.getItems().add("ALL");
        selectChat.getItems().addAll(otherPlayer);
        selectChat.setOnAction(this::selectWhichChatToShow);

        //initialize the chatScroll size - fits the size of VBox shown
        chatScroll.setContent(publicChatBox);
        chatScroll.setFitToWidth(true);
    }

    /**
     * Method to initialize the chat.
     * <p>
     * Checks for previous messages in the chats and inserts them.
     */
    private void initializeChat(){
        //are there any messages in the public chat?
        if (clientState.getChatController().getPublicChat().getChatMessages().size()!=0){
            for (int i = clientState.getChatController().getPublicChat().getOldestMessage(); i>=0; i--){
                //add the message
                updatePublicChat(clientState.getChatController().getPublicChat().getChatMessages().get(i));
            }
        }

        //are there any messages in any private chat?
        for (String st: clientState.getChatController().getPrivateChats().keySet()){
            if (clientState.getChatController().getPrivateChat(st).getChatMessages().size()!=0) {
                for (int i = clientState.getChatController().getPrivateChat(st).getOldestMessage(); i >= 0; i--) {
                    //add the message
                    updatePrivateChat(clientState.getChatController().getPrivateChat(st).getChatMessages().get(i));
                }
            }
        }
    }



    //aggiorna i punti dei common objective
    private void updateCommonObjectivePoints(){
        ArrayList<Integer> commonGoal= clientState.getCommonObjectivePoints();
        String path = "/images/scoring_tokens/scoring_"+commonGoal.get(0)+".jpg";
        commonObjectivePoint1.setImage(getImage(path));
        String path1 = "/images/scoring_tokens/scoring_"+commonGoal.get(1)+".jpg";
        commonObjectivePoint2.setImage(getImage(path1));
    }

    //aggiorna i tuoi punti
    private void updateMyPointsLabel(){
        String myPoints="My Points are: "+clientState.getMyPoints();

        myPointsLabel.setText(myPoints);
    }

    //aggiorna i punti di tutti i giocatori
    private void updateAllPlayerPoints(){
        for (String player : clientState.getAllPublicPoints().keySet()){
            if (!player.equals(clientState.getMyUsername())){
                updateOtherPlayerPointsLabel(player);
            }
        }
    }

    private void updateCurrPlayer() {
        String string;
        if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
            string="It is YOUR turn";
            turnLabel.setText(string);

            //pop up - è il tuo turno
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update turn");
            alert.setHeaderText("Current game turn");
            alert.setContentText(string);
            alert.initOwner(guiController.getStage());
            alert.showAndWait();
        }
        else {
            string="It is "+clientState.getCurrentPlayer()+" turn";
            turnLabel.setText(string);
        }

    }

    /**
     * Method to update the game's ranking.
     */
    private void updateClassification(){
        ArrayList<String> player=sortPlayer();

        //Classification string
        //I used StringBuilder because it provides a mutable object
        //that can be used to create, modify, and manipulate strings without creating new string instances with each operation
        StringBuilder c= new StringBuilder("CLASSIFICATION:\n");

        //chaining of all players in the ranking
        for (int i = 0; i < player.size(); i++) {
            c.append(i+1).append(". ").append(player.get(i)).append(" POINTS ").append(clientState.getAllPublicPoints().get(player.get(i))).append("\n");
        }

        //show the ranking
        classification.setText(c.toString());
    }

    //aggiorna tutte le bookshelf
    private void updateBookshelf(String username){
        if (username.equals(clientState.getMyUsername())){
            updateMyBookshelf();
        }
        else {
            updateOtherBookshelf(username);
        }
    }



    /**
     * Method used to reorder players based on how many points they scored - increasing.
     *
     * @return the sorting in ascending order of the players.
     */
    private ArrayList<String> sortPlayer(){
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

        //return the ranking's ArrayList
        return result;

    }


    //ritorna il numero dell'obiettivo comune completato
    private int commonObjectivePointPlayerImage(ArrayList<Integer> old){
        if (!Objects.equals(clientState.getCommonObjectivePoints().get(0), old.get(0))){
            return 0;
        }
        else {
            return 1;
        }
    }
    private ImageView catchPlayerCommonObjectivePointImage(String username, int commonObjective){
        if (commonObjective==0){
            if (clientState.getMyUsername().equals(username)){
                return myCommonPointImage1;
            }
            else if (otherPlayerLabel1.getText().equals(username)){
                return otherPlayer1CommonPoint1;
            }
            else if (otherPlayerLabel2.getText().equals(username)){
                return otherPlayer2CommonPoint1;
            }
            else if (otherPlayerLabel3.getText().equals(username)){
                return otherPlayer3CommonPoint1;
            }
        }
        else if (commonObjective==1){
            if (clientState.getMyUsername().equals(username)){
                return myCommonPointImage2;
            }
            else if (otherPlayerLabel1.getText().equals(username)){
                return otherPlayer1CommonPoint2;
            }
            else if (otherPlayerLabel2.getText().equals(username)){
                return otherPlayer2CommonPoint2;
            }
            else if (otherPlayerLabel3.getText().equals(username)){
                return otherPlayer3CommonPoint2;
            }
        }
        return null;
    }

    private void updatePlayerCommonObjectivePointImage(String player, ArrayList<Integer> old){

        int point=old.get(commonObjectivePointPlayerImage(old));
        String path = "/images/scoring_tokens/scoring_"+point+".jpg";

        Objects.requireNonNull(catchPlayerCommonObjectivePointImage(player, commonObjectivePointPlayerImage(old))).setImage(getImage(path));
    }




    private void goBack(){

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
                selectedTilesDialog.setStyle("-fx-background-color: null");
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

        assert title != null;
        Image image = new Image(title);
        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        return imageView;
    }

    private Image getImage(String path){
        InputStream s = getClass().getResourceAsStream(path);
        assert s != null;
        return new Image(s);
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

    //ritorna una lista con gli username degli altri giocatori
    private ArrayList<String> catchOtherPlayerName(ArrayList<String> list){
        ArrayList<String> result=new ArrayList<>();
        for (String s : list) {
            if (!s.equals(clientState.getMyUsername())) {
                result.add(s);
            }
        }
        return result;
    }





    //ritorna il Label otherPlayerPointsLabel corretto
    private Label catchOtherPlayerPointsLabel(String username){
        if (otherPlayerLabel1.getText().equals(username)){
            return otherPlayerPointsLabel1;
        }
        else if (otherPlayerLabel2.getText().equals(username)){
            return otherPlayerPointsLabel2;
        }
        else if (otherPlayerLabel3.getText().equals(username)){
            return otherPlayerPointsLabel3;
        }
        else {
            return null;
        }
    }

    //aggiorna i punti dell'altro giocatore
    private void updateOtherPlayerPointsLabel(String username){
        int points=clientState.getAllPublicPoints().get(username);

        Objects.requireNonNull(catchOtherPlayerPointsLabel(username)).setText(username+"\npoints are: "+points);
    }




    private void reinitializeBoard(){
        for (int i = 1; i < Define.NUMBEROFROWS_BOARD.getI()+1; i++) {
            for (int j = 1; j < Define.NUMBEROFCOLUMNS_BOARD.getI()+1; j++) {

                int finalJ = j;
                int finalI = i;
                boardGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node)== finalJ && GridPane.getRowIndex(node)== finalI);

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



    private GridPane catchOtherPlayerBookshelfGrid(String username){
        if (otherPlayerLabel1.getText().equals(username)){
            return otherPlayerBookshelfGrid1;
        }
        else if (otherPlayerLabel2.getText().equals(username)){
            return otherPlayerBookshelfGrid2;
        }
        else if (otherPlayerLabel3.getText().equals(username)){
            return otherPlayerBookshelfGrid3;
        }
        else {
            return null;
        }
    }
    //aggiorna le bookshelf degli altri giocatori
    private void updateOtherBookshelf(String username){
        GridPane pane=catchOtherPlayerBookshelfGrid(username);
        Matrix bookshelf=clientState.getAllBookshelf().get(username);
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                if (!bookshelf.getTile(i , j ).equals(Tiles.EMPTY)) {
                    ImageView tile = setTiles(bookshelf.getTile(i, j));
                    tile.setFitWidth(20);
                    tile.setFitHeight(20);
                    assert pane != null;
                    pane.add(tile, j, i);
                }
            }
        }
    }
    //aggiorna la mia bookshelf
    private void updateMyBookshelf() {
        Matrix bookshelf=clientState.getMyBookshelf();
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                if (!bookshelf.getTile(i , j ).equals(Tiles.EMPTY)) {
                    myBookshelfGrid.add(setTiles(bookshelf.getTile(i, j)), j, i);
                }
            }
        }
    }









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
                    ImageView imageView=(ImageView) click;
                    imageView.setStyle("-fx-opacity: 0.5");
                    click.setDisable(true);
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

            pointsMessage.setText(clientState.getMyUsername());
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
            //disabilita la board
            boardGrid.getChildren().forEach(node -> node.setDisable(false));
            boardGrid.setDisable(true);
            state = State.SWITCH;

        }
    }
    @Override
    public void showError(String error, Stage stage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
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

    //annulla la selezione delle tessere selezionate
    @FXML
    private void rollbackClick(ActionEvent actionEvent){
        removeTiles=new ArrayList<>();
        boardGrid.getChildren().forEach(node -> {
            node.setStyle("-fx-opacity: 1");
            node.setDisable(false);
        });
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
        intArrayMessage.setText(clientState.getMyUsername());
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

        correctSelectColumn();

        boardGrid.setVisible(true);

        state = State.ADD;
    }

    private void correctSelectColumn(){
        int i=clientState.getSelectedTiles().size();
        ArrayList<Integer> column=clientState.checkFreeColumn(i);

        //controllo se le colonne vanno bene
        if (column.contains(0)){
            column1.setVisible(true);
            column1.setDisable(false);
        }
        if (column.contains(1)){
            column2.setVisible(true);
            column2.setDisable(false);
        }
        if (column.contains(2)){
            column3.setVisible(true);
            column3.setDisable(false);
        }
        if (column.contains(3)){
            column4.setVisible(true);
            column4.setDisable(false);
        }
        if (column.contains(4)){
            column5.setVisible(true);
            column5.setDisable(false);
        }
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
        intMessage.setText(clientState.getMyUsername());
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
        intMessage.setText(clientState.getMyUsername());
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
        intMessage.setText(clientState.getMyUsername());
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
        intMessage.setText(clientState.getMyUsername());
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
        intMessage.setText(clientState.getMyUsername());
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

    //invia i messaggi al server
    @FXML
    private void enterChatClick(ActionEvent actionEvent){
        enterChat();
    }
    @FXML
    private void enterChatEnter(KeyEvent event){
        if (event.getCode()== KeyCode.ENTER) {
            enterChat();
        }
    }
    private void enterChat(){
        //lettura messaggio e a chi inviarlo
        String message=sendMessage.getText();
        String receiver=selectChat.getValue();


        if (!message.isEmpty()){
            if (receiver.equals("ALL")){
                //chat a tutti
                ChatMessage chatMessage=new ChatMessage(clientState.getMyUsername(), message);
                chatMessage.setType(MessageTypes.CHAT);

                guiController.sendMessage(chatMessage);
            }
            else {
                //chat a username specifico
                ChatMessage chatMessage=new ChatMessage(clientState.getMyUsername(), message, receiver);
                chatMessage.setType(MessageTypes.CHAT);

                guiController.sendMessage(chatMessage);
            }
        }
        sendMessage.clear();
    }

    //quale chat mostrare
    @FXML
    private void selectWhichChatToShow(ActionEvent actionEvent){
        String chatToShow=selectChat.getValue();
        //nascondile tutte
        publicChatBox.setVisible(false);
        otherPlayerChatBox1.setVisible(false);
        otherPlayerChatBox2.setVisible(false);
        otherPlayerChatBox3.setVisible(false);

        //fai vedere solo quella selezionata
        if (chatToShow.equals("ALL")){
            publicChatBox.setVisible(true);
            chatScroll.setContent(publicChatBox);
            chatScroll.setFitToWidth(true);
        }
        else if (chatToShow.equals(otherPlayerLabel1.getText())){
            otherPlayerChatBox1.setVisible(true);
            chatScroll.setContent(otherPlayerChatBox1);
            chatScroll.setFitToWidth(true);
        }
        else if (chatToShow.equals(otherPlayerLabel2.getText())){
            otherPlayerChatBox2.setVisible(true);
            chatScroll.setContent(otherPlayerChatBox2);
            chatScroll.setFitToWidth(true);
        }
        else if (chatToShow.equals(otherPlayerLabel3.getText())){
            otherPlayerChatBox3.setVisible(true);
            chatScroll.setContent(otherPlayerChatBox3);
            chatScroll.setFitToWidth(true);
        }
    }

    private VBox chatVBox(String username){
        if (username==null){
            return publicChatBox;
        }
        else {
            if (username.equals(otherPlayerLabel1.getText())){
                return otherPlayerChatBox1;
            }
            else if (username.equals(otherPlayerLabel2.getText())){
                return otherPlayerChatBox2;
            }
            else if (username.equals(otherPlayerLabel3.getText())){
                return otherPlayerChatBox3;
            }
            else {
                return null;
            }
        }
    }

    //aggiorna la chat pubblica
    private void updatePublicChat(ChatMessage message){
        Label messageLabel=new Label();
        messageLabel.setPrefWidth(250);
        //è il mio messaggio
        if (message.getText().equals(clientState.getMyUsername())){
            messageLabel.setText(message.getMessage());

            messageLabel.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setStyle("-fx-background-color: #d7fad1");
            messageLabel.setWrapText(true);
            if (messageLabel.getFont().getSize()*messageLabel.getText().length()>250){
                messageLabel.setPrefWidth(messageLabel.getFont().getSize()*messageLabel.getText().length());
            }
        }
        else {
            messageLabel.setText(message.getConversation());

            messageLabel.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setStyle("-fx-background-color: #fdfdfd");
            messageLabel.setWrapText(true);
            if (messageLabel.getFont().getSize()*messageLabel.getText().length()>250){
                messageLabel.setPrefWidth(messageLabel.getFont().getSize()*messageLabel.getText().length());
            }
        }
        Objects.requireNonNull(chatVBox(message.getReceivingUsername())).getChildren().add(messageLabel);

        if (!selectChat.getValue().equals("ALL")){
            //popup ti è arrivato un messaggio
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New message");
            alert.setHeaderText("New Message");
            alert.setContentText("You have a new Public Message");
            alert.initOwner(guiController.getStage());
            alert.showAndWait();
        }

    }

    //aggiorna la chat privata
    private void updatePrivateChat(ChatMessage message){
        Label messageLabel=new Label();
        messageLabel.setPrefWidth(250);

        if (message.getText().equals(clientState.getMyUsername())){
            messageLabel.setText(message.getMessage());

            messageLabel.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setStyle("-fx-background-color: #d7fad1");
            messageLabel.setWrapText(true);
            if (messageLabel.getFont().getSize()*messageLabel.getText().length()>250){
                messageLabel.setPrefWidth(messageLabel.getFont().getSize()*messageLabel.getText().length());
            }

            Objects.requireNonNull(chatVBox(message.getReceivingUsername())).getChildren().add(messageLabel);
        }
        else {
            messageLabel.setText(message.getConversation());

            messageLabel.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setStyle("-fx-background-color: #fdfdfd");
            messageLabel.setWrapText(true);
            if (messageLabel.getFont().getSize()*messageLabel.getText().length()>250){
                messageLabel.setPrefWidth(messageLabel.getFont().getSize()*messageLabel.getText().length());
            }

            Objects.requireNonNull(chatVBox(message.getText())).getChildren().add(messageLabel);

            if (!selectChat.getValue().equals(message.getText())){
                //popup ti è arrivato un messaggio
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New message");
                alert.setHeaderText("New Message");
                alert.setContentText("You have a new Public Message");
                alert.initOwner(guiController.getStage());
                alert.showAndWait();
            }
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case ("board") -> Platform.runLater(() -> {
                if(checkResetPoint==null || clientState.getBoard().getTile(checkResetPoint).equals(Tiles.EMPTY)){
                    updateBoard();
                }
                else {
                    reinitializeBoard();
                    initializeBoardGrid();
                }
            });
            case ("selectedTiles") -> Platform.runLater(this::updateSelectedTiles);
            case ("bookshelf") -> Platform.runLater(()-> updateBookshelf((String) evt.getOldValue()));
            case ("publicPoints") -> Platform.runLater(() -> {
                updateAllPlayerPoints();
                updateClassification();
            });
            case ("privatePoints") -> Platform.runLater(() -> {
                updateMyPointsLabel();
                updateClassification();
            });
            case ("currPlayer") ->
                    Platform.runLater(() -> {
                        updateCurrPlayer();
                        if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
                            boardGrid.setDisable(false);
                        }
                    });
            case ("commonObjPoints") -> {
                String player=clientState.getCurrentPlayer();
                ArrayList<Integer> old=clientState.getOldCommonObjectivePoints();
                Platform.runLater(() -> {
                    commonObjectivePoint1.getImage().cancel();
                    commonObjectivePoint2.getImage().cancel();
                    updateCommonObjectivePoints();
                    updatePlayerCommonObjectivePointImage(player, old);
                });
                clientState.setOldCommonObjectivePoints(clientState.getCommonObjectivePoints());
            }
            case ("publicChat") -> {
                ChatMessage chatMessage=(ChatMessage) evt.getNewValue();
                clientState.getChatController().getPublicChat().addMessage(chatMessage);
                Platform.runLater(() -> updatePublicChat(chatMessage));
            }
            case ("privateChat") -> {
                ChatMessage chatMessage=(ChatMessage) evt.getNewValue();
                if (chatMessage.getText().equals(clientState.getMyUsername())){
                    clientState.getChatController().getPrivateChat(chatMessage.getReceivingUsername()).addMessage(chatMessage);
                }
                else {
                    clientState.getChatController().getPrivateChat(chatMessage.getText()).addMessage(chatMessage);
                }
                Platform.runLater(() -> updatePrivateChat(chatMessage));
            }
        }
    }

}


//TODO mostrare popup hai n nuovi messaggi
//TODO controllare private/public message
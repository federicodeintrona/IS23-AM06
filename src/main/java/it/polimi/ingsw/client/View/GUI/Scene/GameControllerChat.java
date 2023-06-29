package it.polimi.ingsw.client.View.GUI.Scene;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.View.GUI.GUIController;
import it.polimi.ingsw.client.View.GUI.GUIControllerStatic;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.Tile;
import it.polimi.ingsw.utils.Tiles;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
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
import java.util.List;
import java.util.*;

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


//GRAPHIC ELEMENTS OF THE SCENE
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
     * Default constructor
     */
    public GameControllerChat() {
    }



//INITIALIZE
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
        //all players' graphic content
        initializeOtherPlayer();
        //my points
        updateMyPointsLabel();
        //all players' points
        updateAllPlayerPoints();

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
        //initialize all common objective's point
        initializeCommonObjPoint();
        //current player
        initializeCurrPlayer();
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
                    boardGrid.add(setTiles(matrix.getFullTile(i-1, j-1)), j, i); //column - row
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

    /**
     * Method to initialize the personal objective.
     *
     * @throws FileNotFoundException signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    private void initializePersonalObjectiveImageView() throws FileNotFoundException {
        //cathc the path of the personal objective and set it
        String path= "/images/personal_goal_cards/Personal_Goals"+clientState.getMyPersonalObjectiveInt()+".png";
        personalObjectiveImageView.setImage(getImage(path));
        personalObjectiveImageView.setPreserveRatio(true);
        personalObjectiveImageView.setFitWidth(152);
        personalObjectiveImageView.setFitHeight(229);
    }

    /**
     * Method to initialize all graphic content in the scene of other players.
     */
    private void initializeOtherPlayer(){
        //lista con gli username degli altri giocatori
        ArrayList<String> otherPlayerList=catchOtherPlayerName(clientState.getAllUsername());

        //initialize the 1st other player - 2 players' game
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
            //initialize 2nd other player - 3 players' game
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
            //initialize 3rd other player - 4 players' game
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
        // Particular initialization in a 2 players game
        if (clientState.getAllUsername().size() == 2){

            //initialization
            selectChat.setValue("ALL");
            chatScroll.setContent(publicChatBox);
            chatScroll.setFitToWidth(true);
            return;
        }

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

    /**
     * Method to initialize the current player.
     */
    private void initializeCurrPlayer(){
        String string;
        if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
            //update label
            string="It is YOUR turn";
            turnLabel.setText(string);
        }
        else {
            //update label
            string="It is "+clientState.getCurrentPlayer()+" turn";
            turnLabel.setText(string);
        }
    }

    /**
     * Method to initialize all player's common objective points.
     */
    private void initializeCommonObjPoint(){
        if (clientState.getCommonMap1()!=null){
            for (String player: clientState.getCommonMap1().keySet()){
                String path = "/images/scoring_tokens/scoring_"+clientState.getCommonMap1().get(player)+".jpg";
                Objects.requireNonNull(catchCommonObjPointONE(player)).setImage(getImage(path));
            }
        }
        if (clientState.getCommonMap2()!=null){
            for (String player: clientState.getCommonMap2().keySet()){
                String path = "/images/scoring_tokens/scoring_"+clientState.getCommonMap2().get(player)+".jpg";
                Objects.requireNonNull(catchCommonObjPointTWO(player)).setImage(getImage(path));
            }
        }
    }



//REINITIALIZE
    /**
     * Method to reinitialize the board.
     * <p>
     * Removes all tiles still on the board.
     */
    private void reinitializeBoard(){
        for (int i = 1; i < Define.NUMBEROFROWS_BOARD.getI()+1; i++) {
            for (int j = 1; j < Define.NUMBEROFCOLUMNS_BOARD.getI()+1; j++) {

                //remove all node from the boardGrid
                int finalJ = j;
                int finalI = i;
                boardGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node)== finalJ && GridPane.getRowIndex(node)== finalI);

            }
        }
    }



//UPDATE
    /**
     * Method to update the common objective's token (points assignable from completed objective).
     */
    private void updateCommonObjectivePoints(){
        ArrayList<Integer> commonGoal= clientState.getCommonObjectivePoints();

        //catch the path of the 1st common objective's token and set it
        String path = "/images/scoring_tokens/scoring_"+commonGoal.get(0)+".jpg";
        commonObjectivePoint1.setImage(getImage(path));
        //catch the path of the 2nd common objective's token and set it
        String path1 = "/images/scoring_tokens/scoring_"+commonGoal.get(1)+".jpg";
        commonObjectivePoint2.setImage(getImage(path1));
    }

    /**
     * Method to update the client's points (common + private points).
      */
    private void updateMyPointsLabel(){
        String myPoints="My Points are: "+clientState.getMyPoints();

        myPointsLabel.setText(myPoints);
    }

    /**
     * Method to update the other players' points (only the public points).
      */
    private void updateAllPlayerPoints(){
        for (String player : clientState.getAllPublicPoints().keySet()){
            if (!player.equals(clientState.getMyUsername())){
                //show the update the player's points
                updateOtherPlayerPointsLabel(player);
            }
        }
    }

    /**
     * Method to show who is the current player.
     * <ul>
     *     <li>Show a pop up if is the client's turn;</li>
     *     <li>update the label to show the username of the current player in the game scene.</li>
     * </ul>
     */
    private void updateCurrPlayer() {
        String string;
        if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
            //update label
            string="It is YOUR turn";
            turnLabel.setText(string);

            //pop up - it is your turn
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update turn");
            alert.setHeaderText("Current game turn");
            alert.setContentText(string);
            alert.initOwner(guiController.getStage());
            alert.showAndWait();
        }
        else {
            //update label
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

    /**
     * Method to update the board.
     */
    private void updateBoard(Matrix oldBoard) {
        Matrix matrix=clientState.getBoard();
        for (int i = 1; i < Define.NUMBEROFROWS_BOARD.getI()+1; i++) {
            for (int j = 1; j < Define.NUMBEROFCOLUMNS_BOARD.getI()+1; j++) {
                if (!matrix.getTile(i-1, j-1).equals(oldBoard.getTile(i-1, j-1))){
                    //add tile
                    if (oldBoard.getTile(i-1, j-1).equals(Tiles.EMPTY) && !matrix.getTile(i-1, j-1).equals(Tiles.EMPTY)){
                        boardGrid.add(setTiles(matrix.getFullTile(i-1, j-1)), j, i);
                    }
                    //remove tile
                    else {
                        int finalJ = j;
                        int finalI = i;
                        //remove the tile of interest
                        boardGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node)== finalJ && GridPane.getRowIndex(node)== finalI);
                    }
                }
            }
        }
    }

    /**
     * Method to update all players' bookshelf.
     *
      * @param username the username of the player interested.
     */
    private void updateBookshelf(String username){
        //update my bookshelf
        if (username.equals(clientState.getMyUsername())){
            updateMyBookshelf();
        }
        //update other player's bookshelf
        else {
            updateOtherBookshelf(username);
        }
    }

    /**
     * Method to update all the players' common objective points, the token with the point.
     *
     * @param player the username of the player that completed the common objective
     * @param old the old common objective's points
     */
    private void updatePlayerCommonObjectivePointImage(String player, ArrayList<Integer> old){
        int point=old.get(commonObjectivePointPlayerImage(old));

        //catch the path of the token to shown
        String path = "/images/scoring_tokens/scoring_"+point+".jpg";

        Objects.requireNonNull(catchPlayerCommonObjectivePointImage(player, commonObjectivePointPlayerImage(old))).setImage(getImage(path));
    }

    /**
     * Method to update the player's point Label of the player of interest.
     *
     * @param username the username of the player of interest.
     */
    private void updateOtherPlayerPointsLabel(String username){
        int points=clientState.getAllPublicPoints().get(username);

        Objects.requireNonNull(catchOtherPlayerPointsLabel(username)).setText(username+"\npoints are: "+points);
    }

    /**
     * Method to update the client's bookshelf.
     */
    private void updateMyBookshelf() {
        //catch my bookshelf's Matrix
        Matrix bookshelf=clientState.getMyBookshelf();

        //update the bookshelf - add tiles
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                if (!bookshelf.getTile(i , j ).equals(Tiles.EMPTY)) {
                    myBookshelfGrid.add(setTiles(bookshelf.getFullTile(i, j)), j, i);
                }
            }
        }
    }

    /**
     * Method that update the bookshelf of the player of interest.
     *
     * @param username the username of the player of interest.
     */
    private void updateOtherBookshelf(String username){
        //catch the correct bookshelf's GridPane
        GridPane pane=catchOtherPlayerBookshelfGrid(username);
        //catch the correct bookshelf's Matrix
        Matrix bookshelf=clientState.getAllBookshelf().get(username);

        //update the bookshelf - add the tile in the correct position
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                if (!bookshelf.getTile(i , j ).equals(Tiles.EMPTY)) {
                    ImageView tile = setTiles(bookshelf.getFullTile(i, j));
                    tile.setFitWidth(20);
                    tile.setFitHeight(20);
                    assert pane != null;
                    pane.add(tile, j, i);
                }
            }
        }
    }

    /**
     * Method to show the selected tiles.
     */
    private void updateSelectedTiles() {
        //if it is my turn
        if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())) {
            //make the Dialog visible
            selectedTilesDialog.setVisible(true);
            selectedTilesDialog.setDisable(false);
            selectedTilesDialog.setStyle("-fx-background-color: null");

            //make the button visible to finish the switch and go into the column selection
            endSwitch.setVisible(true);
            endSwitch.setDisable(false);

            //make all selected tiles visible
            selectedTiles1.setDisable(false);
            selectedTiles1.setVisible(true);
            selectedTiles2.setDisable(false);
            selectedTiles2.setVisible(true);
            selectedTiles3.setDisable(false);
            selectedTiles3.setVisible(true);

            //make the board invisible
            boardGrid.setDisable(true);
            boardGrid.setVisible(false);

            //set the Image of the selected tiles
            switch (clientState.getSelectedTiles().size()) {
                case 1 -> {
                    selectedTiles1.setImage(getImage(getTile(0)));
                    selectedTiles2.setDisable(true);
                    selectedTiles2.setVisible(false);
                    selectedTiles3.setDisable(true);
                    selectedTiles3.setVisible(false);
                }
                case 2 -> {
                    selectedTiles1.setImage(getImage(getTile(0)));
                    selectedTiles2.setImage(getImage(getTile(1)));
                    selectedTiles3.setDisable(true);
                    selectedTiles3.setVisible(false);
                }
                case 3 -> {
                    selectedTiles1.setImage(getImage(getTile(0)));
                    selectedTiles2.setImage(getImage(getTile(1)));
                    selectedTiles3.setImage(getImage(getTile(2)));
                }
            }
        }
    }

    /**
     * Method to update the public chat.
     *
     * @param message the message received.
     */
    private void updatePublicChat(ChatMessage message){
        //create the new message's Label
        Label messageLabel=new Label();
        messageLabel.setPrefWidth(250);

        //is it the message I sent?
        if (message.getText().equals(clientState.getMyUsername())){
            messageLabel.setText(message.getMessage());

            //alignment, style and dimension of the message
            messageLabel.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setStyle("-fx-background-color: #d7fad1");

            //fit the message size
        }
        else {
            messageLabel.setText(message.getConversation());

            //alignment, style and dimension of the message
            messageLabel.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setStyle("-fx-background-color: #fdfdfd");

            //fit the message size
        }
        messageLabel.setWrapText(true);
        if (messageLabel.getFont().getSize()*messageLabel.getText().length()>250){
            messageLabel.setPrefWidth(messageLabel.getFont().getSize()*messageLabel.getText().length());
        }

        //add the message to the correct chat Vbox
        Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).getChildren().add(messageLabel);


        // Forced update of the graphic interface
        Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).requestLayout();

        // Adding a Listener for the PublicChat automatic sliding
        Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                // Gets the latest element added to the VBOX
                Node lastMessage = Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).getChildren().get(Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).getChildren().size() - 1);

                // Automatic sliding at the end of the VBOX
                if (chatScroll != null && lastMessage != null) {
                    double containerHeight = Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).getHeight();
                    double scrollHeight = chatScroll.getContent().getBoundsInLocal().getHeight();

                    // Set the vertical position based on the height of the containers
                    double vvalue = Math.max(0, (lastMessage.getBoundsInParent().getMaxY() - containerHeight + scrollHeight) / scrollHeight);
                    chatScroll.setVvalue(vvalue);
                }
            });
        });

        if (!selectChat.getValue().equals("ALL")){
            //pop up - you got a message
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New public message");
            alert.setHeaderText("New public Message");

            if (clientState.getChatController().getPublicChat().getUnReadMessages() == 1)
                alert.setContentText("You have a new Public Message");
            else
                alert.setContentText("You have " + clientState.getChatController().getPublicChat().getUnReadMessages() + " new Public Messages");

            alert.initOwner(guiController.getStage());
            alert.showAndWait();
        }

    }

    /**
     * Method to update the private chat.
     *
     * @param message the message received.
     */
    private void updatePrivateChat(ChatMessage message){
        //create the new message's Label
        Label messageLabel=new Label();
        messageLabel.setPrefWidth(250);

        //is it the message I sent?
        if (message.getText().equals(clientState.getMyUsername())){
            messageLabel.setText(message.getMessage());

            //alignment, style and dimension of the message
            messageLabel.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setStyle("-fx-background-color: #d7fad1");
            messageLabel.setWrapText(true);

            //fit the message size
            if (messageLabel.getFont().getSize()*messageLabel.getText().length()>250){
                messageLabel.setPrefWidth(messageLabel.getFont().getSize()*messageLabel.getText().length());
            }

            //add the message to the correct chat Vbox
            Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).getChildren().add(messageLabel);

            // Adding a Listener for the PrivateChat automatic sliding
            Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                Platform.runLater(() -> {
                    // Gets the latest element added to the VBOX
                    Node lastMessage = Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).getChildren().get(Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).getChildren().size() - 1);

                    // Automatic sliding at the end of the VBOX
                    if (chatScroll != null && lastMessage != null) {
                        double containerHeight = Objects.requireNonNull(catchChatVBox(message.getReceivingUsername())).getHeight();
                        double scrollHeight = chatScroll.getContent().getBoundsInLocal().getHeight();

                        // Set the vertical position based on the height of the containers
                        double vvalue = Math.max(0, (lastMessage.getBoundsInParent().getMaxY() - containerHeight + scrollHeight) / scrollHeight);
                        chatScroll.setVvalue(vvalue);
                    }
                });
            });
        }
        else {
            messageLabel.setText(message.getConversation());

            //alignment, style and dimension of the message
            messageLabel.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setStyle("-fx-background-color: #fdfdfd");
            messageLabel.setWrapText(true);

            //fit the message size
            if (messageLabel.getFont().getSize()*messageLabel.getText().length()>250){
                messageLabel.setPrefWidth(messageLabel.getFont().getSize()*messageLabel.getText().length());
            }

            //add the message to the correct chat Vbox
            Objects.requireNonNull(catchChatVBox(message.getText())).getChildren().add(messageLabel);

            // Adding a Listener for the PrivateChat automatic sliding
            Objects.requireNonNull(catchChatVBox(message.getText())).layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                Platform.runLater(() -> {
                    // Gets the latest element added to the VBOX
                    Node lastMessage = Objects.requireNonNull(catchChatVBox(message.getText())).getChildren().get(Objects.requireNonNull(catchChatVBox(message.getText())).getChildren().size() - 1);

                    // Automatic sliding at the end of the VBOX
                    if (chatScroll != null && lastMessage != null) {
                        double containerHeight = Objects.requireNonNull(catchChatVBox(message.getText())).getHeight();
                        double scrollHeight = chatScroll.getContent().getBoundsInLocal().getHeight();

                        // Set the vertical position based on the height of the containers
                        double vvalue = Math.max(0, (lastMessage.getBoundsInParent().getMaxY() - containerHeight + scrollHeight) / scrollHeight);
                        chatScroll.setVvalue(vvalue);
                    }
                });
            });
            if (!selectChat.getValue().equals(message.getText())){
                //pop up - you got a message
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New private message");
                alert.setHeaderText("New private Message");
                if (clientState.getChatController().getPrivateChat(message.getText()).getUnReadMessages() == 1)
                    alert.setContentText("You have a new Private Message from " + message.getText());
                else
                    alert.setContentText("You have " + clientState.getChatController().getPrivateChat(message.getText()).getUnReadMessages() + " new Private Messages from " + message.getText());
                alert.initOwner(guiController.getStage());
                alert.showAndWait();
            }
        }
    }



//CATCH
    /**
     * Method that returns the ImageView, related to the player, to which the points of the newly completed common goal must be added.
     *
     * @param username the username of the player that completed the common objective.
     * @param commonObjective the common objective that the player has completed.
     * @return the ImageView to update, of which you need to change the image shown.
     */
    private ImageView catchPlayerCommonObjectivePointImage(String username, int commonObjective){
        //return all image related to the 1st common objective
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
        //return all image related to the 2nd common objective
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
        //error
        return null;
    }

    /**
     * Method that returns the ArrayList that contains all username of other players.
     *
     * @param list the ArrayList of all players' username.
     * @return the ArrayList that contains all username of other players.
     */
    private ArrayList<String> catchOtherPlayerName(ArrayList<String> list){
        ArrayList<String> result=new ArrayList<>();

        for (String s : list) {
            //the current username is different to the client's username
            if (!s.equals(clientState.getMyUsername())) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Method that returns the player's point Label given the username.
     *
     * @param username the username of interest.
     * @return the player's point Label.
     */
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

    /**
     * Method that returns the player's bookshelf GridPane given the username.
     *
     * @param username the username of interest.
     * @return the player's bookshelf GridPane.
     */
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

    /**
     * Method that returns the selected chat.
     *
     * @param username the name of the selected chat (ALL chat or chat with another player).
     * @return the selected chat's VBox.
     */
    private VBox catchChatVBox(String username){
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

    /**
     * Method that returns the first common objective points of the selected player.
     *
     * @param username the username of the player.
     * @return the <i>ImageView</i> of the player correct.
     */
    private ImageView catchCommonObjPointONE(String username){
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
        return null;
    }

    /**
     * Method that returns the second common objective points of the selected player.
     *
     * @param username the username of the player.
     * @return the <i>ImageView</i> of the player correct.
     */
    private ImageView catchCommonObjPointTWO(String username){
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
        return null;
    }


    /**
     * Method that returns the ImageView of the tile of interest.
     *
     * @param tile the tile of interest.
     * @return the ImageView of the tile of interest.
     */
    private ImageView setTiles(Tile tile){
        //randomize to select the tile
        String[] titles = tile.getTiles().getImage();
        String title = titles[tile.getImage()];

        //set the Image
        assert title != null;
        Image image = getImage(title);
        ImageView imageView = new ImageView(image);

        //set the dimension of ImageView
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        return imageView;
    }

    /**
     * Method that returns the Image related of the path of interest.
     *
     * @param path the patha of interest.
     * @return the Image related of the path of interest.
     */
    private Image getImage(String path){
        //create the InputStream of the path
        InputStream s = getClass().getResourceAsStream(path);

        //create and return the Image related of the path
        assert s != null;
        return new Image(s);
    }

    /**
     * Method used to sorted players based on how many points they scored - increasing.
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

    /**
     * Method that return the number of the common objective completed.
     *
     * @param old the old common objective points.
     * @return 0 if the common objective one has been completed 1 if the other one has been completed.
     */
    private int commonObjectivePointPlayerImage(ArrayList<Integer> old){
        if (!Objects.equals(clientState.getCommonObjectivePoints().get(0), old.get(0))){
            //the common objective one has been completed
            return 0;
        }
        else {
            //the common objective two has been completed
            return 1;
        }
    }



    /**
     * Method that returns the game state to the previous one if an error has occurred.
     */
    private void goBack(){

        switch (state){
            //Remove --> Add
            case REMOVE -> {
                //reactivate all column selection buttons
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
                //set the state to ADD
                state=State.ADD;
            }
            //Switch --> Remove
            case SWITCH -> {
                //wipe the selected tiles
                removeTiles=new ArrayList<>();
                //reactivate the board
                boardGrid.setDisable(false);
                //disable the confirmation button
                confirmationButton.setVisible(false);
                confirmationButton.setDisable(true);
                //remove opacity from all selected tiles
                boardGrid.getChildren().forEach(node -> node.setStyle("-fx-opacity: 1"));
                //set the state to REMOVE
                state=State.REMOVE;
            }
            //Add --> Switch
            case ADD -> {
                //wipe the order tiles
                orderTiles=new ArrayList<>();
                //reactivate the tile switch window
                selectedTilesDialog.setVisible(true);
                selectedTilesDialog.setDisable(false);
                selectedTilesDialog.setStyle("-fx-background-color: null");
                //set the state to SWITCH
                state=State.SWITCH;
            }
        }

    }



//ACTION
    /**
     * Method that manages the removal of one tile at a time from the board.
     *
     * @param event the mouse event, one tile has been clicked.
     */
    @FXML
    private void removeTilesClick(MouseEvent event){
        //catch the correct board's position of the click
        Node click=event.getPickResult().getIntersectedNode();
        Integer colmnIndex=GridPane.getColumnIndex(click);
        Integer rowIndex=GridPane.getRowIndex(click);

        //change the scene after the event
        if(colmnIndex!=null&&rowIndex!=null) {
            if(clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
                //less than 3 tiles were selected - maximum number of tiles selectable
                if(removeTiles.size()<3){
                    //add the tile to removeTiles' List
                    removeTiles.add(new Point(rowIndex - 1, colmnIndex - 1));

                    //enable the selection confirmation button
                    confirmationButton.setVisible(true);
                    confirmationButton.setDisable(false);
                    //enables the selection rollback button
                    rollbackButton.setVisible(true);
                    rollbackButton.setDisable(false);

                    //set the opacity of the selected tile
                    ImageView imageView=(ImageView) click;
                    imageView.setStyle("-fx-opacity: 0.5");

                    //disable another click to this tile
                    click.setDisable(true);
                }
            }
        }

    }

    /**
     * Method that manages the confirmation of the selected tiles.
     */
    @FXML
    private void confirmClick(){
        //you do not select any tile
        if (removeTiles.isEmpty()){
            showError("Select at least 1 tile",guiController.getStage());
        }
        else{
            //create PointsMessage to send to the server
            PointsMessage pointsMessage=new PointsMessage();

            //set the message
            pointsMessage.setText(clientState.getMyUsername());
            pointsMessage.setType(MessageTypes.REMOVE_FROM_BOARD);
            pointsMessage.setTiles(removeTiles);
            guiController.sendMessage(pointsMessage);

            //I save a position that I know is empty and then check it when board reinitialization needs to be done
            checkResetPoint=removeTiles.get(0);

            removeTiles=new ArrayList<>();

            //disable the confirmation button
            confirmationButton.setVisible(false);
            confirmationButton.setDisable(true);
            //disables the rollback button
            rollbackButton.setVisible(false);
            rollbackButton.setDisable(true);
            //disables the board
            boardGrid.getChildren().forEach(node -> node.setDisable(false));
            boardGrid.setDisable(true);

            //change state - Remove --> Switch
            state = State.SWITCH;

        }
    }

    /**
     * Method that manages the rollback of the selected tiles.
     */
    @FXML
    private void rollbackClick(){
        //create a new empty removeTiles' List
        removeTiles=new ArrayList<>();

        //reactivate all previously selected tiles
        boardGrid.getChildren().forEach(node -> {
            node.setStyle("-fx-opacity: 1");
            node.setDisable(false);
        });

        //hide the confirmation and rollback buttons
        rollbackButton.setVisible(false);
        rollbackButton.setDisable(true);
        confirmationButton.setVisible(false);
        confirmationButton.setDisable(true);
    }


    /**
     * Method that manages the selection and change order of the selected tile.
     */
    @FXML
    private void selectTile1(){
        orderTiles.add(1);

        //disable the selection of this card
        selectedTiles1.setDisable(true);
        selectedTiles1.setStyle("-fx-opacity: 0.5");

        //disable the end of the switch because you selected another tile
        endSwitch.setVisible(false);
        endSwitch.setDisable(true);

        //if you have selected all the tiles the button for confirmation is shown
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }

    /**
     * Method that manages the selection and change order of the selected tile.
     */
    @FXML
    private void selectTile2(){
        orderTiles.add(2);

        //disable the selection of this card
        selectedTiles2.setDisable(true);
        selectedTiles2.setStyle("-fx-opacity: 0.5");

        //disable the end of the switch because you selected another tile
        endSwitch.setVisible(false);
        endSwitch.setDisable(true);

        //if you have selected all the tiles the button for confirmation is shown
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }

    /**
     * Method that manages the selection and change order of the selected tile.
     */
    @FXML
    private void selectTile3(){
        orderTiles.add(3);

        //disable the selection of this card
        selectedTiles3.setDisable(true);
        selectedTiles3.setStyle("-fx-opacity: 0.5");

        //disable the end of the switch because you selected another tile
        endSwitch.setVisible(false);
        endSwitch.setDisable(true);

        //if you have selected all the tiles the button for confirmation is shown
        if (orderTiles.size()==clientState.getSelectedTiles().size()){
            confirmSelected.setVisible(true);
            confirmSelected.setDisable(false);
        }
    }

    /**
     * Method that manages the confirmation of the tiles order.
     */
    @FXML
    private void confirmsSelectedClick(){
        //reactivate the tiles to be reversed
        selectedTiles1.setDisable(false);
        selectedTiles1.setVisible(true);
        selectedTiles1.setStyle("-fx-opacity: 1");
        selectedTiles2.setDisable(false);
        selectedTiles2.setVisible(true);
        selectedTiles2.setStyle("-fx-opacity: 1");
        selectedTiles3.setDisable(false);
        selectedTiles3.setVisible(true);
        selectedTiles3.setStyle("-fx-opacity: 1");

        //hide the selection confirmation button
        confirmSelected.setVisible(false);
        confirmSelected.setDisable(true);

        selectedTilesDialog.setVisible(false);
        selectedTilesDialog.setDisable(true);

        //create the IntArrayMessage to send to the server
        IntArrayMessage intArrayMessage=new IntArrayMessage();

        //set the message
        intArrayMessage.setText(clientState.getMyUsername());
        intArrayMessage.setType(MessageTypes.SWITCH_PLACE);
        intArrayMessage.setIntegers(orderTiles);

        //send the message
        guiController.sendMessage(intArrayMessage);
        orderTiles=new ArrayList<>();

        //make the button visible to finish the switch and go into the column selection
        endSwitch.setVisible(true);
        endSwitch.setDisable(false);
    }

    /**
     * Method that manages the confirmation of the tiles order,
     * the player is sure of the selected order and wants to place the tiles in the bookshelf.
     */
    @FXML
    private void endSwitchClick(){
        //hide the endSwitch button
        endSwitch.setVisible(false);
        endSwitch.setDisable(true);

        //hide the dialog
        selectedTilesDialog.setVisible(false);
        selectedTilesDialog.setDisable(true);

        //make the column buttons visible where you can enter the number of tiles selected
        correctSelectColumn();

        //show again the board
        boardGrid.setVisible(true);

        //change state - Switch --> Add
        state = State.ADD;
    }


    /**
     * Method that manages the adding of tiles in the column.
     */
    @FXML
    private void addToColumn1(){
        addToColumn();

        //create the IntMessage to send to the server
        IntMessage intMessage=new IntMessage();

        //set the message
        intMessage.setText(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(0);

        //send the message
        guiController.sendMessage(intMessage);
    }

    /**
     * Method that manages the adding of tiles in the column.
     */
    @FXML
    private void addToColumn2(){
        addToColumn();

        //create the IntMessage to send to the server
        IntMessage intMessage=new IntMessage();

        //set the message
        intMessage.setText(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(1);

        //send the message
        guiController.sendMessage(intMessage);
    }

    /**
     * Method that manages the adding of tiles in the column.
     */
    @FXML
    private void addToColumn3(){
        addToColumn();

        //create the IntMessage to send to the server
        IntMessage intMessage=new IntMessage();

        //set the message
        intMessage.setText(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(2);

        //send the message
        guiController.sendMessage(intMessage);
    }

    /**
     * Method that manages the adding of tiles in the column.
     */
    @FXML
    private void addToColumn4(){
        addToColumn();

        //create the IntMessage to send to the server
        IntMessage intMessage=new IntMessage();

        //set the message
        intMessage.setText(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(3);

        //send the message
        guiController.sendMessage(intMessage);
    }

    /**
     * Method that manages the adding of tiles in the column.
     */
    @FXML
    private void addToColumn5(){
        addToColumn();

        //create the IntMessage to send to the server
        IntMessage intMessage=new IntMessage();

        //set the message
        intMessage.setText(clientState.getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(4);

        //send the message
        guiController.sendMessage(intMessage);
    }


    /**
     * Method to send the chat message to the server.
     */
    @FXML
    private void enterChatClick(){
        //send the message
        enterChat();
    }

    /**
     * Method to send the chat message to the server.
     *
     * @param event the event, enter key has been pressed.
     */
    @FXML
    private void enterChatEnter(KeyEvent event){
        //Has the enter key been pressed?
        if (event.getCode()== KeyCode.ENTER) {
            enterChat();
        }
    }

    /**
     * Method that manage which chat to show.
     *
      * @param actionEvent the event, selection of the chat to show.
     */
    @FXML
    private void selectWhichChatToShow(ActionEvent actionEvent){
        //read which chat to show
        String chatToShow=selectChat.getValue();

        //hide all chat
        publicChatBox.setVisible(false);
        otherPlayerChatBox1.setVisible(false);
        otherPlayerChatBox2.setVisible(false);
        otherPlayerChatBox3.setVisible(false);

        //show only the one chat selected
        if (chatToShow.equals("ALL")){
            publicChatBox.setVisible(true);
            chatScroll.setContent(publicChatBox);
            chatScroll.setFitToWidth(true);
            clientState.getChatController().getPublicChat().resetUnReadMessages();
        }
        else if (chatToShow.equals(otherPlayerLabel1.getText())){
            otherPlayerChatBox1.setVisible(true);
            chatScroll.setContent(otherPlayerChatBox1);
            chatScroll.setFitToWidth(true);
            clientState.getChatController().getPrivateChat(otherPlayerLabel1.getText()).resetUnReadMessages();
        }
        else if (chatToShow.equals(otherPlayerLabel2.getText())){
            otherPlayerChatBox2.setVisible(true);
            chatScroll.setContent(otherPlayerChatBox2);
            chatScroll.setFitToWidth(true);
            clientState.getChatController().getPrivateChat(otherPlayerLabel2.getText()).resetUnReadMessages();
        }
        else if (chatToShow.equals(otherPlayerLabel3.getText())){
            otherPlayerChatBox3.setVisible(true);
            chatScroll.setContent(otherPlayerChatBox3);
            chatScroll.setFitToWidth(true);
            clientState.getChatController().getPrivateChat(otherPlayerLabel3.getText()).resetUnReadMessages();
        }
    }



//ANCILLARY METHODS
    /**
     * Method to show the only column buttons where put the selected tiles.
     */
    private void correctSelectColumn(){
        //number of selected tiles
        int i=clientState.getSelectedTiles().size();
        //ArrayList that contains the number of the column where put the tiles
        ArrayList<Integer> column=clientState.checkFreeColumn(i);

        //check if the columns fit
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

    /**
     * Method to disable all the selected column buttons.
     */
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

        //change state - Add --> Remove
        state = State.REMOVE;
    }

    /**
     * Method to send the chat message to the server.
     * <p>
     * Read the written message, turns it into a ChatMessage and sends it to the server.
     */
    private void enterChat(){
        //read the written message and to whom to send it
        String message=sendMessage.getText();
        //is sent to the chat open at that time
        String receiver=selectChat.getValue();

        //there is a message written
        if (!message.isEmpty()){
            //send to all - public chat
            ChatMessage chatMessage;
            if (receiver.equals("ALL")){
                //create and set the chat message
                chatMessage = new ChatMessage(clientState.getMyUsername(), message);

                //send the ChatMessage
            }
            //send to a specify player
            else {
                //create and set the chat message
                chatMessage = new ChatMessage(clientState.getMyUsername(), message, receiver);

                //send the ChatMessage
            }
            chatMessage.setType(MessageTypes.CHAT);
            guiController.sendMessage(chatMessage);
        }
        //clear the TextField
        sendMessage.clear();
    }

    /**
     * <strong>Getter</strong> -> Return the path of the image of the tile in the selected tiles' ArrayList.
     *
     * @param i the position of the tile in the ArrayList of selected tiles.
     * @return the path of the tile in the selected tiles' ArrayList.
     */
    private String getTile (int i){
        return clientState.getSelectedTiles().get(i).getTiles().getImage()[
                clientState.getSelectedTiles().get(i).getImage()];
    }



//OVERRIDE
    /**
     * Method to show all the error.
     *
     * @param error the error message.
     * @param stage the stage where to show it.
     */
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

        //revert the state
        goBack();
    }

    /**
     * Method that receives updates from the server and updates the game scene.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            //update board
            case ("board") ->
                    Platform.runLater(() -> {
                        //if the board can not be reset
                        if(checkResetPoint==null || clientState.getBoard().getTile(checkResetPoint).equals(Tiles.EMPTY)){
                            updateBoard((Matrix) evt.getOldValue());
                        }
                        //if the board can be reset
                        else {
                            reinitializeBoard();
                            initializeBoardGrid();
                        }
                    });
            //show the selected tiles
            case ("selectedTiles") ->

                    Platform.runLater(this::updateSelectedTiles);
            //update bookshelf
            case ("bookshelf") ->
                    Platform.runLater(()->
                            updateBookshelf((String) evt.getOldValue()));
            //update public points
            case ("publicPoints") ->
                    Platform.runLater(() -> {
                        updateAllPlayerPoints();
                        updateClassification();
                    });
            //update private points
            case ("privatePoints") ->
                    Platform.runLater(() -> {
                        updateMyPointsLabel();
                        updateClassification();
                    });
            //update current player
            case ("currPlayer") ->
                    Platform.runLater(() -> {
                        updateCurrPlayer();
                        //if I am not the current player disable the board
                        if (clientState.getCurrentPlayer().equals(clientState.getMyUsername())){
                            boardGrid.setDisable(false);
                        }
                    });
            //update common objective points
            case ("commonObjPoints") -> {
                //catch the current player that has completed the common objective
                String player=clientState.getCurrentPlayer();
                //catch the old common objective points
                ArrayList<Integer> old=clientState.getOldCommonObjectivePoints();

                Platform.runLater(() -> {
                    //delete the current common objective points image
                    commonObjectivePoint1.getImage().cancel();
                    commonObjectivePoint2.getImage().cancel();
                    //update common objective points
                    updateCommonObjectivePoints();
                    //assign common objective points to the current player
                    updatePlayerCommonObjectivePointImage(player, old);
                });

                //set old common objective points
                clientState.setOldCommonObjectivePoints(clientState.getCommonObjectivePoints());
            }
            //update public chat
            case ("publicChat") -> {
                //catch chatMessage
                ChatMessage chatMessage=(ChatMessage) evt.getNewValue();
                //add message to chatController
                clientState.getChatController().getPublicChat().addMessage(chatMessage);

                if (!selectChat.getValue().equals("ALL"))
                    clientState.getChatController().getPublicChat().updateUnReadMessages();


                Platform.runLater(() ->
                        updatePublicChat(chatMessage));
            }
            //update private chat
            case ("privateChat") -> {
                //catch chatMessage
                ChatMessage chatMessage=(ChatMessage) evt.getNewValue();
                //add message to chatController
                if (chatMessage.getText().equals(clientState.getMyUsername())){
                    clientState.getChatController().getPrivateChat(chatMessage.getReceivingUsername()).addMessage(chatMessage);
                    if (!selectChat.getValue().equals(chatMessage.getText())){
                        clientState.getChatController().getPrivateChat(chatMessage.getReceivingUsername()).updateUnReadMessages();
                    }
                }
                else {
                    clientState.getChatController().getPrivateChat(chatMessage.getText()).addMessage(chatMessage);
                    if (!selectChat.getValue().equals(chatMessage.getText())){
                        clientState.getChatController().getPrivateChat(chatMessage.getText()).updateUnReadMessages();
                    }
                }

                Platform.runLater(() ->
                        updatePrivateChat(chatMessage));
            }
            //update disconnect / reconnect
            case ("notification") -> {
                String playerDis=(String) evt.getSource();
                String conOrDis=(String) evt.getNewValue();
                Platform.runLater(() ->
                        updateDisconnectPlayer(playerDis, conOrDis));
            }
        }
    }



    private Label catchOtherPlayerUsernameLabel(String username){
        if (otherPlayerLabel1.getText().equals(username)){
            return otherPlayerLabel1;
        }
        else if (otherPlayerLabel2.getText().equals(username)){
            return otherPlayerLabel2;
        }
        else if (otherPlayerLabel3.getText().equals(username)){
            return otherPlayerLabel3;
        }
        else {
            return null;
        }
    }

    private ImageView catchOtherPlayerPersonalImage(String username){
        if (otherPlayerLabel1.getText().equals(username)){
            return personal1;
        }
        else if (otherPlayerLabel2.getText().equals(username)){
            return personal2;
        }
        else if (otherPlayerLabel3.getText().equals(username)){
            return personal3;
        }
        else {
            return null;
        }
    }

    private ImageView catchotherPlayerBookshelfImage(String username){
        if (otherPlayerLabel1.getText().equals(username)){
            return otherPlayerImage1;
        }
        else if (otherPlayerLabel2.getText().equals(username)){
            return otherPlayerImage2;
        }
        else if (otherPlayerLabel3.getText().equals(username)){
            return otherPlayerImage3;
        }
        else {
            return null;
        }
    }



    private void updateDisconnectPlayer(String player, String type){
        if (type.equals("reconnection")){
            disconnectOpacityGraphic(player, 1.0);
        }
        else if (type.equals("disconnection")){
            disconnectOpacityGraphic(player, 0.5);
        }
    }


    /**
     * Method used to set the opacity where a client disconnect or reconnect to the game.
     *
     * @param player the username of the interested player.
     * @param v the opacity.
     */
    private void disconnectOpacityGraphic(String player, double v){
        //set the opacity of: bookshelf, personal objective, common objective's points, username, public points
        try{
            Objects.requireNonNull(catchOtherPlayerBookshelfGrid(player)).setOpacity(v);
            Objects.requireNonNull(catchotherPlayerBookshelfImage(player)).setOpacity(v);
            Objects.requireNonNull(catchOtherPlayerPersonalImage(player)).setOpacity(v);
            Objects.requireNonNull(catchCommonObjPointONE(player)).setOpacity(v);
            Objects.requireNonNull(catchCommonObjPointTWO(player)).setOpacity(v);
            Objects.requireNonNull(catchOtherPlayerUsernameLabel(player)).setOpacity(v);
            Objects.requireNonNull(catchOtherPlayerPointsLabel(player)).setOpacity(v);
        }
        catch (NullPointerException e){
            System.out.println("The player do not exist");
        }
    }

}

package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.View.CLI.ColorCLI;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Sachet;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;


public class GameController implements Initializable {
    @FXML
    private GridPane boardGrid;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeBoardGrid();
    }

    public void initializeBoardGrid(){
        Board board=new Board(2, new Sachet());
        board.BoardInitialization();
        Matrix matrix=board.getGamesBoard();
        for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                if (!matrix.getTile(i,j).equals(Tiles.NOTALLOWED) && !matrix.getTile(i,j).equals(Tiles.EMPTY)) {
                    boardGrid.add(setTiles(matrix.getTile(i, j), i, j), i, j);
                }
            }
        }
    }

    private ImageView setTiles(Tiles tiles, int i, int j){
        Image image;

        double cellWidth = boardGrid.getColumnConstraints().get(j).getPercentWidth() / 100.0 * boardGrid.getWidth();
        double cellHeight = boardGrid.getRowConstraints().get(i).getPercentHeight() / 100.0 * boardGrid.getHeight();
        System.out.println(cellHeight+ cellWidth);

        switch (tiles) {
            case GREEN -> {
                image=new Image("css/images/item tiles/Gatti1.1.png");
            }
            case BLUE -> {
                image=new Image("css/images/item tiles/Cornici1.1.png");
            }
            case YELLOW -> {
                image=new Image("css/images/item tiles/Giochi1.1.png");
            }
            case WHITE -> {
                image=new Image("css/images/item tiles/Libri1.1.png");
            }
            case PINK -> {
                image=new Image("css/images/item tiles/Piante1.1.png");
            }
            case LIGHT_BLUE -> {
                image=new Image("css/images/item tiles/Trofei1.1.png");
            }
            default -> {
                image= null;
            }
        }
        ImageView imageView=new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }
}

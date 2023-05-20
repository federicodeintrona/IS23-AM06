package it.polimi.ingsw.client.View.GUI.prova;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;


public class MainController {

    @FXML
    private Circle circle;
    private double x, y;


    public void up(ActionEvent actionEvent){
        System.out.println("up");
        circle.setCenterY(y-=1);
    }
    public void down(ActionEvent actionEvent){
        System.out.println("down");
        circle.setCenterY(y+=1);
    }
    public void right(ActionEvent actionEvent){
        System.out.println("right");
        circle.setCenterX(x+=1);

    }public void left(ActionEvent actionEvent){
        System.out.println("left");
        circle.setCenterX(x-=1);

    }

}

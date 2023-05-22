package it.polimi.ingsw.client.View.GUI.prova;

import it.polimi.ingsw.app.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;

public class prova extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //creazioone root node
        Group root=new Group();
        //creazione scene nel root node
        Scene scene=new Scene(root, Color.LIGHTSKYBLUE);
        //creazione stage
        Stage stage=new Stage();

        //Testo
        Text text=new Text();
        text.setText("ciao");
        //set posizione
        text.setX(50);
        text.setY(50);
        //set font
        text.setFont(Font.font("Verdana", 50));
        text.setFill(Color.GREEN);

        //per sfondo TODO capire che cazzo è sta roba
//        Pane rootbox=new Pane();
//        BackgroundImage backgroundImage=new BackgroundImage();
//        Background background=new Background();
//        rootbox.setBackground();
//
        root.getChildren().add(text);

        //Immagine
        InputStream is=getClass().getResourceAsStream("Publisher.png");
        System.out.println(is);




        //TODO FileInputStreamer
//        Image image=new Image("images/Publisher_material/Publisher.png");
        Image image=new Image(new FileInputStream("images/Publisher_material/Publisher.png"));
        ImageView imageView=new ImageView(image);
        imageView.setX(100);
        imageView.setY(100);

        root.getChildren().add(imageView);






        //set icon TODO non va e non capisco perchè - con il path assoluto va bene, cercando di prenderlo dalle resources non va...
//        Image icon=new Image("/images/Publisher_material/Publisher.png");
//        stage.getIcons().add(icon);

        //set title
        stage.setTitle("Login Page");

        //set dimensioni
        stage.setWidth(1920); //larghezza
        stage.setHeight(1080); //altezza
        //set resizable
        stage.setResizable(true);
        //set fullscreen
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Per uscire da schermo intero premi q");
        stage.setFullScreenExitKeyCombination(KeyCodeCombination.valueOf("q"));

        //set posizione
//        stage.setX(50);
//        stage.setY(50);

        //set la scene da mostrare
        stage.setScene(scene);
        //mostra
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}

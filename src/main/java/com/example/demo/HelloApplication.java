package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        /*
        for (String arg: args) {
            System.out.println(arg);
        }
        */
        if (args.length > 0) {
            String param0 = args[0];
            if (param0.equals( "--server") )
                runAsServer();
        }else {
            launch();
        }
    }

    static void runAsServer(){
        System.out.println("started as server.. bye!");
    }

}
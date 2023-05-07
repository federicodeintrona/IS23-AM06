package it.polimi.ingsw.app;

import it.polimi.ingsw.client.ClientBase;
import it.polimi.ingsw.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloApplication extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene=new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, InterruptedException, NotBoundException {
        if (args.length>0){
            String p0=args[0];
            if (p0.equals("--server")){
                runAsServer(args);
            }
            else if (p0.equals("--client")) {
                runAsClient(args);
            }
            else {
                launch();
            }
        }
    }

    static void runAsServer(String[] args) throws IOException {
        Server.main(args);
    }

    static void runAsClient(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        ClientBase.main(args);
    }
}

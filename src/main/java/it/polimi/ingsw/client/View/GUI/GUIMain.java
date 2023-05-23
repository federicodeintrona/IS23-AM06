package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.GUI.Scene.Login;
import it.polimi.ingsw.client.View.GUI.Scene.LoginController;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIMain extends Application implements View  {

    private final Object lock; //su cosa lockare - comune con ClientState
    private final ClientState clientState; //da dove leggere cambiamenti view
    private final Networker net; //a chi mandare messaggi
    private final GUIController guiController;


    public GUIMain(Object lock, ClientState clientState, Networker net, GUIController guiController) {
        this.lock = lock;
        this.clientState = clientState;
        this.net = net;
        this.guiController = guiController;
    }

    @Override
    public void receivedMessage(Message message) {

    }


    public void runUI(){
//        Login.main(null);
//        GUIFactory guiFactory=new GUIFactory();
        launch();

    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}

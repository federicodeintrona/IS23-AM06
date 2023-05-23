package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.Message;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIController implements View {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Networker networker;

    public GUIController(Networker networker) {
        this.networker = networker;
    }

    @Override
    public void receivedMessage(Message message) {

    }

    //TODO qui si faranno i metodi dell'update della GUI
}

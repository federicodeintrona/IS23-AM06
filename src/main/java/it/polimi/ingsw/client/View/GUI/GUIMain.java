package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.GUI.Scene.Login;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.Message;

public class GUIMain implements View {

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

    @Override
    public void runUI(){
//        Login.main(null);
//        GUIFactory guiFactory=new GUIFactory();

    }


}

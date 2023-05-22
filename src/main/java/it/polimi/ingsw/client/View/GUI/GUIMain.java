package it.polimi.ingsw.client.View.GUI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;

public class GUIMain {

    private final Object lock; //su cosa lockare - comune con ClientState
    private final ClientState clientState; //da dove leggere cambiamenti view
    private final Networker net; //a chi mandare messaggi

    public Object getLock() {
        return lock;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public Networker getNet() {
        return net;
    }

    public GUIMain(Object lock, ClientState clientState, Networker net) {
        this.lock = lock;
        this.clientState = clientState;
        this.net = net;
    }

    public void runGUI(){
        Login.setGuiMain(this);
        Login.main(null);
    }


}

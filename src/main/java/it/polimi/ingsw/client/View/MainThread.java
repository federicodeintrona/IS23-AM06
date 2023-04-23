package it.polimi.ingsw.client.View;

import it.polimi.ingsw.client.NetworkerRmi;

public class MainThread {

    public static void main(String[] args) {
        Thread th1=new ReadThread(new NetworkerRmi());
    }
}

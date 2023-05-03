package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.server.Messages.Message;

public class CLIMain {



    /*
        TODO
            creare oggetto lock
            io leggo l'username e lo invio a networker FirstConnection??
     */


    private final Object lock; //su cosa lockare - comune con ClientState
    private final ClientState clientState; //da dove leggere cambiamenti view
    private final Networker net; //a chi mandare messaggi

    private static CLIPrint cliPrint;
    private static ReadShell readShell;

    public CLIMain(Object lock, ClientState clientState, Networker net) {
        this.lock = lock;
        this.clientState = clientState;
        this.net = net;
    }

    public Object getLock() {
        return lock;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public Networker getNet() {
        return net;
    }

    public CLIPrint getCliPrint() {
        return cliPrint;
    }

    public ReadShell getReadShell() {
        return readShell;
    }


    public void receivedMessage(Message message){
        switch (message.getType()){
            //TODO fare tutti i case
        }
    }



    public void runCLI (){
        cliPrint=new CLIPrint(this);
        readShell=new ReadShell(this);
        String next= getClientState().getCurrentPlayer();

        //facciamo partite readShell
        Thread th1=new Thread(readShell);
        th1.start();

        //chi ha la sedia?
        getCliPrint().printChair();

        while(!clientState.isEndGame()){
            //controllo se è il turno del prossimo giocatore
            if (getClientState().getCurrentPlayer().equals(next)){
                //stampa quello che devi stampare all'inizio di un turno di gioco
                cliPrint.playerTurn();
                next= clientState.getNextPlayer();
            }
        }

        //se è finito il gioco stampa end game
        getCliPrint().printEndGame();
        //elimina thread
        th1.interrupt();

    }








}

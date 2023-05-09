package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.server.Messages.Message;

public class CLIMain {


/*
    TODO
        partita inizia quando
        send message
                received message
        .
            -username
                -error
                -new_lobby -> chiedere il numero di giocatori
                -waiting_for_players -> print aspetto
            -num_of_player
            .
            -ok
            -error
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
        //net.setUserInterface(this);
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
            case NUM_OF_PLAYERS -> readShell.askForNumberOfPlayer();
            //TODO capire che messaggi mi arrivano e in che formato
        }
    }



    public void runCLI (){
        cliPrint=new CLIPrint(this);
        readShell=new ReadShell(this);

        Thread th1=new Thread(readShell);
        th1.start();



        /*
            richieste iniziali

            waiting_for_players
                ciclo gameHasStarted!=true

            inizia partita
                ciclo gameIsEnded!=true

            fine partita
         */







        String next= getClientState().getCurrentPlayer();

        //richieste iniziali - username e numero di giocatori
        readShell.initialRequests();


        //COMANDI GIOCO
        //facciamo partite readShell

        //chi ha la sedia?
        getCliPrint().printChair();

        while(!clientState.isGameIsEnded()){
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

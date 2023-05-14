package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.utils.Messages.Message;

public class CLIMain {




    private final Object lock; //su cosa lockare - comune con ClientState
    private final ClientState clientState; //da dove leggere cambiamenti view
    private final Networker net; //a chi mandare messaggi

    private static CLIPrint cliPrint;
    private static ReadShell readShell;
    private boolean IHaveToRequestTheUsername=true;

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

    public boolean isIHaveToRequestTheUsername() {
        return IHaveToRequestTheUsername;
    }

    public void setIHaveToRequestTheUsername(boolean IHaveToRequestTheUsername) {
        this.IHaveToRequestTheUsername = IHaveToRequestTheUsername;
    }


    /*
        TODO
            send message
                    received message
            .
                -username
                    -new_lobby -> chiedere il numero di giocatori
                    -waiting_for_players -> print aspetto
                -num_of_player
                .
                -ok
                -error
     */
    public void receivedMessage(Message message){
        switch (message.getType()){
            case NEW_LOBBY -> readShell.askNumberOfPlayerMessage();
            case WAITING_FOR_PLAYERS -> cliPrint.printWaiting();
            case ERROR -> cliPrint.printError(message.getUsername()); //TODO il messaggio di errore dove lo trovo
            default -> {
                break;
            }
        }
    }



    public void runCLI () throws InterruptedException {
        cliPrint=new CLIPrint(this);
        readShell=new ReadShell(this);

        //richiesta username
        readShell.askUsername();

        Thread th1=new Thread(readShell);
        th1.start();

        //TODO restiamo in attesa di nuovi giocatori
//        while (!clientState.isGameHasStarted()){
//            cliPrint.printWaiting();
//            Thread.sleep(10000);
//        }

        //inizia la partita
        cliPrint.clearSheel();
        cliPrint.gameHasStarted();
        Thread.sleep(10000);

        cliPrint.playerTurn();
        //ho già stampato il primo turno di gioco
        String curr=clientState.getNextPlayer();

        while (!clientState.isGameIsEnded()){
            //stampa nuovo turno se il current è il next di prima
            if (clientState.getCurrentPlayer().equals(curr)){
                cliPrint.playerTurn();
                curr=clientState.getNextPlayer();
            }

        }

        //è finita la partita
        cliPrint.printEndGame();
        //eliminiamo il thread
        th1.interrupt();


        /*
            richieste iniziali

            waiting_for_players
                ciclo gameHasStarted!=true

            inizia partita
                ciclo gameIsEnded!=true

            fine partita
         */

    }








}

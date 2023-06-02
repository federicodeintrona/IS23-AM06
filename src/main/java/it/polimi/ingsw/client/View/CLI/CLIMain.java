package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeEvent;

public class CLIMain implements View {

    private final Object lock; //su cosa lockare - comune con ClientState
    private final ClientState clientState; //da dove leggere cambiamenti view
    private final Networker net; //a chi mandare messaggi
    private static CLIPrint cliPrint;
    private static ReadShell readShell;
    private static ChatHandler chatHandler;
    private boolean IHaveToRequestTheUsername=true;
    private Thread th1;

    public CLIMain(Object lock, ClientState clientState, Networker net) {
        this.lock = lock;
        this.clientState = clientState;
        this.net = net;
        clientState.addListener(this);
        //net.setUserInterface(this);
    }

    public Object getLock() {
        return lock;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public ChatHandler getChatHandler () { return chatHandler; }

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

    @Override
    public void receivedMessage(Message message){
        switch (message.getType()){
            case NEW_LOBBY -> readShell.askNumberOfPlayerMessage();
            case WAITING_FOR_PLAYERS -> clientState.setWaiting(true);
            case ERROR -> {
                cliPrint.printError(message.getUsername());
                if (message.getUsername().equals("Username already taken")){
                    readShell.askUsername();
                }
            }
            case OK -> {
                if (message.getUsername().equals("Move successful remove tiles")){
                    cliPrint.printOrderTiles(clientState.getSelectedTiles());
                }
                else if (message.getUsername().equals("Move successful swap order")) {
                    cliPrint.printOrderTiles(clientState.getSelectedTiles());
                }
            }
        }
    }


    @Override
    public void close() {
        th1.interrupt();
    }


    public void runUI() {
        cliPrint=new CLIPrint(this);
        readShell=new ReadShell(this);

        //richiesta username
        readShell.askUsername();



        while (!clientState.gameHasStarted()){
            if (clientState.isWaiting()){
                cliPrint.printWaiting();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        chatHandler = new ChatHandler(clientState.getChatController(), this, cliPrint);


        clientState.setChair(clientState.getCurrentPlayer());
        th1=new Thread(readShell);
        th1.start();
        //inizia la partita
        cliPrint.clearSheel();
        cliPrint.gameHasStarted();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cliPrint.printChair();
        cliPrint.printCommonObjective(clientState.getGameCommonObjective(), clientState.getCommonObjectivePoints());
        cliPrint.playerTurn();
        //ho già stampato il primo turno di gioco
        String curr=clientState.getNextPlayer();

        while (!clientState.isGameIsEnded()){
            //stampa nuovo turno se il current è il next di prima
           if (clientState.getCurrentPlayer().equals(curr)){
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               cliPrint.clearSheel();
               cliPrint.playerTurn();
               curr=clientState.getNextPlayer();
          }

        }

        //è finita la partita
        cliPrint.clearSheel();
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case "start" ->{
                // moveToGameScene();
            }
            case "nextTurn"->{
                //  printTurn();
            }
            case "end" -> {
                // moveToEndScene();
            }
            case "publicChat" -> {
                chatHandler.newPublicMessage((ChatMessage) evt.getNewValue());
            }
            case "privateChat" -> {
                chatHandler.newPrivateMessage((ChatMessage) evt.getNewValue());
            }
        }
    }

    private void moveToEndScene() {
        cliPrint.clearSheel();
        cliPrint.printEndGame();
    }
    //Per ora ho fatto copia e incolla da quello che c'era nel run
    private void printTurn() {
        cliPrint.clearSheel();
        cliPrint.playerTurn();
    }

    private void moveToGameScene() {
        cliPrint.clearSheel();
        cliPrint.gameHasStarted();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }

        cliPrint.printChair();
        cliPrint.printCommonObjective(clientState.getGameCommonObjective(), clientState.getCommonObjectivePoints());
        cliPrint.playerTurn();
    }
}

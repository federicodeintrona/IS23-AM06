package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.NetworkerRmi;
import it.polimi.ingsw.client.NetworkerTcp;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Class to manage the Command Line Interface
 * <ul>
 *     <li>create the client lock - ClientState, CLIPrint, ReadShell</li>
 *     <li>initial request
 *          <ul>
 *              <li>Which connection protocol do you choose?</li>
 *              <li>Which host do you use?</li>
 *          </ul>
 *      </li>
 *      <li>create the instance of:
 *          <ul>
 *              <li>ClientState</li>
 *              <li>CLIPrint</li>
 *              <li>ReadShell</li>
 *              <li>Networker (RMI or TCP)</li>
 *          </ul>
 *      </li>
 * </ul>
 */
public class CLIMain implements View {

    /**
     * Attribute used to know what to lock on - it is in common with ClientState
     */
    private final Object lock ;
    /**
     * Attribute used to read all the client changes to show
     */
    private ClientState clientState ;
    /**
     * Attribute used to know whom to send messages that the user writes from command line
     */
    private  Networker net;
    /**
     * Attribute used to know which CLIPrint instance is correct
     */
    private static CLIPrint cliPrint;
    /**
     * Attribute used to know which ReadShell instance is correct
     */
    private static ReadShell readShell;
    /**
     * Attribute used to know which ChatHandler instance is correct
     */
    private static ChatHandler chatHandler;
    /**
     * Thread to start the reader from the command line in parallel with printing on the terminal
     */
    private Thread th1;


    /**
     * Constructor --> create lock
     */
    public CLIMain() {
        this.lock=new Object();
    }

    //TODO è solo nei test del cliprint - NON SERVE PIù - da capire se bisogna testare anche la UI
    public CLIMain(Object lock, ClientState clientState, Networker net) {
        this.lock = lock;
        this.clientState = clientState;
        this.net = net;
    }

    /**
     * Getter --> returns the lock Object
     *
     * @return Object &nbsp;&nbsp;&nbsp; lock on which to make the locks, must be common to CLIPrint, ReadShell and ClientState
     */
    public Object getLock() {
        return lock;
    }

    /**
     * Getter --> returns the ClientState
     *
     * @return ClientState &nbsp;&nbsp;&nbsp; correct instance of ClientState
     */
    public ClientState getClientState() {
        return clientState;
    }

    /**
     * Getter --> returns the CLIPrint
     *
     * @return CLIPrint &nbsp;&nbsp;&nbsp; correct instance of CLIPrint
     */
    public CLIPrint getCliPrint() {
        return cliPrint;
    }

    /**
     * Getter --> returns the ReadShell
     *
     * @return ReadShell &nbsp;&nbsp;&nbsp; correct instance of ReadShell
     */
    public ReadShell getReadShell() {
        return readShell;
    }

    /**
     * Getter --> returns the ChatHandler
     *
     * @return ChatHandler &nbsp;&nbsp;&nbsp; correct instance of ChatHandler
     */
    public ChatHandler getChatHandler () { return chatHandler; }

    /**
     * Getter --> returns the Networker
     *
     * @return Networker &nbsp;&nbsp;&nbsp; correct instance of Networker, class to which the client should send commands to be sent to the server
     */
    public Networker getNet() {
        return net;
    }



    //TODO cosa scrivo per RemoteException
    /**
     * Method to run the Command Line Interface
     *
     * @throws RemoteException
     */
    public void runUI() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        clientState = new ClientState(this.lock);
        clientState.addListener(this);
        String decision;

        //choice of connection protocol
        //prompt if user entered wrong name
        do {
            System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
            decision = scanner.nextLine();
            decision=decision.toUpperCase();
        }while (!decision.equals("RMI") && !decision.equals("TCP"));

        //instance the correct connection protocol
        if (decision.equalsIgnoreCase("RMI")) {
            net = new NetworkerRmi(clientState);
        }
        else {
            net = new NetworkerTcp(clientState);
        }

        boolean connected;
        //choice the host
        //prompt if the user has entered an incorrect host - can't reach the server
        do {
            System.out.print("Which host do you use? ");
            String host = scanner.nextLine();
            //if you do not insert a host, you chose the localhost
            if (host.isEmpty()) {
                System.out.println("You selected the default host: localhost");
                host = "localhost";
            }
            net.setServerIP(host);
            connected= net.initializeConnection();
        }while (!connected);

        //setting the view in Networker
        net.setView(this);

        //instance of CLIPrint, ReadShell, ChatHandler
        cliPrint=new CLIPrint(this);
        readShell=new ReadShell(this);
        chatHandler = new ChatHandler(clientState.getChatController(), this, cliPrint);

        //username request
        readShell.askUsername();

        //waiting until game has started
        while (!clientState.gameHasStarted()){
            //you are not the latest player
            if (clientState.isWaiting()){
                cliPrint.printWaiting();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //you are the latest player
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //new thread because ReadShell must run in parallel with everything else
        th1=new Thread(readShell);
        th1.start();


        //the game has started
        cliPrint.clearShell();
        cliPrint.gameHasStarted();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cliPrint.printChair();
        cliPrint.printCommonObjective(clientState.getGameCommonObjective());
        cliPrint.playerTurn();
        //I have already printed the first round of the game
        String curr=clientState.getNextPlayer();

        while (!clientState.isGameIsEnded()){
            //print new turn if current is next from before
            if (clientState.getCurrentPlayer().equals(curr)){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cliPrint.clearShell();
                cliPrint.playerTurn();
                curr=clientState.getNextPlayer();
            }

        }

        //the game is ended
        cliPrint.clearShell();
        cliPrint.printEndGame();
        //close all thread
        close();
    }

    /**
     * Method to close all the thread managed by CLI
     */
    @Override
    public void close() {
        th1.interrupt();

        Message msg = new Message();
        msg.setType(MessageTypes.DISCONNECT);
        net.closeProgram(msg);

    }



    /**
     * Method to received all the message from server
     *
     * @param message &nbsp;&nbsp;&nbsp; the message that the server sent
     */
    @Override
    public void receivedMessage(Message message){
        switch (message.getType()){
            case NEW_LOBBY -> readShell.askNumberOfPlayerMessage();
            case WAITING_FOR_PLAYERS -> clientState.setWaiting(true);
            case ERROR -> {
                cliPrint.printError(message.getText());
                if (message.getText().equals("Username already taken")){
                    readShell.askUsername();
                }
            }
            case OK -> {
                if (message.getText().equals("Move successful remove tiles")){
                    cliPrint.printOrderTiles(clientState.getSelectedTiles());
                }
                else if (message.getText().equals("Move successful swap order")) {
                    cliPrint.printOrderTiles(clientState.getSelectedTiles());
                }
            }
        }
    }


    //TODO alla fine la facciamo così la CLI??
    //TODO cosa scrivo

    /**
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
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
            case "publicChat" ->
                chatHandler.newPublicMessage((ChatMessage) evt.getNewValue());
            case "privateChat" ->
                chatHandler.newPrivateMessage((ChatMessage) evt.getNewValue());
        }
    }

    private void moveToEndScene() {
        cliPrint.clearShell();
        cliPrint.printEndGame();
    }
    //Per ora ho fatto copia e incolla da quello che c'era nel run
    private void printTurn() {
        cliPrint.clearShell();
        cliPrint.playerTurn();
    }

    private void moveToGameScene() {

        chatHandler = new ChatHandler(clientState.getChatController(), this, cliPrint);

        cliPrint.clearShell();
        cliPrint.gameHasStarted();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }


        cliPrint.printChair();
        cliPrint.printCommonObjective(clientState.getGameCommonObjective());
        cliPrint.playerTurn();
    }
}

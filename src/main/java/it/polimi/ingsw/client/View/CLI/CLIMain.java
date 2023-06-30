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
 * Class to manage the Command Line Interface.
 * <ul>
 *     <li>Create the client lock in common with ClientState, CLIPrint, ReadShell;</li>
 *     <li>initial request:
 *          <ul>
 *              <li>Which connection protocol do you choose?</li>
 *              <li>Which host do you use?</li>
 *          </ul>
 *      </li>
 *      <li>create the instance of:
 *          <ul>
 *              <li>ClientState;</li>
 *              <li>CLIPrint;</li>
 *              <li>ReadShell;</li>
 *              <li>Networker (RMI or TCP).</li>
 *          </ul>
 *      </li>
 * </ul>
 */
public class CLIMain implements View {

    /**
     * Attribute used to know what to lock on - it is in common with ClientState.
     */
    private final Object lock ;
    /**
     * Attribute used to read all the client changes to show.
     */
    private ClientState clientState ;
    /**
     * Attribute used to know whom to send messages that the user writes from command line.
     */
    private  Networker net;
    /**
     * Attribute used to know which CLIPrint instance is correct.
     */
    private static CLIPrint cliPrint;
    /**
     * Attribute used to know which ReadShell instance is correct.
     */
    private static ReadShell readShell;
    /**
     * Attribute used to know which ChatHandler instance is correct.
     */
    private static ChatHandler chatHandler;
    /**
     * Thread to start the reader from the command line in parallel with printing on the terminal.
     */
    private Thread th1;

    /**
     * Attribute used to know if chat is open.
     */
    private boolean openChat;



    /**
     * Initialize lock.
     */
    public CLIMain() {
        this.lock=new Object();
        openChat=false;
    }




    /**
     * <strong>Getter</strong> -> Returns the lock Object.
     *
     * @return the <i>lock</i> on which to make the locks, must be common to CLIPrint, ReadShell and ClientState.
     */
    public Object getLock() {
        return lock;
    }

    /**
     * <strong>Getter</strong> -> Returns the ClientState.
     *
     * @return the <i>ClientState</i>.
     */
    public ClientState getClientState() {
        return clientState;
    }

    /**
     * <strong>Getter</strong> -> Returns the CLIPrint.
     *
     * @return the <i>CLIPrint</i>.
     */
    public CLIPrint getCliPrint() {
        return cliPrint;
    }

    /**
     * <strong>Getter</strong> -> Returns the ReadShell.
     *
     * @return the <i>ReadShell</i>.
     */
    public ReadShell getReadShell() {
        return readShell;
    }

    /**
     * <strong>Getter</strong> -> Returns the ChatHandler
     *
     * @return the <i>ChatHandler</i>.
     */
    public ChatHandler getChatHandler () { return chatHandler; }

    /**
     * <strong>Getter</strong> -> Returns the Networker.
     *
     * @return the <i>Networker</i>, class to which the client should send commands to be sent to the server.
     */
    public Networker getNet() {
        return net;
    }



    /**
     * <strong>Setter</strong> -> Sets true if chat is opened.
     *
     * @param openChat sets true if chat is open, false in other cases.
     */
    public void setOpenChat(boolean openChat) {
        this.openChat = openChat;
    }



    /**
     * Method to run the Command Line Interface.
     *
     * @throws RemoteException in case of problem with connection RMI.
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
            if(decision.equals("#QUIT") || decision.equals("#Q")){
                System.exit(0);
            }
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
            System.out.print("Which server IP do you use? ");
            String host = scanner.nextLine();
            host=host.toLowerCase();
            if(host.equals("#quit") || host.equals("#q")){
                System.exit(0);
            }
            //if you do not insert a host, you chose the localhost
            else if (host.isEmpty()) {
                System.out.println("You selected the default host: localhost");
                host = "localhost";
            }
            net.setServerIP(host);
            connected= net.initializeConnection();
            if (!connected){
                System.out.println(ColorCLI.RED+"IP address not working"+ColorCLI.RESET);
                System.out.println(ColorCLI.RED+"Unable to reach the Server\n"+ColorCLI.RESET);

            }
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
        while (clientState.gameHasStarted()){
            //you are not the latest player
            if (clientState.isWaiting()){
                cliPrint.printWaiting();
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e) {
                    System.out.println("Waiting failed");
                }
            }
            //you are the latest player
            else {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    System.out.println("Waiting failed");
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
            System.out.println("Sleep error");
        }

        cliPrint.printChair();
        cliPrint.printCommonObjective(clientState.getGameCommonObjective());
        cliPrint.playerTurn();
        //I have already printed the first round of the game
        String curr=clientState.getNextPlayer();

        while (clientState.isGameIsEnded()){
            //print new turn if current is next from before
            if (clientState.getCurrentPlayer().equals(curr) && !openChat){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Sleep error");
                }
                cliPrint.clearShell();
                cliPrint.playerTurn();
                curr=clientState.getNextPlayer();
            }

        }

        //the game is ended
        cliPrint.clearShell();
        cliPrint.printEndGame();
        th1.interrupt();
        net.stopTimer();
        //wait until the client quit
        do {
            System.out.println("To exit close: #quit or #q");
            decision=scanner.nextLine();
            decision=decision.toLowerCase();
        }while(!decision.equals("#quit") && !decision.equals("#q"));
        closeClient();
    }

    /**
     * Method to close all the thread managed by CLI.
     */
    @Override
    public void close() {
        if(th1!=null) th1.interrupt();

        Message msg = new Message();
        msg.setType(MessageTypes.DISCONNECT);
        net.closeProgram(msg);
    }

    /**
     * Method to close the Client before creation of CLI-thread.
     */
    public void closeClient(){
        Message msg = new Message();
        msg.setType(MessageTypes.DISCONNECT);
        net.closeProgram(msg);
    }



    /**
     * Method to received all the message from server.
     *
     * @param message the message that the server sent.
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

    /**
     * Method to show the chat and the disconnection or reconnection notification.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case "publicChat" ->
                chatHandler.newPublicMessage((ChatMessage) evt.getNewValue());
            case "privateChat" ->
                chatHandler.newPrivateMessage((ChatMessage) evt.getNewValue());
            case "notification" ->
                cliPrint.printDisconnection((String) evt.getSource(), (String) evt.getNewValue());
        }
    }

}


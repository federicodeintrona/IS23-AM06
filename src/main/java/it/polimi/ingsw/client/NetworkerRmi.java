package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.server.RMIHandlerInterface;
import it.polimi.ingsw.utils.JsonReader;
import it.polimi.ingsw.utils.Messages.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NetworkerRmi implements Networker {
    private static int port;
    private  String serverIP;
    private String username;
    private int gameID;
    private Message message;
    private static RMIHandlerInterface rmiHandler;
    private ClientState clientState;
    private CLIMain cli;


    /**
     * Constructor
     */
    public NetworkerRmi()  {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config = new JsonReader(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        port = config.getInt("rmiPort");

        try {
            clientState = new ClientState(new Object());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public NetworkerRmi(ClientState state,String serverIP)  {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config = new JsonReader(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        port = config.getInt("rmiPort");

        clientState = state;
        this.serverIP = serverIP;
    }

    /**
     * Method to initialize an RMI connection
     */
    public void initializeConnection () {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            // Looking up the registry for the remote object
            rmiHandler = (RMIHandlerInterface) registry.lookup("RMIHandler");

            System.out.println("Created RMI connection with Server");
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Asks the client to enter a valid username. Once he has
     * done the client gets added to the lobby
     *
     * @param username  Message from the View containing the username
     */
    public void firstConnection (Message username) {
        IntMessage message1;
        try {
             message1 = rmiHandler.acceptRmiConnection(username.getUsername(),clientState);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Saving the username after assuring that is ok
        if (!message1.getType().equals(MessageTypes.ERROR)){
           this.username = username.getUsername();
        }

        // Saving the gameID once the new game has been created
        if (message1.getType().equals(MessageTypes.WAITING_FOR_PLAYERS)){
            gameID =  message1.getNum();
        }

        // Forwarding the Server's response message to the View
        cli.receivedMessage(message1);
    }



    /**
     * Method used to select the number of players in a game.
     * Casts the message coming from the View to an IntMessage and calls
     * the controller method passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param numberOfPlayers   Message to cast from the View
     */
    public void numberOfPlayersSelection(Message numberOfPlayers) {
        IntMessage tempMessage = (IntMessage) numberOfPlayers;
        IntMessage message1;
        try {
            message1 = rmiHandler.newLobby(this.username, tempMessage.getNum());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.gameID = message1.getNum();

        // Forwarding the Server's response message to the View
        cli.receivedMessage(message1);
    }

    /**
     * Method used to remove a maximum of 3 tiles from the board send by coordinates.
     * Casts the message coming from the View to a PointsMessage and calls
     * the controller method passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param tiles     Message to cast from the View
     */
    public void removeTilesFromBoard(Message tiles) {
        PointsMessage tempMessage = (PointsMessage) tiles;
        try {
            message = rmiHandler.removeTiles(gameID, username, tempMessage.getTiles());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Forwarding the Server's response message to the View
        cli.receivedMessage(message);
    }

    /**
     * Method used to select the new order for the chosen tiles and switch them.
     * Casts the message coming from the View to a IntArrayMessage and calls
     * the controller method passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param ints  Message to cast from the View
     */
    public void switchTilesOrder(Message ints) {
        IntArrayMessage tempMessage = (IntArrayMessage) ints;
        try {
            message = rmiHandler.swapOrder(tempMessage.getIntegers(), gameID, username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Forwarding the Server's response message to the View
        cli.receivedMessage(message);
    }

    /**
     * Method used to select the column to add the chosen tiles.
     * Casts the message coming from the View to a IntArrayMessage and calls
     * the controller method passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param column    Message to cast from the View
     */
    public void addTilesToBookshelf (Message column) {
        IntMessage tempMessage = (IntMessage) column;
        try {
            message = rmiHandler.addToBookshelf(gameID, username, tempMessage.getNum());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Forwarding the Server's response message to the View
        cli.receivedMessage(message);
    }

    /**
     * Method used to send private or public messages to the other players via server.
     * Casts the message coming from the View to a ChatMessage and calls one of
     * the 2 controller methods, depending on that the message is for a private or
     * public chat, passing it the required arguments.
     * After saving the server's response it sends it back to the View.
     *
     * @param message   Message to cast from the View
     */
    public void chat (Message message) {
        ChatMessage tempMessage = (ChatMessage) message;

        // The message is intended for a public chat due to having the receivingUsername equals to null
        if (tempMessage.getReceivingUsername() == null) {
            try {
                this.message = rmiHandler.sendMessage(gameID, username, tempMessage.getMessage());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        // The message is intended for a private chat
        else {
            try {
                this.message = rmiHandler.sendMessage(gameID, username, tempMessage.getMessage(), tempMessage.getReceivingUsername());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        // Forwarding the Server's response message to the View
        cli.receivedMessage(this.message);
    }

    public void setCli(CLIMain cli) {
        this.cli = cli;
    }
}

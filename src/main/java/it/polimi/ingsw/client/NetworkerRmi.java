package it.polimi.ingsw.client;


import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.Messages.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
public class NetworkerRmi implements Networker {
    private String username;
    private int lobbyID;
    private int gameID;
    private Message message;
    private static Controller controller;

    /**
     * Method to initialize an RMI connection
     *
     */
    public void initializeConnection () {
        String serverHost = "localhost";
        try {
            controller = (Controller) Naming.lookup("rmi://" + serverHost + "/RemoteController");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Created RMI connection with Server");
    }

    /**
     * Asks the client to enter a valid username. Once he has
     * done the client gets added to the lobby
     */
    public Message firstConnection (Message username) {
        message = controller.handleNewClient(username.getUsername());

        return message;
    }

    /**
     *
     * @param numberOfPlayers
     */
    public Message numberOfPlayersSelection(Message numberOfPlayers) {
        message = controller.newLobby(this.username, numberOfPlayers.);

        return message;
    }

    /**
     * Removes a maximum of 3 tiles from the board send by coordinates
     *
     * @param tiles     ArrayList containing the coordinates of the tiles to remove
     */
    public Message removeTilesFromBoard(Message tiles) {
        PointsMessage tempMessage = (PointsMessage) tiles;
        message = controller.removeTiles(gameID, username, tempMessage.getTiles());

        return message;
    }

    /**
     *
     * @param ints
     */
    public Message switchTilesOrder(Message ints) {
        IntArrayMessage tempMessage = (IntArrayMessage) ints;
        message = controller.swapOrder(tempMessage.getInts(), gameID, username);

       return message;
    }

    /**
     *
     * @param column
     */
    public Message addTilesToBookshelf (Message column) {
        IntMessage tempMessage = (IntMessage) column;
        message = controller.addToBookshelf(gameID, username, tempMessage.getNum());

        return message;
    }
}

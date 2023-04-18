package it.polimi.ingsw.client;


import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;

import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
public class NetworkerRmi implements Networker {
    private String username;
    private int lobbyID;
    private int gameID;
    private Message messageOut;
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
    public void firstConnection () {
        Scanner scanner = new Scanner(System.in);
        username = null;
        boolean validUsername = false;

        while (!validUsername) {
            System.out.print("Inserisci un username: ");
            username = scanner.nextLine();

            messageOut = controller.handleNewClient(this.username);

            if (!messageOut.getType().equals(MessageTypes.ERROR)) validUsername = true;

            System.out.println(messageOut);
        }
    }

    /**
     *
     * @param numberOfPlayers
     */
    public void numberOfPlayersSelection(int numberOfPlayers) {
        messageOut = controller.newLobby(this.username, numberOfPlayers);

        System.out.println(messageOut);
    }

    /**
     * Removes a maximum of 3 tiles from the board send by coordinates
     *
     * @param tiles     ArrayList containing the coordinates of the tiles to remove
     */
    public void removeTilesFromBoard(ArrayList<Point> tiles) {
        messageOut = controller.removeTiles(gameID, username, tiles);

        System.out.println(messageOut);
    }

    /**
     *
     * @param ints
     */
    public void switchTilesOrder(ArrayList<Integer> ints) {
        messageOut = controller.swapOrder(ints, gameID, username);

        System.out.println(messageOut);
    }

    /**
     *
     * @param column
     */
    public void addTilesToBookshelf (int column) {
        messageOut = controller.addToBookshelf(gameID, username, column);

        System.out.println(messageOut);
    }
}

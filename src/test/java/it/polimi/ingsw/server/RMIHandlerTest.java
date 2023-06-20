package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

class RMIHandlerTest {
    private static RMIHandler rmiHandler;
    private static Controller mockController;
    public static HashMap<String,Player> mockPlayerMap = new HashMap<>();
    public static HashMap<Integer,Model> mockModels = new HashMap<>();
    public static ArrayList<ArrayList<Player>> mockPlayers = new ArrayList<>();
    public static ArrayList<ArrayList<VirtualView>> mockViews = new ArrayList<>();

    /**
     * Setting up a game via mockController for tests
     */
    @BeforeAll
    public static void setUp() {
        mockModels.put(0,new Model());

        // Add a game
        mockPlayers.add(new ArrayList<>());
        mockViews.add(new ArrayList<>());

        for (int i = 0; i < 4; i++) {
            mockPlayers.get(0).add(new Player("User"+ 0 + i));
            mockPlayerMap.put(mockPlayers.get(0).get(i).getUsername(), mockPlayers.get(0).get(i));
            try {
                mockViews.get(0).add(new RMIVirtualView("User" + 0 + i,
                        new ClientState("User" + 0 + i,new Object())));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        mockModels.get(0).setPlayers(mockPlayers.get(0));
        mockModels.get(0).setVirtualViews(mockViews.get(0));


        // MockController creation for tests
        mockController = new Controller(mockModels, mockPlayerMap);
        rmiHandler = new RMIHandler(mockController);

        mockController.startGame(0);
    }

    @Test
    void removeTiles() {
        Message message;
        Message expectedMessage = new Message();
        String playerID = mockController.getLobby().getGames().get(0).getCurrPlayer().getUsername();
        Point point1 = new Point(3, 8);
        Point point2 = new Point(4, 8);
        ArrayList<Point> points = new ArrayList<>();
        points.add(point1);
        points.add(point2);

        expectedMessage.setType(MessageTypes.OK);
        expectedMessage.setContent("Move successful remove tiles");

        try {
            message = rmiHandler.removeTiles(0, playerID, points);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expectedMessage.getType(), message.getType());
        Assertions.assertEquals(expectedMessage.getUsername(), message.getUsername());
    }
    /*
    @Test
    void swapOrder() {
        Message message;
        Message expectedMessage = new Message();
        String playerID = mockController.getLobby().getGames().get(0).getCurrPlayer().getUsername();
        ArrayList<Integer> ints = new ArrayList<>();

        ints.add(2);
        ints.add(1);

        expectedMessage.setType(MessageTypes.OK);
        expectedMessage.setContent("Move successful add to bookshelf");

        try {
            message = rmiHandler.swapOrder(ints, 0, playerID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println(message.getType());
        System.out.println("message = " + message.getUsername());

        Assertions.assertEquals(expectedMessage.getType(), message.getType());
        Assertions.assertEquals(expectedMessage.getUsername(), message.getUsername());
    }
    */
    @Test
    void addToBookshelf() {
        Message message;
        Message expectedMessage = new Message();
        String playerID = mockController.getLobby().getGames().get(0).getCurrPlayer().getUsername();
        int column = 0;

        expectedMessage.setType(MessageTypes.OK);
        expectedMessage.setContent("Move successful add to bookshelf");

        try {
            message = rmiHandler.addToBookshelf(0, playerID, column);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println(message.getType());
        System.out.println("message = " + message.getUsername());

        Assertions.assertEquals(expectedMessage.getType(), message.getType());
        Assertions.assertEquals(expectedMessage.getUsername(), message.getUsername());
    }

    @Test
    void newLobby() {
    }

    @Test
    void acceptRmiConnection() {
    }

    @Test
    void pingPong() {
    }

    @Test
    void sendMessage() {
    }

    @Test
    void testSendMessage() {
    }
}
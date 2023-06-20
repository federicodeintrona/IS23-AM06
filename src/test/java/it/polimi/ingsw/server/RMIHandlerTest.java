package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.server.Model.Model;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Test class for RMIHandler
 * <p>
 * Used the @TestMethodOrder(MethodOrderer.OrderAnnotation.class) to decide
 * the order of tests to simulate a real-like 4 players game
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    /**
     * Testing rmiHandler's removeTiles method by removing 2
     * tiles from the starting board of a 4 players game
     */
    @Test
    @Order(1)
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

    /**
     * Testing rmiHandler's swapOrder method
     * by swapping 2 tiles with each other
     */
    @Test
    @Order(2)
    void swapOrder() {
        Message message;
        Message expectedMessage = new Message();
        String playerID = mockController.getLobby().getGames().get(0).getCurrPlayer().getUsername();
        ArrayList<Integer> ints = new ArrayList<>();

        ints.add(2);
        ints.add(1);

        expectedMessage.setType(MessageTypes.OK);
        expectedMessage.setContent("Move successful swap order");

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

    /**
     * Testing rmiHandler's addToBookshelf method by adding 2
     * tiles in the first column of an empty bookshelf
     */
    @Test
    @Order(3)
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

    /*
    @Test
    @Order(6)
    void acceptRmiConnection() {
        IntMessage message;
        IntMessage expectedMessage = new IntMessage();
        String newPlayer = new String("#");

        // Creating an instance for ClientState
        ClientState clientState;
        try {
            clientState = new ClientState(new Object());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        expectedMessage.setType(MessageTypes.NEW_LOBBY);
        expectedMessage.setContent("Select the number of players (2 to 4)");

        // Calling the Controller method
        try {
            message = rmiHandler.acceptRmiConnection(newPlayer, clientState);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("message.getUsername() = " + message.getUsername());
        System.out.println("message.getType() = " + message.getType());

        Assertions.assertEquals(expectedMessage.getType(), message.getType());
        Assertions.assertEquals(expectedMessage.getUsername(), message.getUsername());
    }
     */

    @Test
    void pingPong() {
        boolean reply;

        try {
            reply = rmiHandler.pingPong();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(reply);
    }

    /**
     * Testing a ChatMessage forwarding destined to PublicChat
     */
    @Test
    @Order(4)
    void sendMessage1() {
        ChatMessage message;
        String playerID = mockController.getLobby().getGames().get(0).getCurrPlayer().getUsername();
        String str = new String("test");
        ChatMessage expectedMessage = new ChatMessage(playerID, str);
        expectedMessage.setType(MessageTypes.CHAT);

        try {
            message = (ChatMessage) rmiHandler.sendMessage(0, playerID, str);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expectedMessage.getUsername(), message.getUsername());
        Assertions.assertEquals(expectedMessage.getMessage(), message.getMessage());
        Assertions.assertEquals(expectedMessage.getType(), message.getType());
    }

    /**
     * Testing a ChatMessage forwarding destined to PrivateChat
     */
    @Test
    @Order(5)
    void sendMessage2() {
        ChatMessage message;
        String sendingUsername = mockController.getLobby().getGames().get(0).getCurrPlayer().getUsername();
        String receivingUsername = mockController.getLobby().getGames().get(0).getNextPlayer().getUsername();
        String str = new String("test");
        ChatMessage expectedMessage = new ChatMessage(sendingUsername, str, receivingUsername);
        expectedMessage.setType(MessageTypes.CHAT);

        try {
            message = (ChatMessage) rmiHandler.sendMessage(0, sendingUsername, str, receivingUsername);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expectedMessage.getUsername(), message.getUsername());
        Assertions.assertEquals(expectedMessage.getMessage(), message.getMessage());
        Assertions.assertEquals(expectedMessage.getReceivingUsername(), message.getReceivingUsername());
        Assertions.assertEquals(expectedMessage.getType(), message.getType());
    }
}
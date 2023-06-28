package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.utils.Chat;
import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;

/**
 * Test Class for ChatHandler
 */

class ChatHandlerTest {
    @Mock
    private CLIMain cli;
    @Mock
    private ClientState clientState;
    @Mock
    private ReadShell readShell;
    @Mock
    private CLIPrint cliPrint;
    @Mock
    private ChatController chatController;
    @Captor
    private ArgumentCaptor<ChatMessage> chatMessageCaptor;

    /**
     * Setup for Mockito
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testing method that assure the correct creation
     * of a message during the use of the PublicChat
     */
    @Test
    void settingForPublicChat1() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username = "yoda";
        String input = "Test message\n#exit";
        ChatMessage message = new ChatMessage(username, "Test message");

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);
        when(cli.getReadShell()).thenReturn(readShell);
        doNothing().when(readShell).sendMessage(chatMessageCaptor.capture());

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            chatHandler.settingForPublicChat();
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        ChatMessage capturedMessage = chatMessageCaptor.getValue();

        Assertions.assertEquals(message, capturedMessage);
    }

    /**
     * Testing method to control the correct
     * activation of case "#help" in the switch
     * while chatting in PublicChat
     */
    @Test
    void settingForPublicChat2() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username = "yoda";
        String input = "#help\n#exit";

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);
        doNothing().when(cliPrint).helpForChat();

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            chatHandler.settingForPublicChat();
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        //Assertions.assertEquals(expectedOutput, output);
        verify(cliPrint).helpForChat();
    }

    /**
     * Testing method to control the correct
     * activation of case "#printprivatechat" in
     * the switch while chatting in PublicChat
     */
    @Test
    void settingForPublicChat3() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String myUsername = "yoda";
        String otherUsername = "obi-wan";
        String input = "#printprivatechat\n#exit";

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(myUsername);
        allUsername.add(otherUsername);

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(otherUsername, true);
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(myUsername);
        when(clientState.getAllUsername()).thenReturn(allUsername);
        doNothing().when(cliPrint).helpForChat();

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            chatHandler.settingForPublicChat();
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        //Assertions.assertEquals(expectedOutput, output);
        verify(cliPrint).printChat(otherUsername, false);
    }

    /**
     * Testing method to control the correct
     * activation of default case in the switch
     * while chatting in PublicChat
     */
    @Test
    void settingForPublicChat4() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username = "yoda";
        String input = "#\n#exit";

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);
        doNothing().when(cliPrint).helpForChat();

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            chatHandler.settingForPublicChat();
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }
    }

    /**
     * Testing method to control the correct
     * activation of case "#switchtoprivate" in
     * the switch while chatting in PublicChat
     */
    @Test
    void settingForPublicChat5() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        Chat privateChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String myUsername = "yoda";
        String otherUsername = "obi-wan";
        String input = "#switchtoprivate";
        boolean switchToPrivate = false;

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(myUsername);
        allUsername.add(otherUsername);

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(otherUsername, true);
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(chatController.getPrivateChat(otherUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(myUsername);
        when(clientState.getAllUsername()).thenReturn(allUsername);
        doNothing().when(cliPrint).helpForChat();

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            try {
                chatHandler.settingForPublicChat();
            } catch (NoSuchElementException e) {
                switchToPrivate = true;
            }
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        Assertions.assertTrue(switchToPrivate);
    }

    /**
     * Testing method that assure the correct creation
     * of a message during the use of the PrivateChat
     */
    @Test
    void settingForPrivateChat1() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String myUsername = "yoda";
        String otherUsername = "obi-wan";
        String input = "Test message\n#exit";
        ChatMessage message = new ChatMessage(myUsername, "Test message", otherUsername);

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(myUsername);
        allUsername.add(otherUsername);

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(otherUsername, true);
        when(chatController.getPrivateChat(otherUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(myUsername);
        when(clientState.getAllUsername()).thenReturn(allUsername);
        when(cli.getReadShell()).thenReturn(readShell);
        doNothing().when(readShell).sendMessage(chatMessageCaptor.capture());

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            chatHandler.settingForPrivateChat();
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        ChatMessage capturedMessage = chatMessageCaptor.getValue();

        Assertions.assertEquals(message, capturedMessage);
    }

    /**
     * Testing method to control the correct
     * activation of case "#help" in the switch
     * while chatting in PublicChat
     */
    @Test
    void settingForPrivateChat2() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String myUsername = "yoda";
        String otherUsername = "obi-wan";
        String input = "#help\n#exit";

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(myUsername);
        allUsername.add(otherUsername);

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(otherUsername, true);
        when(chatController.getPrivateChat(otherUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(myUsername);
        when(clientState.getAllUsername()).thenReturn(allUsername);
        when(cli.getReadShell()).thenReturn(readShell);
        doNothing().when(readShell).sendMessage(chatMessageCaptor.capture());

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            chatHandler.settingForPrivateChat();
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        verify(cliPrint).helpForChat();
    }

    /**
     * Testing method to control the correct
     * activation of case "#printpublicchat" in the switch
     * while chatting in PrivateChat
     */
    @Test
    void settingForPrivateChat3() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String myUsername = "yoda";
        String otherUsername = "obi-wan";
        String input = "#printpublicchat\n#exit";

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(myUsername);
        allUsername.add(otherUsername);

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(otherUsername, true);
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPrivateChat(otherUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(myUsername);
        when(clientState.getAllUsername()).thenReturn(allUsername);
        when(cli.getReadShell()).thenReturn(readShell);
        doNothing().when(readShell).sendMessage(chatMessageCaptor.capture());

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            chatHandler.settingForPrivateChat();
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        verify(cliPrint).printChat(false);
    }

    /**
     * Testing method to control the correct
     * activation of default case in the switch
     * while chatting in PrivateChat
     */
    @Test
    void settingForPrivateChat4() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        Chat publicChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String myUsername = "yoda";
        String otherUsername = "obi-wan";
        String input = "#\n#exit";

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(myUsername);
        allUsername.add(otherUsername);

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(true);
        doNothing().when(cliPrint).printChat(otherUsername, true);
        when(chatController.getPrivateChat(otherUsername)).thenReturn(privateChat);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(myUsername);
        when(clientState.getAllUsername()).thenReturn(allUsername);
        when(cli.getReadShell()).thenReturn(readShell);
        doNothing().when(readShell).sendMessage(chatMessageCaptor.capture());

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            chatHandler.settingForPrivateChat();
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }
    }

    /**
     * Testing method to control the correct
     * activation of case "#switchtopublic" in the switch
     * while chatting in PrivateChat
     */
    @Test
    void settingForPrivateChat5() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        Chat publicChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String myUsername = "yoda";
        String otherUsername = "obi-wan";
        String input = "#switchtopublic";
        boolean switchToPublic = false;

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(myUsername);
        allUsername.add(otherUsername);

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(otherUsername, true);
        doNothing().when(cliPrint).printChat(true);
        when(chatController.getPrivateChat(otherUsername)).thenReturn(privateChat);
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(myUsername);
        when(clientState.getAllUsername()).thenReturn(allUsername);
        when(cli.getReadShell()).thenReturn(readShell);
        doNothing().when(readShell).sendMessage(chatMessageCaptor.capture());

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            try {
                chatHandler.settingForPrivateChat();

            } catch (NoSuchElementException e){
                switchToPublic = true;
            }
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        Assertions.assertTrue(switchToPublic);
    }

    /**
     * Testing method to assure the correct manage of an
     * incoming PublicChat message while the chat is open
     */
    @Test
    void newPublicMessage1() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username1 = "yoda";
        String username2 = "obi-wan";
        String message = "may the force be with you";
        String expectedOutput = ColorCLI.UNDERLINE + "yoda" + ColorCLI.RESET + ": may the force be with you" + System.lineSeparator();
        ChatMessage chatMessage = new ChatMessage(username1, message);

        publicChat.setChatIsEnable(true);

        // Setting Mock's methods
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username2);

        // Creating a ByteArrayOutputStream object to intercept the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        chatHandler.newPublicMessage(chatMessage);

        // Getting the output as string
        String output = outputStream.toString();

        Assertions.assertEquals(expectedOutput, output);
    }

    /**
     * Testing method to assure the correct manage of an
     * incoming PublicChat message while the chat is closed
     */
    @Test
    void newPublicMessage2() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat publicChat = new Chat();
        String username = "yoda";
        String message = "may the force be with you";
        String expectedOutput1 = "*One new message from the PUBLIC CHAT*" + System.lineSeparator() + "*2 new messages from the PUBLIC CHAT*" + System.lineSeparator();
        ChatMessage chatMessage = new ChatMessage(username, message);

        publicChat.setChatIsEnable(false);

        // Setting Mock's methods
        when(chatController.getPublicChat()).thenReturn(publicChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(username);

        // Creating a ByteArrayOutputStream object to intercept the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        chatHandler.newPublicMessage(chatMessage);
        chatHandler.newPublicMessage(chatMessage);

        // Getting the output as string
        String output = outputStream.toString();

        Assertions.assertEquals(expectedOutput1, output);
    }

    /**
     * Testing method to assure the correct manage
     * of an incoming PrivateChat message when the
     * sendingUsername is the player himself
     */
    @Test
    void newPrivateMessage1() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        String forwardingUsername = "yoda";
        String receivingUsername = "obi-wan";
        String message = "may the force be with you";
        ChatMessage chatMessage = new ChatMessage(forwardingUsername, message, receivingUsername);

        // Setting Mock's methods
        when(chatController.getPrivateChat(receivingUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(forwardingUsername);

        chatHandler.newPrivateMessage(chatMessage);

        Assertions.assertEquals(chatMessage, privateChat.getChatMessages().get(0));
    }

    /**
     * Testing method to assure the correct manage of an
     * incoming PrivateChat message while the chat is open
     */
    @Test
    void newPrivateMessage2() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        String forwardingUsername = "yoda";
        String receivingUsername = "obi-wan";
        String message = "may the force be with you";
        String expectedOutput = ColorCLI.UNDERLINE + "yoda" + ColorCLI.RESET + ": may the force be with you" + System.lineSeparator();
        ChatMessage chatMessage = new ChatMessage(forwardingUsername, message, receivingUsername);

        privateChat.setChatIsEnable(true);

        // Setting Mock's methods
        when(chatController.getPrivateChat(forwardingUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(receivingUsername);

        // Creating a ByteArrayOutputStream object to intercept the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        chatHandler.newPrivateMessage(chatMessage);

        // Getting the output as string
        String output = outputStream.toString();

        Assertions.assertEquals(expectedOutput, output);
        Assertions.assertEquals(chatMessage, privateChat.getChatMessages().get(0));
    }

    /**
     * Testing method to assure the correct manage of an
     * incoming PrivateChat message while the chat is closed
     */
    @Test
    void newPrivateMessage3() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        String forwardingUsername = "yoda";
        String receivingUsername = "obi-wan";
        String message = "may the force be with you";
        String expectedOutput = "*One new message from the PRIVATE CHAT with " + ColorCLI.UNDERLINE + "yoda" + ColorCLI.RESET + "*" + System.lineSeparator() +
                                "*2 new messages from the PRIVATE CHAT with " + ColorCLI.UNDERLINE + "yoda" + ColorCLI.RESET + "*" + System.lineSeparator();
        ChatMessage chatMessage = new ChatMessage(forwardingUsername, message, receivingUsername);

        privateChat.setChatIsEnable(false);

        // Setting Mock's methods
        when(chatController.getPrivateChat(forwardingUsername)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(receivingUsername);

        // Creating a ByteArrayOutputStream object to intercept the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        chatHandler.newPrivateMessage(chatMessage);
        chatHandler.newPrivateMessage(chatMessage);

        // Getting the output as string
        String output = outputStream.toString();

        Assertions.assertEquals(expectedOutput, output);
        Assertions.assertEquals(chatMessage, privateChat.getChatMessages().get(0));
        Assertions.assertEquals(chatMessage, privateChat.getChatMessages().get(1));
    }

    /**
     * Testing the private method privateChatHandler() for
     * success when the username inserted is correct
     */
    @Test
    void privateChatHandler1() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String player1 = "yoda";
        String player2 = "obi-wan";
        String input = "palpatine";
        boolean methodWorking = false;

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(player1);
        allUsername.add(player2);
        allUsername.add(input);

        doNothing().when(cliPrint).printChat(input, true);
        when(chatController.getPrivateChat(input)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(player1);
        when(clientState.getAllUsername()).thenReturn(allUsername);

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            try {
                chatHandler.settingForPrivateChat();
            } catch (NoSuchElementException e) {
                methodWorking = true;
            }
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        Assertions.assertTrue(methodWorking);
    }

    /**
     * Testing the private method privateChatHandler() for
     * failure when the first username inserted is not existing
     */
    @Test
    void privateChatHandler2() {
        ChatHandler chatHandler = new ChatHandler(chatController, cli, cliPrint);
        Chat privateChat = new Chat();
        ArrayList<String> allUsername = new ArrayList<>();
        String player1 = "yoda";
        String player2 = "obi-wan";
        String input = "vhaiuqbvq\npalpatine";
        boolean methodWorking = false;

        // Converts the input string into an InputStream object
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        // Saves the current input in order to restore it later
        InputStream originalInputStream = System.in;

        allUsername.add(player1);
        allUsername.add(player2);
        allUsername.add(input);

        // Setting Mock's methods
        doNothing().when(cliPrint).printChat(input, true);
        when(chatController.getPrivateChat(input)).thenReturn(privateChat);
        when(cli.getClientState()).thenReturn(clientState);
        when(clientState.getMyUsername()).thenReturn(player1);
        when(clientState.getAllUsername()).thenReturn(allUsername);

        try {
            // Set the simulated input as system input
            System.setIn(inputStream);

            try {
                chatHandler.settingForPrivateChat();
            } catch (NoSuchElementException e) {
                methodWorking = true;
            }
        } finally {
            // Restore the original system input
            System.setIn(originalInputStream);
        }

        Assertions.assertTrue(methodWorking);
    }
}
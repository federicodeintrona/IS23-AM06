package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.MessageTypes;

import java.util.Scanner;

/**
 * <p>
 *     Class to manage the Chat in CLI.
 * </p>
 * <p>
 *     Its job is to create, forward, receive and print
 *     all types of messages destined to the Chat.
 * </p>
 */
public class ChatHandler {
    private final ChatController chatController;
    private final CLIMain cliMain;
    private final CLIPrint cliPrint;

    /**
     * Constructor that get called by CLIMain.
     *
     * @param chatController        ChatController saved in ClientState.
     * @param cliMain       Instance of cliMain.
     * @param cliPrint        Instance of cliPrint.
     */
    public ChatHandler(ChatController chatController, CLIMain cliMain, CLIPrint cliPrint) {
        this.chatController = chatController;
        this.cliPrint = cliPrint;
        this.cliMain = cliMain;
    }

    /**
     * Method to clear the shell.
     */
    public void clearCLI(){
        System.out.println(ColorCLI.CLEAR);
        System.out.flush();
    }

    /**
     * <p>
     *     Method to menage the PublicChat.
     * </p>
     * <p>
     *     The scanner gets the message written by the player and saves it in str.
     *     Depending on the first character of str the method has 2 behavior:
     *     <ul>
     *         <li>to create a ChatMessage to send;</li>
     *         <li>to call one of the chat's method.</li>
     *     </ul>
     * </p>
     */
    private void chat () {
        Scanner scanner=new Scanner(System.in);
        String str;

        while (chatController.getPublicChat().ChatIsEnable()) {

            str = scanner.nextLine();
            if (str.isEmpty()) continue;

            // Creating the ChatMessage in case the str is not a command
            if (str.charAt(0) != '#') {
                createChatMessage(str);
                continue;
            }

            str = str.toLowerCase();
            switch (str) {
                case "#exit" -> endOfChat();
                case "#help", "#h" -> cliPrint.helpForChat();
                case "#quit", "#q" -> cliMain.close();
                case "#switchtoprivate" -> {
                    chatController.getPublicChat().setChatIsEnable(false);

                    String username = privateChatHandler();
                    clearCLI();

                    // Printing publicChat's history
                    cliPrint.printChat(username, true);

                    chatController.getPrivateChat(username).setChatIsEnable(true);
                    chat(username);
                }
                case "#printprivatechat" -> {
                    String player = privateChatHandler();
                    cliPrint.printChat(player, false);
                }
                default -> System.out.println(str + " is NOT a valid command \nIf you need help put #help or #h");
            }
        }
    }

    /**
     * <p>
     *     Method to menage the PrivateChat.
     * </p>
     * <p>
     *     The scanner gets the message written by the player and saves it in str.
     *     Depending on the first character of str the method has 2 behavior:
     *     <ul>
     *         <li>to create a ChatMessage to send;</li>
     *         <li>to call one of the chat's method.</li>
     *     </ul>
     * </p>
     *
     * @param username        The player who the message is destined to
     */
    private void chat (String username) {
        Scanner scanner=new Scanner(System.in);
        String str;

        while (chatController.getPrivateChat(username).ChatIsEnable()) {

            str = scanner.nextLine();
            if (str.isEmpty()) continue;

            // Creating the ChatMessage in case the str is not a command
            if (str.charAt(0) != '#') {
                createChatMessage(str, username);
                continue;
            }

            str = str.toLowerCase();
            switch (str) {
                case "#exit" -> endOfChat(username);
                case "#help", "#h" -> cliPrint.helpForChat();
                case "#quit", "#q" -> cliMain.close();
                case "#switchtopublic" -> {
                    chatController.getPrivateChat(username).setChatIsEnable(false);
                    clearCLI();

                    // Printing publicChat's history
                    cliPrint.printChat(true);
                    chatController.getPublicChat().setChatIsEnable(true);
                    chat();
                }
                case "#switchtoprivate" -> {
                    if (cliMain.getClientState().getAllUsername().size() == 2) {
                        System.out.println("The command " + str + " is not available for a 2 players game");
                        break;
                    }

                    chatController.getPublicChat().setChatIsEnable(false);

                    String otherUsername = privateChatHandler();
                    clearCLI();

                    // Printing privateChat's history
                    cliPrint.printChat(otherUsername, true);

                    chatController.getPrivateChat(otherUsername).setChatIsEnable(true);
                    chat(otherUsername);
                }
                case "#printpublicchat" -> cliPrint.printChat(false);
                case "#printprivatechat" -> {
                    if (cliMain.getClientState().getAllUsername().size() == 2) {
                        System.out.println("The command " + str + " is not available for a 2 players game");
                        break;
                    }

                    String player = privateChatHandler();
                    cliPrint.printChat(player, false);
                }
                default -> System.out.println(str + " is NOT a valid command \nIf you need help put #help or #h");
            }
        }
    }

    /**
     * <p>
     *      Method that helps the PrivateChat by getting via scanner
     *      the username of the player you want to chat with.
     * </p>
     * <p>
     *     During a 2 players game it automatically
     *     returns the other player without asking.
     * </p>
     *
     * @return      <i>String</i> containing username.
     */
    private String privateChatHandler () {
        int numOfPlayers = cliMain.getClientState().getAllUsername().size();
        Scanner scanner=new Scanner(System.in);
        String str = null;
        boolean existingUsername = false;

        // Returning the only other available player in a 2 players game
        if (numOfPlayers == 2) {
            return cliMain.getClientState().getAllUsername().stream()
                    .filter(x -> !x.equals(cliMain.getClientState().getMyUsername()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No other player found"));
        }

        System.out.println("Who do you want to chat with?");
        while (!existingUsername) {
            str = scanner.nextLine().toLowerCase();

            if (cliMain.getClientState().getAllUsername().contains(str) && !cliMain.getClientState().getMyUsername().equals(str)) existingUsername = true;
            else System.out.println("Username not found, try again\n");
        }

        return str;
    }

    /**
     * Method that creates a ChatMessage
     * destined for the PublicChat.
     *
     * @param string        The message to forward.
     */
    private void createChatMessage(String string) {

        // Setting the message
        ChatMessage message = new ChatMessage(cliMain.getClientState().getMyUsername(), string);
        message.setType(MessageTypes.CHAT);

        // Sending the message
        cliMain.getReadShell().sendMessage(message);
    }

    /**
     * Method that creates a ChatMessage
     * destined for the PrivateChat.
     *
     * @param string        The message to forward.
     * @param receivingPlayer       The player who the message is destined to.
     */
    private void createChatMessage(String string, String receivingPlayer) {

        // Setting the message
        ChatMessage message = new ChatMessage(cliMain.getClientState().getMyUsername(), string, receivingPlayer);
        message.setType(MessageTypes.CHAT);

        // Sending the message
        cliMain.getReadShell().sendMessage(message);
    }

    /**
     * Method that prepares the CLI in order to start the PublicChat.
     */
    public void settingForPublicChat () {
        // Printing publicChat's history
        cliPrint.printChat(true);

        chatController.getPublicChat().setChatIsEnable(true);
        chat();
    }

    /**
     * Method that prepares the CLI in order to start the PrivateChat.
     */
    public void settingForPrivateChat () {
        String username = privateChatHandler();
        clearCLI();

        // Printing publicChat's history
        cliPrint.printChat(username, true);

        chatController.getPrivateChat(username).setChatIsEnable(true);
        chat(username);
    }

    /**
     * Method to manage the arriving of a new message
     * from server destined to the PublicChat.
     *
     * @param message       the ChatMessage to analyze.
     */
    public void newPublicMessage(ChatMessage message) {
        if (chatController.getPublicChat().ChatIsEnable()) {
            if (!message.getText().equals(cliMain.getClientState().getMyUsername())) System.out.println(ColorCLI.UNDERLINE + message.getText() + ColorCLI.RESET + ": " + message.getMessage());
        }
        else {
            chatController.getPublicChat().updateUnReadMessages();
            if (chatController.getPublicChat().getUnReadMessages() == 1) System.out.println("*One new message from the PUBLIC CHAT*");
            else System.out.println("*" + chatController.getPublicChat().getUnReadMessages() + " new messages from the PUBLIC CHAT*");
        }

        chatController.getPublicChat().addMessage(message);

    }

    /**
     * Method to manage the arriving of a new message
     * from server destined to the PrivateChat.
     *
     * @param message       the ChatMessage to analyze.
     */
    public void newPrivateMessage(ChatMessage message) {
        String forwardingPlayer = message.getText();
        String conversation = message.getMessage();
        String receivingPlayer = message.getReceivingUsername();

        if (forwardingPlayer.equals(cliMain.getClientState().getMyUsername())) {
            chatController.getPrivateChat(receivingPlayer).addMessage(message);
        }
        else {
            if (chatController.getPrivateChat(forwardingPlayer).ChatIsEnable()) System.out.println(ColorCLI.UNDERLINE + forwardingPlayer + ColorCLI.RESET + ": " + conversation);
            else {
                chatController.getPrivateChat(forwardingPlayer).updateUnReadMessages();

                if (chatController.getPrivateChat(forwardingPlayer).getUnReadMessages() == 1)
                    System.out.println("*One new message from the PRIVATE CHAT with " + ColorCLI.UNDERLINE + forwardingPlayer + ColorCLI.RESET + "*");
                else
                    System.out.println("*" + chatController.getPrivateChat(forwardingPlayer).getUnReadMessages() + " new messages from the PRIVATE CHAT with " + ColorCLI.UNDERLINE + forwardingPlayer + ColorCLI.RESET + "*");
            }
            chatController.getPrivateChat(forwardingPlayer).addMessage(message);
        }

    }

    /**
     * Method that helps to visualize the ending of the PublicChat.
     */
    private void endOfChat () {
        System.out.println("The PUBLIC CHAT is now closed ________________________________");
        chatController.getPublicChat().setChatIsEnable(false);
        cliMain.setOpenChat(false);
        clearCLI();
    }

    /**
     * Method that helps to visualize the ending of the PrivateChat.
     */
    private void endOfChat (String username) {
        System.out.println("The PRIVATE CHAT with " + username + "is now closed _________________________");
        chatController.getPrivateChat(username).setChatIsEnable(false);
        cliMain.setOpenChat(false);
        clearCLI();
    }
}


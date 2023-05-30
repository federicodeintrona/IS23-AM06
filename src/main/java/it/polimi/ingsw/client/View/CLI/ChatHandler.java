package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.MessageTypes;

import java.util.Scanner;

public class ChatHandler {
    private ChatController chatController;
    private CLIMain cliMain;
    private CLIPrint cliPrint;

    public ChatHandler(ChatController chatController, CLIMain cliMain, CLIPrint cliPrint) {
        this.chatController = chatController;
        this.cliPrint = cliPrint;
        this.cliMain = cliMain;
    }

    public void clearCLI(){
        System.out.println(ColorCLI.CLEAR);
        System.out.flush();
    }
    private void chat () {
        Scanner scanner=new Scanner(System.in);
        String str = null;

        while (chatController.getPublicChat().ChatIsEnable()) {


            str = scanner.nextLine();

            switch (str) {
                case "#exit" -> endOfChat();
                case "#help", "#h" -> cliPrint.helpForChat();
                case "#switchToPrivate" -> {
                    chatController.getPublicChat().setChatIsEnable(false);

                    String username = privateChatHandler();
                    clearCLI();

                    // Printing publicChat's history
                    cliPrint.printChat(username, true);

                    chatController.getPrivateChat(username).setChatIsEnable(true);
                    chat(username);
                }
                case "#printPrivateChat" -> {
                    String player = privateChatHandler();
                    cliPrint.printChat(player, false);
                }
                default -> createChatMessage(str);
            }
        }
    }

    private void chat (String username) {
        Scanner scanner=new Scanner(System.in);
        String str = null;

        while (chatController.getPrivateChat(username).ChatIsEnable()) {


            str = scanner.nextLine();

            switch (str) {
                case "#exit" -> endOfChat(username);
                case "#help", "#h" -> cliPrint.helpForChat();
                case "#switchToPublic" -> {
                    chatController.getPrivateChat(username).setChatIsEnable(false);
                    clearCLI();

                    cliPrint.printChat(true);
                    chatController.getPublicChat().setChatIsEnable(true);
                    chat();
                }
                case "#printPublicChat" -> cliPrint.printChat(false);
                case "#printPrivateChat" -> {
                    String player = privateChatHandler();
                    cliPrint.printChat(player, false);
                }
                default -> createChatMessage(str, username);
            }
        }
    }

    private String privateChatHandler () {
        Scanner scanner=new Scanner(System.in);
        String str = null;

        System.out.println("Who do you want to chat with?");
        str = scanner.nextLine();

        return str;
    }

    private void createChatMessage(String string) {

        // Setting the message
        ChatMessage message = new ChatMessage(cliMain.getClientState().getMyUsername(), string);
        message.setType(MessageTypes.CHAT);

        // Sending the message
        cliMain.getReadShell().sendMessage(message);
    }

    private void createChatMessage(String string, String receivingPlayer) {

        // Setting the message
        ChatMessage message = new ChatMessage(cliMain.getClientState().getMyUsername(), string, receivingPlayer);
        message.setType(MessageTypes.CHAT);

        // Sending the message
        cliMain.getReadShell().sendMessage(message);
    }

    public void settingForPublicChat () {
        // Printing publicChat's history
        cliPrint.printChat(true);

        chatController.getPublicChat().setChatIsEnable(true);
        chat();
    }

    public void settingForPrivateChat () {
        String username = privateChatHandler();
        clearCLI();

        // Printing publicChat's history
        cliPrint.printChat(username, true);

        chatController.getPrivateChat(username).setChatIsEnable(true);
        chat(username);
    }

    public void newPublicMessage(ChatMessage message) {
        if (chatController.getPublicChat().ChatIsEnable()) {
            if (!message.getUsername().equals(cliMain.getClientState().getMyUsername())) System.out.println(ColorCLI.UNDERLINE + message.getUsername() + ColorCLI.RESET + ": " + message.getMessage());
        }
        else {
            chatController.getPublicChat().updateUnReadMessages();
            if (chatController.getPublicChat().getUnReadMessages() == 1) System.out.println("*One new message from the PUBLIC CHAT*");
            else System.out.println("*" + chatController.getPublicChat().getUnReadMessages() + " new messages from the PUBLIC CHAT*");
        }

        chatController.getPublicChat().addMessage(message);

    }

    public void newPrivateMessage(ChatMessage message) {
        String forwardingPlayer = message.getUsername();
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

    private void endOfChat () {
        System.out.println("The PUBLIC CHAT is now closed ________________________________");
        chatController.getPublicChat().setChatIsEnable(false);
        clearCLI();
    }

    private void endOfChat (String username) {
        System.out.println("The PRIVATE CHAT with " + username + "is now closed _________________________");
        chatController.getPrivateChat(username).setChatIsEnable(false);
        clearCLI();
    }
}

package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.ChatController;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.MessageTypes;

import java.util.Scanner;

public class ChatHandler {
    private final ChatController chatController;
    private final CLIMain cliMain;
    private final CLIPrint cliPrint;

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
        String str;

        while (chatController.getPublicChat().ChatIsEnable()) {

            str = scanner.nextLine();

            // Creating the ChatMessage in case the str is not a command
            if (str.charAt(0) != '#') {
                createChatMessage(str);
                continue;
            }

            str = str.toLowerCase();
            switch (str) {
                case "#exit" -> endOfChat();
                case "#help", "#h" -> cliPrint.helpForChat();
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

    private void chat (String username) {
        Scanner scanner=new Scanner(System.in);
        String str;

        while (chatController.getPrivateChat(username).ChatIsEnable()) {

            str = scanner.nextLine();

            // Creating the ChatMessage in case the str is not a command
            if (str.charAt(0) != '#') {
                createChatMessage(str, username);
                continue;
            }

            str = str.toLowerCase();
            switch (str) {
                case "#exit" -> endOfChat(username);
                case "#help", "#h" -> cliPrint.helpForChat();
                case "#switchtopublic" -> {
                    chatController.getPrivateChat(username).setChatIsEnable(false);
                    clearCLI();

                    cliPrint.printChat(true);
                    chatController.getPublicChat().setChatIsEnable(true);
                    chat();
                }
                case "#printublicchat" -> cliPrint.printChat(false);
                case "#printrivatechat" -> {
                    String player = privateChatHandler();
                    cliPrint.printChat(player, false);
                }
                default -> System.out.println(str + " is NOT a valid command \nIf you need help put #help or #h");
            }
        }
    }

    private String privateChatHandler () {
        Scanner scanner=new Scanner(System.in);
        String str;

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
            if (!message.getText().equals(cliMain.getClientState().getMyUsername())) System.out.println(ColorCLI.UNDERLINE + message.getText() + ColorCLI.RESET + ": " + message.getMessage());
        }
        else {
            chatController.getPublicChat().updateUnReadMessages();
            if (chatController.getPublicChat().getUnReadMessages() == 1) System.out.println("*One new message from the PUBLIC CHAT*");
            else System.out.println("*" + chatController.getPublicChat().getUnReadMessages() + " new messages from the PUBLIC CHAT*");
        }

        chatController.getPublicChat().addMessage(message);

    }

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

    private void endOfChat () {
        System.out.println("The PUBLIC CHAT is now closed ________________________________");
        chatController.getPublicChat().setChatIsEnable(false);
        cliMain.setOpenChat(false);
        clearCLI();
    }

    private void endOfChat (String username) {
        System.out.println("The PRIVATE CHAT with " + username + "is now closed _________________________");
        chatController.getPrivateChat(username).setChatIsEnable(false);
        cliMain.setOpenChat(false);
        clearCLI();
    }
}


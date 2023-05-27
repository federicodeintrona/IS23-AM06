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
                case "#exit" -> {
                    chatController.getPublicChat().setChatIsEnable(false);
                    clearCLI();
                }
                case "#help", "#h" -> cliPrint.helpForChat();
                case "#switchtoprivate" -> {
                    chatController.getPublicChat().setChatIsEnable(false);

                    String username = privateChatHandler();
                    clearCLI();

                    // Printing publicChat's history
                    cliPrint.printChat(username);

                    chatController.getPrivateChat(username).setChatIsEnable(true);
                    chat(username);
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
                case "#exit" -> {
                    chatController.getPrivateChat(username).setChatIsEnable(false);
                    clearCLI();
                }
                case "#help", "#h" -> cliPrint.helpForChat();
                case "#switchtopublic" -> {
                    chatController.getPrivateChat(username).setChatIsEnable(false);
                    clearCLI();

                    cliPrint.printChat();
                    chatController.getPublicChat().setChatIsEnable(true);
                    chat();
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

        message.getConversation();

        // Sending the message
        cliMain.getReadShell().sendMessage(message);
    }

    public void settingForPublicChat () {
        // Printing publicChat's history
        cliPrint.printChat();

        chatController.getPublicChat().setChatIsEnable(true);
        chat();
    }

    public void settingForPrivateChat () {
        String username = privateChatHandler();
        clearCLI();

        // Printing publicChat's history
        cliPrint.printChat(username);

        chatController.getPrivateChat(username).setChatIsEnable(true);
        chat(username);
    }
}


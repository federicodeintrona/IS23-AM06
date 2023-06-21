package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.Messages.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;


class ChatControllerTest {

    @Test
    void getPublicChat() {
        ChatController chatController = new ChatController();
        Chat chat = new Chat();
        ChatMessage message = new ChatMessage("obi-wan", "test");

        chatController.getPublicChat().addMessage(message);
        chat.addMessage(message);
        Assertions.assertEquals(chat.getChatMessages().get(0), chatController.getPublicChat().getChatMessages().get(0));
    }

    @Test
    void getPrivateChat() {
        ChatController chatController = new ChatController();
        Chat chat = new Chat();
        String receivingUsername = "anakin";
        ChatMessage message = new ChatMessage("obi-wan", "test", receivingUsername);

        chatController.getPrivateChats().put(receivingUsername, new Chat());
        chatController.getPrivateChat(receivingUsername).addMessage(message);
        chat.addMessage(message);
        Assertions.assertEquals(chat.getChatMessages().get(0), chatController.getPrivateChat(receivingUsername).getChatMessages().get(0));
    }

    @Test
    void getPrivateChats() {
        ChatController chatController = new ChatController();
        HashMap<String, Chat> chats = new HashMap<>();
        String receivingUsername1 = "anakin";
        String receivingUsername2 = "yoda";
        ChatMessage message1 = new ChatMessage("obi-wan", "test1", receivingUsername1);
        ChatMessage message2 = new ChatMessage("obi-wan", "test2", receivingUsername2);

        chatController.getPrivateChats().put(receivingUsername1, new Chat());
        chatController.getPrivateChats().put(receivingUsername2, new Chat());
        chatController.getPrivateChat(receivingUsername1).addMessage(message1);
        chatController.getPrivateChat(receivingUsername2).addMessage(message2);

        chats.put(receivingUsername1, new Chat());
        chats.put(receivingUsername2, new Chat());
        chats.get(receivingUsername1).addMessage(message1);
        chats.get(receivingUsername2).addMessage(message2);

        Assertions.assertEquals(chats, chatController.getPrivateChats());
    }

    @Test
    void setPublicChat() {
        ChatController chatController = new ChatController();
        Chat chat = new Chat();
        ChatMessage message = new ChatMessage("obi-wan", "test");

        chat.addMessage(message);
        Assertions.assertEquals(0, chatController.getPublicChat().getChatMessages().size());

        chatController.setPublicChat(chat);
        Assertions.assertEquals(chat, chatController.getPublicChat());
    }

    @Test
    void setPrivateChats() {
        ChatController chatController = new ChatController();
        HashMap<String, Chat> chats = new HashMap<>();
        ChatMessage message = new ChatMessage("obi-wan", "test", "anakin");

        chats.put("anakin", new Chat());
        chats.get("anakin").addMessage(message);
        Assertions.assertTrue(chatController.getPrivateChats().isEmpty());

        chatController.setPrivateChats(chats);
        Assertions.assertEquals(chats, chatController.getPrivateChats());
    }
}
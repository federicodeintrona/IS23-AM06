package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.Messages.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        chatController.getPrivateChat(receivingUsername).addMessage(message);
        chat.addMessage(message);
        Assertions.assertEquals(chat.getChatMessages().get(0), chatController.getPrivateChat(receivingUsername).getChatMessages().get(0));
    }

    @Test
    void getPrivateChats() {
    }

    @Test
    void setPublicChat() {
    }

    @Test
    void setPrivateChat() {
    }
}
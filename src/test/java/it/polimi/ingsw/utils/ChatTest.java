package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.Messages.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    @Test
    void addMessage1() {
        Chat chat = new Chat();

        ChatMessage message1 = new ChatMessage("obi-wan", "test1");
        ChatMessage message2 = new ChatMessage("obi-wan", "test2", "anakin");
        chat.addMessage(message1);

        Assertions.assertEquals(chat.getOldestMessage(), 0);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getUsername(), message1.getUsername());
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1.getMessage());
        assertNull(chat.getChatMessages().get(chat.getOldestMessage()).getReceivingUsername());


        chat.addMessage(message2);

        Assertions.assertEquals(chat.getOldestMessage(), 1);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()), message1);
        Assertions.assertEquals(chat.getChatMessages().get(0).getUsername(), message2.getUsername());
        Assertions.assertEquals(chat.getChatMessages().get(0).getMessage(), message2.getMessage());
        Assertions.assertEquals(chat.getChatMessages().get(0).getReceivingUsername(), message2.getReceivingUsername());
    }

    @Test
    void addMessage2() {
        Chat chat = new Chat();

        String username1 = new String("yoda");
        String message1 = new String("may the force be with you");
        String username2 = new String("Mace_windu");
        String message2 = new String("purple");

        chat.addMessage(username1, message1);

        Assertions.assertEquals(chat.getOldestMessage(), 0);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getUsername(), username1);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1);
        assertNull(chat.getChatMessages().get(chat.getOldestMessage()).getReceivingUsername());


        chat.addMessage(username2, message2);

        Assertions.assertEquals(chat.getOldestMessage(), 1);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1);
        Assertions.assertEquals(chat.getChatMessages().get(0).getUsername(), username2);
        Assertions.assertEquals(chat.getChatMessages().get(0).getMessage(), message2);
        assertNull(chat.getChatMessages().get(0).getReceivingUsername());
    }

    @Test
    void addMessage3() {
        Chat chat = new Chat();

        String username1 = new String("yoda");
        String message1 = new String("may the force be with you");
        String receivingUsername1 = new String("obi-wan");
        ChatMessage conversation = new ChatMessage(username1, message1, receivingUsername1);
        String username2 = new String("Mace_windu");
        String message2 = new String("purple");
        String receivingUsername2 = new String("palpatine");

        chat.addMessage(username1, message1 ,receivingUsername1);

        Assertions.assertEquals(chat.getOldestMessage(), 0);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getUsername(), username1);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getReceivingUsername(), receivingUsername1);

        chat.addMessage(username2, message2 ,receivingUsername2);

        Assertions.assertEquals(chat.getOldestMessage(), 1);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1);
        Assertions.assertEquals(chat.getChatMessages().get(0).getUsername(), username2);
        Assertions.assertEquals(chat.getChatMessages().get(0).getMessage(), message2);
        Assertions.assertEquals(chat.getChatMessages().get(0).getReceivingUsername(), receivingUsername2);
    }

    @Test
    void getChatMessages() {
        Chat chat = new Chat();

        ChatMessage message1 = new ChatMessage("obi-wan", "test1");
        ChatMessage message2 = new ChatMessage("obi-wan", "test2", "anakin");
        ArrayList<ChatMessage> messages = new ArrayList<>();
        messages.add(message2);
        messages.add(message1);

        chat.addMessage(message1);
        chat.addMessage(message2);
        Assertions.assertEquals(messages, chat.getChatMessages());
    }

    @Test
    void getOldestMessage() {
        Chat chat = new Chat();

        ChatMessage message1 = new ChatMessage("obi-wan", "test1");
        ChatMessage message2 = new ChatMessage("obi-wan", "test2", "anakin");
        ChatMessage message3 = new ChatMessage("obi-wan", "test3");
        ChatMessage message4 = new ChatMessage("obi-wan", "test4");

        chat.addMessage(message1);
        chat.addMessage(message2);
        chat.addMessage(message3);
        chat.addMessage(message4);

        Assertions.assertEquals(3, chat.getOldestMessage());
    }

    @Test
    void updateUnReadMessages() {
        Chat chat = new Chat();

        Assertions.assertEquals(0, chat.getUnReadMessages());

        chat.updateUnReadMessages();
        Assertions.assertEquals(1, chat.getUnReadMessages());
    }

    @Test
    void resetUnReadMessages() {
        Chat chat = new Chat();

        Assertions.assertEquals(0, chat.getUnReadMessages());

        chat.updateUnReadMessages();
        Assertions.assertEquals(1, chat.getUnReadMessages());

        chat.resetUnReadMessages();
        Assertions.assertEquals(0, chat.getUnReadMessages());
    }

    @Test
    void getUnReadMessages() {
        Chat chat = new Chat();

        Assertions.assertEquals(0, chat.getUnReadMessages());
    }

    @Test
    void chatIsEnable() {
    }

    @Test
    void setChatIsEnable() {
    }
}
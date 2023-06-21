package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.Messages.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *      Test class for Chat
 * </p>
 */
class ChatTest {

    /**
     * Testing the adding mechanism of the Chat
     * when given a new ChatMessage
     */
    @Test
    void addMessage1() {
        Chat chat = new Chat();

        ChatMessage message1 = new ChatMessage("obi-wan", "test1");
        ChatMessage message2 = new ChatMessage("obi-wan", "test2", "anakin");
        chat.addMessage(message1);

        assertEquals(chat.getOldestMessage(), 0);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getText(), message1.getText());
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1.getMessage());
        assertNull(chat.getChatMessages().get(chat.getOldestMessage()).getReceivingUsername());


        chat.addMessage(message2);

        assertEquals(chat.getOldestMessage(), 1);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()), message1);
        assertEquals(chat.getChatMessages().get(0).getText(), message2.getText());
        assertEquals(chat.getChatMessages().get(0).getMessage(), message2.getMessage());
        assertEquals(chat.getChatMessages().get(0).getReceivingUsername(), message2.getReceivingUsername());
    }

    /**
     * Testing the adding mechanism of the Chat
     * when given a username sanding and a message
     */
    @Test
    void addMessage2() {
        Chat chat = new Chat();

        String username1 = "yoda";
        String message1 = "may the force be with you";
        String username2 = "Mace_windu";
        String message2 = "purple";

        chat.addMessage(username1, message1);

        assertEquals(chat.getOldestMessage(), 0);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getText(), username1);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1);
        assertNull(chat.getChatMessages().get(chat.getOldestMessage()).getReceivingUsername());


        chat.addMessage(username2, message2);

        assertEquals(chat.getOldestMessage(), 1);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1);
        assertEquals(chat.getChatMessages().get(0).getText(), username2);
        assertEquals(chat.getChatMessages().get(0).getMessage(), message2);
        assertNull(chat.getChatMessages().get(0).getReceivingUsername());
    }

    /**
     * Testing the adding mechanism of the Chat
     * when given a username sanding, a message
     * and a receiving username
     */
    @Test
    void addMessage3() {
        Chat chat = new Chat();

        String username1 = "yoda";
        String message1 = "may the force be with you";
        String receivingUsername1 = "obi-wan";
        String username2 = "Mace_windu";
        String message2 = "purple";
        String receivingUsername2 = "palpatine";

        chat.addMessage(username1, message1 ,receivingUsername1);

        assertEquals(chat.getOldestMessage(), 0);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getText(), username1);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getReceivingUsername(), receivingUsername1);

        chat.addMessage(username2, message2 ,receivingUsername2);

        assertEquals(chat.getOldestMessage(), 1);
        assertEquals(chat.getChatMessages().get(chat.getOldestMessage()).getMessage(), message1);
        assertEquals(chat.getChatMessages().get(0).getText(), username2);
        assertEquals(chat.getChatMessages().get(0).getMessage(), message2);
        assertEquals(chat.getChatMessages().get(0).getReceivingUsername(), receivingUsername2);
    }

    /**
     * Testing the method the gets all
     * Chat's history of messages
     */
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

    /**
     * Testing the method the gets the ArrayList
     * position of the oldest message in a Chat
     */
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

    /**
     * Testing the method to update the unread messages
     */
    @Test
    void updateUnReadMessages() {
        Chat chat = new Chat();

        Assertions.assertEquals(0, chat.getUnReadMessages());

        chat.updateUnReadMessages();
        Assertions.assertEquals(1, chat.getUnReadMessages());
    }

    /**
     * Testing the method that resets the unread messages to zero
     */
    @Test
    void resetUnReadMessages() {
        Chat chat = new Chat();

        Assertions.assertEquals(0, chat.getUnReadMessages());

        chat.updateUnReadMessages();
        Assertions.assertEquals(1, chat.getUnReadMessages());

        chat.resetUnReadMessages();
        Assertions.assertEquals(0, chat.getUnReadMessages());
    }

    /**
     * Testing the method that gets the number of unread messages
     */
    @Test
    void getUnReadMessages() {
        Chat chat = new Chat();

        Assertions.assertEquals(0, chat.getUnReadMessages());
    }

    /**
     * Testing the method that returns the maximum number
     * of messages allowed to be stored in the Chat's history
     */
    @Test
    void getMaxNumberOfMessages() {
        Chat chat = new Chat(10);

        Assertions.assertEquals(10, chat.getMaxNumberOfMessages());
    }

    /**
     * Testing the method that return the boolean that
     * checks if the chat is open on screen in order
     * to print the message
     */
    @Test
    void chatIsEnable() {
        Chat chat = new Chat();

        Assertions.assertFalse(chat.ChatIsEnable());
    }

    /**
     * Testing the method that sets the boolean chatIsEnable
     */
    @Test
    void setChatIsEnable() {
        Chat chat = new Chat();

        chat.setChatIsEnable(true);
        Assertions.assertTrue(chat.ChatIsEnable());

    }

    /**
     * Testing the private method that refresh the
     * chat by deleting the oldest message when
     * the maximum number of messages is exceeded
     */
    @Test
    void chatRefresh() {
        Chat chat = new Chat();
        ChatMessage message = new ChatMessage("r2-d2", "test");
        ChatMessage message1 = new ChatMessage("obi-wan", "test1");
        ChatMessage message2 = new ChatMessage("yoda", "test2");

        chat.addMessage(message);
        Assertions.assertEquals(chat.getChatMessages().get(0), message);

        for (int i=0; i< chat.getMaxNumberOfMessages(); i++) {
            chat.addMessage(message1);
        }

        chat.addMessage(message2);
        Assertions.assertEquals(chat.getChatMessages().get(chat.getOldestMessage()), message1);
        Assertions.assertEquals(chat.getChatMessages().get(0), message2);
    }

    /**
     * Testing the override method equals to see
     * if the chats get properly confronted
     */
    @Test
    void equals() {
        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        HashMap<String, Chat> privateChat1 = new HashMap<>();
        HashMap<String, Chat> privateChat2 = new HashMap<>();

        ChatMessage message1 = new ChatMessage("r2-d2", "test1");
        ChatMessage message2 = new ChatMessage("obi-wan", "test2");

        chat1.addMessage(message1);
        chat1.addMessage(message2);
        privateChat1.put("anakin", chat1);
        chat2.addMessage(message1);
        privateChat2.put("anakin", chat2);

        assertFalse(chat1.equals(chat2));
        assertFalse(privateChat1.equals(privateChat2));

        chat2.addMessage(message2);
        privateChat2.put("asoka", chat2);

        assertTrue(chat1.equals(chat2));
        assertTrue(privateChat1.get("anakin").equals(privateChat2.get("asoka")));

    }
}
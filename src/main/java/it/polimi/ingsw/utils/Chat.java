package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.Messages.ChatMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chat implements Serializable {
    private ArrayList<ChatMessage> chatMessages;        // List of previous messages from latest (index = 0) to oldest (index = oldestMessage)
    private boolean chatIsEnable;
    private int oldestMessage;
    private final int maxNumberOfMessages;
    private int unReadMessages;

    public Chat () {
        chatMessages = new ArrayList<>();
        maxNumberOfMessages = 20;
        unReadMessages = 0;
        chatIsEnable = false;
    }

    public Chat(int maxNumberOfMessages) {
        chatMessages = new ArrayList<>();
        this.maxNumberOfMessages = maxNumberOfMessages;
        unReadMessages = 0;
        chatIsEnable = false;
    }

    public synchronized void addMessage(ChatMessage message) {
        if (chatMessages.isEmpty()) {
            chatMessages.add(0, message);
            oldestMessage = 0;
            return;
        }

        if (oldestMessage == (maxNumberOfMessages -1)) chatRefresh();

        chatMessages.add(0, message);
        oldestMessage++;
    }

    public synchronized void addMessage(String username, String message) {
        if (chatMessages.isEmpty()) {
            chatMessages.add(0, new ChatMessage(username, message));
            oldestMessage = 0;
            return;
        }

        if (oldestMessage == (maxNumberOfMessages -1)) chatRefresh();

        chatMessages.add(0, new ChatMessage(username, message));
        oldestMessage++;
    }

    public synchronized void addMessage(String forwardingPlayer, String message, String receivingPlayer) {
        if (chatMessages.isEmpty()) {
            chatMessages.add(0, new ChatMessage(forwardingPlayer, message, receivingPlayer));
            oldestMessage = 0;
            return;
        }

        if (oldestMessage == (maxNumberOfMessages -1)) chatRefresh();

        chatMessages.add(0, new ChatMessage(forwardingPlayer, message, receivingPlayer));
        oldestMessage++;
    }

    private void chatRefresh () {
        chatMessages.remove(oldestMessage);
        oldestMessage--;
    }

    public List<ChatMessage> getChatMessages () {return chatMessages;}

    public int getOldestMessage() {
        return oldestMessage;
    }

    public synchronized void updateUnReadMessages () {unReadMessages++;}

    public synchronized void resetUnReadMessages () {unReadMessages = 0;}

    public int getUnReadMessages() {return unReadMessages;}

    public int getMaxNumberOfMessages() {return maxNumberOfMessages;}

    public boolean ChatIsEnable() {
        return chatIsEnable;
    }

    public void setChatIsEnable(boolean chatIsEnable) {
        this.chatIsEnable = chatIsEnable;
    }

    /**
     * Method that does Override of equals in order
     * to properly confront 2 Chat-like structures
     *
     * @param o     Chat-type Object to analyze
     * @return      True or false depending on the result of the confrontation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return oldestMessage == chat.oldestMessage &&
                maxNumberOfMessages == chat.maxNumberOfMessages &&
                unReadMessages == chat.unReadMessages &&
                chatIsEnable == chat.chatIsEnable &&
                Objects.equals(chatMessages, chat.chatMessages);
    }

    /**
     * Method defined in the class Object that allows to extend the
     * "equals" confrontation to hash-type structures
     *
     * @return      Returns an entire value that represents the hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(chatMessages, chatIsEnable, oldestMessage, maxNumberOfMessages, unReadMessages);
    }
}

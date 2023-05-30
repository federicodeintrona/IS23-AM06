package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.Messages.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class Chat {
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

    public boolean ChatIsEnable() {
        return chatIsEnable;
    }

    public void setChatIsEnable(boolean chatIsEnable) {
        this.chatIsEnable = chatIsEnable;
    }
}

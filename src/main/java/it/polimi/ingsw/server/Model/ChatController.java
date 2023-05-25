package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.utils.Messages.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatController {
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();        // List of previous messages from latest (index = 0) to oldest (index = oldestMessage)
    private int oldestMessage;
    private final int maxNumberOfMessages = 20;
    private int unReadMessages = 0;

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
}

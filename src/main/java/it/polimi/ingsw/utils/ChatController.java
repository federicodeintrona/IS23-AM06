package it.polimi.ingsw.utils;

import java.util.HashMap;

public class ChatController {
    private Chat publicChat;
    private HashMap<String, Chat> privateChats;

    public ChatController() {
        publicChat = new Chat();
        privateChats = new HashMap<>();
    }

    public ChatController (boolean forServer){
        privateChats = new HashMap<>();
    }

    public Chat getPublicChat() {
        return publicChat;
    }

    public Chat getPrivateChat (String username) {
        return privateChats.get(username);
    }

    public HashMap<String, Chat> getPrivateChats() {
        return privateChats;
    }

    public void setPublicChat (Chat publicChat) { this.publicChat = publicChat; }

    public void setPrivateChat (HashMap<String, Chat> privateChats) { this.privateChats = privateChats; }

}
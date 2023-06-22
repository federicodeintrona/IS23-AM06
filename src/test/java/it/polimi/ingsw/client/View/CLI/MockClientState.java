package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.ChatController;

public class MockClientState {
    private ChatController chatController = new ChatController();

    public ChatController getChatController() {
        return chatController;
    }
}

package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.Messages.ChatMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatHandlerTest {
    CLIMain cli = new CLIMain();

    @Test
    void settingForPublicChat() {
    }

    @Test
    void settingForPrivateChat() {
    }

    @Test
    void newPublicMessage() {
        ChatMessage message = new ChatMessage("pippo", "test");

    }

    @Test
    void newPrivateMessage() {
    }
}
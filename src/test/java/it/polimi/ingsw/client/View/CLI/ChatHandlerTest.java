package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.Messages.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

class ChatHandlerTest {

    @Mo
    private MockCliMain cli;

    @BeforeAll
    public static void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void chat1() {

    }

    @Test
    void settingForPublicChat() {
        cli.getChatHandler().settingForPublicChat();

        Assertions.assertTrue(cli.getCliPrint().isWorking());
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
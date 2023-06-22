package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.utils.CliMainBackbone;
import it.polimi.ingsw.utils.Messages.Message;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class MockCliMain implements CliMainBackbone {
    private static MockClientState clientState;
    private static MockCliPrint cliPrint;
    private static ChatHandler chatHandler;


    @Override
    public Object getLock() {
        return null;
    }

    @Override
    public ClientState getClientState() {
        return null;
    }

    @Override
    public CLIPrint getCliPrint() {
        return null;
    }

    @Override
    public ReadShell getReadShell() {
        return null;
    }

    @Override
    public ChatHandler getChatHandler() {
        return null;
    }

    @Override
    public Networker getNet() {
        return null;
    }

    @Override
    public boolean isOpenChat() {
        return false;
    }

    @Override
    public void setOpenChat(boolean openChat) {

    }

    @Override
    public void runUI() throws RemoteException {
        clientState = new MockClientState();
        cliPrint = new MockCliPrint();

        chatHandler = new ChatHandler(clientState.getChatController(), this, cliPrint);

    }

    @Override
    public void close() {

    }

    @Override
    public void receivedMessage(Message message) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}

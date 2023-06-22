package it.polimi.ingsw.utils;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.NetworkerRmi;
import it.polimi.ingsw.client.NetworkerTcp;
import it.polimi.ingsw.client.View.CLI.CLIPrint;
import it.polimi.ingsw.client.View.CLI.ChatHandler;
import it.polimi.ingsw.client.View.CLI.ReadShell;
import it.polimi.ingsw.utils.Messages.ChatMessage;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.Scanner;

public interface CliMainBackbone {
    Object getLock();
    ClientState getClientState();
    CLIPrint getCliPrint();
    ReadShell getReadShell();
    ChatHandler getChatHandler();
    Networker getNet();
    boolean isOpenChat();
    void setOpenChat(boolean openChat);
    void runUI() throws RemoteException;
    void close();
    void receivedMessage(Message message);
    void propertyChange(PropertyChangeEvent evt);
}

package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.Messages.Message;

import java.util.ArrayList;

public interface ReadShellBackbone {
    void run();
    void askUsername();
    void askNumberOfPlayerMessage();
    void sendMessage(Message message);
}

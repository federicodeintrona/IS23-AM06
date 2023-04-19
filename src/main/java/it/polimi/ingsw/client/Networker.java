package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.Messages.Message;

import java.awt.*;
import java.util.ArrayList;

public interface Networker {
    public void initializeConnection ();
    public Message firstConnection (Message username);
    public Message numberOfPlayersSelection(Message numberOfPlayers);
    public Message removeTilesFromBoard(Message tiles);
    public Message switchTilesOrder(Message ints);
    public Message addTilesToBookshelf (Message column);
}

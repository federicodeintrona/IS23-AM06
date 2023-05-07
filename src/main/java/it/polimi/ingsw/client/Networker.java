package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Messages.Message;


public interface Networker {
    void initializeConnection ();
    void firstConnection (Message username);
    void numberOfPlayersSelection(Message numberOfPlayers);
    void removeTilesFromBoard(Message tiles);
    void switchTilesOrder(Message ints);
    void addTilesToBookshelf (Message column);
}

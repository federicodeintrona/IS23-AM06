package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.utils.Messages.*;

public interface Networker {
    void initializeConnection ();
    void firstConnection (Message username);
    void numberOfPlayersSelection(Message numberOfPlayers);
    void removeTilesFromBoard(Message tiles);
    void switchTilesOrder(Message ints);
    void addTilesToBookshelf (Message column);
    void setCli(CLIMain cli);
    void chat (Message message);
}

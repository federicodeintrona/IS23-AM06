package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.*;

public interface Networker {
    boolean initializeConnection ();
    void firstConnection (Message username);
    void numberOfPlayersSelection(Message numberOfPlayers);
    void removeTilesFromBoard(Message tiles);
    void switchTilesOrder(Message ints);
    void addTilesToBookshelf (Message column);
    void setView(View view);
    void chat (Message message);
    void setServerIP(String serverIP);
}

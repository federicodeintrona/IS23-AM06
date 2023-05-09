package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.server.Messages.Message;


public interface Networker {
    void initializeConnection ();
    void setUserInterface(CLIMain cliMain);
    void firstConnection (Message username);
    void numberOfPlayersSelection(Message numberOfPlayers);
    void removeTilesFromBoard(Message tiles);
    void switchTilesOrder(Message ints);
    void addTilesToBookshelf (Message column);
}

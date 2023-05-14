package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.utils.Messages.*;

public interface Networker {
    public void initializeConnection ();
    public void firstConnection (Message username);
    public void numberOfPlayersSelection(Message numberOfPlayers);
    public void removeTilesFromBoard(Message tiles);
    public void switchTilesOrder(Message ints);
    public void addTilesToBookshelf (Message column);
    public void setCli(CLIMain cli);
}

package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.Messages.Message;

import java.awt.*;
import java.util.ArrayList;

public interface Networker {
    public void initializeConnection ();
    public void firstConnection ();
    public void numberOfPlayersSelection(int numberOfPlayers);
    public void removeTilesFromBoard(ArrayList<Point> tiles);
    public void switchTilesOrder(ArrayList<Integer> ints);
    public void addTilesToBookshelf (int column);
}

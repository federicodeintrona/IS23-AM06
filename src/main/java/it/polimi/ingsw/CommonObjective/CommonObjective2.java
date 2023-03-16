package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

public class CommonObjective2 extends CommonObjective{

    static{
        subclasses.add(CommonObjective2.class);
    }

    public boolean checkCondition(Player player){

        // using tile as a buffer for the first angle of the bookshelf
        Tiles tile = player.bookshelf.tiles.getTile(0, 0);

        //checking if the four angles of the bookshelf are equal
        if (tile.equals(player.bookshelf.tiles.getTile(0, 2))) {
            if (tile.equals(player.bookshelf.tiles.getTile(2, 2))) {
                if (tile.equals(player.bookshelf.tiles.getTile(2, 2))) {
                    return true;
                }
            }
        }

        // else returns false
        return false;
    }

}

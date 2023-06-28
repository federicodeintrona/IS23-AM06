package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

/**
 * Class that regulates the functioning of CommonObjective 2.
 */
public class CommonObjective2 extends CommonObjective {

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective2() {
        this.setNum(2);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if the 4 tiles at the bookshelf's corners have the same color.
     *
     * @param player     player whose bookshelf gets analyze.
     * @return   <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player) {

        // using tile as a buffer for the first angle of the bookshelf
        Tiles tile = player.getBookshelf().getTiles().getTile(0, 0);

        //checking if the four angles of the bookshelf are equal
        if (!(tile.equals(Tiles.EMPTY)) && (tile.equals(player.getBookshelf().getTiles().getTile(0, 4)))) {
            if (tile.equals(player.getBookshelf().getTiles().getTile(5, 0))) {
                return tile.equals(player.getBookshelf().getTiles().getTile(5, 4));
            }
        }

        // else returns false
        return false;
    }
}
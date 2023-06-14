package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

public class CommonObjective2 extends CommonObjective {


    public CommonObjective2() {
        this.setNum(2);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if the 4 tiles at the bookshelf's corners have the same color
     *
     * @param player player whose bookshelf gets analyze
     * @return true if the bookshelf meets the criteria, else false
     */
    @Override
    public boolean checkCondition(Player player) {

        // using tile as a buffer for the first angle of the bookshelf
        Tiles tile = player.getBookshelf().getTiles().getTile(0, 0);

        //checking if the four angles of the bookshelf are equal
        if (!(tile.equals(Tiles.EMPTY)) && (tile.equals(player.getBookshelf().getTiles().getTile(0, 4)))) {
            if (tile.equals(player.getBookshelf().getTiles().getTile(5, 0))) {
                if (tile.equals(player.getBookshelf().getTiles().getTile(5, 4))) {
                    return true;
                }
            }
        }

        // else returns false
        return false;
    }

    /**
     * Method to calculate the commonObjective points
     *
     * @param player    player whose bookshelf gets analyze
     * @param numOfPlayers      number of player to assign points
     */
}
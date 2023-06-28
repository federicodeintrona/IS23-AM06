package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that regulates the functioning of CommonObjective 6.
 */
public class CommonObjective6 extends CommonObjective{

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective6() {
        this.setNum(6);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 8 tiles of the color, in any position,
     * inside it and, in case, returns true.
     *
     * @param player    player whose bookshelf gets analyze.
     * @return      <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player){
        Map<Tiles, Integer> countMap = new HashMap<>();

        // scrolling all bookshelf's elements while counting theme
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){

                // checking if the box is empty or not
                if (!player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) {
                    countMap.put(player.getBookshelf().getTiles().getTile(i, j), countMap.getOrDefault(player.getBookshelf().getTiles().getTile(i, j), 0) + 1);
                }
            }
        }

        // searching for the color that occurs the most
        int maxCount = 0;

        for (Map.Entry<Tiles, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
            }
        }

        // returning true only if it appears 8 or more times
        return maxCount > 7;
    }
}

package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that regulates the functioning of CommonObjective 9.
 */
public class CommonObjective9 extends CommonObjective{

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective9() {
        this.setNum(9);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 2 filled columns, each one made by 6 tiles of
     * 6 different colors, inside it and, in case, returns true.
     *
     * @param player    player whose bookshelf gets analyze.
     * @return      <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player) {
        int count = 0;
        int numOfColor;
        int i = 0;
        Set<Tiles> buffer = new HashSet<>();

        while ((count < 2) && (i < 5)){

            // controlling that column i is full first
            if (player.getBookshelf().getTiles().columnIsFull(i)) {
                buffer.add(player.getBookshelf().getTiles().getTile(0, i));
                numOfColor = 1;

                // analyzing each element of column i
                for (int j = 1; j < 6; j++) {

                    // every new color encountered gets save in a set
                    if (!buffer.contains(player.getBookshelf().getTiles().getTile(j, i))) {
                        buffer.add(player.getBookshelf().getTiles().getTile(j, i));

                        // buffer's length
                        numOfColor++;
                    }
                }

                // counting the number of columns that have 6 different colors
                if (numOfColor == 6) count++;
                buffer.clear();
            }
            i++;
        }

        // returning true if there are at least 2 columns that meet the requirements
        return count == 2;
    }
}

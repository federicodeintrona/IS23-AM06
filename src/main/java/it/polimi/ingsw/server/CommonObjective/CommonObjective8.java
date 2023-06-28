package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that regulates the functioning of CommonObjective 8.
 */
public class CommonObjective8 extends CommonObjective{

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective8() {
        this.setNum(8);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 4 filled rows, each one made by tiles of
     * maximum 3 different colors, inside it and, in case, returns true.
     *
     * @param player    player whose bookshelf gets analyze.
     * @return      <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player){
        int count = 0;
        int numOfColor;
        int i = 0;
        Set<Tiles> buffer = new HashSet<>();

        while ((count < 4) && (i < 6)){

            // controlling that row i is full first
            if (player.getBookshelf().getTiles().rowIsFull(i)) {
                buffer.add(player.getBookshelf().getTiles().getTile(i, 0));
                numOfColor = 1;

                // analyzing each element of row i
                for (int j = 1; j < 5; j++) {
                    if (!buffer.contains(player.getBookshelf().getTiles().getTile(i, j))) {
                        buffer.add(player.getBookshelf().getTiles().getTile(i, j));
                        numOfColor++;
                    }
                }
                if (numOfColor < 4) count++;
                buffer.clear();
            }
            i++;
        }
        return count == 4;
    }
}

package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

import java.util.HashSet;
import java.util.Set;

public class CommonObjective10 extends CommonObjective{

    public CommonObjective10() {
        this.setNum(10);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 2 filled rows, each one made by 5 tiles of
     * 5 different colors, inside it and, in case, returns true
     *
     * @param player    player whose bookshelf gets analyze
     * @return      true if the bookshelf meets the criteria, else false
     */
    @Override
    public boolean checkCondition(Player player){
        int count = 0;
        int numOfColor;
        int i = 0;
        Set<Tiles> buffer = new HashSet<>();

        while ((count < 2) && (i < 6)){

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
                if (numOfColor == 5) count++;
                buffer.clear();
            }
            i++;
        }

        // there has to be at least two row that respect the criteria
        if (count == 2) return true;

        return false;
    }

    /**
     * Method to calculate the commonObjective points
     *
     * @param player    player whose bookshelf gets analyze
     * @param numOfPlayers      number of player to assign points
     */
}

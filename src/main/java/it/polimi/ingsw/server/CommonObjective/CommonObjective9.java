package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;

import java.util.HashSet;
import java.util.Set;

public class CommonObjective9 extends CommonObjective{

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 2 filled columns, each one made by 6 tiles of
     * 6 different colors, inside it and, in case, returns true
     *
     * @param player    player whose bookshelf gets analyze
     * @return      true if the bookshelf meets the criteria, else false
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
        if (count == 2) return true;
        else return false;
    }

    /**
     * Method to calculate the commonObjective points
     *
     * @param player    player whose bookshelf gets analyze
     * @param numOfPlayers      number of player to assign points
     */
    public void commonObjPointsCalculator(Player player, int numOfPlayers){
        if (checkCondition(player) && !playersWhoCompletedComObj.contains(player)) {

            // adding the player to the set of players who already received the points
            playersWhoCompletedComObj.add(player);

            // for a 2 players game the first to complete a commonObj gets 8 points and the second to do so 4
            if (numOfPlayers == 2) {
                player.setCommonObjectivePoint(points);
                points -= 4;
            }

            // in case there are more than 2 players each time a commonObj is completed its points decrease by 2
            else {
                player.setCommonObjectivePoint(points);
                points -= 2;
            }
        }
    }
}

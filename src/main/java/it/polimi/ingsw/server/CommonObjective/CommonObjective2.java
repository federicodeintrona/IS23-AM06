package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;

public class CommonObjective2 extends CommonObjective{

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if the 4 tiles at the bookshelf's corners have the same color
     *
     * @param player    player whose bookshelf gets analyze
     * @return      true if the bookshelf meets the criteria, else false
     */
    @Override
    public boolean checkCondition(Player player){

        // using tile as a buffer for the first angle of the bookshelf
        Tiles tile = player.getBookshelf().getTiles().getTile(0, 0);

        //checking if the four angles of the bookshelf are equal
        if (!(tile.equals(Tiles.EMPTY))  && (tile.equals(player.getBookshelf().getTiles().getTile(0, 4)))) {
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

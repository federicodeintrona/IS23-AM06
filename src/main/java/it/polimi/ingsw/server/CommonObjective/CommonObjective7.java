package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;

public class CommonObjective7 extends CommonObjective{

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 5 tiles of the same color in a diagonal
     * inside it and, in case, returns true
     *
     * @param player    player whose bookshelf gets analyze
     * @return      true if the bookshelf meets the criteria, else false
     */
    @Override
    public boolean checkCondition(Player player){
        int i;
        int j;
        int count;

        // checking first diagonal
        i = 0;
        j = 0;
        count = 0;
        while (i<4 && j<4){

            if ((!player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) && (player.getBookshelf().getTiles().getTile(i, j).equals(player.getBookshelf().getTiles().getTile(i+1, j+1)))) count++;

            i++;
            j++;
        }

        if (count == 4) return true;

        // checking second diagonal
        i = 1;
        j = 0;
        count = 0;
        while (i<5 && j<4){

            if ((!player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) && (player.getBookshelf().getTiles().getTile(i, j).equals(player.getBookshelf().getTiles().getTile(i+1, j+1)))) count++;

            i++;
            j++;
        }

        if (count == 4) return true;

        // checking first inverse-diagonal
        i = 5;
        j = 0;
        count = 0;
        while (i>1 && j<4){

            if ((!player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) && (player.getBookshelf().getTiles().getTile(i, j).equals(player.getBookshelf().getTiles().getTile(i-1, j+1)))) count++;

            i--;
            j++;
        }

        if (count == 4) return true;

        // checking second inverse-diagonal
        i = 4;
        j = 0;
        count = 0;
        while (i>0 && j<4){

            if ((!player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) && (player.getBookshelf().getTiles().getTile(i, j).equals(player.getBookshelf().getTiles().getTile(i-1, j+1)))) count++;

            i--;
            j++;
        }

        if (count == 4) return true;

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

package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.server.Model.Tiles;

public class CommonObjective12 extends CommonObjective{

    public CommonObjective12() {
        this.setNum(12);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 5 columns of growing or decreasing height,
     * each one that differs from the previous one by a box,
     * inside it and, in case, returns true
     *
     * @param player    player whose bookshelf gets analyze
     * @return      true if the bookshelf meets the criteria, else false
     */
    @Override
    public boolean checkCondition(Player player) {
        int i;
        int j;

        // Checks if the bookshelf is empty and in case returns false
        if (player.getBookshelf().isEmpty()) return false;

        // checking first diagonal
        i = 0;
        j = 0;
        while (i<5 && j<5){

            if (player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) return true;

            i++;
            j++;
        }

        // checking second diagonal
        i = 1;
        j = 0;
        while (i<6 && j<5){

            if (player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) return true;

            i++;
            j++;
        }

        // checking first inverse-diagonal
        i = 5;
        j = 0;
        while (i>0 && j<5){

            if (player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) return true;

            i--;
            j++;
        }

        // checking second inverse-diagonal
        i = 4;
        j = 0;
        while (i>=0 && j<5){

            if (player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) return true;

            i--;
            j++;
        }

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

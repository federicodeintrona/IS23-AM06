package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.*;

import java.util.HashSet;
import java.util.Set;

public class CommonObjective10 extends CommonObjective{

    static{
        subclasses.add(CommonObjective10.class);
    }

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

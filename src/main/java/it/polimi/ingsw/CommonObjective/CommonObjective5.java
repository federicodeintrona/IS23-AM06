package it.polimi.ingsw.CommonObjective;
import it.polimi.ingsw.*;

import java.util.HashSet;
import java.util.Set;

public class CommonObjective5 extends CommonObjective{


    public boolean checkCondition(Player player){
        int count = 0;
        int numOfColor;
        int i = 0;
        Set<Tiles> buffer = new HashSet<>();

        while ((count < 3) && (i < 5)){

            // controlling that column i is full first
            if (player.getBookshelf().getTiles().columnIsFull(i)) {
                buffer.add(player.getBookshelf().getTiles().getTile(0, i));
                numOfColor = 1;

                // analyzing each element of column i
                for (int j = 1; j < 6; j++) {
                    if (!buffer.contains(player.getBookshelf().getTiles().getTile(j, i))) {
                        buffer.add(player.getBookshelf().getTiles().getTile(j, i));
                        numOfColor++;
                    }
                }

                if (numOfColor < 4) count++;
                buffer.clear();
            }
            i++;
        }
        if (count == 3) return true;
        else return false;
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

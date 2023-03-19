package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

import java.util.HashSet;
import java.util.Set;

public class CommonObjective5 extends CommonObjective{
    static{
        subclasses.add(CommonObjective5.class);
    }

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
                i++;
            }
        }
        if (count == 3) return true;
        else return false;
    }
}

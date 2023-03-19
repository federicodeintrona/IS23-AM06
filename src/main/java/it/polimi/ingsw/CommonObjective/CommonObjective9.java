package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.*;

import java.util.HashSet;
import java.util.Set;

public class CommonObjective9 extends CommonObjective{
    static{
        subclasses.add(CommonObjective9.class);
    }

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
}

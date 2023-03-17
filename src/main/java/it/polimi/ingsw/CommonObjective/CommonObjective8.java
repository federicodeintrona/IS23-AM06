package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

import java.util.HashSet;
import java.util.Set;

public class CommonObjective8 extends CommonObjective{
    static{
        subclasses.add(CommonObjective8.class);
    }

    public boolean checkCondition(Player player){
        int count = 0;
        int numOfColor;
        int i = 0;
        Set<Tiles> buffer = new HashSet<>();

        while ((count < 4) && (i < 6)){
            buffer.add(player.getBookshelf().getTiles().getTile(i, 0));
            numOfColor = 1;
            for (int j=1; j<5; j++){
                if (!buffer.contains(player.getBookshelf().getTiles().getTile(i, j))){
                    buffer.add(player.getBookshelf().getTiles().getTile(i, j));
                    numOfColor++;
                }
            }
            if (numOfColor < 4) count++;
            buffer.clear();
            i++;
        }
        if (count == 4) return true;
        else return false;
    }
}

package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

import java.util.HashMap;
import java.util.Map;

public class CommonObjective6 extends CommonObjective{
    static{
        subclasses.add(CommonObjective6.class);
    }

    public boolean checkCondition(Player player){
        Map<Tiles, Integer> countMap = new HashMap<>();

        // scrolling all bookshelf's elements while counting theme
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                // checking if the box is empty or not
                if (player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.NOTALLOWED) && player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) {
                    countMap.put(player.getBookshelf().getTiles().getTile(i, j), countMap.getOrDefault(player.getBookshelf().getTiles().getTile(i, j), 0) + 1);
                }
            }
        }

        // searching for the color that occurs the most
        int maxCount = 0;

        for (Map.Entry<Tiles, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
            }
        }

        // returning true only if it appears 8 or more times
        if(maxCount > 7) return true;

        return false;
    }
}

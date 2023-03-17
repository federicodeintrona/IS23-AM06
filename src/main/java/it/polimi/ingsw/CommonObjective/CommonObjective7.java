package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

public class CommonObjective7 extends CommonObjective{
    static{
        subclasses.add(CommonObjective7.class);
    }

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
}

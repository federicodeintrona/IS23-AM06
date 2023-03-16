package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Bookshelf;
import it.polimi.ingsw.Player;

public class CommonObjective2 extends CommonObjective{

    static{
        subclasses.add(CommonObjective2.class);
    }

    boolean checkCondition(Player player){

        //checking if the four angles of the boockshelf are equal
        if (player.bookshelf.tiles.getTile(0, 0) == player.bookshelf.tiles.getTile(0, 4) == player.bookshelf.tiles.getTile(5, 0) == player.bookshelf.tiles.getTile(5, 4)){
            return true;
        }

        // if not returns false
        else return false;
    }

}

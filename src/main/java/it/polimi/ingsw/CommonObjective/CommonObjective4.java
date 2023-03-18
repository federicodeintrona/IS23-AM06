package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;

public class CommonObjective4 extends CommonObjective{

    static{
        subclasses.add(CommonObjective4.class);
    }
    public boolean checkCondition(Player player) {
        return false;
    }
}

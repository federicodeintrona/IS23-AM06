package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;

public class CommonObjective12 extends CommonObjective{
    static{
        subclasses.add(CommonObjective12.class);
    }

    public boolean checkCondition(Player player) {
        return false;
    }
}

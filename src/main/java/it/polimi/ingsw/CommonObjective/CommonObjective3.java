package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Player;

public class CommonObjective3 extends CommonObjective{

    static{
        subclasses.add(CommonObjective3.class);
    }
    public boolean checkCondition(Player player) {
        return false;
    }
}

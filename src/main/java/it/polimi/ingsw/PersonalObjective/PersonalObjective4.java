package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.Player;

public class PersonalObjective4 extends PersonalObjective{

    static{
        subclasses.add(PersonalObjective4.class);
    }

//metodi
    //ritorna il numero di obiettivi completati -->
    // --> posizione-colore carta PersonalObjective coincide con posizione-colore nella Bookshelf
    @Override
    public int checkCondition(Player player) {
        return 1;
    }
}

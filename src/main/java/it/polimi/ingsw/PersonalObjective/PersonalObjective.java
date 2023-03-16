package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.Player;

import java.util.ArrayList;

public abstract class PersonalObjective {

    protected static ArrayList<Class> subclasses = new ArrayList();

//metodi
        //ritorna il numero di obiettivi completati -->
        // --> posizione-colore carta PersonalObjective coincide con posizione-colore nella Bookshelf
        public abstract int checkCondition(Player player);
}

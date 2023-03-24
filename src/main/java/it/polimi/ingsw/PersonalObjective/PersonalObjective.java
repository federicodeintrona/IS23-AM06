package it.polimi.ingsw.PersonalObjective;



import it.polimi.ingsw.CommonObjective.CommonObjective;
import it.polimi.ingsw.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public abstract class PersonalObjective {
//attributi
    private static int points;
    private static int numOfComplitedObjective;


    protected static ArrayList<Class> subclasses = new ArrayList();


    static{
        try {
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective1");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective2");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective3");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective4");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective5");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective6");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective7");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective8");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective9");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective10");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective11");
            Class.forName("it.polimi.ingsw.PersonalObjective.PersonalObjective12");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public static ArrayList<PersonalObjective> randomSubclass(int num)  {



        ArrayList<Class> temp = new ArrayList<>();
        temp.addAll(subclasses);
        Random rand = new Random();


        ArrayList<PersonalObjective> result = new ArrayList<>();


        for(int i = 0; i<num; i++ ){
            int index = rand.nextInt(temp.size());
            Constructor c;
            try {
                c = temp.get(index).getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            temp.remove(index);
            try {
                result.add((PersonalObjective) c.newInstance() );
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


        return result;

    }


//metodi
    //ritorna il numero di obiettivi completati -->
    // --> posizione-colore carta PersonalObjective coincide con posizione-colore nella Bookshelf
    public abstract int checkCondition(Player player);

    //ritorna il punteggio del player
    public abstract int personalObjectivePoint(Player player);


}

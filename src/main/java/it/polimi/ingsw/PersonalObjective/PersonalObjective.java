package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.CommonObjective.CommonObjective;
import it.polimi.ingsw.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public abstract class PersonalObjective {
//attributi
    private int points;
    private int numOfComplitedObjective;

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

    public static ArrayList<PersonalObjective> randomSubclass(int num) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {







        ArrayList temp = (ArrayList) subclasses.clone();
        Random rand = new Random();

        System.out.println(subclasses.size());
        System.out.println(temp.size());


        ArrayList<PersonalObjective> result = new ArrayList<PersonalObjective>();

        /*for (int i = 0; i< temp.size();i++){
            System.out.println(temp.get(i));
        }*/

        for(int i = 0; i<num; i++ ){
            int index = rand.nextInt(subclasses.size());
            Constructor c= subclasses.get(index).getDeclaredConstructor();
            temp.remove(index);
            result.add((PersonalObjective) c.newInstance() );
        }


        return result;

    }


//metodi
    public abstract int checkCondition(Player player);

}

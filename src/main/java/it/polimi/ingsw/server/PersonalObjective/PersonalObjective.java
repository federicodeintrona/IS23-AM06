package it.polimi.ingsw.server.PersonalObjective;



import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import it.polimi.ingsw.server.Bookshelf;
import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Player;

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
        for (PojoClass pojoClass : PojoClassFactory.enumerateClassesByExtendingType(
                "it.polimi.ingsw.server.PersonalObjective", PersonalObjective.class,
                null)) {
            subclasses.add(pojoClass.getClazz());
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



    /**
     * return the number of position that match with the PersonalObjective's card
     *
     * @param player    the player whose correct position number you want to calculate
     * @return int  number of position that match with the PersonalObjective's card
     */
    public abstract int checkCondition(Player player);

    /**
     * return the number of position that match with the PersonalObjective's card
     *
     * @param bookshelf the bookshelf whose correct position number you want to calculate
     * @return int  number of position that match with the PersonalObjective's card
     */
    public abstract int checkCondition(Bookshelf bookshelf);

    /**
     * return the score of PersonalObjective's card
     *
     * @param player    the player whose score you want to calculate
     * @return int  score of PersonalObjective's card
     */
    public abstract int personalObjectivePoint(Player player);

    /**
     * return the score of PersonalObjective's card
     *
     * @param bookshelf the bookshelf whose score you want to calculate
     * @return int  score of PersonalObjective's card
     */
    public abstract int personalObjectivePoint(Bookshelf bookshelf);

}

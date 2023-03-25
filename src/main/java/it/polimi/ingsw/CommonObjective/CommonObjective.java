package it.polimi.ingsw.CommonObjective;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import it.polimi.ingsw.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class CommonObjective {
    int points = 8;
    Set<Player> playersWhoCompletedComObj = new HashSet<>();

    protected static ArrayList<Class> subclasses = new ArrayList();

    static{
            for (PojoClass pojoClass : PojoClassFactory.enumerateClassesByExtendingType("it.polimi.ingsw.CommonObjective", CommonObjective.class, null)) {
                subclasses.add(pojoClass.getClazz());
            }
    }
    public static ArrayList<CommonObjective> randomSubclass(int num) {


        ArrayList<Class> temp = new ArrayList<>();
        temp.addAll(subclasses);
        Random rand = new Random();


        ArrayList<CommonObjective> result = new ArrayList<CommonObjective>();


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
                result.add((CommonObjective) c.newInstance() );
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


        return result;

    }

    public abstract boolean checkCondition(Player player);

    public abstract void commonObjPointsCalculator(Player player, int numOfPlayers);

    public int getPoints() {
        return points;
    }

    public Set<Player> getPlayersWhoCompletedComObj() {
        return playersWhoCompletedComObj;
    }

    public static ArrayList<Class> getSubclasses() {
        return subclasses;
    }
}

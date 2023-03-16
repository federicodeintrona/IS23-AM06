package it.polimi.ingsw.CommonObjective;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public abstract class CommonObjective {


    protected static ArrayList<Class> subclasses = new ArrayList();


    static{
        try {
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective1");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective2");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective3");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective4");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective5");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective6");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective7");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective8");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective9");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective10");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective11");
            Class.forName("it.polimi.ingsw.CommonObjective.CommonObjective12");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static ArrayList<CommonObjective> randomSubclass(int num) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {


        ArrayList temp = (ArrayList) subclasses.clone();
        Random rand = new Random();

        System.out.println(subclasses.size());
        System.out.println(temp.size());


        ArrayList<CommonObjective> result = new ArrayList<CommonObjective>();

        /*for (int i = 0; i< temp.size();i++){
            System.out.println(temp.get(i));
        }*/

        for(int i = 0; i<num; i++ ){
            int index = rand.nextInt(subclasses.size());
            Constructor c= subclasses.get(index).getDeclaredConstructor();
            temp.remove(index);
            result.add((CommonObjective) c.newInstance() );
        }


        return result;

    }


    public CommonObjective() {


    }
}

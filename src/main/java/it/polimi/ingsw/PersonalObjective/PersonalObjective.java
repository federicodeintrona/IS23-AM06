package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.CommonObjective.CommonObjective;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public abstract class PersonalObjective {

    protected static ArrayList<Class> subclasses = new ArrayList();




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







}





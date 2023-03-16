package it.polimi.ingsw;

import it.polimi.ingsw.CommonObjective.CommonObjective;
import it.polimi.ingsw.CommonObjective.CommonObjective1;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{


    public static void main( String[] args )  {


        ArrayList<CommonObjective> c1 = new ArrayList<>();

        //Testing matrice
        Matrix mat = new Matrix(3,3);
        mat.setTile(Tiles.BLUE,2,2);
        mat.setTile(Tiles.BLUE,1,1);
        mat.setTile(Tiles.BLUE,0,0);
        mat.setTile(Tiles.GREEN,1,2);
        mat.setTile(Tiles.GREEN,0,2);
        mat.print();
        System.out.println(mat.getTile(1,1));
        System.out.println(mat.getTile(0,1));

        //Testing Common Objective Random

        try {
             c1.addAll( CommonObjective.randomSubclass(3));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (CommonObjective commonObjective : c1) System.out.println(commonObjective);

    }
}

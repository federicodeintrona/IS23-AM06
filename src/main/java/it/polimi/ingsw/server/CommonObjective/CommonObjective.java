package it.polimi.ingsw.server.CommonObjective;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import it.polimi.ingsw.server.Model.Player;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public abstract class CommonObjective {
    public int points = 8;
    public Set<Player> playersWhoCompletedComObj = new HashSet<>();

    private int num;


    public static ArrayList<CommonObjective> randomSubclass(int num) {

        ArrayList<Class> subclasses = new ArrayList();


        for (PojoClass pojoClass : PojoClassFactory.enumerateClassesByExtendingType(
                "it.polimi.ingsw.server.CommonObjective", CommonObjective.class,
                null)) {
            subclasses.add(pojoClass.getClazz());
        }

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

    public  boolean commonObjPointsCalculator(Player player, int numOfPlayers){

        if (checkCondition(player) && !playersWhoCompletedComObj.contains(player)) {

            // adding the player to the set of players who already received the points
            playersWhoCompletedComObj.add(player);

            // for a 2 players game the first to complete a commonObj gets 8 points and the second to do so 4
            if (numOfPlayers == 2) {
                player.setCommonObjectivePoint(points);
                points -= 4;
            }

            // in case there are more than 2 players each time a commonObj is completed its points decrease by 2
            else {
                player.setCommonObjectivePoint(points);
                points -= 2;
            }
            return true;
        } return false;

    };

    public int getPoints() {
        return points;
    }

    public Set<Player> getPlayersWhoCompletedComObj() {
        return playersWhoCompletedComObj;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

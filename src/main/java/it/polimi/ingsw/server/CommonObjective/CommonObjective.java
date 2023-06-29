package it.polimi.ingsw.server.CommonObjective;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import it.polimi.ingsw.server.Model.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Abstract class.
 */
public abstract class CommonObjective {
    private int points = 8;
    private final HashMap<Player, Integer> playersWhoCompletedComObj = new HashMap<>();
    private int num;

    /**
     * Default constructor.
     */
    public CommonObjective() {
    }

    /**
     * Method that selects randomly a number of CommonObjectives equals to the
     * int num and instantiate only the selected ones among the 12 totals.
     *
     * @param num      Number of CommonObjective to instantiate.
     * @return      ArrayList containing the instantiated CommonObjective.
     */
    public static ArrayList<CommonObjective> randomSubclass(int num) {

        ArrayList<Class> subclasses = new ArrayList();


        for (PojoClass pojoClass : PojoClassFactory.enumerateClassesByExtendingType(
                "it.polimi.ingsw.server.CommonObjective", CommonObjective.class,
                null)) {
            subclasses.add(pojoClass.getClazz());
        }

        ArrayList<Class> temp = new ArrayList<>(subclasses);
        Random rand = new Random();


        ArrayList<CommonObjective> result = new ArrayList<>();


        for(int i = 0; i<num; i++ ){
            int index = rand.nextInt(temp.size());
            Constructor c = null;
            try {
                c = temp.get(index).getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                System.out.println("No non è vero sto metodo esiste");
            }
            temp.remove(index);
            try {
                assert c != null;
                result.add((CommonObjective) c.newInstance() );
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                System.out.println("No non è vero sto metodo esiste");
            }
        }


        return result;

    }

    /**
     * Method to be implemented in each subclass that check if a
     * player's bookshelf meets the criteria of the relative commonObjective.
     *
     * @param player    Player whose bookshelf gets analyze.
     * @return      Boolean depending on the result of the analysis.
     */
    public abstract boolean checkCondition(Player player);

    /**
     * Method to calculate the commonObjective points.
     *
     * @param player    player whose bookshelf gets analyze.
     * @param numOfPlayers      number of player to assign points.
     * @return <i>true</i> if the points are to add, <i>false</i> in other cases.
     */
    public  boolean commonObjPointsCalculator(Player player, int numOfPlayers){

        if (checkCondition(player) && !playersWhoCompletedComObj.containsKey(player)) {

            // adding the player to the set of players who already received the points
            playersWhoCompletedComObj.put(player, points);

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

    }

    /**
     * <Strong>Getter</Strong> -> Gets the points to assign.
     *
     * @return      Points to assign.
     */
    public int getPoints() {
        return points;
    }


    /**
     * <Strong>Getter</Strong> -> Gets a set of players who completed the CommonObjective.
     *
     * @return  <i>HashSet</i> containing the name players who completed the CommonObjective.
     */

    public HashMap<String, Integer> getPlayersNameCommonObj(){
        HashMap<String,Integer> map = new HashMap<>();
        for(Player p : playersWhoCompletedComObj.keySet()){
            map.put(p.getUsername(),playersWhoCompletedComObj.get(p));
        }
        return map;
    }

    /**
     * <Strong>Getter</Strong> -> Gets the enumeration of the CommonObjective.
     *
     * @return      The int num.
     */
    public int getNum() {
        return num;
    }

    /**
     * <Strong>Setter</Strong> -> Sets the enumeration for a CommonObjective.
     *
     * @param num   The int num.
     */
    public void setNum(int num) {
        this.num = num;
    }
}

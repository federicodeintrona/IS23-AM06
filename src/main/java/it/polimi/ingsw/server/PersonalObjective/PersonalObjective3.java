package it.polimi.ingsw.server.PersonalObjective;

import it.polimi.ingsw.server.Bookshelf;
import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;

import java.awt.*;
import java.util.HashMap;

public class PersonalObjective3 extends PersonalObjective{

    private static final HashMap<Point, Tiles> card=new HashMap<>();

    static{
        card.put(new Point(2,2), Tiles.PINK);
        card.put(new Point(1,0), Tiles.BLUE);
        card.put(new Point(3,1), Tiles.GREEN);
        card.put(new Point(5,0), Tiles.WHITE);
        card.put(new Point(1,3), Tiles.YELLOW);
        card.put(new Point(3,4), Tiles.LIGHT_BLUE);
    }



    /**
     * return the number of position that match with the PersonalObjective's card
     *
     * @param player    the player whose correct position number you want to calculate
     * @return int  number of position that match with the PersonalObjective's card
     */
    @Override
    public int checkCondition(Player player) {
        int result=0;
        for (Point key: card.keySet()){
            //player-->bookshelf-->matrice-->tile(x, y) == card.get(key)
            // = Tiles                                  == Tiles
            if (player.getBookshelf().getTiles().getTile(key).equals(card.get(key))){
                result++;
            }
        }
        return result;
    }

    /**
     * return the number of position that match with the PersonalObjective's card
     *
     * @param bookshelf the bookshelf whose correct position number you want to calculate
     * @return int  number of position that match with the PersonalObjective's card
     */
    @Override
    public int checkCondition(Bookshelf bookshelf){
        int result=0;
        for (Point key: card.keySet()){
            if (bookshelf.getTiles().getTile(key).equals(card.get(key))){
                result++;
            }
        }
        return result;
    }

    /**
     * return the score of PersonalObjective's card
     *
     * @param player    the player whose score you want to calculate
     * @return int  score of PersonalObjective's card
     */
    @Override
    public int personalObjectivePoint(Player player){
        return switch (checkCondition(player)) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 9;
            case 6 -> 12;
            default -> 0;
        };
    }

    /**
     * return the score of PersonalObjective's card
     *
     * @param bookshelf the bookshelf whose score you want to calculate
     * @return int  score of PersonalObjective's card
     */
    @Override
    public int personalObjectivePoint(Bookshelf bookshelf){
        return switch (checkCondition(bookshelf)) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 9;
            case 6 -> 12;
            default -> 0;
        };
    }
}


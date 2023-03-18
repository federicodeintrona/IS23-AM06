package it.polimi.ingsw.PersonalObjective;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

import java.awt.*;
import java.util.HashMap;

public class PersonalObjective2 extends PersonalObjective{

    private static final HashMap<Point, Tiles> card=new HashMap<>();


    static{
        subclasses.add(PersonalObjective2.class);
        //creazione PersonalObjective
        Point p=new Point();
        p.x=1;
        p.y=1;
        card.put(p, Tiles.PINK);
        p.x=5;
        p.y=4;
        card.put(p, Tiles.BLUE);
        p.x=2;
        p.y=0;
        card.put(p, Tiles.GREEN);
        p.x=3;
        p.y=4;
        card.put(p, Tiles.WHITE);
        p.x=2;
        p.y=2;
        card.put(p, Tiles.YELLOW);
        p.x=4;
        p.y=3;
        card.put(p, Tiles.LIGHT_BLUE);
    }

//-------------------------------------------------------------------------------------------------------\\

//metodi
    //ritorna il numero di obiettivi completati -->
    // --> posizione-colore carta PersonalObjective coincide con posizione-colore nella Bookshelf
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
}

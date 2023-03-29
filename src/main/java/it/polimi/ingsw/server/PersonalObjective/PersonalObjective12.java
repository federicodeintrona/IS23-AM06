package it.polimi.ingsw.server.PersonalObjective;

import it.polimi.ingsw.server.Bookshelf;
import it.polimi.ingsw.server.Player;
import it.polimi.ingsw.server.Tiles;

import java.awt.*;
import java.util.HashMap;

public class PersonalObjective12 extends PersonalObjective{

    private static final HashMap<Point, Tiles> card=new HashMap<>();

    static{
        //creazione PersonalObjective
        card.put(new Point(1,1), Tiles.PINK);
        card.put(new Point(2,2), Tiles.BLUE);
        card.put(new Point(5,0), Tiles.GREEN);
        card.put(new Point(0,2), Tiles.WHITE);
        card.put(new Point(4,4), Tiles.YELLOW);
        card.put(new Point(3,3), Tiles.LIGHT_BLUE);
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
    //ritorna il numero di obiettivi completati -->
    // --> posizione-colore carta PersonalObjective coincide con posizione-colore nella Bookshelf
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

    //ritorna il punteggio del player
    @Override
    public int personalObjectivePoint(Player player){
        int condition=checkCondition(player);
        switch (condition){
            case 1:
                return 1; //1 punto
            case 2:
                return 2; //2 punti
            case 3:
                return 4; //4 punti
            case 4:
                return 6; //6 punti
            case 5:
                return 9; //9 punti
            case 6:
                return 12; //12 punti
            default:
                return 0; //0 punti
        }
    }
    //ritorna il punteggio del player
    @Override
    public int personalObjectivePoint(Bookshelf bookshelf){
        int condition=checkCondition(bookshelf);
        switch (condition){
            case 1:
                return 1; //1 punto
            case 2:
                return 2; //2 punti
            case 3:
                return 4; //4 punti
            case 4:
                return 6; //6 punti
            case 5:
                return 9; //9 punti
            case 6:
                return 12; //12 punti
            default:
                return 0; //0 punti
        }
    }
}

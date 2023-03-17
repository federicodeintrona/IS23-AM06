package it.polimi.ingsw;

import java.util.*;

public class Sachet {
//attributi
    private static ArrayList<Tiles> sachet=new ArrayList<Tiles>();

    //creazione sachet vero e proprio
    // --> vero sacchetto contenente le tessere (contiene 132 tessere = 6tipi * 22tessere)
    static{
        //132tiles = 6tipi * 22tiles

    }

//-------------------------------------------------------------------------------------------------------\\

//metodi
    //getter sachet --> ritorna il set di Tiles
    public static ArrayList<Tiles> getSachet() {
        return sachet;
    }



    //1. scegliere una tiles --> posizione randomica
    //2. rimuovere la tiles dal set --> removeTiles
    public Tiles draw(){
        return Tiles.YELLOW;
    }

    //ritorna il numero di tessere rimanenti nel sachet - OK
    public int remainingTiles(){
        return sachet.size();
    }

    //ritorna il numero di tessere rimanenti nel sachet del colore tiles richiesto
    public int remainingTilesPerColor(Tiles tiles){
        return 0;
    }

    //aggiunge tiles nel sachet --> aggiunta ad array list - OK
    public void addTiles(Tiles til){
        sachet.add(til);
    }

    //rimuove tiles dal sachet in posizione i - OK
    public void removeTiles(int i){
        sachet.remove(i);
    }
}

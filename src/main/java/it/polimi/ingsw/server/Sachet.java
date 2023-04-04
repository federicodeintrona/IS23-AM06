package it.polimi.ingsw.server;

import java.util.*;

public class Sachet {
//attributi
    private final ArrayList<Tiles> sachet=new ArrayList<>();

//-------------------------------------------------------------------------------------------------------\\

//metodi
    //costruttore --> creai il sacchetto con 132 tessere iniziali dei 6 colori diversi
    public  Sachet() {
        //132tiles = 6tipi * 22tiles
        for (int i = 0; i < 22; i++) {
            sachet.add(Tiles.GREEN);
        }
        for (int i = 0; i < 22; i++) {
            sachet.add(Tiles.BLUE);
        }
        for (int i = 0; i < 22; i++) {
            sachet.add(Tiles.YELLOW);
        }
        for (int i = 0; i < 22; i++) {
            sachet.add(Tiles.WHITE);
        }
        for (int i = 0; i < 22; i++) {
            sachet.add(Tiles.PINK);
        }
        for (int i = 0; i < 22; i++) {
            sachet.add(Tiles.LIGHT_BLUE);
        }
    }



    //ritorna una tile randomicamente - OK
    //se il sachet è vuoto ritorna EMPTY
    //1. scegliere una tile --> posizione randomica
    //2. rimuovere la tile dal set --> removeTiles
    public Tiles draw(){
        if (sachet.size()==0){
            return Tiles.EMPTY;
        }
        Random random=new Random(); //crea oggetto Random
        Tiles result;
        //scelta numero casuale
        int n=random.nextInt(sachet.size());
        //salvataggio tile in posizione casuale
        result=sachet.get(n);
        //rimozione tile da sachet
        removeTiles(n);
        return result;
   }

    //ritorna il numero di tessere rimanenti nel sachet - OK
    public int remainingTiles(){
        return sachet.size();
    }

    //ritorna il numero di tessere rimanenti nel sachet del colore tiles richiesto - OK
    public int remainingTilesPerColor(Tiles tiles){
        int result=0;
        for (Tiles value : sachet) {
            if (value.equals(tiles)) {
                result++;
            }
        }
        return result;
    }

    //aggiunge tiles nel sachet --> aggiunta ad array list - OK
    public void addTiles(Tiles til){
        //controllare se til si può aggiungere:
        //sachet.size()+1<=132
        if (sachet.size()+1<=132){
            //remainingTilesperColor(til)<=22
            if (remainingTilesPerColor(til)+1<=22){
                sachet.add(til);
            }
        }
    }

    //rimuove tiles dal sachet in posizione i - OK
    public void removeTiles(int i){
        sachet.remove(i);
    }

    //rimuove tile dal sachet di colore tiles - OK
    public void removeTiles(Tiles tiles){
        sachet.remove(tiles);
    }

}

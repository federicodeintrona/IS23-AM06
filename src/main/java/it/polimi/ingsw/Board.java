package it.polimi.ingsw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
//attributi
    private static Matrix gamesBoard=new Matrix(9, 9);
    private int numberOfPlayer;
    private Sachet boardSachet;

    //creazione gamesBoard vera e propria -->
    // --> vera board (celle che non fanno parte tabellone settati a NOTALLOWED e altre a EMPTY)
    // --> board in base al numero di giocatori creata nel costruttore  Board(int numberOfPlayer)
    static {
        //tutta matrice EMPTY
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gamesBoard.setEmpty(i, j);
            }
        }
        //posizioni matrice che NON possono essere inserite tessere
        //riga 0
        for (int i = 0; i <= 2; i++) {
            gamesBoard.setNotAllowed(0, i);
        }
        for (int i = 5; i <= 8; i++) {
            gamesBoard.setNotAllowed(0, i);
        }
        //riga 1
        for (int i = 0; i <= 2; i++) {
            gamesBoard.setNotAllowed(1, i);
        }
        for (int i = 6; i <= 8; i++) {
            gamesBoard.setNotAllowed(0, i);
        }
        //riga 2
        for (int i = 0; i <= 1; i++) {
            gamesBoard.setNotAllowed(2, i);
        }
        for (int i = 7; i <= 8; i++) {
            gamesBoard.setNotAllowed(2, i);
        }
        //riga 3
        gamesBoard.setNotAllowed(3, 0);
        //riga 4
        //riga 5
        gamesBoard.setNotAllowed(5, 8);
        //riga 6
        for (int i = 0; i <= 1; i++) {
            gamesBoard.setNotAllowed(6, i);
        }
        for (int i = 7; i <= 8; i++) {
            gamesBoard.setNotAllowed(6, i);
        }
        //riga 7
        for (int i = 0; i <= 2; i++) {
            gamesBoard.setNotAllowed(7, i);
        }
        for (int i = 6; i <= 8; i++) {
            gamesBoard.setNotAllowed(7, i);
        }
        //riga 8
        for (int i = 0; i <= 3; i++) {
            gamesBoard.setNotAllowed(8, i);
        }
        for (int i = 6; i <= 8; i++) {
            gamesBoard.setNotAllowed(8, i);
        }
    }

//-------------------------------------------------------------------------------------------------------\\

//metodi

    //costruttore --> assegna numberOfPlayer
    //            --> crea la Board in base al numero di giocatori
        // RICHIEDE CHE numberOfPlayer SIA UN NUMERO TRA 2, 3, 4
    public Board(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
        switch (numberOfPlayer){
            case 2:
                gamesBoard.setNotAllowed(0, 3);
                gamesBoard.setNotAllowed(0, 4);
                gamesBoard.setNotAllowed(1, 5);
                gamesBoard.setNotAllowed(2, 2);
                gamesBoard.setNotAllowed(2, 6);
                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(3, 8);
                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);
                gamesBoard.setNotAllowed(5, 0);
                gamesBoard.setNotAllowed(5, 7);
                gamesBoard.setNotAllowed(6, 2);
                gamesBoard.setNotAllowed(6, 6);
                gamesBoard.setNotAllowed(7, 3);
                gamesBoard.setNotAllowed(8, 4);
                gamesBoard.setNotAllowed(8, 5);
                break;
            case 3:
                gamesBoard.setNotAllowed(0, 4);
                gamesBoard.setNotAllowed(1, 5);
                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);
                gamesBoard.setNotAllowed(5, 7);
                gamesBoard.setNotAllowed(7, 3);
                gamesBoard.setNotAllowed(8, 4);
                break;
        }
    }
    //costruttore --> assegna boardSachet
    public Board(Sachet boardSachet) {
        this.boardSachet = boardSachet;
    }
    //costruttore --> assegna numberOfPlayer e boardSachet
    //            --> crea la Board in base al numero di giocatori
        // RICHIEDE CHE numberOfPlayer SIA UN NUMERO TRA 2, 3, 4
    public Board(int numberOfPlayer, Sachet boardSachet) {
        this.numberOfPlayer = numberOfPlayer;
        switch (numberOfPlayer){
            case 2:
                gamesBoard.setNotAllowed(0, 3);
                gamesBoard.setNotAllowed(0, 4);
                gamesBoard.setNotAllowed(1, 5);
                gamesBoard.setNotAllowed(2, 2);
                gamesBoard.setNotAllowed(2, 6);
                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(3, 8);
                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);
                gamesBoard.setNotAllowed(5, 0);
                gamesBoard.setNotAllowed(5, 7);
                gamesBoard.setNotAllowed(6, 2);
                gamesBoard.setNotAllowed(6, 6);
                gamesBoard.setNotAllowed(7, 3);
                gamesBoard.setNotAllowed(8, 4);
                gamesBoard.setNotAllowed(8, 5);
                break;
            case 3:
                gamesBoard.setNotAllowed(0, 4);
                gamesBoard.setNotAllowed(1, 5);
                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);
                gamesBoard.setNotAllowed(5, 7);
                gamesBoard.setNotAllowed(7, 3);
                gamesBoard.setNotAllowed(8, 4);
                break;
        }
        this.boardSachet = boardSachet;
    }

    //getter gamesBoard --> ritorna la Matrix
    public  Matrix getGamesBoard() {
        return gamesBoard;
    }
    //getter numberOfPlayer --> ritorna il numero di giocatori
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }



    //riempimento matrice iniziale - OK
    public void BoardInitialization(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //se tiles==EMPTY riempe randomicamente
                if (gamesBoard.getTile(i, j).equals(Tiles.EMPTY)){
                    //in posizione i,j scegli randomicamente le tiles --> sachet.draw()
                    PlaceTiles(boardSachet.draw(), i, j);
                }
            }
        }
    }

    //ritorna true se la board deve essere resettata - OK
    public boolean checkBoardReset(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //per tutte le tiles!=NOTALLOWED && !=EMPTY
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i, j).equals(Tiles.EMPTY)){
                    //se adiacente c'è almeno 1 tiles ==> return false
                    //se NON siamo sul bordo della board
                    if ((i!=0 && i!=8) &&
                        (j!=0 && j!=8)){
                        //adiacenza:
                        //sx
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //dx
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //alto
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //basso
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    //siamo sul bordo superiore
                    else if (i==0){
                        //adiacenza:
                        //sx
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //dx
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //basso
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    //siamo sul bordo inferiore
                    else if (i==8){
                        //adiacenza:
                        //sx
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //dx
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //alto
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    //siamo sul bordo sx
                    else if (j==0){
                        //adiacenza:
                        //dx
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //alto
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //basso
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    //siamo sul bordo dx
                    else if (j==8){
                        //adiacenza:
                        //sx
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //alto
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //basso
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                }
            }
        }
        //se supera i 2 for ==> non ci sono tiles adiacenti con altre tiles
        return true;
    }

    //resetta la board - versione ENG - OK
    //1. rimuovere tiles rimanenti e rimette in sachet
    //2. riempe board con tiles rimantenti nel sachet
        //RICHIEDE CHE checkBoardReset SIA A TRUE --> NON CONTROLLO
    public void boardResetENG(){
        //ricerca tiles!=EMPTY && tiles!=NOTALLOWED
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i, j).equals(Tiles.EMPTY)) {
                    //chiama addTiles --> aggiunge colore al Sachet
                    boardSachet.addTiles(gamesBoard.getTile(i, j));
                    //chiama remove   --> rimuovi effettivamente tiles da board
                    gamesBoard.remove(i, j);
                }
            }
        }
        //riempe board con tiles presenti nel sachet --> chiama BoardInitialization()
        BoardInitialization();
    }

    //resetta la board - versione ITA - OK
    //0. lascia le tiles rimaste sulla board
    //1. riempe board con tiles rimanenti nel sachet
    public void boardResetITA(){
        //riempe board con tiles presenti nel sachet --> chiama BoardInitialization()
        BoardInitialization();
    }


    //aggiunge tiles nella board - OK
    public void PlaceTiles(Tiles tile, int row, int col){
        gamesBoard.setTile(tile, row, col);
    }

    //rimuove i tiles nelle posizioni indicate - OK
        //RICHIEDE CHE List.size() SIA COMPRESA TRA 1, 2, 3
    public void remove(List<Point> position){
        //per ogni elemento nella position rimuovi il tiles da gamesBoard
        for (int i = 0; i < position.size(); i++) {
            gamesBoard.remove(position.get(i).x, position.get(i).y);
        }
    }

    //ritorna TRUE se tiles è libera --> ha almeno un lato libero - OK
        //RICHIEDE CHE position APPARTIENE ALLA BOARD VERA --> croce storta
    public boolean checkFreeTiles(Point position){
        int i=position.x;
        int j=position.y;
        //se NON siamo sul bordo
        if ((position.x!=0 && position.x!=8) &&
            (position.y!=0 && position.y!=8)){
            //sx
            if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                return false;
            }
            //dx
            if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                return false;
            }
            //alto
            if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                return false;
            }
            //basso
            if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                return false;
            }
        }
        //se siamo nella riga in alto
        else if (position.x==0){
            //sx
            if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                return false;
            }
            //dx
            if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                return false;
            }
            //basso
            if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                return false;
            }
        }
        //se siamo nella riga in basso
        else if (position.x==8){
            //sx
            if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                return false;
            }
            //dx
            if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                return false;
            }
            //alto
            if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                return false;
            }
        }
        //se siamo nella colonna a sx
        else if (position.y==0){
            //dx
            if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                return false;
            }
            //alto
            if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                return false;
            }
            //basso
            if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                return false;
            }
        }
        //se siamo nella colonna a dx
        else if (position.y==8){
            //sx
            if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                return false;
            }
            //alto
            if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                return false;
            }
            //basso
            if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                return false;
            }
        }
        //se siamo arrivati qui vuol dire che tile è libera
        return true;
    }

    //ritorna la posizione delle tiles libere - OK
    public ArrayList<Point> freeTiles(){
        ArrayList<Point> result=new ArrayList<Point>();
        Point position=new Point();
        //giriamo tutta la board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //assegniamo a position (i, j) --> Point=(int, int)
                position.setLocation(i, j);
                //ricerca delle tiles!=NOTALLOWED, !=EMPTY e libere
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i, j).equals(Tiles.EMPTY) &&
                    checkFreeTiles(position)){
                    //aggiungi position all'ArrayList delle tiles libere
                    result.add(position);
                }
            }
        }
        //ritorna il result
        return result;
    }

    //ritorna TRUE se nella List i Point sono adiacenti --> hanno un lato in comune - OK
    //adiacenza su board --> sono sulla stessa riga/colonna
        //RICHIEDE CHE NON CI SIANO PIÙ Point UGUALI --> STESSA X E STESSA Y
    public boolean checkAdjacentTiles(List<Point> position){
        //stessa riga o stessa colonna
        Point p=new Point();
        Point p1=new Point();
        if (!checkSameRow(position) && !checkSameColumn(position)){
            return false;
        }
        //sono veramente adiacenti ==> i Point sono vicini --> cambia di +-1 solo x o solo y
        //sono sulla stessa riga
        if (checkSameRow(position)){
            for (int i = 0; i < position.size(); i++) {
                //siamo sulla x-esima riga prima colonna
                if (position.get(i).y==0){
                    p.x=position.get(i).x;
                    p.y=position.get(i).y+1;
                    if (!position.contains(p)){ //NON sono adiacenti sono solo sulla stessa riga
                        return false;
                    }
                }
                //siamo sulla x-esima riga ultima colonna
                else if (position.get(i).y==8){
                    p.x=position.get(i).x;
                    p.y=position.get(i).y-1;
                    //NON sono adiacenti sono solo sulla stessa riga
                    if (!position.contains(p)){
                        return false;
                    }
                }
                //siamo sulla x-esima riga / NO casi limite
                else {
                    p.x=position.get(i).x;
                    p.y=position.get(i).y-1;
                    p1.x=position.get(i).x;
                    p1.y=position.get(i).y+1;
                    //NON sono adiacenti sono solo sulla stessa riga
                    if (!position.contains(p) && !position.contains(p1)){
                        return false;
                    }
                }
            }
        }
        //sono sulla stessa colonna
        else if (checkSameColumn(position)){
            for (int i = 0; i < position.size(); i++) {
                //siamo sulla y-esima colonna prima riga
                if (position.get(i).x==0){
                    p.x=position.get(i).x+1;
                    p.y=position.get(i).y;
                    //NON sono adiacenti sono solo sulla stessa colonna
                    if (!position.contains(p)){
                        return false;
                    }
                }
                //siamo sulla y-esima colonna ultima riga
                else if (position.get(i).x==8){
                    p.x=position.get(i).x-1;
                    p.y=position.get(i).y;
                    //NON sono adiacenti sono solo sulla stessa colonna
                    if (!position.contains(p)){
                        return false;
                    }
                }
                //siamo sulla y-esima colonna / NO casi limite
                else {
                    p.x=position.get(i).x-1;
                    p.y=position.get(i).y;
                    p1.x=position.get(i).x+1;
                    p1.y=position.get(i).y;
                    //NON sono adiacenti sono solo sulla stessa colonna
                    if (!position.contains(p) && !position.contains(p1)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //ritorna la posizione delle tiles adiacenti a quella in ingresso - OK
        //RICHIEDE CHE tile SIA IN UNA POSIZIONE NOTALLOWED
    public ArrayList<Point> adjacentTiles(Point tile){
        Point p=new Point();
        ArrayList<Point> result=new ArrayList<>();
        //NON siamo sul bordo
        if ((tile.x!=0 && tile.x!=8) &&
            (tile.y!=0 && tile.y!=8)){
            //alto
            p.x=tile.x+1;
            p.y=tile.y;
            if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                result.add(p);
            }
            //basso
            p.x=tile.x-1;
            if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                result.add(p);
            }
            //sx
            p.x=tile.x;
            p.y=tile.y-1;
            if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                result.add(p);
            }
            //dx
            p.y=tile.y+1;
            if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                result.add(p);
            }
        }
        //siamo sul bordo
        else {
            //siamo sul bordo alto
            if (tile.x==0){
                //basso
                p.x=tile.x-1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
                //sx
                p.x=tile.x;
                p.y=tile.y-1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
                //dx
                p.y=tile.y+1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
            }
            //siamo sul bordo basso
            else if (tile.x==8){
                //alto
                p.x=tile.x+1;
                p.y=tile.y;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
                //sx
                p.x=tile.x;
                p.y=tile.y-1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
                //dx
                p.y=tile.y+1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
            }
            //siamo sul bordo sx
            else if (tile.y==0){
                //alto
                p.x=tile.x+1;
                p.y=tile.y;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
                //basso
                p.x=tile.x-1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
                //dx
                p.y=tile.y+1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
            }
            //siamo sul bordo dx
            else if (tile.y==8){
                //alto
                p.x=tile.x+1;
                p.y=tile.y;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
                //basso
                p.x=tile.x-1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
                //sx
                p.x=tile.x;
                p.y=tile.y-1;
                if (!gamesBoard.getTile(p).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(p).equals(Tiles.EMPTY)){
                    result.add(p);
                }
            }
        }
        //ritorna l'ArrayList delle tiles adiacenti a quella data
        return result;
    }

    //ritorna TRUE se i Point sono sulla stessa riga - OK
    public boolean checkSameRow(List<Point> position){
        for (int i = 0; i < position.size(); i++) {
            for (int j = 0; j < position.size(); j++) {
                if (position.get(i).x!=position.get(j).x){
                    return false;
                }
            }
        }
        return true;
    }

    //ritorna TRUE se i Point sono sulla stessa colonna - OK
    public boolean checkSameColumn(List<Point> position){
        for (int i = 0; i < position.size(); i++) {
            for (int j = 0; j < position.size(); j++) {
                if (position.get(i).y!=position.get(j).y){
                    return false;
                }
            }
        }
        return true;
    }



}




/*
    i = riga
    j = colonna
    ==> alto i--
    ==> basso i++





 */
package it.polimi.ingsw;

import java.awt.*;
import java.util.List;

public class Board {
//attributi
    private static Matrix gamesBoard=new Matrix(9, 9);
    private int numberOfPlayer;
    private Sachet boardSachet;

    //creazione gamesBoard vera e propria -->
    // --> vero tabellone (celle che non fanno parte tabellone settati a NOTALLOWED e altre a EMPTY)
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
    public Board(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }
    //costruttore --> assegna boardSachet
    public Board(Sachet boardSachet) {
        this.boardSachet = boardSachet;
    }
    //costruttore --> assegna numberOfPlayer e boardSachet
    public Board(int numberOfPlayer, Sachet boardSachet) {
        this.numberOfPlayer = numberOfPlayer;
        this.boardSachet = boardSachet;
    }

    //getter gamesBoard --> ritorna la Matrix
    public static Matrix getGamesBoard() {
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
                //se NOTALLOWED riempire randomicamente
                if (gamesBoard.getTile(i, j)!=Tiles.NOTALLOWED){
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
                    if (i==0){
                        //adiacenza solo:
                        // sx
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        // dx
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        // basso
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    else if (i==8){
                        //adicenza solo:
                        // sx
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        // dx
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        // alto
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    if (j==0){
                        //adiacenza solo:
                        // alto
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        // basso
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        // dx
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    else if (j == 8) {
                        //adiacenza solo:
                        // alto
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        // basso
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        // sx
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                }
            }
        }
        //se supera i 2 for ==> non ci sono tiles adiacenti con altre tiles
        return true;
    }

//DOMANDA SU SLACK IN ATTESA RISPOSTA
    //resetta la board
    //1. rimuovere tiles rimanenti e rimette in sachet
    //2. riempe board con tiles rimantenti nel sachet
    public void boardReset(){
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

    //aggiunge tiles nella board - OK
    public void PlaceTiles(Tiles tile, int row, int col){
        gamesBoard.setTile(tile, row, col);
    }

    //rimuove i tiles nelle posizioni indicate - OK
    public void remove(List<Point> position){
        //per ogni elemento nella position rimuovi il tiles da gamesBoard
        for (int i = 0; i < position.size(); i++) {
            gamesBoard.remove(position.get(i).x, position.get(i).y);
        }
    }




}

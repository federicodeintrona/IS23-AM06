package it.polimi.ingsw.server.View;

import it.polimi.ingsw.server.Board;
import it.polimi.ingsw.server.Bookshelf;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.server.Sachet;
import it.polimi.ingsw.server.Tiles;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class CLIView extends View{


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()){
            case "board":
                break;


            case "bookshelf" :
                break;


            case "points" :
                break;
        }

    }




    //ritorna il colore della tile - lettera
    private static ColorCLI tileColor(Tiles tiles){
        return switch (tiles){
            case GREEN -> ColorCLI.GREEN;
            case BLUE -> ColorCLI.BLUE;
            case YELLOW -> ColorCLI.YELLOW;
            case WHITE -> ColorCLI.WHITE;
            case PINK -> ColorCLI.PINK;
            case LIGHT_BLUE -> ColorCLI.LIGHT_BLUE;
            case NOTALLOWED -> ColorCLI.NOTALLOWED;
            case EMPTY -> ColorCLI.EMPTY;
        };
    }

    //ritorna il colore della tile - background
    private static ColorCLI tileColorBG(Tiles tiles){
        return switch (tiles){
            case GREEN -> ColorCLI.GREENBG;
            case BLUE -> ColorCLI.BLUEBG;
            case YELLOW -> ColorCLI.YELLOWBG;
            case WHITE -> ColorCLI.WHITEBG;
            case PINK -> ColorCLI.PINKBG;
            case LIGHT_BLUE -> ColorCLI.LIGHT_BLUEBG;
            case NOTALLOWED -> ColorCLI.NOTALLOWEDBG;
            case EMPTY -> ColorCLI.EMPTYBG;
        };
    }


    //stampa la board
    public static void printBoard(Board board){
        System.out.print("  ");
        for (int i = 0; i < 9; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < 9; j++) {
                System.out.print(tileColorBG(board.getGamesBoard().getTile(i,j)) + "   " + ColorCLI.RESET);
            }
            System.out.println(" "+i);
        }
        System.out.print("  ");
        for (int i = 0; i < 9; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println("\n");
    }

    //stampa la bookshelf
    public static void printBookshelf(Bookshelf bookshelf){
        System.out.print("  ");
        for (int i = 0; i < 5; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println();
        for (int i = 0; i < 6; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < 5; j++) {
                System.out.print(tileColorBG(bookshelf.getTiles().getTile(i,j)) + "   " + ColorCLI.RESET);
            }
            System.out.println(" "+i);
        }
        System.out.print("  ");
        for (int i = 0; i < 5; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println("\n");
    }

    //recupera il Personal Objective
    private static Bookshelf personalObjectiveReturn(PersonalObjective personalObjective){
        Bookshelf bookshelf=new Bookshelf();
        for (Point key: personalObjective.getCard().keySet()){
            bookshelf.getTiles().setTile(personalObjective.getCard().get(key), key);
        }
        return bookshelf;
    }

    //stampa il personal Objective
    public static void printPersonalObjective(PersonalObjective personalObjective){
        Bookshelf bookshelf=personalObjectiveReturn(personalObjective);
        System.out.print("  ");
        for (int i = 0; i < 5; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println();
        for (int i = 0; i < 6; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < 5; j++) {
                if (personalObjective.getCard().containsKey(new Point(i,j))){
                    System.out.print(tileColorBG(bookshelf.getTiles().getTile(i,j)) + "\u001b[30m X " + ColorCLI.RESET);
                }
                else{
                    System.out.print(tileColorBG(bookshelf.getTiles().getTile(i,j)) + "   " + ColorCLI.RESET);
                }
            }
            System.out.println(" "+i);
        }
        System.out.print("  ");
        for (int i = 0; i < 5; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println("\n");
    }

    //stampa la Bookshelf insieme al Personal Objective
    public static void printBookshelfPersonalObjective(Bookshelf bookshelf, PersonalObjective personalObjective){
        Bookshelf bookshelfPO=personalObjectiveReturn(personalObjective);
        System.out.print("  ");
        for (int i = 0; i < 5; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println();
        for (int i = 0; i < 6; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < 5; j++) {
                //posizioni della PersonalObjective card
                if (personalObjective.getCard().containsKey(new Point(i,j))){
                    //posizione della Board == PersonalObjective
                    if ( personalObjective.getCard().get(new Point(i,j))
                        .equals(bookshelf.getTiles().getTile(new Point(i,j))) ){
                        System.out.print(tileColor(bookshelfPO.getTiles().getTile(i, j)) +
                                String.valueOf(tileColorBG(bookshelf.getTiles().getTile(i,j))) +
                                "\u001b[30m V " +
                                ColorCLI.RESET);
                    }
                    else {
                        System.out.print(tileColor(bookshelfPO.getTiles().getTile(i, j)) +
                                String.valueOf(tileColorBG(bookshelf.getTiles().getTile(i,j))) +
                                "\u001b[1m X " +
                                ColorCLI.RESET);
                    }
                }
                else{
                    System.out.print(tileColorBG(bookshelf.getTiles().getTile(i,j)) + "   " + ColorCLI.RESET);
                }
            }
            System.out.println(" "+i);
        }
        System.out.print("  ");
        for (int i = 0; i < 5; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println("\n");
    }

    //todo stampa i personal objective





    public static void main(String[] args) {
//        System.out.print(ColorCLI.BLUE + " B " + ColorCLI.RESET);
//        System.out.println(ColorCLI.WHITE + " W " + ColorCLI.RESET);
//        System.out.print(ColorCLI.WHITEBG + "   " + ColorCLI.RESET);
//        System.out.print(ColorCLI.EMPTYBG + "   " + ColorCLI.RESET);
//        System.out.print(ColorCLI.NOTALLOWEDBG + "   " + ColorCLI.RESET);

        Sachet sachet=new Sachet();
        Board board=new Board(3, sachet);
        board.BoardInitialization();
        printBoard(board);
        board.remove(new Point(3,3));
        board.remove(new Point(3,4));
        printBoard(board);

        Bookshelf bookshelf=new Bookshelf();
        ArrayList<Tiles> arrayList=new ArrayList<>();
        arrayList.add(Tiles.BLUE);
        arrayList.add(Tiles.BLUE);
        arrayList.add(Tiles.BLUE);
        bookshelf.addTile(arrayList, 0);
        bookshelf.addTile(arrayList, 0);

        bookshelf.addTile(arrayList, 1);
        bookshelf.addTile(arrayList, 1);

        bookshelf.addTile(arrayList, 2);
        bookshelf.addTile(arrayList, 2);

        bookshelf.addTile(arrayList, 3);
        bookshelf.addTile(arrayList, 3);

        bookshelf.addTile(arrayList, 4);
        bookshelf.addTile(arrayList, 4);

        printBookshelf(bookshelf);

        PersonalObjective personalObjective=new PersonalObjective(1);
        printPersonalObjective(personalObjective);

        printBookshelfPersonalObjective(bookshelf, personalObjective);

        Bookshelf bookshelf1=new Bookshelf();
        printBookshelfPersonalObjective(bookshelf1, personalObjective);

    }







//todo diversi metodi
    /*
        metodo per avviare la CLI

        metodo per pulire la CLI

        metodo per chiedere IP e port

        metodo per leggere nickname

        metodo per leggere i comandi durante la partita

        metodo per mostrare i comandi durante la partita
     */
}

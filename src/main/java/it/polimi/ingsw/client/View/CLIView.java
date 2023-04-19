package it.polimi.ingsw.client.View;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.server.*;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.server.Model.*;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.Define;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public class CLIView extends View{

    private static final String STR_INPUT_CANCELED = "User input canceled.";
    private Networker net;
    private static String username;



//COSTRUTTORE
    public CLIView(Networker net) {
        this.net = net;
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

//STAMPA
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
        for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println();
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            System.out.print(i+" ");
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                System.out.print(tileColorBG(bookshelf.getTiles().getTile(i,j)) + "   " + ColorCLI.RESET);
            }
            System.out.println(" "+i);
        }
        System.out.print("  ");
        for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
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
        for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println();
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            System.out.print(i+" ");
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
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
        for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println("\n");
    }

    //stampa la Bookshelf insieme al Personal Objective
    public static void printBookshelfPersonalObjective(Bookshelf bookshelf, PersonalObjective personalObjective){
        Bookshelf bookshelfPO=personalObjectiveReturn(personalObjective);
        System.out.print("  ");
        for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println();
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            System.out.print(i+" ");
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
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
        for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println("\n");
    }

    //stampa tutti i comandi che può fare il client  #help #h
    public static void help(){
        System.out.println("All command, WITH EXAMPLE, are:");
        System.out.println("#remove (0,0), (0,0), (0,0) ..... It is also possible not to fill all the relatives");
        System.out.println("#switch 2, 0, 1 ................. Switch the order of the selected tiles");
        System.out.println("#add 0 .......................... Add the tiles in the column of the bookshelf");
        System.out.println("#rollback ....................... Return to the previous move");
        System.out.println("#chat -hello- ................... Chatting with all players");
        System.out.println("#whisper @username -hello- ...... Chatting with username player");
    }

    //todo stampa i personal objective
    public static void printPersonalObjective(){

    }


//LEGGI, RICEVI E INVIA MESSAGGI
    //leggere da stdIN
    public static String readLine() {
        //istanzia scanner che legge da stdIN
        Scanner scanner=new Scanner(System.in);
        //stringhe lette da stdIN
        String world=new String();
        //aspetta emissione dati e li legge
        world= String.valueOf(scanner.nextLine());
        return world;
    }

    //legge messaggi, li crea, li invia
    public void readMessage(){
        String st=readLine();
        sendMessage(createMessage(st));
    }

    //capire come trasformare stringa in valori accettabili
    //todo creare messaggi da line letta da stdIN
    public static Message createMessage(String st){
        System.out.println(st);
        switch (st) {
            case "#remove" -> {

                        System.out.println(st + "\n#remove (0,0), (0,0), (0,0) ..... It is also possible not to fill all the relatives");
            }
            case "#switch" -> {
                /*
                    1. vai avanti di 1
                    2. if leggi "\n"
                 */
                System.out.println("#switch 2, 0, 1 ................. Switch the order of the selected tiles");
            }
            case "#add" -> System.out.println("#add 0 .......................... Add the tiles in the column of the bookshelf");
            case "#rollback" -> System.out.println("#rollback ....................... Return to the previous move");
            case "#chat" -> System.out.println("#chat -hello- ................... Chatting with all players");
            case "#whisper" -> System.out.println("#whisper @username -hello- ...... Chatting with username player");
            case "#help", "#h" -> help();
            default -> System.out.println(st + " is NOT a valid command \nIf you need help put #help or #h");
        }
        return null;
    }


    //todo invia messaggi a Networker
    public void sendMessage(Message message){

    }

    //todo ricevi messaggi
    public void receivedMessage(Message message){
    }


//RICHIESTA DA CLI
    //avvia la CLI
    public void runCLI()  {
        System.out.println("Welcome to My Shelfie game");
        //richiesta nickname
        sendMessage(askNickname());
        //arrivano altri messaggi
        while (true){
            //se abbiamo ricevuto messaggi dal server


        }

    }

    //pulisce CLI
    public void clearCLI(){
        System.out.println(ColorCLI.CLEAR);
        System.out.flush();
    }

    //richista nickname
    public Message askNickname() {
        Message message=new Message();

        System.out.print("Enter your nickname: ");
        String nickname = readLine();
        message.setUsername(nickname);
        message.setType(MessageTypes.USERNAME);
        username=nickname;

        return message;

    }

    //todo richiesta varie mosse che deve fare giocatore

    //todo ricevi aggiornamento view
    // Ing Conti meglio inviare tutto o solo aggiornamenti
    // meglio inviare .JSON o JSONOject






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

        String h=readLine();
//        help();
        createMessage(h);
    }

}


/*
     invio messaggi a: Networker
     ricevo messaggi da: Networker


     quando ricevo messaggi faccio partire la stampa / salvo soltanto
 */

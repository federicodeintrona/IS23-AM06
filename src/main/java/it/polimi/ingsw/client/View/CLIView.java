package it.polimi.ingsw.client.View;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.server.CommonObjective.*;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.server.Model.*;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.Define;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class CLIView extends View{

    private Networker net;
    private static String username;

    private Bookshelf myBookshelf;
    private ArrayList<Bookshelf> allBookshelf;
    private Board board;
    private ArrayList<String> allUsername;
    private PersonalObjective myPO;
    private ArrayList<CommonObjective> commonObjectives;


//COSTRUTTORE
    public CLIView(Networker net) {
        this.net = net;
    }

//GETTER & SETTER
    public Networker getNet() {
        return net;
    }
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        CLIView.username = username;
    }
    public Bookshelf getMyBookshelf() {
        return myBookshelf;
    }
    public void setMyBookshelf(Bookshelf myBookshelf) {
        this.myBookshelf = myBookshelf;
    }
    public ArrayList<Bookshelf> getAllBookshelf() {
        return allBookshelf;
    }
    public void setAllBookshelf(ArrayList<Bookshelf> allBookshelf) {
        this.allBookshelf = allBookshelf;
    }
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public ArrayList<String> getAllUsername() {
        return allUsername;
    }
    public void setAllUsername(ArrayList<String> allUsername) {
        this.allUsername = allUsername;
    }
    public PersonalObjective getMyPO() {
        return myPO;
    }
    public void setMyPO(PersonalObjective myPO) {
        this.myPO = myPO;
    }
    public ArrayList<CommonObjective> getCommonObjectives() {
        return commonObjectives;
    }
    public void setCommonObjectives(ArrayList<CommonObjective> commonObjectives) {
        this.commonObjectives = commonObjectives;
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
            case POSITION -> ColorCLI.POSITION;
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
            case POSITION -> ColorCLI.POSITIONBG;
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
        System.out.println("#printpersonal .................. Print your personal objective");
        System.out.println("#printcommon .................... Print the common objective for this game");
        System.out.println("#printboard ..................... Print the board");
        System.out.println("#printyourbookshelf ............. Print your bookshelf");
        System.out.println("#printbookshelf @username ....... Print the username's bookshelf");
    }

    //legge da JSON il Common Objective d'interesse
    private static void readJSONCO(CommonObjective commonObjective){
        Bookshelf bookshelf=new Bookshelf();
        JSONParser jsonParser=new JSONParser();

        try {
            FileReader reader=new FileReader("src/main/java/it/polimi/ingsw/server/CommonObjective/CommonObjective.json");
            Object obj=jsonParser.parse(reader);
            JSONObject co=(JSONObject) obj;
            JSONObject coDetails = new JSONObject();

            //che common objective sei?
            if (commonObjective.getClass().equals(CommonObjective1.class)){
                coDetails=(JSONObject) co.get("CO1");
            }
            else if (commonObjective.getClass().equals(CommonObjective2.class)){
                coDetails=(JSONObject) co.get("CO2");
            }
            else if (commonObjective.getClass().equals(CommonObjective3.class)){
                coDetails=(JSONObject) co.get("CO3");
            }
            else if (commonObjective.getClass().equals(CommonObjective4.class)){
                coDetails=(JSONObject) co.get("CO4");
            }
            else if (commonObjective.getClass().equals(CommonObjective5.class)){
                coDetails=(JSONObject) co.get("CO5");
            }
            else if (commonObjective.getClass().equals(CommonObjective6.class)){
                coDetails=(JSONObject) co.get("CO6");
            }
            else if (commonObjective.getClass().equals(CommonObjective7.class)){
                coDetails=(JSONObject) co.get("CO7");
            }
            else if (commonObjective.getClass().equals(CommonObjective8.class)){
                coDetails=(JSONObject) co.get("CO8");
            }
            else if (commonObjective.getClass().equals(CommonObjective9.class)){
                coDetails=(JSONObject) co.get("CO9");
            }
            else if (commonObjective.getClass().equals(CommonObjective10.class)){
                coDetails=(JSONObject) co.get("CO10");
            }
            else if (commonObjective.getClass().equals(CommonObjective11.class)){
                coDetails=(JSONObject) co.get("CO11");
            }
            else if (commonObjective.getClass().equals(CommonObjective12.class)){
                coDetails=(JSONObject) co.get("CO12");
            }

            //salvataggio quantità e tipo
            int quantity=Integer.parseInt(coDetails.get("QUANTITY").toString());
            String type=coDetails.get("TYPE").toString();

            JSONArray coPosition=(JSONArray) coDetails.get("POSITION");
            ArrayList<Point> points=new ArrayList<>();

            //salvataggio posizioni da mostrare per far capire il common objective
            for (Object o : coPosition) {
                JSONArray coPoint = (JSONArray) o;
                int x = Integer.parseInt(coPoint.get(0).toString());
                int y = Integer.parseInt(coPoint.get(1).toString());
                points.add(new Point(x, y));
            }

            //inserimento posizioni nella bookshelf
            for (Point point : points) {
                bookshelf.getTiles().setTile(Tiles.POSITION, point);
            }

            //stampaggio
            System.out.println("X"+quantity);
            System.out.println(type);
            printBookshelf(bookshelf);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //stampa i common objective
    public static void printCommonObjective(CommonObjective co1, CommonObjective co2){
        System.out.println("COMMON OBJECTIVE 1:");
        readJSONCO(co1);
        System.out.println("\nCOMMONOBJECTIVE 2:");
        readJSONCO(co2);

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

    //todo legge messaggi, li crea, li invia a chi di dovere
    public void readMessage(){
        String st=readLine();
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
            case "#printpersonal" -> {
                System.out.println("printPersonalObjective(personalObjective);"); //todo sistemare
                System.out.println("printBooksehlfPersonal(personalObjective);"); //todo sistemare
            }
            case "#printboard" -> System.out.println("printBoard(board);"); //todo sistemare
            case "#printyourbookshelf" -> {
                System.out.println("printBookshelf(bookshelf);"); //todo sistemare
                System.out.println("printBooksehlfPersonal(personalObjective);"); //todo sistemare
            }
            case "#printbookshelf" -> System.out.println("printBookshelf(bookshelf);"); //todo sistemare - dell'username
            case "#printcommon" -> System.out.println("printCommonObjective(commonObjective)"); //todo sistemare x2
            default -> System.out.println(st + " is NOT a valid command \nIf you need help put #help or #h");
        }

    }


    //todo invia messaggi a Networker
    public void sendMessage(Message message){

    }

    //todo ricevi messaggi
    public void receivedMessage(Message message){
    }


//RICHIESTA DA CLI
    //todo avvia la CLI
    public void runCLI()  {
        System.out.println("Welcome to My Shelfie game");
        //richiesta nickname
        sendMessage(askUsername());
        //arrivano altri messaggi
        while (true){
            //se abbiamo ricevuto messaggi dal server


        }

    }

    //pulisce CLI
    public static void clearCLI(){
        System.out.println(ColorCLI.CLEAR);
        System.out.flush();
    }

    //richista nickname
    public Message askUsername() {
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


        CommonObjective7 co1=new CommonObjective7();
        CommonObjective4 co2=new CommonObjective4();

        printCommonObjective(co1, co2);
    }

}


/*
     invio messaggi a: Networker
     ricevo messaggi da: Networker


     quando ricevo messaggi faccio partire la stampa / salvo soltanto
 */

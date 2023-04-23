package it.polimi.ingsw.client.View;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.server.CommonObjective.*;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.Model.Tiles;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.Define;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PrintThread extends Thread{



    private final Networker net; //a chi mandare messaggi
    private static String username; //username del mio utente

    private Bookshelf myBookshelf; //mia bookshelf
    private ArrayList<Bookshelf> allBookshelf; //tutte le bookshelf - le posizioni sono uguali alle posizioni di allUsername
    private Board board; //la board di gioco
    private ArrayList<String> allUsername; //tutti gli username
    private PersonalObjective myPO; //il mio personal objective
    private ArrayList<CommonObjective> commonObjectives; //i common objective


    //COSTRUTTORE
    public PrintThread(Networker net) {
        this.net = net;
    }



    public Networker getNet() {
        return net;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        PrintThread.username = username;
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
    public void printBoard(Board board){
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
    public void printBookshelf(Bookshelf bookshelf){
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
    private Bookshelf personalObjectiveReturn(PersonalObjective personalObjective){
        Bookshelf bookshelf=new Bookshelf();
        for (Point key: personalObjective.getCard().keySet()){
            bookshelf.getTiles().setTile(personalObjective.getCard().get(key), key);
        }
        return bookshelf;
    }

    //stampa il personal Objective
    public void printPersonalObjective(PersonalObjective personalObjective){
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
    public void printBookshelfPersonalObjective(Bookshelf bookshelf, PersonalObjective personalObjective){
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
    public void help(){
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
    private void readJSONCO(CommonObjective commonObjective){
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

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //stampa i common objective
    public void printCommonObjective(CommonObjective co1, CommonObjective co2){
        System.out.println("COMMON OBJECTIVE 1:");
        readJSONCO(co1);
        System.out.println("\nCOMMONOBJECTIVE 2:");
        readJSONCO(co2);

    }


    @Override
    public void run() {
        //stampa su stdOUT
    }
}

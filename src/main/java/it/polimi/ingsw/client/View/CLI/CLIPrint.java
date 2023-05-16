package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.Tiles;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CLIPrint implements PropertyChangeListener {

    private final CLIMain cliMain;


    public CLIPrint(CLIMain cliMain) {
        this.cliMain=cliMain;
    }



    //ritorna il colore della tile - lettera
    private ColorCLI tileColor(Tiles tiles){
        synchronized (cliMain.getLock()) {
            return switch (tiles) {
                case GREEN -> ColorCLI.GREEN1;
                case BLUE -> ColorCLI.BLUE1;
                case YELLOW -> ColorCLI.YELLOW1;
                case WHITE -> ColorCLI.WHITE1;
                case PINK -> ColorCLI.PINK1;
                case LIGHT_BLUE -> ColorCLI.LIGHT_BLUE1;
                case NOTALLOWED -> ColorCLI.NOTALLOWED;
                case EMPTY -> ColorCLI.EMPTY1;
                case POSITION -> ColorCLI.POSITION;
            };
        }
    }

    //ritorna il colore della tile - background
    private ColorCLI tileColorBG(Tiles tiles){
        synchronized (cliMain.getLock()) {
            return switch (tiles) {
                case GREEN -> ColorCLI.GREENBG1;
                case BLUE -> ColorCLI.BLUEBG1;
                case YELLOW -> ColorCLI.YELLOWBG1;
                case WHITE -> ColorCLI.WHITEBG1;
                case PINK -> ColorCLI.PINKBG1;
                case LIGHT_BLUE -> ColorCLI.LIGHT_BLUEBG1;
                case NOTALLOWED -> ColorCLI.NOTALLOWEDBG;
                case EMPTY -> ColorCLI.EMPTYBG1;
                case POSITION -> ColorCLI.POSITIONBG;
            };
        }
    }


    //stampa la board
    public void printBoard(Matrix board){
        synchronized (cliMain.getLock()) {
            System.out.print("  ");
            for (int i = 0; i < 9; i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println();
            for (int i = 0; i < 9; i++) {
                System.out.print(i + " ");
                for (int j = 0; j < 9; j++) {
                    System.out.print(tileColorBG(board.getTile(i, j)) + "   " + ColorCLI.RESET);
                }
                System.out.println(" " + i);
            }
            System.out.print("  ");
            for (int i = 0; i < 9; i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    //stampa la bookshelf
    public void printBookshelf(Matrix bookshelf){
        synchronized (cliMain.getLock()) {
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println();
            for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
                System.out.print("  ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    System.out.print(tileColorBG(bookshelf.getTile(i, j)) + "   " + ColorCLI.RESET);
                }
                System.out.println();
            }
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    //stampa tutte le bookshelf
    public void printAllBookshelf(HashMap<String, Matrix> allMatrix){
        synchronized (cliMain.getLock()){
            for (String key: allMatrix.keySet()){
                if (key.equals(cliMain.getClientState().getMyUsername())){
                    System.out.println(ColorCLI.RED + "My Bookshelf: " + ColorCLI.RESET);
                    //TODO quale metto solo bookshelf or bookshelf + personal
//                    printBookshelf(allMatrix.get(key));
                    printBookshelfPersonalObjective(allMatrix.get(key), cliMain.getClientState().getMyPersonalObjective());
                }
                else{
                    System.out.println("Bookshelf of: "+key);
                    printBookshelf(allMatrix.get(key));
                }

                System.out.println("\n");
            }
        }
    }

    //recupera il Personal Objective
    private Matrix personalObjectiveReturn(HashMap<Point, Tiles> personalObjective){
        synchronized (cliMain.getLock()) {
            Matrix bookshelf=new Matrix(Define.NUMBEROFROWS_BOOKSHELF.getI(), Define.NUMBEROFCOLUMNS_BOOKSHELF.getI());
            for (Point key : personalObjective.keySet()){
                bookshelf.setTile(personalObjective.get(key), key);
            }
            return bookshelf;
        }
    }

    //stampa il personal Objective
    public void printPersonalObjective(HashMap<Point, Tiles> personalObjective){
        synchronized (cliMain.getLock()) {
            Matrix bookshelf = personalObjectiveReturn(personalObjective);
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println();
            for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
                System.out.print("  ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    if (personalObjective.containsKey(new Point(i, j))) {
                        System.out.print(tileColorBG(bookshelf.getTile(i, j)) + "\u001b[30m X " + ColorCLI.RESET);
                    } else {
                        System.out.print(tileColorBG(bookshelf.getTile(i, j)) + "   " + ColorCLI.RESET);
                    }
                }
                System.out.println();
            }
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    //stampa la Bookshelf insieme al Personal Objective
    public void printBookshelfPersonalObjective(Matrix bookshelf, HashMap<Point, Tiles> personalObjective){
        synchronized (cliMain.getLock()) {
            Matrix bookshelfPO = personalObjectiveReturn(personalObjective);
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println();
            for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
                System.out.print("  ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    //posizioni della PersonalObjective card
                    if (personalObjective.containsKey(new Point(i, j))) {
                        //posizione della Board == PersonalObjective
                        if (personalObjective.get(new Point(i, j))
                                .equals(bookshelf.getTile(new Point(i, j)))) {
                            System.out.print(tileColor(bookshelfPO.getTile(i, j)) +
                                    String.valueOf(tileColorBG(bookshelf.getTile(i, j))) +
                                    "\u001b[30m V " +
                                    ColorCLI.RESET);
                        } else {
                            System.out.print(tileColor(bookshelfPO.getTile(i, j)) +
                                    String.valueOf(tileColorBG(bookshelf.getTile(i, j))) +
                                    "\u001b[1m X " +
                                    ColorCLI.RESET);
                        }
                    } else {
                        System.out.print(tileColorBG(bookshelf.getTile(i, j)) + "   " + ColorCLI.RESET);
                    }
                }
                System.out.println();
            }
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    //stampa tutti i comandi che può fare il client  #help #h
    public void help(){
        synchronized (cliMain.getLock()) {
            System.out.println("All command, WITH EXAMPLE, are:");
            System.out.println("#remove (0,0), (0,0), (0,0) ..... It is also possible not to fill all the relatives");
            System.out.println("#switch 2, 0, 1 ................. Switch the order of the selected tiles");
            System.out.println("#add 0 .......................... Add the tiles in the column of the bookshelf");
//            System.out.println("#rollback ....................... Return to the previous move");
//            System.out.println("#chat -hello- ................... Chatting with all players");
//            System.out.println("#whisper @username -hello- ...... Chatting with username player");
            System.out.println("#printpersonal .................. Print your personal objective");
            System.out.println("#printcommon .................... Print the common objective for this game");
            System.out.println("#printboard ..................... Print the board");
            System.out.println("#printyourbookshelf ............. Print your bookshelf");
            System.out.println("#printbookshelf @username ....... Print the username's bookshelf");
            System.out.println("#printpoints .................... Print all public points");
            System.out.println("#printmypoint ................... Print my points");
            System.out.println("#printchair ..................... Print who has the chair - who is the first player?");
        }
    }

    //legge da JSON il Common Objective d'interesse
    private void readJSONCO(Integer commonObjective){
        synchronized (cliMain.getLock()) {
            Matrix bookshelf = new Matrix(Define.NUMBEROFROWS_BOOKSHELF.getI(), Define.NUMBEROFCOLUMNS_BOOKSHELF.getI());
            JSONParser jsonParser = new JSONParser();

            try {
//                FileReader reader = new FileReader("src/main/resources/CommonObjective.json");
                InputStream is=this.getClass().getClassLoader().getResourceAsStream("CommonObjective.json");
                Object obj = jsonParser.parse(new InputStreamReader(is));
                JSONObject co = (JSONObject) obj;
                JSONObject coDetails = new JSONObject();

                //che common objective sei?
                switch (commonObjective){
                    case 1 -> coDetails = (JSONObject) co.get("CO1");
                    case 2 -> coDetails = (JSONObject) co.get("CO2");
                    case 3 -> coDetails = (JSONObject) co.get("CO3");
                    case 4 -> coDetails = (JSONObject) co.get("CO4");
                    case 5 -> coDetails = (JSONObject) co.get("CO5");
                    case 6 -> coDetails = (JSONObject) co.get("CO6");
                    case 7 -> coDetails = (JSONObject) co.get("CO7");
                    case 8 -> coDetails = (JSONObject) co.get("CO8");
                    case 9 -> coDetails = (JSONObject) co.get("CO9");
                    case 10-> coDetails = (JSONObject) co.get("CO10");
                    case 11-> coDetails = (JSONObject) co.get("CO11");
                    case 12-> coDetails = (JSONObject) co.get("CO12");

                }

                //salvataggio quantità e tipo
                String type = coDetails.get("TYPE").toString();

                JSONArray coPosition = (JSONArray) coDetails.get("POSITION");
                ArrayList<Point> points = new ArrayList<>();

                //salvataggio posizioni da mostrare per far capire il common objective
                for (Object o : coPosition) {
                    JSONArray coPoint = (JSONArray) o;
                    int x = Integer.parseInt(coPoint.get(0).toString());
                    int y = Integer.parseInt(coPoint.get(1).toString());
                    points.add(new Point(x, y));
                }

                //inserimento posizioni nella bookshelf
                for (Point point : points) {
                    bookshelf.setTile(Tiles.POSITION, point);
                }

                //stampaggio
                System.out.println(type);
                printBookshelf(bookshelf);

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //stampa i common objective
    public void printCommonObjective(ArrayList<Integer> commonObjective){
        synchronized (cliMain.getLock()) {
            System.out.println("COMMON OBJECTIVE 1:");
            readJSONCO(commonObjective.get(0));
            System.out.println("\nCOMMON OBJECTIVE 2:");
            readJSONCO(commonObjective.get(1));
        }
    }

    //stampa i punti
    public void printPoints(HashMap<String, Integer> publicPoints){
        System.out.println("All Public Points: ");
        for (String key: publicPoints.keySet()){
            System.out.println(key+": "+publicPoints.get(key));
        }
    }

    //stampa i mie punti
    public void printMyPoints(Integer point){
        System.out.println("My Points: "+point);
    }

    //stampa chi ha la sedia
    public void printChair(){
        synchronized (cliMain.getLock()){
            System.out.println("The first player, that have chair, is: " +
                               cliMain.getClientState().getAllUsername().get(0));
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n");
        }
    }

    //stampa l'oridne delle tiles selezionate
    public void printOrderTiles(ArrayList<Tiles> order){
        System.out.print("Selected tiles: ");
        for (Tiles tiles : order) {
            System.out.print(tileColorBG(tiles) + "   " + ColorCLI.RESET + " ");
        }
        System.out.println("\n\n");
    }

    //stampa l'errore - c'è un errore
    public void printError(String error){
        synchronized (cliMain.getLock()) {
            System.out.println(error);
        }
    }

    //siamo in attesa di nuovi giocatori - singolo
    private void printWaitingPrivate(){
        synchronized (cliMain.getLock()){
            System.out.println("Waiting for the other players...");
        }
    }

    //stampa che siamo in attesa di giocatori - fino a che
    public void printWaiting(){
        while (!cliMain.getClientState().gameHasStarted()){
            printWaitingPrivate();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    //stampa che sta per iniziare la partita
    public void gameHasStarted(){
        synchronized (cliMain.getLock()){
            System.out.println("The number of players has been reached \n\n\nMY SHELFIE GAME HAS STARTED\n\n\n\n\n");
        }
    }

    //stampa di chi è il turno
    private void printTurn(){
        synchronized (cliMain.getLock()){
            if (cliMain.getClientState().getCurrentPlayer().equals(cliMain.getClientState().getMyUsername())){
                System.out.println(ColorCLI.RED + String.valueOf(ColorCLI.BOLD) + "It is YOUR turn"+ ColorCLI.RESET);
            }
            else {
                System.out.println("It is " + cliMain.getClientState().getCurrentPlayer() + " turn");
            }
        }
    }

    //stampa tutto quello che serve all'inizio di un turno
    public void playerTurn(){
        printTurn();
        printBoard(cliMain.getClientState().getBoard());
        printAllBookshelf(cliMain.getClientState().getAllBookshelf());
        printPoints(cliMain.getClientState().getAllPublicPoints());
    }

    //è finito il gioco
    public void printEndGame(){
        System.out.println("The Game is ended");
        System.out.println("THE WINNER IS: " + cliMain.getClientState().getWinnerPlayer());
        System.out.println("\n\nALL THE PLAYERS POINTS: ");
        for (String key: cliMain.getClientState().getAllPublicPoints().keySet()){
            System.out.println(key+": "+cliMain.getClientState().getAllPublicPoints().get(key));
        }
    }

    //pulire la CLI funziona solo su terminale - no terminale IDE
    public void clearSheel()  {
        System.out.print(ColorCLI.CLEAR);
        System.out.flush();

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}

package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.server.CommonObjective.*;
import it.polimi.ingsw.server.Messages.Message;
import it.polimi.ingsw.server.Messages.MessageTypes;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.Model.Tiles;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CLIPrint{


    private final CLIMain cliMain;


    public CLIPrint(CLIMain cliMain) {
        this.cliMain=cliMain;
    }






    //ritorna il colore della tile - lettera
    private ColorCLI tileColor(Tiles tiles){
        synchronized (cliMain.getLock()) {
            return switch (tiles) {
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
    }

    //ritorna il colore della tile - background
    private ColorCLI tileColorBG(Tiles tiles){
        synchronized (cliMain.getLock()) {
            return switch (tiles) {
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
    }


    //STAMPA
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
                System.out.print(i + " ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    System.out.print(tileColorBG(bookshelf.getTile(i, j)) + "   " + ColorCLI.RESET);
                }
                System.out.println(" " + i);
            }
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    //stampa tutte le bookshelf
    //TODO forse c'è un deadlock
    public void printAllBookshelf(ArrayList<Matrix> allMatrix){
        synchronized (cliMain.getLock()){
            for (int i = 0; i < cliMain.getClientState().getAllBookshelf().size(); i++) {
                System.out.println(cliMain.getClientState().getAllUsername().get(i) + ": ");
                printBookshelf(cliMain.getClientState().getMyBookshelf());
                System.out.println("\n");
            }
        }
    }

    //recupera il Personal Objective
    private Bookshelf personalObjectiveReturn(PersonalObjective personalObjective){
        synchronized (cliMain.getLock()) {
            Bookshelf bookshelf = new Bookshelf();
            for (Point key : personalObjective.getCard().keySet()) {
                bookshelf.getTiles().setTile(personalObjective.getCard().get(key), key);
            }
            return bookshelf;
        }
    }

    //stampa il personal Objective
    public void printPersonalObjective(PersonalObjective personalObjective){
        synchronized (cliMain.getLock()) {
            Bookshelf bookshelf = personalObjectiveReturn(personalObjective);
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println();
            for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
                System.out.print(i + " ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    if (personalObjective.getCard().containsKey(new Point(i, j))) {
                        System.out.print(tileColorBG(bookshelf.getTiles().getTile(i, j)) + "\u001b[30m X " + ColorCLI.RESET);
                    } else {
                        System.out.print(tileColorBG(bookshelf.getTiles().getTile(i, j)) + "   " + ColorCLI.RESET);
                    }
                }
                System.out.println(" " + i);
            }
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    //stampa la Bookshelf insieme al Personal Objective
    public void printBookshelfPersonalObjective(Matrix bookshelf, PersonalObjective personalObjective){
        synchronized (cliMain.getLock()) {
            Bookshelf bookshelfPO = personalObjectiveReturn(personalObjective);
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println();
            for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
                System.out.print(i + " ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    //posizioni della PersonalObjective card
                    if (personalObjective.getCard().containsKey(new Point(i, j))) {
                        //posizione della Board == PersonalObjective
                        if (personalObjective.getCard().get(new Point(i, j))
                                .equals(bookshelf.getTile(new Point(i, j)))) {
                            System.out.print(tileColor(bookshelfPO.getTiles().getTile(i, j)) +
                                    String.valueOf(tileColorBG(bookshelf.getTile(i, j))) +
                                    "\u001b[30m V " +
                                    ColorCLI.RESET);
                        } else {
                            System.out.print(tileColor(bookshelfPO.getTiles().getTile(i, j)) +
                                    String.valueOf(tileColorBG(bookshelf.getTile(i, j))) +
                                    "\u001b[1m X " +
                                    ColorCLI.RESET);
                        }
                    } else {
                        System.out.print(tileColorBG(bookshelf.getTile(i, j)) + "   " + ColorCLI.RESET);
                    }
                }
                System.out.println(" " + i);
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
            System.out.println("#rollback ....................... Return to the previous move");
            System.out.println("#chat -hello- ................... Chatting with all players");
            System.out.println("#whisper @username -hello- ...... Chatting with username player");
            System.out.println("#printpersonal .................. Print your personal objective");
            System.out.println("#printcommon .................... Print the common objective for this game");
            System.out.println("#printboard ..................... Print the board");
            System.out.println("#printyourbookshelf ............. Print your bookshelf");
            System.out.println("#printbookshelf @username ....... Print the username's bookshelf");
        }
    }

    //legge da JSON il Common Objective d'interesse
    private void readJSONCO(CommonObjective commonObjective){
        synchronized (cliMain.getLock()) {
            Matrix bookshelf = new Matrix(Define.NUMBEROFROWS_BOOKSHELF.getI(), Define.NUMBEROFCOLUMNS_BOOKSHELF.getI());
            JSONParser jsonParser = new JSONParser();

            try {
                FileReader reader = new FileReader("src/main/java/it/polimi/ingsw/View/CLI/CommonObjective.json");
                Object obj = jsonParser.parse(reader);
                JSONObject co = (JSONObject) obj;
                JSONObject coDetails = new JSONObject();

                //che common objective sei?
                if (commonObjective.getClass().equals(CommonObjective1.class)) {
                    coDetails = (JSONObject) co.get("CO1");
                } else if (commonObjective.getClass().equals(CommonObjective2.class)) {
                    coDetails = (JSONObject) co.get("CO2");
                } else if (commonObjective.getClass().equals(CommonObjective3.class)) {
                    coDetails = (JSONObject) co.get("CO3");
                } else if (commonObjective.getClass().equals(CommonObjective4.class)) {
                    coDetails = (JSONObject) co.get("CO4");
                } else if (commonObjective.getClass().equals(CommonObjective5.class)) {
                    coDetails = (JSONObject) co.get("CO5");
                } else if (commonObjective.getClass().equals(CommonObjective6.class)) {
                    coDetails = (JSONObject) co.get("CO6");
                } else if (commonObjective.getClass().equals(CommonObjective7.class)) {
                    coDetails = (JSONObject) co.get("CO7");
                } else if (commonObjective.getClass().equals(CommonObjective8.class)) {
                    coDetails = (JSONObject) co.get("CO8");
                } else if (commonObjective.getClass().equals(CommonObjective9.class)) {
                    coDetails = (JSONObject) co.get("CO9");
                } else if (commonObjective.getClass().equals(CommonObjective10.class)) {
                    coDetails = (JSONObject) co.get("CO10");
                } else if (commonObjective.getClass().equals(CommonObjective11.class)) {
                    coDetails = (JSONObject) co.get("CO11");
                } else if (commonObjective.getClass().equals(CommonObjective12.class)) {
                    coDetails = (JSONObject) co.get("CO12");
                }

                //salvataggio quantità e tipo
                int quantity = Integer.parseInt(coDetails.get("QUANTITY").toString());
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
                System.out.println("X" + quantity);
                System.out.println(type);
                printBookshelf(bookshelf);

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //stampa i common objective
    public void printCommonObjective(CommonObjective co1, CommonObjective co2){
        synchronized (cliMain.getLock()) {
            System.out.println("COMMON OBJECTIVE 1:");
            readJSONCO(co1);
            System.out.println("\nCOMMONOBJECTIVE 2:");
            readJSONCO(co2);
        }
    }

    //stampa l'errore - c'è un errore
    //RICHIEDE CHE CI SIA VERAMENTE UN ERRORE
    public void printError(Message error){
        synchronized (cliMain.getLock()) {
            System.out.println(error.getUsername());
            if (error.getType()== MessageTypes.NEW_LOBBY){
                cliMain.getReadShell().askNumberOfPlayerMessage();
            }
        }
    }

    //stampa di chi è il turno
    private void printTurn(){
        synchronized (cliMain.getLock()){
            if (cliMain.getClientState().getCurrentPlayer().equals(cliMain.getClientState().getUsername())){
                System.out.println("It is YOUR turn");
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
        printCommonObjective(cliMain.getClientState().getCommonObjectives().get(0), cliMain.getClientState().getCommonObjectives().get(1));
        printAllBookshelf(cliMain.getClientState().getAllBookshelf());
    }

    //è finito il gioco
    public void printEndGame(){
        System.out.println("The Game is ended");
        System.out.println("THE WINNER IS: " + cliMain.getClientState().getWinnerPlayer());
        System.out.println("\n\nALL THE PLAYERS POINTS: ");
        for (int i = 0; i < cliMain.getClientState().getAllPlayerPonits().size(); i++) {
            System.out.println(cliMain.getClientState().getAllUsername().get(i) + ": " + cliMain.getClientState().getAllPlayerPonits().get(i));
        }
    }


}

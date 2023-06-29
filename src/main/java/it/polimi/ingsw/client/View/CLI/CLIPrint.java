package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to print all the game's information on the terminal.
 * <ul>
 *     <li>Help (related to game and to chat);</li>
 *     <li>error;</li>
 *     <li>board;</li>
 *     <li>bookshelf;</li>
 *     <li>personal objective;</li>
 *     <li>common objective;</li>
 *     <li>player's points (public and private);</li>
 *     <li>chair (who is the first player);</li>
 *     <li>order tiles (order of selected tiles - before add to bookshelf);</li>
 *     <li>game status (waiting for other player, start of game, player turn, end game).</li>
 * </ul>
 */
public class CLIPrint  {

    /**
     * Attribute used to retrieve CLI-side all the client information.
     */
    private final CLIMain cliMain;


    /**
     * Initialize cliMain.
     *
     * @param cliMain the cliMain instance that is used to retrieve CLI-side all the client information.
     */
    public CLIPrint(CLIMain cliMain) {
        this.cliMain=cliMain;
    }



    /**
     * Method to return the color of the tile - foreground color.
     *
     * @param tiles the tile that you want to have the character's color.
     * @return the foreground color.
     */
    private ColorCLI tileColor(Tiles tiles){
        synchronized (cliMain.getLock()) {
            return switch (tiles) {
                case GREEN -> ColorCLI.GREEN1;
                case BLUE -> ColorCLI.BLUE1;
                case YELLOW -> ColorCLI.YELLOW1;
                case WHITE -> ColorCLI.WHITE1;
                case PINK -> ColorCLI.PINK1;
                case LIGHT_BLUE -> ColorCLI.LIGHT_BLUE1;
                case NOT_ALLOWED -> ColorCLI.NOTALLOWED;
                case EMPTY -> ColorCLI.EMPTY1;
                case POSITION -> ColorCLI.POSITION;
            };
        }
    }

    /**
     * Method to return the color of the tile - background color.
     *
     * @param tiles the tile that you want to have the background's color.
     * @return the background color.
     */
    private ColorCLI tileColorBG(Tiles tiles){
        synchronized (cliMain.getLock()) {
            return switch (tiles) {
                case GREEN -> ColorCLI.GREENBG1;
                case BLUE -> ColorCLI.BLUEBG1;
                case YELLOW -> ColorCLI.YELLOWBG1;
                case WHITE -> ColorCLI.WHITEBG1;
                case PINK -> ColorCLI.PINKBG1;
                case LIGHT_BLUE -> ColorCLI.LIGHT_BLUEBG1;
                case NOT_ALLOWED -> ColorCLI.NOTALLOWEDBG;
                case EMPTY -> ColorCLI.EMPTYBG1;
                case POSITION -> ColorCLI.POSITIONBG;
            };
        }
    }



    /**
     * Method to print the title of the game.
     */
    private void printTitle(){
        String st2=
                """
                           ▄▄▄▄███▄▄▄▄   ▄██   ▄           ▄████████    ▄█    █▄       ▄████████  ▄█          ▄████████  ▄█     ▄████████\s
                         ▄██▀▀▀███▀▀▀██▄ ███   ██▄        ███    ███   ███    ███     ███    ███ ███         ███    ███ ███    ███    ███\s
                         ███   ███   ███ ███▄▄▄███        ███    █▀    ███    ███     ███    █▀  ███         ███    █▀  ███▌   ███    █▀ \s
                         ███   ███   ███ ▀▀▀▀▀▀███        ███         ▄███▄▄▄▄███▄▄  ▄███▄▄▄     ███        ▄███▄▄▄     ███▌  ▄███▄▄▄    \s
                         ███   ███   ███ ▄██   ███      ▀███████████ ▀▀███▀▀▀▀███▀  ▀▀███▀▀▀     ███       ▀▀███▀▀▀     ███▌ ▀▀███▀▀▀    \s
                         ███   ███   ███ ███   ███               ███   ███    ███     ███    █▄  ███         ███        ███    ███    █▄ \s
                         ███   ███   ███ ███   ███         ▄█    ███   ███    ███     ███    ███ ███▌    ▄   ███        ███    ███    ███\s
                          ▀█   ███   █▀   ▀█████▀        ▄████████▀    ███    █▀      ██████████ █████▄▄██   ███        █▀     ██████████\s
                                                                                                 ▀                                        \
                        """;
        System.out.println(ColorCLI.RED+st2+"\n\n\n\n\n"+ColorCLI.RESET);
    }

    /**
     * Method to print the request's board.
     *
     * @param board the game's board.
     */
    public void printBoard(Matrix board){
        synchronized (cliMain.getLock()) {
            //print the number of the column - upper
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOARD.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println();

            //print the tiles and the number of row at the beginning and at the end of row - right & left
            for (int i = 0; i < Define.NUMBEROFROWS_BOARD.getI(); i++) {
                System.out.print(i + " ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOARD.getI(); j++) {
                    System.out.print(tileColorBG(board.getTile(i, j)) + "   " + ColorCLI.RESET);
                }
                System.out.println(" " + i);
            }

            //print the number of the column - bottom
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOARD.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    /**
     * Method to print the request's bookshelf.
     *
      * @param bookshelf the request's bookshelf.
     */
    public void printBookshelf(Matrix bookshelf){
        synchronized (cliMain.getLock()) {
            //print the number of the column - upper
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }

            //print the tiles
            System.out.println();
            for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
                System.out.print("  ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    System.out.print(tileColorBG(bookshelf.getTile(i, j)) + "   " + ColorCLI.RESET);
                }
                System.out.println();
            }

            //print the number of the column - bottom
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    /**
     * Method to print all the players' bookshelf.
     *
      * @param allMatrix the HashMap that contains all the players' bookshelf and their username.
     */
    public void printAllBookshelf(HashMap<String, Matrix> allMatrix){
        synchronized (cliMain.getLock()){
            //forall username
            for (String key: allMatrix.keySet()){
                //if key==MyUsername
                if (key.equals(cliMain.getClientState().getMyUsername())){
                    System.out.println(ColorCLI.RED + "My Bookshelf: " + ColorCLI.RESET);
                    //print the bookshelf and the personal objective
                    printBookshelfPersonalObjective(allMatrix.get(key), cliMain.getClientState().getMyPersonalObjective());
                }
                else{
                    System.out.println("Bookshelf of: "+key);
                    //print only the bookshelf
                    printBookshelf(allMatrix.get(key));
                }

                System.out.println("\n");
            }
        }
    }

    /**
     * Method to create the personal objective's matrix.
     *
     * @param personalObjective the personal objective to transform in Matrix.
     * @return the personal objective matrix.
     */
    private Matrix personalObjectiveReturn(HashMap<Point, Tiles> personalObjective){
        synchronized (cliMain.getLock()) {
            Matrix bookshelf=new Matrix(Define.NUMBEROFROWS_BOOKSHELF.getI(), Define.NUMBEROFCOLUMNS_BOOKSHELF.getI());
            //forall points
            for (Point key : personalObjective.keySet()){
                //add the color in correct position
                bookshelf.setTile(personalObjective.get(key), key);
            }
            return bookshelf;
        }
    }

    /**
     * Method to print the personal objective.
     *
     * @param personalObjective the personal objective.
     */
    public void printPersonalObjective(HashMap<Point, Tiles> personalObjective){
        synchronized (cliMain.getLock()) {
            //Matrix of personal objective
            Matrix bookshelf = personalObjectiveReturn(personalObjective);

            //print the number of the column - upper
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }

            //print the color in the correct position
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
            //print the number of the column - bottom
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    /**
     * Method to print the bookshelf and the personal objective together.
     *
     * @param bookshelf the client's bookshelf.
     * @param personalObjective the client's personal objective.
     */
    public void printBookshelfPersonalObjective(Matrix bookshelf, HashMap<Point, Tiles> personalObjective){
        synchronized (cliMain.getLock()) {
            //Matrix of personal objective
            Matrix bookshelfPO = personalObjectiveReturn(personalObjective);

            //print the number of the column - upper
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }

            //print bookshelf's tile and the personal objective position
            System.out.println();
            for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
                System.out.print("  ");
                for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                    //position of personal objective
                    if (personalObjective.containsKey(new Point(i, j))) {
                        //bookshelf's tile==personal objective's color
                        if (personalObjective.get(new Point(i, j))
                                .equals(bookshelf.getTile(new Point(i, j)))) {
                            //print the tile's color and the personal objective V - corrected point
                            System.out.print(tileColor(bookshelfPO.getTile(i, j)) +
                                    String.valueOf(tileColorBG(bookshelf.getTile(i, j))) +
                                    "\u001b[30m V " +
                                    ColorCLI.RESET);
                        }
                        else {
                            //print the tile's color and the personal objective color X - uncorrected point
                            System.out.print(tileColor(bookshelfPO.getTile(i, j)) +
                                    String.valueOf(tileColorBG(bookshelf.getTile(i, j))) +
                                    "\u001b[1m X " +
                                    ColorCLI.RESET);
                        }
                    }
                    else {
                        //print the tile's color
                        System.out.print(tileColorBG(bookshelf.getTile(i, j)) + "   " + ColorCLI.RESET);
                    }
                }
                System.out.println();
            }

            //print the number of the column - bottom
            System.out.print("  ");
            for (int i = 0; i < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); i++) {
                System.out.print(" " + i + " ");
            }
            System.out.println("\n");
        }
    }

    /**
     * Method to print game's help, it prints all the correct command structure.
      */
    public void help(){
        synchronized (cliMain.getLock()) {
            System.out.println("All command, WITH EXAMPLE, are:");
            System.out.println("#quit or #q ..................... Quit the game");
            System.out.println("#remove (0,0), (0,0), (0,0) ..... It is also possible not to fill all the relatives");
            System.out.println("#switch 3, 2, 1 ................. Switch the order of the selected tiles");
            System.out.println("#add 0 .......................... Add the tiles in the column of the bookshelf");
            System.out.println("#chat -hello- ................... Chatting with all players");
            System.out.println("#printpersonal .................. Print your personal objective");
            System.out.println("#printcommon .................... Print the common objective for this game");
            System.out.println("#printboard ..................... Print the board");
            System.out.println("#printmybookshelf ............... Print my bookshelf");
            System.out.println("#printbookshelf @username ....... Print the username's bookshelf");
            System.out.println("#printpoints .................... Print all public points");
            System.out.println("#printmypoint ................... Print my points");
            System.out.println("#printchair ..................... Print who has the chair - who is the first player?");
            System.out.println("\n");
        }
    }

    /**
     * Method to print chat's help, it prints all the correct command structure.
     */
    public void helpForChat () {
        System.out.println("All command, WITH EXAMPLE, are:");
        System.out.println("#exit ........................... To leave the chat and return to the game");
        System.out.println("#quit or #q ..................... Quit the game");
        System.out.println("#switchToPublic ................. To switch from a private chat to the public one");
        System.out.println("#switchToPrivate ................ To switch from the public chat to a private one");
        System.out.println("#printPublicChat ................ Print the public chat (only view)");
        System.out.println("#printPrivateChat ............... Print a private chat (only view)");
        System.out.println("\n");
    }

    /**
     * Method to read from JSON the interest's common objective and print it.
     *
     * @param commonObjective the number of common objective.
     */
    private void readJSONCO(Integer commonObjective){
        synchronized (cliMain.getLock()) {
            Matrix bookshelf = new Matrix(Define.NUMBEROFROWS_BOOKSHELF.getI(), Define.NUMBEROFCOLUMNS_BOOKSHELF.getI());
            JSONParser jsonParser = new JSONParser();

            try {
                InputStream is=this.getClass().getClassLoader().getResourceAsStream("CommonObjective.json");
                assert is != null;
                Object obj = jsonParser.parse(new InputStreamReader(is));
                JSONObject co = (JSONObject) obj;
                JSONObject coDetails = new JSONObject();

                //which common objective are you?
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

                //save the description of common objective
                String type = coDetails.get("TYPE").toString();

                JSONArray coPosition = (JSONArray) coDetails.get("POSITION");
                ArrayList<Point> points = new ArrayList<>();

                //save the position to show of common objective
                for (Object o : coPosition) {
                    JSONArray coPoint = (JSONArray) o;
                    int x = Integer.parseInt(coPoint.get(0).toString());
                    int y = Integer.parseInt(coPoint.get(1).toString());
                    points.add(new Point(x, y));
                }

                //insert the position in Matrix
                for (Point point : points) {
                    bookshelf.setTile(Tiles.POSITION, point);
                }

                //print the common objective
                System.out.println(type);
                printBookshelf(bookshelf);

            } catch (IOException | ParseException e) {
                System.out.println("Open Json error");
            }
        }
    }

    /**
     * Method to print all the common objective.
     *
     * @param commonObjective the ArrayList of all common objective.
     */
    public void printCommonObjective(ArrayList<Integer> commonObjective){
        synchronized (cliMain.getLock()) {
            System.out.println("\nCOMMON OBJECTIVE 1:");
            readJSONCO(commonObjective.get(0));
            System.out.println("\nCOMMON OBJECTIVE 2:");
            readJSONCO(commonObjective.get(1));
        }
    }

    /**
     * Method to print all public points.
     *
     * @param publicPoints the HashMap with all public points.
     */
    public void printPoints(HashMap<String, Integer> publicPoints){
        System.out.println("All Public Points: ");

        //forall username
        for (String key: publicPoints.keySet()){
            //if key==MyUsername
            if (key.equals(cliMain.getClientState().getMyUsername())){
                System.out.println(ColorCLI.RED + "my points: "+publicPoints.get(key)+ ColorCLI.RESET);
            }
            else{
                System.out.println(key+": "+publicPoints.get(key));
            }
        }
        System.out.println("\n");
    }

    /**
     * Method to print my points - public points + personal objective points.
     *
     * @param point the client's points (public points + personal objective points).
     */
    public void printMyPoints(Integer point){
        System.out.println("My Points: "+point);
    }

    /**
     * Method to print the player that has the chair - who is the first player to start the game.
     */
    public void printChair(){
        synchronized (cliMain.getLock()){
            System.out.println("The first player, that has chair, is: " +
                               cliMain.getClientState().getChair());

            //wait before you move on - only graphics idea
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Sleep error");
            }
            System.out.println("\n");
        }
    }

    /**
     * Method to print the order of selected tiles.
     *
     * @param order the ArrayList of tiles removed from the board.
     */
    public void printOrderTiles(ArrayList<Tile> order){
        clearShell();
        System.out.print("Selected tiles: ");
        //print the order of tiles
        for (Tile tiles : order) {
            System.out.print(tileColorBG(tiles.getTiles()) + "   " + ColorCLI.RESET + " ");
        }
        System.out.println("\n");
    }

    /**
     * Method to print error - the server send an error message.
     *
     * @param error the error to show received from server.
     */
    public void printError(String error){
        synchronized (cliMain.getLock()) {
            System.out.println(error);
            System.out.println("\n");
        }
    }

    /**
     * Method to print waiting other player.
     */
    public void printWaiting(){
            System.out.println("Waiting for the other players...");
    }

    /**
     * Method to print that the number of players has been reached.
     */
    public void gameHasStarted(){
        synchronized (cliMain.getLock()){
            System.out.println("The number of players has been reached \n\n\n");
            printTitle();
        }
    }

    /**
     * Method to print the turn.
     * <p>
     * The username of the current player.
     */
    private void printTurn(){
        synchronized (cliMain.getLock()){
            //if is my turn
            if (cliMain.getClientState().getCurrentPlayer().equals(cliMain.getClientState().getMyUsername())){
                System.out.println(ColorCLI.RED + String.valueOf(ColorCLI.BOLD) + "It is YOUR turn"+ ColorCLI.RESET);
            }
            else {
                System.out.println("It is " + cliMain.getClientState().getCurrentPlayer() + " turn");
            }
        }
    }

    /**
     * Method to print the player's turn.
     * <p>
     * All information:
     * <ul>
     *     <li>username of the current player;</li>
     *     <li>print board;</li>
     *     <li>print all bookshelf;</li>
     *     <li>print all points;</li>
     * </ul>
     */
    public void playerTurn(){
        printTurn();
        printBoard(cliMain.getClientState().getBoard());
        printAllBookshelf(cliMain.getClientState().getAllBookshelf());
        printPoints(cliMain.getClientState().getAllPublicPoints());
    }

    /**
     * Method to print the end game.
     * <p>
     * All information:
     * <ul>
     *     <li>winner player;</li>
     *     <li>all player points.</li>
     * </ul>
     */
    public void printEndGame(){
        System.out.println("The Game is ended");
        System.out.println("THE WINNER IS: " + cliMain.getClientState().getWinnerPlayer());
        System.out.println("\n\nALL THE PLAYERS POINTS: ");
        for (String key: cliMain.getClientState().getAllPublicPoints().keySet()){
            System.out.println(key+": "+cliMain.getClientState().getAllPublicPoints().get(key));
        }
    }

    /**
     * Method to clear the shell.
     */
    public void clearShell()  {
        System.out.print(ColorCLI.CLEAR);
        System.out.flush();

    }

    /**
     * <p>
     *     Method that prints the history of the publicChat.
     * </p>
     * <p>
     *     Using the boolean @printAndChat it prints
     *     different outputs depending if the method
     *     gets called while the Chat is enable or not.
     * </p>
     *
     * @param printAndChat      Boolean used to activate different code's part via if-else.
     */
    public void printChat (boolean printAndChat) {
        ChatController chat = cliMain.getClientState().getChatController();
        chat.getPublicChat().resetUnReadMessages();

        if (!printAndChat) System.out.println("\n");

        System.out.println(ColorCLI.BOLD + "PUBLIC CHAT" + ColorCLI.RESET + ": _________________________________________________");

        if (printAndChat) {
            System.out.println("use the command #help or #h to display chat's commands");
        }

        if (chat.getPublicChat().getChatMessages().isEmpty()) {
            System.out.println(ColorCLI.REVERSED + "There are no previous messages :(" + ColorCLI.RESET);

            if (!printAndChat) {
                System.out.print("_________________________________________________________");
                System.out.println("\n");

                System.out.println(ColorCLI.CLEAR);
                System.out.flush();
            }
            return;
        }

        for (int i = chat.getPublicChat().getOldestMessage(); i>=0; i--){
            if (chat.getPublicChat().getChatMessages().get(i).getText().equals(cliMain.getClientState().getMyUsername())) {
                System.out.println(ColorCLI.RED + "me: " + ColorCLI.RESET + chat.getPublicChat().getChatMessages().get(i).getMessage());
            }
            else System.out.println(ColorCLI.UNDERLINE + chat.getPublicChat().getChatMessages().get(i).getText() + ColorCLI.RESET + ": " + chat.getPublicChat().getChatMessages().get(i).getMessage());
        }

        if (!printAndChat) {
            System.out.print("______________________________________________________________");
            System.out.println("\n");

            System.out.println(ColorCLI.CLEAR);
            System.out.flush();
        }
    }

    /**
     * <p>
     *     Method that prints the history of a privateChat.
     * </p>
     * <p>
     *     Prints the history the chat's history that the player
     *     who called the method has with username.
     * </p>
     * <p>
     *     Using the boolean printAndChat it prints
     *     different outputs depending if the method
     *     gets called while the Chat is enable or not.
     * </p>
     *
     * @param username      Player to chat with.
     * @param printAndChat      Boolean used to activate different code's part via if-else.
     */
    public void printChat (String username, boolean printAndChat) {
        ChatController chat = cliMain.getClientState().getChatController();
        chat.getPrivateChat(username).resetUnReadMessages();

        if (!printAndChat) System.out.println("\n");

        System.out.println(ColorCLI.BOLD + "PRIVATE CHAT" + ColorCLI.RESET + " with " + ColorCLI.UNDERLINE + username + ColorCLI.RESET + ": _________________________________________");

        if (printAndChat) {
            System.out.println("use the command #help or #h to display chat's commands");
        }

        if (chat.getPrivateChat(username).getChatMessages().isEmpty()) {
            System.out.println(ColorCLI.REVERSED + "There are no previous messages :(" + ColorCLI.RESET);

            if (!printAndChat) {
                System.out.print("_______________________________________________________");
                System.out.println("\n");

                System.out.println(ColorCLI.CLEAR);
                System.out.flush();
            }
            return;
        }

        for (int i = chat.getPrivateChat(username).getOldestMessage(); i>=0; i--){
            if (chat.getPrivateChat(username).getChatMessages().get(i).getText().equals(cliMain.getClientState().getMyUsername())) {
                System.out.println(ColorCLI.RED + "me: " + ColorCLI.RESET + chat.getPrivateChat(username).getChatMessages().get(i).getMessage());
            }
            else System.out.println(ColorCLI.UNDERLINE + chat.getPrivateChat(username).getChatMessages().get(i).getText() + ColorCLI.RESET + ": " + chat.getPrivateChat(username).getChatMessages().get(i).getMessage());
        }

        if (!printAndChat) {
            System.out.print("___________________________________________________");
            System.out.println("\n");

            System.out.println(ColorCLI.CLEAR);
            System.out.flush();
        }
    }

    /**
     * Method to print notification for disconnection and reconnection.
     *
     * @param username username of the player reconnected/disconnected.
     * @param disconnection notification of disconnection or reconnection.
     */
    public void printDisconnection(String username, String disconnection){
        System.out.println(disconnection.equals("disconnection")?
                (username+" has disconnected"):
                (username+" has reconnected"));
    }
}

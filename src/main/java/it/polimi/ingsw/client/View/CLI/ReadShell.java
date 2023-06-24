package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.Messages.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to read all the strings that the user enters from the command line.
 * It reads and interprets the input string, if it is a valid command creates the corresponding messages to send to the server and sends it.
 */
public class ReadShell extends Thread {

    /**
     * Attribute used to retrieve CLI-side all the client information.
     */
    private final CLIMain cliMain;



    /**
     * Initialize cliMain
     *
     * @param cliMain the cliMain instance that is used to retrieve CLI-side all the client information.
     */
    public ReadShell(CLIMain cliMain) {
        this.cliMain = cliMain;
    }



    /**
     * Method to run the ReadShell.
     */
    @Override
    public void run() {
        //Readshell must continue reading ommand until the game is finished
        while (cliMain.getClientState().isGameIsEnded()) {
            readCommand();
        }
    }



    /**
     * Method to read on standard input.
     *
     * @return the string that is on the standard input.
     */
    private String readLine() {
        //instantiate Scanner that reads from standard input
        Scanner scanner = new Scanner(System.in);
        //string that read from standard input
        String word;
        //wait for data input and reads them
        word = String.valueOf(scanner.nextLine());
        cliMain.getCliPrint().clearShell();
        return word;
    }

    /**
     * Method to read the number on standard input.
     *
     * @return the number that is on the standard input.
     */
    private Integer readNumber(){
        //instantiate Scanner that reads from standard input
        Scanner scanner = new Scanner(System.in);
        //the return value
        int integer;
        //string that read from standard input
        String word;

        //wait for data input and reads them
        word=String.valueOf(scanner.nextLine());
        //if is insert the quit command
        if (word.equalsIgnoreCase("#quit") || word.equalsIgnoreCase("#q")){
            cliMain.closeClient();
        }

        //parse to int
        try{
            integer=Integer.parseInt(word);
        }
        catch (NumberFormatException e){
            return -1;
        }

        cliMain.getCliPrint().clearShell();
        return integer;
    }

    /**
     * Method to read all the numbers in the input string.
     * <p>
     * ONLY READS THE DIGIT (for example, if in the input string there is 12, this method return 1 and 2 and NOT 12).
     *
     * @param s the input string that we want to know which numbers it contains.
     * @return the ArrayList that contains all the number in the string
     */
    private ArrayList<Integer> readNumber(String s) {
        ArrayList<Integer> result = new ArrayList<>();

        //read only the figures in the input string
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            result.add(Integer.valueOf(matcher.group()));
        }

        return result;
    }

    /**
     * Method to read user command:
     * <ul>
     *     <li>read command;</li>
     *     <li>create the correct message;</li>
     *     <li>send the message to server.</li>
     * </ul>
     */
    public void readCommand() {
        //read the user command from standard input
        String st = readLine();
        st = st + ' ';
        st = st.toLowerCase();
        //read the number in the command
        ArrayList<Integer> number = readNumber(st);
        int n = st.indexOf(" ");
        String substring = st.substring(0, n);
        //base on the command insert, it creates the correct message to send at the server
        switch (substring) {
            //#remove command
            case "#remove" -> {
                //if the user insert to many or to fewer numbers - print error
                if (number.size() != 2 && number.size() != 4 && number.size() != 6) {
                    System.out.println(st + " is NOT a valid command \nIf you need help put #help or #h");
                    break;
                }
                //create the correct message
                createRemoveMessage(number);
            }
            //#switch command
            case "#switch" -> {
                //if the user insert to many or to fewer numbers - print error
                if (number.size() < 1 || number.size() > 3) {
                    System.out.println(st + ": Type of command correct but data entered wrong \nIf you need help put #help or #h");
                    break;
                }
                //create the correct message
                createSwitchMessage(number);
            }
            //#add command
            case "#add" -> {
                //if the user insert to many or to fewer numbers - print error
                if (number.size() != 1) {
                    System.out.println(st + ": Type of command correct but data entered wrong \nIf you need help put #help or #h");
                    break;
                }
                //create the correct message
                createAddMessage(number);
            }
            //#help command
            case "#help", "#h" ->
                //print help's method - print all the correct command structure
                cliMain.getCliPrint().help();
            //#printpersonal command
            case "#printpersonal" ->
                //print my personal objective
                    cliMain.getCliPrint().printPersonalObjective(cliMain.getClientState().getMyPersonalObjective());
            //#printboard command
            case "#printboard" ->
                //print the game's board
                cliMain.getCliPrint().printBoard(cliMain.getClientState().getBoard());
            //#printmybookshelf command
            case "#printmybookshelf" ->
                //print my bookshelf
                cliMain.getCliPrint().printBookshelf(cliMain.getClientState().getMyBookshelf());
            //#printbookshelf
            case "#printbookshelf" -> {
                //find the player whose bookshelf you want to see
                int indice = st.indexOf("@");
                //if you do not insert the username - return error
                if (indice == -1) {
                    System.out.println(st + ": Type of command correct but you do NOT inserted the username of player \nIf you need help put #help or #h");
                    break;
                }
                //username of the player whose bookshelf you want to see
                String sub = st.substring(indice + 1, st.length() - 1);

                //find the position in AllUsername ArrayList
                int position = cliMain.getClientState().getAllUsername().indexOf(sub);
                //if the username is not in AllUsername ArrayList
                if (position == -1) {
                    System.out.println(st + ": Type of command correct but you do NOT inserted the username of a player in this game \nIf you need help put #help or #h");
                    break;
                }
                //print the request player's bookshelf
                cliMain.getCliPrint().printBookshelf(cliMain.getClientState().getAllBookshelf().get(sub));
            }
            //#printcommon command
            case "#printcommon" ->
                //print all game's common objective
                cliMain.getCliPrint().printCommonObjective(cliMain.getClientState().getGameCommonObjective());
            //#printpoints command
            case "#printpoints" ->
                //print all player points
                cliMain.getCliPrint().printPoints(cliMain.getClientState().getAllPublicPoints());
            //#printmypoint command
            case "#printmypoint" ->
                //print my private points - public points + personal objective's points
                cliMain.getCliPrint().printMyPoints(cliMain.getClientState().getMyPoints());
            //#printchair command
            case "#printchair" ->
                //print who is the first player to start the game
                cliMain.getCliPrint().printChair();
            //#chat command
            case "#chat" ->{
                //enter in public chat and print it
                cliMain.setOpenChat(true);
                cliMain.getChatHandler().settingForPublicChat();
            }
            //#privatechat command
            case "#privatechat" -> {
                //enter in private chat and print it
                cliMain.setOpenChat(true);
                cliMain.getChatHandler().settingForPrivateChat();
            }
            //#quit command
            case "#quit", "#q" ->
                //close the client on CLI
                cliMain.close();
            default ->
                //you do not insert a valid command
                System.out.println(st + " is NOT a valid command \nIf you need help put #help or #h");
        }

    }

    /**
     * Method to request the username at game beginning.
     */
    public void askUsername() {
        Message message = new Message();
        String nickname;

        //request the username until username!=empty
        do {
            System.out.print("Enter your username: ");
            nickname = readLine();
            if (nickname.equalsIgnoreCase("#quit") || nickname.equalsIgnoreCase("#q")){
                cliMain.closeClient();
            }
        }while (nickname.isEmpty());

        //set the username in ClientState
        cliMain.getClientState().setMyUsername(nickname);
        //set the username's message
        message.setText(nickname);
        message.setType(MessageTypes.USERNAME);

        //send message
        sendMessage(message);
    }

    /**
     * Method to request the number of players.
     * <p>
     * If and only if the server requires it.
     */
    public void askNumberOfPlayerMessage() {
        IntMessage message = new IntMessage();
        int num;

        do{
            System.out.print("Enter number of player: ");
            num=readNumber();
        }while(num==-1);

        //set the number of players' message
        message.setText(cliMain.getClientState().getMyUsername());
        message.setNum(num);
        message.setType(MessageTypes.NUM_OF_PLAYERS);

        //send message
        sendMessage(message);
    }



    /**
     * Method to create the remove tiles from board's message - remove tiles from board.
     *
     * @param input the list of int - then the method transforms it in Points.
     */
    private void createRemoveMessage(ArrayList<Integer> input) {
        PointsMessage pointsMessage = new PointsMessage();
        ArrayList<Point> result = new ArrayList<>();

        //create the ArrayList of Point
        int i = 0;
        while (i < input.size()) {
            int x = input.get(i);
            i = i + 1;
            int y = input.get(i);
            i = i + 1;
            result.add(new Point(x, y));
        }

        //setting correct the message
        pointsMessage.setText(cliMain.getClientState().getMyUsername());
        //message type=REMOVE_FROM_BOARD
        pointsMessage.setType(MessageTypes.REMOVE_FROM_BOARD);
        pointsMessage.setTiles(result);

        //send the message
        sendMessage(pointsMessage);
    }

    /**
     * Method to create the switch tiles' message - switch selected tiles.
     *
     * @param input the list of int - order of switching tiles.
     */
    private void createSwitchMessage(ArrayList<Integer> input) {
        IntArrayMessage intArrayMessage = new IntArrayMessage();

        //setting the correct message
        intArrayMessage.setText(cliMain.getClientState().getMyUsername());
        //message type=SWITCH_PLACE
        intArrayMessage.setType(MessageTypes.SWITCH_PLACE);
        intArrayMessage.setIntegers(input);

        //send the message
        sendMessage(intArrayMessage);
    }

    /**
     * Method to create the add to bookshelf's message - add to bookshelf's column
     *
     * @param input the list of int, it contains only 1 int.
     */
    private void createAddMessage(ArrayList<Integer> input) {
        IntMessage intMessage = new IntMessage();

        //setting the correct message
        intMessage.setText(cliMain.getClientState().getMyUsername());
        //message type=ADD_TO_BOOKSHELF
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(input.get(0));

        //send the message
        sendMessage(intMessage);
    }



    /**
     * Method to send all possible message to Networker, and then it sends them to Server.
     *
     * @param message the sending message.
     */
    public void sendMessage(Message message) {
        //call the correct Networker's method
        switch (message.getType()) {
            case REMOVE_FROM_BOARD -> cliMain.getNet().removeTilesFromBoard(message);
            case SWITCH_PLACE -> cliMain.getNet().switchTilesOrder(message);
            case ADD_TO_BOOKSHELF -> cliMain.getNet().addTilesToBookshelf(message);
            case USERNAME -> cliMain.getNet().firstConnection(message);
            case NUM_OF_PLAYERS -> cliMain.getNet().numberOfPlayersSelection(message);
            case CHAT -> cliMain.getNet().chat(message);
        }
    }

}
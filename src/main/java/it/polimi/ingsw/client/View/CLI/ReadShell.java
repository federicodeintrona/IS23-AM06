package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.utils.Messages.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadShell extends Thread{


    private final CLIMain cliMain;


    public ReadShell(CLIMain cliMain) {
        this.cliMain=cliMain;
    }






    //pulisce CLI
    public void clearCLI(){
        System.out.println(ColorCLI.CLEAR);
        System.out.flush();
    }

    //leggere da stdIN
    private String readLine() {
        //istanzia scanner che legge da stdIN
        Scanner scanner=new Scanner(System.in);
        //stringhe lette da stdIN
        String word;
        //aspetta emissione dati e li legge
        word= String.valueOf(scanner.nextLine());
        clearCLI();
        return word;
    }

    //legge solo i numeri presenti nella stringa
    //I NUMERI SONO DA 0-9
    private ArrayList<Integer> readNumber(String s){
        ArrayList<Integer> result=new ArrayList<>();

        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher= pattern.matcher(s);
        while(matcher.find()){
            result.add(Integer.valueOf(matcher.group()));
        }

        return result;
    }

    //richista nickname
    public void askUsername() {
        Message message=new Message();

        System.out.print("Enter your nickname: ");
        String nickname = readLine();
        cliMain.getClientState().setMyUsername(nickname);
        message.setUsername(nickname);
        message.setType(MessageTypes.USERNAME);
        sendMessage(message);
    }

    //legge messaggi, li crea, li invia a chi di dovere
    public void readCommand(){

        String st=readLine();
        st=st+' ';
        st=st.toLowerCase();
        ArrayList<Integer> number=readNumber(st);
        int n=st.indexOf(" ");
        String substring=st.substring(0, n);
        switch (substring) {
            case "#remove" -> {
                if (number.size()!=2 && number.size()!=4 && number.size()!=6){
                    System.out.println(st + " is NOT a valid command \nIf you need help put #help or #h");
                    break;
                }
                createRemoveMessage(number);
            }
            case "#switch" -> {
                if (number.size()<1 || number.size()>3){
                    System.out.println(st+": Type of command correct but data entered wrong \nIf you need help put #help or #h");
                    break;
                }
                createSwitchMessage(number);
            }
            case "#add" -> {
                if (number.size()!=1){
                    System.out.println(st+": Type of command correct but data entered wrong \nIf you need help put #help or #h");
                    break;
                }
                createAddMessage(number);
            }
//            case "#rollback" -> createRollbackMessage();
//            case "#chat" -> System.out.println("#chat -hello- ................... Chatting with all players");
//            case "#whisper" -> System.out.println("#whisper @username -hello- ...... Chatting with username player");
            case "#help", "#h" -> cliMain.getCliPrint().help();
            case "#printpersonal" -> cliMain.getCliPrint().printPersonalObjective(cliMain.getClientState().getMyPersonalObjective());
            case "#printboard" -> cliMain.getCliPrint().printBoard(cliMain.getClientState().getBoard());
            case "#printmybookshelf" -> {
                //todo QUALE STAMPO???
                cliMain.getCliPrint().printBookshelf(cliMain.getClientState().getMyBookshelf());
//                cliMain.getCliPrint().printBookshelfPersonalObjective(cliMain.getClientState().getMyBookshelf(), cliMain.getClientState().getMyPersonalObjective());
            }
            case "#printbookshelf" -> {
                int indice=st.indexOf("@");
                System.out.println(indice);
                if (indice==-1){
                    System.out.println(st+": Type of command correct but you do NOT inserted the username of player \nIf you need help put #help or #h");
                    break;
                }
                String sub=st.substring(indice+1, st.length()-1);
                System.out.println(sub);

                //posizione dell'username desiderato
                int position=cliMain.getClientState().getAllUsername().indexOf(sub);
                if (position==-1){
                    System.out.println(st+": Type of command correct but you do NOT inserted the username of a player in this game \nIf you need help put #help or #h");
                    break;
                }
                cliMain.getCliPrint().printBookshelf(cliMain.getClientState().getAllBookshelf().get(sub));
            }
            case "#printcommon" -> cliMain.getCliPrint().printCommonObjective(cliMain.getClientState().getGameCommonObjective());
            case "#printpoints" -> cliMain.getCliPrint().printPoints(cliMain.getClientState().getAllPublicPoints());
            case "#printmypoint" -> cliMain.getCliPrint().printMyPoints(cliMain.getClientState().getMyPoints());
            case "#printchair" -> cliMain.getCliPrint().printChair();
            case "#chat" -> createChatMessage(readLine());
            default -> System.out.println(st + " is NOT a valid command \nIf you need help put #help or #h");
        }

    }

    //crea il messaggio Remove Tiles dall board
    private void createRemoveMessage(ArrayList<Integer> input){
        PointsMessage pointsMessage=new PointsMessage();
        ArrayList<Point> result=new ArrayList<>();

        //crea l'ArrayList di Point
        int i=0;
        while (i< input.size()){
            int x=input.get(i);
            i=i+1;
            int y=input.get(i);
            i=i+1;
            result.add(new Point(x,y));
        }

        //setta il messaggio
        pointsMessage.setUsername(cliMain.getClientState().getMyUsername());
        pointsMessage.setType(MessageTypes.REMOVE_FROM_BOARD);
        pointsMessage.setTiles(result);

        //invia il messaggio
        sendMessage(pointsMessage);
    }

    //crea il messaggio Switch Tiles
    private void createSwitchMessage(ArrayList<Integer> input){
        IntArrayMessage intArrayMessage=new IntArrayMessage();

        //setto il messaggio
        intArrayMessage.setUsername(cliMain.getClientState().getMyUsername());
        intArrayMessage.setType(MessageTypes.SWITCH_PLACE);
        intArrayMessage.setIntegers(input);

        //invia il messaggio
        sendMessage(intArrayMessage);
    }

    //crea il messaggio Add to bookshelf
    private void createAddMessage(ArrayList<Integer> input){
        IntMessage intMessage=new IntMessage();

        //setta il messaggio
        intMessage.setUsername(cliMain.getClientState().getMyUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(input.get(0));

        //invia il messaggio
        sendMessage(intMessage);
    }

    //crea il messaggio di Rollback
    private void createRollbackMessage(){
        Message message=new Message();

        //setta il messaggio
        message.setUsername(cliMain.getClientState().getMyUsername());
        message.setType(MessageTypes.ROLLBACK);

        //invia il messaggio
        sendMessage(message);
    }

    private void createChatMessage (String string){
        Message message = new Message();

        System.out.println("il messaggio è:" + string);
        // Setting the message
        message.setUsername(string);
        message.setType(MessageTypes.CHAT);

        // Sending the message
        sendMessage(message);
    }


    //invia messaggi a Networker
    private void sendMessage(Message message){
        switch (message.getType()){
            case REMOVE_FROM_BOARD -> cliMain.getNet().removeTilesFromBoard(message);
            case SWITCH_PLACE -> cliMain.getNet().switchTilesOrder(message);
            case ADD_TO_BOOKSHELF -> cliMain.getNet().addTilesToBookshelf(message);
            //MANCA MESSAGGIO DI ROLLBACK DA PARTE DEL NETWORKER
//            case ROLLBACK -> net.rollback(message);
            case USERNAME -> cliMain.getNet().firstConnection(message);
            case NUM_OF_PLAYERS -> cliMain.getNet().numberOfPlayersSelection(message);
            case CHAT -> cliMain.getNet().chat(message);
        }
    }

    //richiede il numero di giocatori con cui giocare
    public void askNumberOfPlayerMessage(){
        IntMessage message=new IntMessage();

        System.out.print("Enter number of player: ");
        String num = readLine();

        //setta il messaggio
        message.setUsername(cliMain.getClientState().getMyUsername());
        message.setNum(Integer.parseInt(num));
        message.setType(MessageTypes.NUM_OF_PLAYERS);

        //invia il messaggio
        sendMessage(message);
    }



    @Override
    public void run() {
        while(!cliMain.getClientState().isGameIsEnded()){
            readCommand();
        }
    }
}

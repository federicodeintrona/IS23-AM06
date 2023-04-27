package it.polimi.ingsw.client.View;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.server.CommonObjective.*;
import it.polimi.ingsw.server.Messages.*;
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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadThread extends Thread{


    private MainThread mt;

    public MainThread getMt() {
        return mt;
    }

    public void setMt(MainThread mt) {
        this.mt = mt;
    }


    //LEGGI, RICEVI E INVIA MESSAGGI
    //pulisce CLI
    public static void clearCLI(){
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
    public Message askUsername() {
        Message message=new Message();

        System.out.print("Enter your nickname: ");
        String nickname = readLine();
        message.setUsername(nickname);
        message.setType(MessageTypes.USERNAME);
        mt.setUsername(nickname);

        return message;
    }

    //legge messaggi, li crea, li invia a chi di dovere
    public void readMessage(){
        String st=readLine();
        ArrayList<Integer> number=readNumber(st);
        switch (st) {
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
            case "#rollback" -> createRollbackMessage();
//            case "#chat" -> System.out.println("#chat -hello- ................... Chatting with all players");
//            case "#whisper" -> System.out.println("#whisper @username -hello- ...... Chatting with username player");
            case "#help", "#h" -> mt.getPt().help();
            case "#printpersonal" -> mt.getPt().printPersonalObjective(mt.getMyPO());
            case "#printboard" -> mt.getPt().printBoard(mt.getBoard());
            case "#printyourbookshelf" -> {
                //todo QUALE STAMPO???
                mt.getPt().printBookshelf(mt.getMyBookshelf());
                mt.getPt().printBookshelfPersonalObjective(mt.getMyBookshelf(), mt.getMyPO());
            }
            case "#printbookshelf" -> {
                int i=st.indexOf("@");
                if (i==-1){
                    System.out.println(st+": Type of command correct but you do NOT inserted the username of player \nIf you need help put #help or #h");
                    break;
                }
                String sub=st.substring(i+1);

                //posizione dell'username desiderato
                int position=mt.getAllUsername().indexOf(sub);
                if (position==-1){
                    System.out.println(st+": Type of command correct but you do NOT inserted the username of a player in this game \nIf you need help put #help or #h");
                    break;
                }
                mt.getPt().printBookshelf(mt.getAllBookshelf().get(position));
            }
            case "#printcommon" -> mt.getPt().printCommonObjective(mt.getCommonObjectives().get(0), mt.getCommonObjectives().get(1));
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
        pointsMessage.setUsername(mt.getUsername());
        pointsMessage.setType(MessageTypes.REMOVE_FROM_BOARD);
        pointsMessage.setTiles(result);

        //invia il messaggio
        sendMessage(pointsMessage);
    }

    //crea il messaggio Switch Tiles
    private void createSwitchMessage(ArrayList<Integer> input){
        IntArrayMessage intArrayMessage=new IntArrayMessage();

        //setto il messaggio
        intArrayMessage.setUsername(mt.getUsername());
        intArrayMessage.setType(MessageTypes.SWITCH_PLACE);
        intArrayMessage.setIntegers(input);

        //invia il messaggio
        sendMessage(intArrayMessage);
    }

    //crea il messaggio Add to bookshelf
    private void createAddMessage(ArrayList<Integer> input){
        IntMessage intMessage=new IntMessage();

        //setta il messaggio
        intMessage.setUsername(mt.getUsername());
        intMessage.setType(MessageTypes.ADD_TO_BOOKSHELF);
        intMessage.setNum(input.get(0));

        //invia il messaggio
        sendMessage(intMessage);
    }

    //crea il messaggio di Rollback
    private void createRollbackMessage(){
        Message message=new Message();

        //setta il messaggio
        message.setUsername(mt.getUsername());
        message.setType(MessageTypes.ROLLBACK);

        //invia il messaggio
        sendMessage(message);
    }


    //invia messaggi a Networker
    public void sendMessage(Message message){
        switch (message.getType()){
            case REMOVE_FROM_BOARD -> mt.getNet().removeTilesFromBoard(message);
            case SWITCH_PLACE -> mt.getNet().switchTilesOrder(message);
            case ADD_TO_BOOKSHELF -> mt.getNet().addTilesToBookshelf(message);
            //todo MANCA MESSAGGIO DI ROLLBACK DA PARTE DEL NETWORKER
//            case ROLLBACK -> net.rollback(message);
        }
    }


    @Override
    public void run() {
        //leggere da stdIN
    }
}

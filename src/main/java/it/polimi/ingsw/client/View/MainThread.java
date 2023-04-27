package it.polimi.ingsw.client.View;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.NetworkerRmi;
import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;

import java.util.ArrayList;

public class MainThread {

    private final Networker net; //a chi mandare messaggi
    private String username; //username del mio utente

    private Bookshelf myBookshelf; //mia bookshelf
    private ArrayList<Bookshelf> allBookshelf; //tutte le bookshelf - le posizioni sono uguali alle posizioni di allUsername
    private Board board; //la board di gioco
    private ArrayList<String> allUsername; //tutti gli username
    private PersonalObjective myPO; //il mio personal objective
    private ArrayList<CommonObjective> commonObjectives; //i common objective

    private PrintThread pt;
    private ReadThread rt;


    //COSTRUTTORE
    public MainThread(Networker net) {
        this.net = net;
        pt=new PrintThread();
        rt=new ReadThread();
        pt.setMt(this);
        rt.setMt(this);
    }


    public PrintThread getPt() {
        return pt;
    }

    public void setPt(PrintThread pt) {
        this.pt = pt;
    }

    public ReadThread getRt() {
        return rt;
    }

    public void setRt(ReadThread rt) {
        this.rt = rt;
    }

    public Networker getNet() {
        return net;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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








    public static void main(String[] args) {
        Thread th1=new ReadThread();
    }
}

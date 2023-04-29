package it.polimi.ingsw.client.View;

import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.NetworkerRmi;
import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.Matrix;

import java.util.ArrayList;

public class MainThread {



    /*
        TODO
            ClientBase crea lock e lo passa a main, read, print
            PrintError lato PrintThread
            io leggo l'username e lo invio a networker FirstConnection??
            fare metodo run CLI per creare e iniziare a elaborare i dati da cli
     */

    private final Networker net; //a chi mandare messaggi
    private String username; //username del mio utente

    private Matrix myBookshelf; //mia bookshelf
    private ArrayList<Matrix> allBookshelf; //tutte le bookshelf - le posizioni sono uguali alle posizioni di allUsername
    private Matrix board; //la board di gioco
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

    public Matrix getMyBookshelf() {
        return myBookshelf;
    }

    public void setMyBookshelf(Matrix myBookshelf) {
        this.myBookshelf = myBookshelf;
    }

    public ArrayList<Matrix> getAllBookshelf() {
        return allBookshelf;
    }

    public void setAllBookshelf(ArrayList<Matrix> allBookshelf) {
        this.allBookshelf = allBookshelf;
    }

    public Matrix getBoard() {
        return board;
    }

    public void setBoard(Matrix board) {
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

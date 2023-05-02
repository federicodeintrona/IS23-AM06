package it.polimi.ingsw.client;

import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Model.Tiles;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.Matrix;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class ClientState implements ClientStateRemoteInterface {

    private final Lock viewLock;

    private Networker net; //TODO controlla se ci va o meno non ricordo
    private String username; //username del mio utente

    private Matrix myBookshelf; //mia bookshelf
    private ArrayList<Matrix> allBookshelf; //tutte le bookshelf - le posizioni sono uguali alle posizioni di allUsername
    private Matrix board; //la board di gioco
    private ArrayList<String> allUsername; //tutti gli username
    private PersonalObjective myPO; //il mio personal objective
    private ArrayList<CommonObjective> commonObjectives; //i common objective
    private String currentPlayer; //giocatore corrente
    private String nextPlayer; //prossimo giocatore
    private boolean endGame; //è finita la partita?
    private String winnerPlayer; //vincitore della partita
    private ArrayList<Integer> allPlayerPonits; //i punti di tutti i giocatori
    private ArrayList<Tiles> order; //ordine delle tessere
    private int myPoints; //i miei punti

    public ClientState(Lock viewLock) {
        this.viewLock = viewLock;
    }




    public Networker getNet() {
        synchronized (viewLock){
            return net;
        }
    }

    public String getUsername() {
        synchronized (viewLock) {
            return username;
        }
    }

    public void setUsername(String username) {
        synchronized (viewLock) {
            this.username = username;
        }
    }

    public Matrix getMyBookshelf() {
        synchronized (viewLock) {
            return myBookshelf;
        }
    }

    public void setMyBookshelf(Matrix myBookshelf) {
        synchronized (viewLock) {
            this.myBookshelf = myBookshelf;
        }
    }

    public ArrayList<Matrix> getAllBookshelf() {
        synchronized (viewLock) {
            return allBookshelf;
        }
    }

    public void setAllBookshelf(ArrayList<Matrix> allBookshelf) {
        synchronized (viewLock) {
            this.allBookshelf = allBookshelf;
        }
    }

    public Matrix getBoard() {
        synchronized (viewLock) {
            return board;
        }
    }

    public void setBoard(Matrix board) {
        synchronized (viewLock) {
            this.board = board;
        }
    }

    public ArrayList<String> getAllUsername() {
        synchronized (viewLock) {
            return allUsername;
        }
    }

    public void setAllUsername(ArrayList<String> allUsername) {
        synchronized (viewLock) {
            this.allUsername = allUsername;
        }
    }

    public PersonalObjective getMyPO() {
        synchronized (viewLock) {
            return myPO;
        }
    }

    public void setMyPO(PersonalObjective myPO) {
        synchronized (viewLock) {
            this.myPO = myPO;
        }
    }

    public ArrayList<CommonObjective> getCommonObjectives() {
        synchronized (viewLock) {
            return commonObjectives;
        }
    }

    public void setCommonObjectives(ArrayList<CommonObjective> commonObjectives) {
        synchronized (viewLock) {
            this.commonObjectives = commonObjectives;
        }
    }

    public String getCurrentPlayer() {
        synchronized (viewLock) {
            return currentPlayer;
        }
    }

    public void setCurrentPlayer(String currentPlayer) {
        synchronized (viewLock) {
            this.currentPlayer = currentPlayer;
        }
    }

    public boolean isEndGame() {
        synchronized (viewLock) {
            return endGame;
        }
    }

    public void setEndGame(boolean endGame) {
        synchronized (viewLock) {
            this.endGame = endGame;
        }
    }

    public String getWinnerPlayer() {
        synchronized (viewLock) {
            return winnerPlayer;
        }
    }

    public void setWinnerPlayer(String winnerPlayer) {
        synchronized (viewLock) {
            this.winnerPlayer = winnerPlayer;
        }
    }

    public ArrayList<Integer> getAllPlayerPonits() {
        synchronized (viewLock) {
            return allPlayerPonits;
        }
    }

    public void setAllPlayerPonits(ArrayList<Integer> allPlayerPonits) {
        synchronized (viewLock) {
            this.allPlayerPonits = allPlayerPonits;
        }
    }

    public String getNextPlayer() {
        synchronized (viewLock) {
            return nextPlayer;
        }
    }

    public void setNextPlayer(String nextPlayer) {
        synchronized (viewLock){
            this.nextPlayer = nextPlayer;
        }
    }

    public ArrayList<Tiles> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Tiles> order) {
        this.order = order;
    }

    public int getMyPoints() {
        return myPoints;
    }

    public void setMyPoints(int myPoints) {
        this.myPoints = myPoints;
    }

    //TODO punti e libreria modifica singola input username


    /*
        TODO
            all username - primo chair
            mio username
            personal - Hash
            common - array<integer> - da modificare print
            all bookshelf - Map<username, matrix>
            board
            all point - Map<username, integer> - pubblici
            my point
            current
            next
            isEndGame
            winner
            order selected tiles
     */
}

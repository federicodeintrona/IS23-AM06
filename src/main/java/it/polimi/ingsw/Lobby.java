package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Lobby {
    Controller controller;
    ArrayList<ArrayList<ServerClientHandler>> lobbys = new ArrayList<>();
    Queue<Integer> waitingLobbys = new LinkedList<>();
    HashMap<Integer,Integer> idToPlayers = new HashMap<>();



    public boolean waitingLobbys(){
        if(waitingLobbys.isEmpty()){
        return false;}
        else return true;
    }


    public synchronized void addClient(ServerClientHandler client){
        Integer index = waitingLobbys.peek();
        lobbys.get(index).add(client);
        if(lobbys.get(index).size() == idToPlayers.get(index)) {
            waitingLobbys.remove();
            startGame(index);

        }
    }

    public void startGame(int index){
        ArrayList<Player> players = new ArrayList<>();
        for (ServerClientHandler s : lobbys.get(index)){
            Player p = new Player(s.getNickname());
            players.add(p);
        }
        Model m = new Model(players);
        controller.addModel(m);
        controller.startGame(index);
    }

    public synchronized void newLobby(ServerClientHandler client,int numplayers){
          ArrayList<ServerClientHandler> newLobby = new ArrayList<>();
          newLobby.add(client);
          lobbys.add(newLobby);
          idToPlayers.put(lobbys.size()-1, numplayers);
          waitingLobbys.add(lobbys.size()-1);


    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}

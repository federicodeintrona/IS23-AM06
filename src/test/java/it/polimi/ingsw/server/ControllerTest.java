//package it.polimi.ingsw.server;
//
//import it.polimi.ingsw.server.View.CLIView;
//import it.polimi.ingsw.server.View.View;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ControllerTest {
//
//    public HashMap<Integer,Model> games = new HashMap<>();
//    public HashMap<String,ServerClientHandler> clients = new HashMap<>();
//    public Controller controller = new Controller(games,clients);;
//    public ArrayList<Player> players0 = new ArrayList<>();
//    public ArrayList<Player> players1 = new ArrayList<>();
//    public ArrayList<View> views0 = new ArrayList<>();
//    public ArrayList<View> views1 = new ArrayList<>();
//
//    @BeforeEach
//    void setUp(){
//
//        ServerClientHandler client0 = new ServerClientHandler(controller);
//        client0.setNickname("player0");
//        client0.setGameID(0);
//        client0.setPlayer(new Player(client0.getNickname()));
//        clients.put(client0.getNickname(),client0);
//        players0.add(client0.getPlayer());
//        views0.add(new CLIView());
//
//        ServerClientHandler client1 = new ServerClientHandler(controller);
//        client1.setNickname("player1");
//        client1.setGameID(0);
//        client1.setPlayer(new Player(client1.getNickname()));
//        clients.put(client1.getNickname(),client1);
//        players0.add(client1.getPlayer());
//        views0.add(new CLIView());
//
//        ServerClientHandler client2 = new ServerClientHandler(controller);
//        client2.setNickname("player2");
//        client2.setGameID(1);
//        client2.setPlayer(new Player(client2.getNickname()));
//        clients.put(client2.getNickname(),client2);
//        players1.add(client2.getPlayer());
//        views1.add(new CLIView());
//
//
//        ServerClientHandler client3 = new ServerClientHandler(controller);
//        client3.setNickname("player3");
//        client3.setGameID(1);
//        client3.setPlayer(new Player(client3.getNickname()));
//        clients.put(client3.getNickname(),client3);
//        players1.add(client3.getPlayer());
//        views1.add(new CLIView());
//
//        ServerClientHandler client4 = new ServerClientHandler(controller);
//        client4.setNickname("player4");
//        client4.setGameID(1);
//        client4.setPlayer(new Player(client4.getNickname()));
//        clients.put(client4.getNickname(),client4);
//        players1.add(client4.getPlayer());
//        views1.add(new CLIView());
//
//        games.put(0,new Model(players0,views0));
//        games.put(1,new Model(players1,views1));
//
//        controller.startGame(0);
//        controller.startGame(1);
//    }
//
//    @Test
//    void startGame() {
//
//        for(int i=0;i<2;i++) {
//            assertNotEquals(Tiles.EMPTY, games.get(i).getBoard().getGamesBoard().getTile(4, 4));
//            assertEquals(Tiles.NOTALLOWED, games.get(i).getBoard().getGamesBoard().getTile(5, 7));
//            for (Player p : games.get(i).getPlayers()) {
//                assertEquals(Tiles.EMPTY, p.getBookshelf().getTiles().getTile(getRandomPointInBookshelf()));
//                assertEquals(0, p.getPersonalObjective().personalObjectivePoint(p));
//            }
//            assertEquals(8, games.get(i).getCommonObj().get(0).getPoints());
//
//        }
//
//    }
//
//
//    @Test
//    void addToBookshelf() {
//
//
//
//    }
//
//    @Test
//    void swapOrder() {
//
//
//
//    }
//
//    @Test
//    void removeTiles() {
//
//
//    }
//
//    @Test
//    void newLobby() {
//    }
//
//    @Test
//    void handleNewClient() {
//    }
//
//
//
//    public static Point getRandomPointInBookshelf() {
//        Random rdm = new Random();
//        return new Point(rdm.nextInt(6),rdm.nextInt(5));
//
//    }
//}
//
//

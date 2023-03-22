package it.polimi.ingsw;

import it.polimi.ingsw.CommonObjective.CommonObjective;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class Server
{
    private static int port =9898;
    private static ArrayList <ServerClientHandler> clientList = new ArrayList<>();
    private Controller controller= new Controller();

    public static void main( String[] args ) {


        Matrix m = new Matrix(3,3);
        m.setTile(Tiles.PINK, 0,0);
        m.setTile(Tiles.YELLOW, 0,1);
        m.setTile(Tiles.EMPTY, 0,2);
        m.setTile(Tiles.BLUE, 1,0);
        m.setTile(Tiles.LIGHT_BLUE, 1,1);
        m.setTile(Tiles.BLUE, 1,2);
        m.setTile(Tiles.GREEN, 2,0);
        m.setTile(Tiles.WHITE, 2,1);
        m.setTile(Tiles.NOTALLOWED, 2,2);

        m.print();


        /*
        ArrayList<CommonObjective> c1 = new ArrayList<>();

        Server EchoServer = new Server();
        try {
            EchoServer.startServer();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }

        try {
            c1.addAll( CommonObjective.randomSubclass(3));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (CommonObjective commonObjective : c1) System.out.println(commonObjective);*/

    }

    public void startServer() throws IOException{
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }
        System.out.println("Server pronto");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                clientList.add(new ServerClientHandler(socket, controller));
                executor.submit(clientList.get(clientList.size()-1));
            } catch (IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

    public static ArrayList<ServerClientHandler> getClientList() {
        return clientList;
    }
    public void removeClient(ServerClientHandler client){
        clientList.remove(client);
}

}

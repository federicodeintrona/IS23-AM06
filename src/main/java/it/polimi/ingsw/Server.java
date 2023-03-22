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
        for (CommonObjective commonObjective : c1) System.out.println(commonObjective);

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

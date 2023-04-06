package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server extends UnicastRemoteObject {
    private static int port = 9898;     // Da sistemare
    private final static ArrayList <ServerClientHandler> clientList = new ArrayList<>();
    private final Lobby lobby = new Lobby();
    private final Controller controller= new Controller(lobby,lobby.getGames(),lobby.getClients());

    protected Server() throws RemoteException {
        super();
    }

    public static void main( String[] args ) throws RemoteException {
        Server EchoServer = new Server();

        try {
            EchoServer.startServer();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }


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

        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("RemoteController", controller);
        } catch (Exception e) {
            System.err.println(e.getMessage());
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

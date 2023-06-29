package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.JsonReader;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to manage the Server.
 * <p>
 *     Prepares both the Socket for tcp and the
 *     instance of the Controller to bind for rmi.
 * </p>
 */
public class Server extends UnicastRemoteObject {

    private static int tcpPort;
    private static int rmiPort;

    private final static ArrayList <ServerClientHandler> clientList = new ArrayList<>();
    private final Lobby lobby = new Lobby();
    private final Controller controller= new Controller(lobby);
    private final RMIHandlerInterface rmiHandler = new RMIHandler(controller);
    boolean tcpServer = true;
    boolean rmiServer = true;

    /**
     * Constructor for Server.
     * <p>
     *     It initializes both rmi and tcp ports.
     * </p>
     *
     * @throws IOException      In case of input/output error.
     * @throws ParseException       In case of error during parsing process using json.
     */
    protected Server() throws IOException, ParseException{
        super();
        JsonReader config;
        InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
        config = new JsonReader(is);

        // Configuring ports for both rmi and tcp from json
        tcpPort = config.getInt("tcpPort");
        rmiPort = config.getInt("rmiPort");
    }

    /**
     * Static method to launch the Server.
     *
     * @param args      The arguments that you insert on input.
     * @throws RemoteException      In case of error during the rmi connection process.
     */
    public static void main( String[] args ) throws RemoteException {
        Server Server = null;
        try {
            Server = new Server();
        } catch (IOException | ParseException e) {
            System.out.println("Error");
            System.exit(0);
        }

        try {
            Server.startServer();
        }
        catch (IOException e){
            System.out.println("Error");
            System.exit(0);
        }


    }

    private void startServer() throws IOException{

        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        lobby.setController(controller);

        try {
            serverSocket = new ServerSocket(103);
        } catch (IOException e) {
            tcpServer = false;
            System.out.println("Tcp port not available\nYou can still play using rmi"); // Porta non disponibile
            startOnlyRmi();
        }

        // Preparing for the RMI connections
        System.out.println(getLocalIPAddress());

        InetAddress ip = InetAddress.getLocalHost();
        String ipaddr = ip.getHostAddress();
        System.out.println(ipaddr);


        try{
            System.setProperty("java.rmi.server.hostname", Objects.requireNonNull(getLocalIPAddress()));
            RMIHandlerInterface stub = null;
            try {
                stub = (RMIHandlerInterface) UnicastRemoteObject.exportObject(rmiHandler, 0);
            } catch (RemoteException e) {
                rmiServer = false;
            }
            // Bind the remote object's stub in the registry
            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(103);
            } catch (RemoteException e) {
                rmiServer = false;
            }
            try {
                assert registry != null;
                registry.bind("RMIHandler", stub);
            } catch (RemoteException | AlreadyBoundException e) {
                rmiServer = false;
            }

            System.out.println("Server pronto");
            if (!rmiServer) {
                System.out.println("Rmi connection not available\nYou can still play using tcp");
            }
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
        catch (NullPointerException e){
            System.out.println("The server is disconnected");
            System.exit(0);
        }

    }

    /**
     * Method that returns the Server's IP Address.
     *
     * @return      Ip address.
     * @throws SocketException      Exception due to socket.
     */
    public static String getLocalIPAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface current = interfaces.nextElement();
            if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
                continue;
            }
            Enumeration<InetAddress> addresses = current.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress currentAddr = addresses.nextElement();
                if (currentAddr.isLoopbackAddress()) {
                    continue;
                }
                if (currentAddr instanceof Inet4Address) {
                    return currentAddr.getHostAddress();
                }
            }
        }
        return null;
    }

    public void startOnlyRmi () throws SocketException, UnknownHostException {
        // Preparing for the RMI connections
        System.out.println(getLocalIPAddress());

        InetAddress ip = InetAddress.getLocalHost();
        String ipaddr = ip.getHostAddress();
        System.out.println(ipaddr);


        try {
            System.setProperty("java.rmi.server.hostname", Objects.requireNonNull(getLocalIPAddress()));
            RMIHandlerInterface stub = null;
            try {
                stub = (RMIHandlerInterface) UnicastRemoteObject.exportObject(rmiHandler, 0);
            } catch (RemoteException e) {
                if (!tcpServer) {
                    System.out.println();
                    System.exit(0);
                }
            }
            // Bind the remote object's stub in the registry
            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(103);
            } catch (RemoteException e) {
                if (!tcpServer) {
                    System.exit(0);
                }
            }
            try {
                assert registry != null;
                registry.bind("RMIHandler", stub);
            } catch (RemoteException | AlreadyBoundException e) {
                if (!tcpServer) {
                    System.exit(0);
                }
            }
            while (true) {

            }
        } catch (NullPointerException e) {
            System.out.println("The server is disconnected");
            System.exit(0);
        }

    }
}
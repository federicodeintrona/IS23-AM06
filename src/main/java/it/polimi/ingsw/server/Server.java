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
    boolean tcpServer;

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
        lobby.setController(controller);

        // Preparing for TCP connections
        ExecutorService executor = Executors.newCachedThreadPool();

        // Printing ip addresses
        System.out.println(getLocalIPAddress());
        InetAddress ip = InetAddress.getLocalHost();
        String ipaddr = ip.getHostAddress();
        System.out.println(ipaddr);

        // Preparing for RMI connections
        System.setProperty("java.rmi.server.hostname", Objects.requireNonNull(getLocalIPAddress()));


        ServerSocket serverSocket = startTcpServer();
        boolean rmiService = startRmiServer();

        if (tcpServer) {
            // Only tcp available
            if (!rmiService) {
                System.out.println("Rmi protocol is not available");
                System.out.println("You can still play using only tcp");

                menageTcp(serverSocket, executor);
            }
            // Both protocols available
            else {
                System.out.println("Both tcp and rmi protocols available");

                menageTcp(serverSocket, executor);
            }

        }
        else {
            // Neither protocols available
            if (!rmiService) {
                System.out.println("Neither protocols are available");
                System.out.println("The server is disconnected");

                executor.shutdown();
                System.exit(0);
            }
            // Only rmi available
            else {
                System.out.println("Tcp protocol is not available");
                System.out.println("You can still play using only rmi");

                executor.shutdown();

                while (true) {

                }
            }
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

    /**
     * Method that starts rmi connection
     *
     * @return      Boolean <i>true</i> or <i>false</i> depending on if the connection was created or not
     */
    public boolean startRmiServer () {

        // Preparing for the RMI connections
        RMIHandlerInterface stub;

        try {
            stub = (RMIHandlerInterface) UnicastRemoteObject.exportObject(rmiHandler, 0);
        } catch (RemoteException e) {
            return false;
        }
        // Bind the remote object's stub in the registry
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(rmiPort);
        } catch (RemoteException e) {
            return false;
        }
        try {
            registry.bind("RMIHandler", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            return false;
        }

        return true;
    }

    /**
     * Method that starts tcp connection
     *
     * @return      Initialized serverSocket if the connection was created
     */
    private ServerSocket startTcpServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(tcpPort);
            tcpServer = true;
        } catch (IOException e) {
            tcpServer = false;
        }

        return serverSocket;
    }

    /**
     * Method that keeps the server alive after the initialization of tcp connection
     *
     * @param serverSocket      ServerSocket initialized
     * @param executor      Executor initialized
     */
    private void menageTcp(ServerSocket serverSocket, ExecutorService executor) {
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
}
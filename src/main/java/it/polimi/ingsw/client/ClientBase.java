package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientBase {

    public static void main( String[] args ) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String decision ;
        System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
        decision = scanner.nextLine();
        Object lock = new Object();
        ClientState state = new ClientState(lock);
        System.out.println("Which host do you use?");
        String host = scanner.nextLine();
        Networker client = switch (decision.toUpperCase()) {
            case "RMI" -> new NetworkerRmi(state,host);
            case "TCP" -> new NetworkerTcp(state,host);
            default -> null;
        };
        CLIMain cli = new CLIMain(lock,state,client);
        client.setCli(cli);
        client.initializeConnection();
        try {
            cli.runCLI();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

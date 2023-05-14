package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientBase {

    public static void main( String[] args ) throws InterruptedException {
        Scanner scanner = new Scanner (System.in);
        String decision = null;

        System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
        decision = scanner.nextLine();
        Object lock = new Object();
        ClientState state = new ClientState(lock);
        Networker client = switch (decision) {
            case "RMI" -> new NetworkerRmi(state);
            case "TCP" -> new NetworkerTcp();
            default -> null;
        };
            CLIMain cli = new CLIMain (lock, state, client);
            client.setCli(cli);
            client.initializeConnection();
            cli.runCLI();

    }
}

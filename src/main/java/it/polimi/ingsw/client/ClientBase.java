package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;

import java.util.Scanner;

public class ClientBase {

    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        String decision ;
        Object lock = new Object();

        System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
        decision = scanner.nextLine();
        ClientState clientState=new ClientState(lock);

        Networker client = switch (decision) {
            case "RMI" -> new NetworkerRmi(clientState);
            case "TCP" -> new NetworkerTcp(clientState);
            default -> null;
        };
        client.initializeConnection();

        CLIMain cli= new CLIMain(lock, clientState, client);
        cli.runCLI();
    }
}

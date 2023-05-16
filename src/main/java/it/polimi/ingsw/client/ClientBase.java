package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;

import java.util.Scanner;

public class ClientBase {

    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        String decision ;

        System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
        decision = scanner.nextLine();
        Object lock = new Object();
        ClientState state = new ClientState(lock);

        Networker client = switch (decision) {
            case "RMI" -> new NetworkerRmi(state);
            case "TCP" -> new NetworkerTcp(state);
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

package it.polimi.ingsw.client;

import java.util.Scanner;

public class ClientBase {

    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        String decision ;

        System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
        decision = scanner.nextLine();
        ClientState clientState=new ClientState();

        Networker client = null;
        switch (decision) {
            case "RMI":
                client = new NetworkerRmi();
                break;

            case "TCP":
                client = new NetworkerTcp(clientState);
                break;

        }
        client.initializeConnection();

    }
}

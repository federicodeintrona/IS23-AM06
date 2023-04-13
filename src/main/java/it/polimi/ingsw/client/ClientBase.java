package it.polimi.ingsw.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientBase {

    public static void main( String[] args ) throws MalformedURLException, NotBoundException, RemoteException {
        Scanner scanner = new Scanner(System.in);
        String decision = null;

        System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
        decision = scanner.nextLine();

        switch (decision) {
            case "RMI":
                NetworkerRmi client = new NetworkerRmi();
                client.initializeConnection();

            case "TCP":
        }
    }
}

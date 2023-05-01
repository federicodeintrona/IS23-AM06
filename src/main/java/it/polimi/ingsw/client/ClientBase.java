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

        Networker client = null;
        switch (decision) {
            case "RMI":
                client = new NetworkerRmi();
                break;

            case "TCP":
                client = new NetworkerTcp();
                break;

        }
        client.initializeConnection();

    }
}
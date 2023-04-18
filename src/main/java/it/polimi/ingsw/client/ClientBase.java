package it.polimi.ingsw.client;

import org.json.simple.parser.ParseException;

import java.io.IOException;
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

            case "TCP":
                try {
                    client = new NetworkerTcp();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }
        client.initializeConnection();
    }
}

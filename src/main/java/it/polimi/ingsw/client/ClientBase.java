package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.GUI.GUIMain;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientBase {

    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        String decision = null;

        System.out.print("Which connection protocol do you choose? (RMI/TCP): ");
        decision = scanner.nextLine();
        decision=decision.toUpperCase();
        Object lock = new Object();
        ClientState state = null;
        try {
            state = new ClientState(lock);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        Networker client = switch (decision) {
            case "RMI" -> new NetworkerRmi(state);
            case "TCP" -> new NetworkerTcp();
            default -> null;
        };

        System.out.print("Which User Interface do you choose? (CLI/GUI): ");
        decision = scanner.nextLine();
        decision=decision.toUpperCase();
        switch (decision){
            case "CLI" -> {
                CLIMain cli = new CLIMain(lock, state, client);
                client.setCli(cli);
                client.initializeConnection();
                try {
                    cli.runCLI();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            case "GUI" -> {
                GUIMain gui=new GUIMain(lock, state, client);
                gui.runGUI();
            }
        }

    }
}

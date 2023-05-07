package it.polimi.ingsw.app;

import java.io.IOException;
import java.rmi.NotBoundException;

public class Launcher {

    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
        HelloApplication.main(args);
    }
}

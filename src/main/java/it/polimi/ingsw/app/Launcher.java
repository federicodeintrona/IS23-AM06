package it.polimi.ingsw.app;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * Class to launch the My Shelfie Application.
 */
public class Launcher {

    /**
     * Default constructor
     */
    public Launcher() {
    }

    /**
     * Static method to launch MyShelfieApplication main.
     *
     * @param args the arguments that you insert on the opening jar.
     * @throws NotBoundException in case of attempt to search or disassociate in the registry a name that does not have an associated link.
     * @throws IOException in case of problem with input/output.
     * @throws InterruptedException in case of problem with thread interruption.
     */
    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
        MyShelfieApplication.main(args);
    }
}
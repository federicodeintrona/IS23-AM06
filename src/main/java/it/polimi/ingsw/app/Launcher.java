package it.polimi.ingsw.app;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * Class to launch the My Shelfie Application.
 */
public class Launcher {

    /**
     * Static method to launch MyShelfieApplication main.
     *
     * @param args the arguments that you insert on the opening jar.
     * @throws NotBoundException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
        MyShelfieApplication.main(args);
    }
}
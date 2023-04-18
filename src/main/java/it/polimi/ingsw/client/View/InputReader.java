package it.polimi.ingsw.client.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

//Callable permette ad un Thread di comportarsi come classe asincrona
public class InputReader implements Callable<String> {
    private final BufferedReader br; //legge carattere da stdIN


    public InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String call() throws IOException, InterruptedException {
        String input;
        // wait until there is data to complete a readLine()
        while (!br.ready()) {
            Thread.sleep(200);
        }
        input = br.readLine();
        return input;
    }

}

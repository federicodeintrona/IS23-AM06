package it.polimi.ingsw.utils.Timer;

import java.util.TimerTask;

/**
 * <p>A class, that extends TimerTask, which implements a counter.</p>
 * <p>It needs an object that extends TimerInterface to update its timer.</p>
 */
public class TimerCounter extends TimerTask {
    private TimerInterface client;
    private static final int timeout = 20;

    /**
     * <p>The standard constructor for the class.</p>
     * @param client The object that extends TimerInterface.
     */
    public TimerCounter(TimerInterface client) {
        this.client = client;
    }


    /**
     * <p>An override of the TimerTask run method.</p>
     * <p>It implements a countdown using a counter from the TimerInterface class,
     * if the counter reaches 20 it calls the disconnect method from the TimerInterface and
     * prints the error message.</p>
     */
    @Override
    public void run() {
        int time = client.updateTime();
        if (time > timeout) {
            System.out.println(client.getErrorMessage());
            client.disconnect();
            cancel();
        }
    }
}


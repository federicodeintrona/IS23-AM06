package it.polimi.ingsw.utils.Timer;

import java.util.TimerTask;

/**
 * <p>Class, that extends TimerTask, which implements a counter.</p>
 * <p>It needs an object that extends TimerInterface to update its timer.</p>
 */
public class TimerCounter extends TimerTask {
    private final TimerInterface client;
    private static final int timeout = 30;

    /**
     * <p>Initialize client and construct the timer.</p>
     * @param client The object that extends TimerInterface.
     */
    public TimerCounter(TimerInterface client) {
        this.client = client;
    }


    /**
     * <p>Method that implements a countdown using a counter from the TimerInterface class.</p>
     * <p>If the counter reaches 20 it calls the disconnect method from the TimerInterface and
     * prints the error message. It is an override of the TimerTask run method.</p>
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


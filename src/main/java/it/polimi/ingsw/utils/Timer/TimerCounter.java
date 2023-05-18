package it.polimi.ingsw.utils.Timer;

import java.util.TimerTask;

public class TimerCounter extends TimerTask {
    TimerInterface client;
    private static final int timeout = 20;

    public TimerCounter(TimerInterface client) {
        this.client = client;
    }


    @Override
    public void run() {
        int time = client.updateTime();
        if (time > timeout) {
            System.out.println(client.getErrorMessage());
            client.disconnect();
            this.cancel();
        }
    }
}


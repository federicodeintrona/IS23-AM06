package it.polimi.ingsw.utils.Timer;

import java.util.TimerTask;

public class TimerCounter extends TimerTask {
    ClientTimerInterface client;
    private static final int timeout = 20;

    public TimerCounter(ClientTimerInterface client) {
        this.client=client;
    }


    @Override
    public void run() {
        int time = client.updateTime();
        if (time>timeout) {
            System.out.println(client.getUsername() + " timed out");
            client.disconnect();
            this.cancel();

        }
    }

}


package it.polimi.ingsw.utils.Timer;

import it.polimi.ingsw.server.ClientInterface;

import java.util.TimerTask;

public class TimerCounter extends TimerTask {

    TimoutCheckerInterface timeOutChecker;
    ClientInterface client;

    public TimerCounter(TimoutCheckerInterface timeOutChecker) {
        this.timeOutChecker = timeOutChecker;
    }
    public TimerCounter(TimoutCheckerInterface timeOutChecker, ClientInterface client) {
        this.timeOutChecker = timeOutChecker;
        this.client=client;
    }


    @Override
    public void run() {

        Boolean stop = timeOutChecker.check(client.updateTime());
        if (stop) {

            System.out.println("Got stop inside TimerTask");
            client.disconnect();
            this.cancel();

        }
    }


}


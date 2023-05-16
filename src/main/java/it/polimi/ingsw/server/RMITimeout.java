package it.polimi.ingsw.server;

import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.server.VirtualView.VirtualView;
import it.polimi.ingsw.utils.Messages.Message;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimoutCheckerInterface;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RMITimeout extends Thread implements ClientInterface {

    private ScheduledExecutorService e;
    private String username=null;
    private final RMIVirtualView view;
    private final Controller controller;
    private int time = 0;
    private static final int timeout=20;
    private static final int initialDelay = 50;
    private static final int delta = 1000;

    public RMITimeout(String username, RMIVirtualView view,Controller controller) {
        this.username = username;
        this.view = view;
        this.controller=controller;
    }



    @Override
    public void disconnect() {
        System.out.println("Disconnection");
        e.shutdown();
        interrupt();
    }

    @Override
    public int updateTime() {
        this.time+=1;
        return this.time;
    }

    @Override
    public void run() {
        pingPongSender();

    }


    public void pingPongSender(){

        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{

            try {
                if(view.getClientState().pingPong()){
                    this.time=0;
                }
            } catch (RemoteException ex) {
                System.out.println(username+" is not responding...");
            }

        },10,1000, TimeUnit.MILLISECONDS);


        TimoutCheckerInterface timeOutChecker = (l) -> {
            System.out.println("Waiting "+username+ " for : " +l+" seconds");
            Boolean timeoutReached = l>timeout;
            if (timeoutReached){
                System.out.println("Got timeout inside server class");
                return true;
            }
            return false;
        };

        Timer timer = new Timer();
        TimerTask task = new TimerCounter(timeOutChecker,this);

        timer.schedule(task, initialDelay, delta);

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package it.polimi.ingsw.utils.Timer;

import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RMITimer implements ClientTimerInterface {

    private ScheduledExecutorService e;
    private String username = null;
    private final RMIVirtualView view;
    private final Controller controller;
    private final Timer timer = new Timer();
    private int time = 0;
    private static final int initialDelay = 50;
    private static final int delta = 1000;

    public RMITimer(String username, RMIVirtualView view, Controller controller) {
        this.username = username;
        this.view = view;
        this.controller=controller;
    }



    @Override
    public void disconnect() {

        e.shutdown();
        timer.cancel();

        if(username!=null){
            controller.playerDisconnection(username);
        }
        System.out.println(username + " has disconnected");
    }



    public int updateTime() {
        this.time+=1;
        return this.time;
    }



    public void pingPong(){
        System.out.println(username + "'s timer has started");

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

        TimerTask task = new TimerCounter(this);
        timer.schedule(task, initialDelay, delta);

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

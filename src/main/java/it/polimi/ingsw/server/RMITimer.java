package it.polimi.ingsw.server;

import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimerInterface;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RMITimer implements TimerInterface {
    private boolean disconnected = false;
    private ScheduledExecutorService e;
    private String username;
    private final RMIVirtualView view;
    private final Controller controller;
    private final Timer timer = new Timer();
    private int time = 0;
    private static final int initialDelay = 50;
    private static final int delta = 2000;


    public RMITimer(String username, RMIVirtualView view, Controller controller) {
        this.username = username;
        this.view = view;
        this.controller=controller;
    }


    @Override
    public void disconnect() {
        e.shutdown();
        timer.cancel();
        disconnected=true;
        if(username!=null){
            controller.playerDisconnection(username);
        }else System.out.println("A client has disconnected before having successfully logged in");

    }



    public int updateTime() {
        this.time+=1;
        return this.time;
    }



    public void pingPong(){

        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{

            try {
                if(view.getClientState().pingPong()){
                    this.time=0;
                }
            } catch (RemoteException ex) {
                if(!disconnected)
                  System.out.println(username + " is not responding...");
            }

        },50,1000, TimeUnit.MILLISECONDS);

        TimerTask task = new TimerCounter(this);
        timer.schedule(task, initialDelay, delta);

    }


    public String getErrorMessage() {
        return username+" timed out";
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

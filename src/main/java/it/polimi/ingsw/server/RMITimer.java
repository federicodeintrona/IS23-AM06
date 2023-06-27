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
    private final RMIVirtualView view;
    private final Controller controller;
    private Timer timer;
    private final TimerTask task = new TimerCounter(this);
    private int time = 0;
    private static final int initialDelay = 50;
    private static final int delta = 500;
    private boolean ponging = true;



    public RMITimer( RMIVirtualView view, Controller controller) {
        this.view = view;
        this.controller=controller;
    }


    @Override
    public void disconnect() {
        e.shutdown();
        timer.cancel();
        disconnected=true;
        if(view.getUsername()!=null){
            controller.playerDisconnection(view.getUsername());
        }else System.out.println("A client has disconnected before having successfully logged in");

    }



    public int updateTime() {
        this.time+=1;
        return this.time;
    }



    public void pingPong(){
        System.out.println("pongherò " + view.getUsername());
        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{

            try {
                if(view.getClientState().pingPong()){
                    this.time=0;
                }
            } catch (RemoteException ex) {
                if(ponging&&!disconnected) {
                    System.out.println(view.getUsername() + " is not responding...");
                    ponging = false;
                }
            }

        },50,500, TimeUnit.MILLISECONDS);

        startTimer();

    }

    public void stopTimer(){
        timer.cancel();
        e.shutdown();
    }

    public void startTimer(){
        if(timer!=null)
            timer.cancel();

        timer = new Timer();
        timer.schedule(task, initialDelay, delta);
    }




    public String getErrorMessage() {
        return view.getUsername()+" timed out";
    }


}

package it.polimi.ingsw.utils.Timer;

import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;
import it.polimi.ingsw.utils.Timer.TimerCounter;
import it.polimi.ingsw.utils.Timer.TimerInterface;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A countdown timer used with RMI connection.
 */
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


    /**
     * Main constructor of the class.
     * @param view The RMIVirtualView of the player.
     * @param controller The controller.
     */
    public RMITimer( RMIVirtualView view, Controller controller) {
        this.view = view;
        this.controller=controller;
    }


    /**
     * Method to disconnect the player.
     */
    @Override
    public void disconnect() {
        e.shutdown();
        timer.cancel();
        disconnected=true;
        if(view.getUsername()!=null){
            controller.playerDisconnection(view.getUsername());
        }else System.out.println("A client has disconnected before having successfully logged in");

    }


    /**
     * Method to update the time counter.
     * @return The updated counter.
     */
    public int updateTime() {
        this.time+=1;
        return this.time;
    }


    /**
     * Method to start sending pings and to start the timer.
     */
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

    /**
     * Method to stop the timer.
     */
    public void stopTimer(){
        timer.cancel();
        e.shutdown();
    }

//TODO javadoc FEDE
    private void startTimer(){
        if(timer!=null)
            timer.cancel();

        timer = new Timer();
        timer.schedule(task, initialDelay, delta);
    }

    /**
     * Method to get the personalized error message of the user of the timer.
     * @return The personalized message.
     */
    public String getErrorMessage() {
        return view.getUsername()+" timed out";
    }


}

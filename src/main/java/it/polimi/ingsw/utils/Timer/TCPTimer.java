package it.polimi.ingsw.utils.Timer;

import it.polimi.ingsw.server.Controller;
import it.polimi.ingsw.server.ServerClientHandler;
import it.polimi.ingsw.server.VirtualView.RMIVirtualView;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TCPTimer implements ClientTimerInterface {
    private ScheduledExecutorService e;
    private String username = null;
    private final ServerClientHandler clientHandler;
    private final Controller controller;
    private final Timer timer = new Timer();
    private int time = 0;
    private static final int timeout = 20;
    private static final int initialDelay = 50;
    private static final int delta = 1000;


    public TCPTimer(String username, ServerClientHandler clientHandler, Controller controller) {
        this.username = username;
        this.clientHandler = clientHandler;
        this.controller = controller;
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



    public void pingPongSender(){

        e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(()->{

         /*   try {
                if(clientHandler.pingPongSender()){
                    this.time=0;
                }
            } catch (RemoteException ex) {
                System.out.println(username+" is not responding...");
            }*/

        },10,1000, TimeUnit.MILLISECONDS);


        TimoutCheckerInterface timeOutChecker = (l) -> {
            System.out.println("Waiting "+username+ " for : " +l+" seconds");
            boolean timeoutReached = l>timeout;
            if (timeoutReached){
                System.out.println("Got timeout inside server class");
                return true;
            }
            return false;
        };


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

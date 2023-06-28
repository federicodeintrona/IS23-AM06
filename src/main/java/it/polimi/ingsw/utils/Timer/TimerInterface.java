package it.polimi.ingsw.utils.Timer;

/**
 * <p> Interface for classes that use countdown timers.</p>
 * <p> It is used to automatically disconnect or close a class when the timer ends.
 *  The class that implements this interface needs to have a counter (an int variable suffices)
 *  which will be updated by the timer itself.</p>
 *  <p>The class needs to autonomously instantiate a TimerCounter and pass itself in the contructor,
 *  then start the timer with the TimerCounter as TimerTask.</p>
 */
public interface TimerInterface {
     /**
      * <p>Method that is called when the timer finished the countdown.</p>
      */
     void disconnect();

     /**
      * <p>Method that updates and returns the counter.</p>
      * @return The updated counter
      */
     int updateTime();

     /**
      * <p>Method that gets the class specific error message that will be printed when the
      * timer countdown finishes.</p>
      * @return The class specific error message.
      */
     String getErrorMessage();

}

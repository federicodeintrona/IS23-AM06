package it.polimi.ingsw.server.CommonObjective;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;


import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that regulates the functioning of CommonObjective 1.
 */
public class CommonObjective1 extends CommonObjective {

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective1() {
        this.setNum(1);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 6 separate groups, made by 2 tiles of the same
     * color each, inside it and, in case, returns true.
     *
     * @param player player whose bookshelf gets analyze.
     * @return <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player) {

        // creation of set to contain all tiles approved by the algorithm
        Set<Point> nodes = new HashSet<>();

        // creation of 2 thread that will apply the algorithm
        // (the first one on the rows, the second on the columns) of player's bookshelf
        MyRunnable firstRunnable = new MyRunnable("row-Thread 1", player.getBookshelf().getTiles(), nodes);
        MyRunnable secondRunnable = new MyRunnable("column-Thread 2", player.getBookshelf().getTiles(), nodes);

        Thread rowThread = new Thread(firstRunnable);
        Thread columnThread = new Thread(secondRunnable);

        // starting the threads
        rowThread.start();
        columnThread.start();

        try {
            rowThread.join();
            columnThread.join();
        } catch (InterruptedException e) {
            System.out.println("20€ che non succede");
        }

        // the division by 2 of the set's size will give us the number of pair found in the bookshelf
        return nodes.size() / 2 > 5;
    }
}

/**
 * <p>
 *     Thread class:
 * </p>
 * <p>
 *     one to analyze the columns and one for the rows.
 * </p>
 */
class MyRunnable implements Runnable {
    private final String name;
    private final Matrix matrix;
    final Set<Point> buffer;
    Point point1;
    Point point2;

    /**
     * Initializer for each thread
     *
     * @param name      thread's name
     * @param matrix        player's bookshelf
     * @param buffer        HashSet of points found
     */
    public MyRunnable(String name, Matrix matrix, Set<Point> buffer) {
        this.name = name;
        this.matrix = matrix;
        this.buffer = buffer;
    }

    /**
     * Runnable
     */
    public void run() {


        // code for the thread 1 that will analyze the rows
        if (name.equals("row-Thread 1")){
            for (int i=0; i<6; i++){
                for (int j=0; j<4; j++){

                    // Skipping if cell is set to EMPTY
                    if (matrix.getTile(i, j).equals(Tiles.EMPTY)) continue;

                    if (matrix.getTile(i, j).equals(matrix.getTile(i, j+1))){

                        // synchronizing on buffer so threads will check and add the coordinates one at the time
                        synchronized (buffer) {
                            point1 = new Point(i, j);
                            point2 = new Point(i, j+1);
                            if (!buffer.contains(point1) && !buffer.contains(point2)) {
                                buffer.add(point1);
                                buffer.add(point2);
                            }
                        }

                    }
                }

            }
        }

        // code for the thread 2 that will analyze the columns
        else if (name.equals("column-Thread 2")) {
            for (int j=0; j<5; j++){
                for (int i=0; i<5; i++){

                    // Skipping if cell is set to EMPTY
                    if (matrix.getTile(i, j).equals(Tiles.EMPTY)) continue;

                    if (matrix.getTile(i, j).equals(matrix.getTile(i+1, j))){

                        // synchronizing on buffer so threads will check and add the coordinates one at the time
                        synchronized (buffer) {
                            point1 = new Point(i, j);
                            point2 = new Point(i+1, j);
                            if (!buffer.contains(point1) && !buffer.contains(point2)) {
                                buffer.add(point1);
                                buffer.add(point2);
                            }
                        }
                    }
                }
            }
        }
    }
}

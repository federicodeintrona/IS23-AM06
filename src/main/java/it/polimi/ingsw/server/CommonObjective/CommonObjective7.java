package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

/**
 * Class that regulates the functioning of CommonObjective 7.
 */
public class CommonObjective7 extends CommonObjective{

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective7() {
        this.setNum(7);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 5 tiles of the same color in a diagonal
     * inside it and, in case, returns true.
     *
     * @param player    player whose bookshelf gets analyze.
     * @return      <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player) {
        DiagonalAnalyzerThread thread1 = new DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 0, 0, 4, 4, false);
        DiagonalAnalyzerThread thread2 = new DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 1, 0, 5, 4, false);
        DiagonalAnalyzerThread thread3 = new DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 0, 4, 5, 0, true);
        DiagonalAnalyzerThread thread4 = new DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 1, 4, 5, 0, true);
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            System.out.println("50€ che non succede");
        }
        
        return thread1.areAllElementsEqual() || thread2.areAllElementsEqual() || thread3.areAllElementsEqual() || thread4.areAllElementsEqual();
    }

    /**
     * <p>
     *      Thread class:
     * </p>
     * <p>
     *     regulates the work of 4 threads each one
     *     dedicated to analyze one out of 4 diagonals.
     * </p>
     */
    private static class DiagonalAnalyzerThread extends Thread {
        Matrix matrix;
        private final int rowStart;
        private final int colStart;
        private final int rowEnd;
        private final int colEnd;
        private final boolean isInverse;        // Switch used to differentiate between diagonal and inverse-diagonal
        private volatile boolean areAllElementsEqual;
        private volatile boolean stop;

        /**
         * Initializer for each thread.
         *
         * @param matrix    player's bookshelf.
         * @param rowStart      the starting index for the row.
         * @param colStart      the starting index for the column.
         * @param rowEnd    the ending index for the row.
         * @param colEnd    the ending index for the column.
         * @param isInverse     switch case.
         */
        public DiagonalAnalyzerThread(Matrix matrix, int rowStart, int colStart, int rowEnd, int colEnd, boolean isInverse) {
            this.matrix = matrix;
            this.rowStart = rowStart;
            this.colStart = colStart;
            this.rowEnd = rowEnd;
            this.colEnd = colEnd;
            this.areAllElementsEqual = true;
            this.stop = false;
            this.isInverse = isInverse;
        }

        /**
         * Method that returns the boolean areAllElementsEqual.
         *
         * @return  areAllElementsEqual.
         */
        public boolean areAllElementsEqual() {
            return areAllElementsEqual;
        }

        /**
         * <p>
         *     Thread's code: analyzes each element of the respective diagonal.
         * </p>
         * <p>
         *     Through an if-else construct decides if the
         *     diagonal to analyze is an inverse or not.
         * </p>
         * <p>
         *     It sets the areAllElementsEqual boolean to false in case its
         *     diagonal does not meet the criteria, else it calls the
         *     helping method stopOtherThreads() to end the other threads.
         * </p>
         */
        @Override
        public void run() {
            int row = rowStart;
            int col = colStart;
            Tiles firstElement = matrix.getTile(row, col);

            if (firstElement.equals(Tiles.EMPTY)) {
                areAllElementsEqual = false;
                stop = true;
                return;
            }

            if (isInverse) {
                while (row != rowEnd && col != colEnd && !stop) {
                    if (!matrix.getTile(row, col).equals(firstElement)) {
                        areAllElementsEqual = false;
                        break;
                    }
                    row++;
                    col--;
                }
            }
            else {
                while (row != rowEnd && col != colEnd && !stop) {
                    if (!matrix.getTile(row, col).equals(firstElement)) {
                        areAllElementsEqual = false;
                        break;
                    }
                    row++;
                    col++;
                }
            }

            stopOtherThreads();
        }

        /**
         * Method used to end all the threads
         * except for the one who calls it.
         */
        private void stopOtherThreads() {
            if (areAllElementsEqual) {
                stop = true;
                ThreadGroup group = Thread.currentThread().getThreadGroup();
                Thread[] threads = new Thread[group.activeCount()];
                group.enumerate(threads);
                for (Thread thread : threads) {
                    if (thread instanceof DiagonalAnalyzerThread && thread != this) {
                        ((DiagonalAnalyzerThread) thread).stop = true;
                    }
                }
            }
        }
    }
}



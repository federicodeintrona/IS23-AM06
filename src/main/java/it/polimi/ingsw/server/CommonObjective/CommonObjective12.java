package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.utils.Tiles;

/**
 * Class that regulates the functioning of CommonObjective 12.
 */
public class CommonObjective12 extends CommonObjective{

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective12() {
        this.setNum(12);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 5 columns of growing or decreasing height,
     * each one that differs from the previous one by a box,
     * inside it and, in case, returns true.
     *
     * @param player    player whose bookshelf gets analyze.
     * @return      <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player) {

        // Checks if the bookshelf is empty and in case returns false
        if ( (player.getBookshelf().getNum_of_tiles() != 10) && (player.getBookshelf().getNum_of_tiles() != 15) && (player.getBookshelf().getNum_of_tiles() != 20) ) return false;

        CommonObjective12.DiagonalAnalyzerThread thread1 = new CommonObjective12.DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 0, 1, 3, 4, false);
        CommonObjective12.DiagonalAnalyzerThread thread2 = new CommonObjective12.DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 0, 0, 4, 4, false);
        CommonObjective12.DiagonalAnalyzerThread thread3 = new CommonObjective12.DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 1, 0, 5, 4, false);

        CommonObjective12.DiagonalAnalyzerThread thread4 = new CommonObjective12.DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 0, 3, 3, 0, true);
        CommonObjective12.DiagonalAnalyzerThread thread5 = new CommonObjective12.DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 0, 4, 4, 0, true);
        CommonObjective12.DiagonalAnalyzerThread thread6 = new CommonObjective12.DiagonalAnalyzerThread(player.getBookshelf().getTiles(), 1, 4, 5, 0, true);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
        } catch (InterruptedException e) {
            System.out.println("60€ che non succede");
        }

        return thread1.areAllElementsEqual() || thread2.areAllElementsEqual() || thread3.areAllElementsEqual() || thread4.areAllElementsEqual() || thread5.areAllElementsEqual() || thread6.areAllElementsEqual();
    }

    /**
     * <p>
     *     Thread class:
     * </p>
     * <p>
     *     regulates the work of 6 threads each one
     *     dedicated to analyze one out of 6 possible diagonals.
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

            if (isInverse) {
                while (row <= rowEnd && col >= colEnd && !stop) {
                    if (!matrix.getTile(row, col).equals(Tiles.EMPTY)) {
                        areAllElementsEqual = false;
                        break;
                    } else if ((row+1 < rowEnd) && matrix.getTile(row+1, col).equals(Tiles.EMPTY)) {
                        areAllElementsEqual = false;
                        break;
                    }
                    row++;
                    col--;
                }
            }
            else {
                while (row <= rowEnd && col <= colEnd && !stop) {
                    if (!matrix.getTile(row, col).equals(Tiles.EMPTY)) {
                        areAllElementsEqual = false;
                        break;
                    } else if ((row+1 < rowEnd) && matrix.getTile(row+1, col).equals(Tiles.EMPTY)) {
                        areAllElementsEqual = false;
                        break;
                    }
                    row++;
                    col++;
                }
            }

            if (areAllElementsEqual) stopOtherThreads();
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
                    if (thread instanceof CommonObjective12.DiagonalAnalyzerThread && thread != this) {
                        ((CommonObjective12.DiagonalAnalyzerThread) thread).stop = true;
                    }
                }
            }
        }
    }
}

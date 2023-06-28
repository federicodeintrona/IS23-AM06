package it.polimi.ingsw.server.CommonObjective;

import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;

/**
 * Class that regulates the functioning of CommonObjective 11.
 */
public class CommonObjective11 extends CommonObjective{

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective11() {
        this.setNum(11);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 5 tiles of the same color that form an
     * x-shape inside it and, in case, returns true.
     *
     * @param player    player whose bookshelf gets analyze.
     * @return      <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player) {
        Point p;

        for (int i=1; i<5; i++){
            for (int j=1; j<4; j++){

                // first control to get rid of empty tiles
                if (!player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)){
                    p = new Point(i, j);

                    // checking the for corners
                    if (checkForXShape(p, player.getBookshelf().getTiles())) return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that returns true if the given point can make an x-shape,
     * with it in the center, of same colored tiles in the given matrix.
     *
     * @param coordinate    point that has to be in the center of the x-shape.
     * @param matrix      player's bookshelf.
     * @return      <i>true</i> if an x-shape is possible, else <i>false</i>.
     */
    public static boolean checkForXShape(Point coordinate, Matrix matrix){
        int x = coordinate.x;
        int y = coordinate.y;

        // checking the 4 corners adjacent to coordinate
        return (matrix.getTile(x - 1, y - 1).equals(matrix.getTile(x, y))) && (matrix.getTile(x - 1, y + 1).equals(matrix.getTile(x, y))) && (matrix.getTile(x + 1, y + 1).equals(matrix.getTile(x, y))) && (matrix.getTile(x + 1, y - 1).equals(matrix.getTile(x, y)));
    }
}


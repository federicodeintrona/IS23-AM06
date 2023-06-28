package it.polimi.ingsw.server.CommonObjective;


import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class that regulates the functioning of CommonObjective 3.
 */
public class CommonObjective3 extends CommonObjective{

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective3() {
        this.setNum(3);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 4 separate groups, each one made by 4 tiles of the same
     * color adjacent to one another, inside it and, in case, returns true.
     *
     * @param player    player whose bookshelf gets analyze.
     * @return      <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player) {
        int numOfGroups = 0;
        boolean[][] visited = new boolean[Define.NUMBEROFROWS_BOOKSHELF.getI()][Define.NUMBEROFCOLUMNS_BOOKSHELF.getI()];

        // Loop through all the cells in the matrix
        for (int i = 0; i < Define.NUMBEROFROWS_BOOKSHELF.getI(); i++) {
            for (int j = 0; j < Define.NUMBEROFCOLUMNS_BOOKSHELF.getI(); j++) {
                if (visited[i][j] || player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) {

                    // Skip if cell already visited or set to EMPTY
                    continue;
                }

                // Performing BFS on this node
                Queue<Point> queue = new LinkedList<>();
                queue.add(new Point(i, j));
                visited[i][j] = true;
                int count = 1;

                while (!queue.isEmpty()) {
                    Point current = queue.poll();

                    // Check all adjacent cells with the same color
                    for (Point adjacent : getAdjacentSameColor(player.getBookshelf().getTiles(), current)) {
                        int x = adjacent.x;
                        int y = adjacent.y;
                        if (!visited[x][y]) {
                            visited[x][y] = true;
                            queue.add(adjacent);
                            count++;

                            // Four adjacent cells of the same color found
                            if (count == 4) {
                                numOfGroups++;
                                queue.clear();
                                break;
                            }
                        }
                    }

                    // Four groups that meet the criteria found
                    if (numOfGroups == 4) return true;
                }
            }
        }

        // No four adjacent cells of the same color found
        return false;
    }

    /**
     * Helping method that takes a matrix, a point inside and returns an ArrayList
     * of points, adjacent to the given one, that share the same tile color.
     *
     * @param matrix    player's bookshelf.
     * @param point     coordinates of the analyzed tile.
     * @return      adjacent: ArrayList of points that meet the criteria.
     */
    private static ArrayList<Point> getAdjacentSameColor(Matrix matrix, Point point) {
        ArrayList<Point> adjacent = new ArrayList<>();
        int x = point.x;
        int y = point.y;
        Tiles color = matrix.getTile(x, y);

        // Checking adjacent cells with the same color
        if (x > 0 && matrix.getTile(x - 1, y) == color) {
            adjacent.add(new Point(x - 1, y));
        }
        if (x < 5 && matrix.getTile(x + 1, y) == color) {
            adjacent.add(new Point(x + 1, y));
        }
        if (y > 0 && matrix.getTile(x, y - 1) == color) {
            adjacent.add(new Point(x, y - 1));
        }
        if (y < 4 && matrix.getTile(x, y + 1) == color) {
            adjacent.add(new Point(x, y + 1));
        }

        return adjacent;
    }
}

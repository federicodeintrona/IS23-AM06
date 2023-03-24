package it.polimi.ingsw.CommonObjective;


import it.polimi.ingsw.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import it.polimi.ingsw.*;

public class CommonObjective3 extends CommonObjective{

    static{
        subclasses.add(CommonObjective3.class);
    }

    public boolean checkCondition(Player player) {
        int numOfGroups = 0;
        boolean[][] visited = new boolean[6][5];

        // Loop through all the cells in the matrix
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (visited[i][j] || player.getBookshelf().getTiles().getTile(i, j).equals(Tiles.EMPTY)) {

                    // Skip if cell already visited or set to EMPTY
                    continue;
                }

                // Performing BFS (algoritmo di ricerca in ampiezza) on this node
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

    public void commonObjPointsCalculator(Player player, int numOfPlayers){
        if (checkCondition(player) && !playersWhoCompletedComObj.contains(player)) {

            // adding the player to the set of players who already received the points
            playersWhoCompletedComObj.add(player);

            // for a 2 players game the first to complete a commonObj gets 8 points and the second to do so 4
            if (numOfPlayers == 2) {
                player.setCommonObjectivePoint(points);
                points -= 4;
            }

            // in case there are more than 2 players each time a commonObj is completed its points decrease by 2
            else {
                player.setCommonObjectivePoint(points);
                points -= 2;
            }
        }
    }
}

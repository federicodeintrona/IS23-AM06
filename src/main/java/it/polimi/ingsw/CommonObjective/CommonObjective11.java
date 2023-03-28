package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.*;

import java.awt.*;

public class CommonObjective11 extends CommonObjective{


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

    public static boolean checkForXShape(Point coordinate, Matrix matrix){
        int x = coordinate.x;
        int y = coordinate.y;

        // checking the 4 corners adjacent to coordinate
        if ((matrix.getTile(x-1, y-1).equals(matrix.getTile(x, y))) && (matrix.getTile(x-1, y+1).equals(matrix.getTile(x, y))) && (matrix.getTile(x+1, y+1).equals(matrix.getTile(x, y))) && (matrix.getTile(x+1, y-1).equals(matrix.getTile(x, y)))) return true;

        return false;
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


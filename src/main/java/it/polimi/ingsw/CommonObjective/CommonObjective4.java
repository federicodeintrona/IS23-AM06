package it.polimi.ingsw.CommonObjective;

import it.polimi.ingsw.Matrix;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommonObjective4 extends CommonObjective{

    static{
        subclasses.add(CommonObjective4.class);
    }
    public boolean checkCondition(Player player) {
        Matrix matrix = player.getBookshelf().getTiles();       //creating a copy of player's bookshelf To decrease the number of access
        ArrayList<Tiles> colors = new ArrayList<>();        //to store the colors of the checking matrix 2x2
        Map<Tiles, Integer> groupsPerColor = new HashMap<>();       //keeps track of the number of groups per color
        boolean[][] visited = new boolean[6][5];        //used to eliminate groups that are not detached

        for (int i=0; i<5; i++){
            for (int j=0; j<4; j++){

                //checking if first box of checking matrix 2x2 is EMPTY or visited
                if (!matrix.getTile(0 + i, 0 + j).equals(Tiles.EMPTY) && !visited[0 + i][0 + j]){
                    colors.add(matrix.getTile(0 + i, 0 + j));
                    colors.add(matrix.getTile(0 + i, 1 + j));
                    colors.add(matrix.getTile(1 + i, 0 + j));
                    colors.add(matrix.getTile(1 + i, 1 + j));

                    //checking if the matrix 2x2 has all same colors
                    if (checkColorBox(colors)){

                        //updating visited matrix
                        visited[0 + i][0 + j] = true;
                        visited[0 + i][1 + j] = true;
                        visited[1 + i][0 + j] = true;
                        visited[1 + i][1 + j] = true;

                        groupsPerColor.merge(colors.get(0), 1, (oldValue, value) -> oldValue + value);
                    }

                    colors.clear();
                }
            }
        }

        //returns true if there is at least a group that meets the criteria else false
        return groupsPerColor.values()
                             .stream()
                             .anyMatch(count -> count >= 2);
    }

    //method to check if a matrix as all elements of same color
    public static boolean checkColorBox(ArrayList<Tiles> box){
        Tiles case0 = box.get(0);

        for (int i=1; i<box.size(); i++){
            if (!case0.equals(box.get(i))) return false;
        }

        return true;
    }
}

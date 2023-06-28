package it.polimi.ingsw.server.CommonObjective;
import it.polimi.ingsw.utils.Define;
import it.polimi.ingsw.utils.Matrix;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that regulates the functioning of CommonObjective 4.
 */
public class CommonObjective4 extends CommonObjective{

    /**
     * Sets the num variable of the CommonObjective.
     */
    public CommonObjective4() {
        this.setNum(4);
    }

    /**
     * Method that takes a player and analyzes his bookshelf to
     * see if there are 2 separate groups, each one made by 4 tiles of the same
     * color disposed in a 2x2 formation, inside it and, in case, returns true.
     * The tiles of the 2 groups need to have the same color to meet the criteria.
     *
     * @param player    player whose bookshelf gets analyze.
     * @return      <i>true</i> if the bookshelf meets the criteria, else <i>false</i>.
     */
    @Override
    public boolean checkCondition(Player player) {
        Matrix matrix = player.getBookshelf().getTiles();       //creating a copy of player's bookshelf To decrease the number of access
        ArrayList<Tiles> colors = new ArrayList<>();        //to store the colors of the checking matrix 2x2
        Map<Tiles, Integer> groupsPerColor = new HashMap<>();       //keeps track of the number of groups per color
        boolean[][] visited = new boolean[Define.NUMBEROFROWS_BOOKSHELF.getI()][Define.NUMBEROFCOLUMNS_BOOKSHELF.getI()];        //used to eliminate groups that are not detached

        for (int i=0; i<5; i++){
            for (int j=0; j<4; j++){

                //checking if first box of checking matrix 2x2 is EMPTY or visited
                if (!matrix.getTile(i, j).equals(Tiles.EMPTY) && !visited[i][j]){
                    colors.add(matrix.getTile(i, j));
                    colors.add(matrix.getTile(i, 1 + j));
                    colors.add(matrix.getTile(1 + i, j));
                    colors.add(matrix.getTile(1 + i, 1 + j));

                    //checking if the matrix 2x2 has all same colors
                    if (checkColorBox(colors)){

                        //updating visited matrix
                        visited[i][j] = true;
                        visited[i][1 + j] = true;
                        visited[1 + i][j] = true;
                        visited[1 + i][1 + j] = true;

                        groupsPerColor.merge(colors.get(0), 1, Integer::sum);
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

    /**
     * Helping method that takes an Arraylist of tiles and
     * returns true only if all of its tiles have the same color.
     *
     * @param box   <i>ArrayList</i> of tiles.
     * @return      true if all elements of box are the same, else false.
     */
    public static boolean checkColorBox(ArrayList<Tiles> box){
        Tiles case0 = box.get(0);

        for (int i=1; i<box.size(); i++){
            if (!case0.equals(box.get(i))) return false;
        }

        return true;
    }
}

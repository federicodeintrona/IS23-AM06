package it.polimi.ingsw;

import java.util.ArrayList;

import it.polimi.ingsw.*;

public class Bookshelf {
        Matrix tiles;
        int num_of_tiles;

        public Bookshelf(){
            tiles=new Matrix(6,5);
            num_of_tiles=0;
        }

        public Matrix getTiles(){
            return tiles;
        }
        public boolean checkColumns(int choice, int column){
            if(tiles.getTile(0+choice-1,column).equals(Tiles.EMPTY))
                return true;
            else return false;
        }
        public boolean checkEndGame(){
            for(int i=0;i<5;i++){
                if(tiles.getTile(0,i).equals(Tiles.EMPTY)){
                    return false;
                }
            }
            return true;
        }
        public void addTile(ArrayList<Tiles> tiles, int column){
            int pos=firstFree(column);
            if (tiles.size() == 1) this.tiles.setTile(tiles.get(0), pos, column);
            else if (tiles.size()==2) {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos+1, column);
            }
            else {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos+1, column);
                this.tiles.setTile(tiles.get(2), pos+2, column);
            }
        }

        private int firstFree (int column){
            for (int i=0;i<6;i++){
                if(tiles.getTile(i,column).equals(Tiles.EMPTY)){
                    return i;
                }
            }
            return -1;
        }
        public int checkVicinityPoints(){

            /*
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
                            if (count == 4) numOfGroups++;

                            // Four groups that meet the criteria found
                            if (numOfGroups == 4) return true;
                        }
                    }
                }
            }
        }

        // No four adjacent cells of the same color found
        return false;
             */


            return 0;
        }
}

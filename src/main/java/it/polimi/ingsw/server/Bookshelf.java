package it.polimi.ingsw.server;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Bookshelf {
        Matrix tiles;
        int num_of_tiles;

    /**
     * Inizialize the bookshelf with the Matrix
     * and set every position to EMPTY
     */
        public Bookshelf(){
            tiles=new Matrix(6,5);
            num_of_tiles=0;
            for( int i=0; i<tiles.getNumRows();i++){
                for( int j=0; j<tiles.getNumCols();j++){
                    tiles.setEmpty(i,j);
                }

            }
        }

        /**
        * Returns the matrix of tiles
        * @return The matrix of tiles
         **/

        public Matrix getTiles(){
            return tiles;
        }

    /**
     * Checks if th column given is full of tiles
     * @param choice number of tiles chosen
     * @param column index of the colum chosen
     * @return true if the column is full, false if it isn't
     */
        public boolean checkColumns(int choice, int column){
            if(tiles.getTile(choice-1,column).equals(Tiles.EMPTY))
                return true;
            else return false;
        }

    /**
     * Check if the matrix is full
     * @return true if the matrix is full, false if it isn't
     */
        public boolean checkEndGame(){
            if (num_of_tiles==30) return true;
            return false;
        }
     /**
     * Check if the matrix is empty
     * @return true if the matrix is empty, false if it isn't
     */
        public boolean checkEmptyBoard(){
            if (num_of_tiles==0) return true;
            return false;
        }

    /**
     * Check if the matrix is empty
     * @return true if the matrix is empty, false if it isn't
     */
    public boolean isEmpty(){
        for (int j=0; j<5; j++){
            if (!tiles.getTile(5, j).equals(Tiles.EMPTY)) return false;
        }
        return true;
    }

    /**
     * Insert the array of tiles in the matrix
     * in the column chosen
     * @param tiles tiles to put in the matrix
     * @param column index of the column for tiles
     */
    public void addTile(ArrayList<Tiles> tiles, int column){
            int pos=firstFree(column);
            if (tiles.size() == 1) {
                this.tiles.setTile(tiles.get(0), pos, column);
                num_of_tiles++;
            }
            else if (tiles.size()==2) {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos-1, column);
                num_of_tiles=num_of_tiles+2;
            }
            else {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos-1, column);
                this.tiles.setTile(tiles.get(2), pos-2, column);
                num_of_tiles=num_of_tiles+3;
            }
        }

    /**
     * Finds the first free position of the matrix in the given column
     * @param column index of the column
     * @return first free row of the matrix
     */

        private int firstFree (int column){
            for (int i=5;i>=0;i--){
                if(tiles.getTile(i,column).equals(Tiles.EMPTY)){
                    return i;
                }
            }
            return -1;
        }

        //ritorna l'ArrayList delle posizioni con colori adiacenti
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

    /**
     * Associate the number of adiacent tiles to the corresponding point
     * @param n number of adiacent tiles
     * @return corresponding point
     */
    private int vicinityPointCount(int n){
              switch (n){
                  case 3:
                      return 2;
                  case 4:
                      return 3;
                  case 5:
                      return 5;
                  default:
                      if (n>=6){
                          return 8;
                      }
                      else {
                          return 0;
                      }
              }
        }

    /**
     * Calculate vicinity points
     * @return vicinity point
     */
    public int checkVicinityPoints(){
        int adjPoint=0;
        int sameColor=0;
        boolean[][] visited = new boolean[6][5];

        //per ogni posizione
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                //se NON è stato ancora visitato && la tile!=EMPTY
                if (!visited[i][j] && !getTiles().getTile(i, j).equals(Tiles.EMPTY)) {
                    //inizializiamo coda
                    Queue<Point> queue=new LinkedList<>();
                    queue.add(new Point(i, j));
                    visited[i][j]=true;
                    sameColor=1;
                    //fino tanto che ci sono elementi nella coda
                    while (!queue.isEmpty()){
                        //legge elemento in testa e lo rimuove
                        Point current=queue.poll();
                        //controlliamo tutte le celle adiacenti con lo stesso colore
                        for (Point adj : getAdjacentSameColor(getTiles(), current)){
                            //se NON è stata ancora visitata
                            if (!visited[adj.x][adj.y]){
                                visited[adj.x][adj.y]=true; //ora è stata visitata
                                queue.add(adj);
                                sameColor++;
                            }
                        }
                        //sameColor ha il numero di tiles dello stesso colore vicine

                    }
                    adjPoint+=vicinityPointCount(sameColor);
                }

            }
        }

        return adjPoint;
    }


}

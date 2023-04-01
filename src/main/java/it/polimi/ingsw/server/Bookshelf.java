package it.polimi.ingsw.server;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Bookshelf {
        Matrix tiles;
        int num_of_tiles;

        public Bookshelf(){
            tiles=new Matrix(6,5);
            num_of_tiles=0;
            for( int i=0; i<tiles.getNumRows();i++){
                for( int j=0; j<tiles.getNumCols();j++){
                    tiles.setEmpty(i,j);
                }

            }
        }

        public Matrix getTiles(){
            return tiles;
        }

        public boolean checkColumns(int choice, int column){
            if(tiles.getTile(choice-1,column).equals(Tiles.EMPTY))
                return true;
            else return false;
        }


        public boolean checkEndGame(){
            for(int i=0;i<5;i++){
                if(tiles.getTile(5,i).equals(Tiles.EMPTY)){
                    return false;
                }
            }
            return true;
        }

        public boolean checkEmptyBoard(){
            for(int i=0; i<6; i++){
                for (int j=0; j<5; j++){
                    if (!tiles.getTile(i, j).equals(Tiles.EMPTY)) return false;
                }
            }
            return true;
        }

    public void addTile(ArrayList<Tiles> tiles, int column){
            int pos=firstFree(column);
            if (tiles.size() == 1) this.tiles.setTile(tiles.get(0), pos, column);
            else if (tiles.size()==2) {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos-1, column);
            }
            else {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos-1, column);
                this.tiles.setTile(tiles.get(2), pos-2, column);
            }
        }

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

        //ritorna i punti di una vicinanza
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

        //ritorna il totale dei punti dei punti di vicinanza
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

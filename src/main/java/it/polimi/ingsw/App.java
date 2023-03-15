package it.polimi.ingsw;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Matrix mat = new Matrix(3,3);
        mat.add(Tiles.BLUE,2,2);
        mat.add(Tiles.BLUE,1,1);
        mat.add(Tiles.BLUE,0,0);
        mat.add(Tiles.GREEN,1,2);
        mat.add(Tiles.GREEN,0,2);
        mat.print();
        System.out.println(mat.getTile(1,1));
        System.out.println(mat.getTile(0,1));
    }
}

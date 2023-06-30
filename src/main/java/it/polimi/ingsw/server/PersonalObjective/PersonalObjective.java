package it.polimi.ingsw.server.PersonalObjective;

import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.HashMap;

/**
 * Class to manage the personal objective.
 * <ul>
 *     <li>Create the personal objective;</li>
 *     <li>personal objective score.</li>
 * </ul>
 */
public class PersonalObjective {

    /**
     * Attribute used to save the personal objective color positions.
     */
    private final HashMap<Point, Tiles>card=new HashMap<>();
    /**
     * Attribute used to save the personal objective number.
     */
    private final int personalObjectiveNum;



    /**
     * Create the n-th personal objective.
     *
     * @param n the ID of Personal Objective to create.
     */
    public PersonalObjective(int n) {
        //mapping the card position
        readJSON(n);
        personalObjectiveNum=n;
    }



    /**
     * <strong>Getter</strong> -> return the number of the personal objective.
     *
     * @return the number of the personal objective.
     */
    public int getPersonalObjectiveNum() {
        return personalObjectiveNum;
    }

    /**
     * Getter -> return PersonalObjective card.
     *
     * @return the <i>HashMap</i> that represent the personal objective.
     */
    public HashMap<Point, Tiles> getCard(){
        return card;
    }



    /**
     * Method to read the PersonalObjective.json file and create the HashMap card.
     *
     * @param n the ID of Personal Objective to read.
     */
    private void readJSON(int n){
        JSONParser jsonParser=new JSONParser();

        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("PersonalObjective.json");
            //read all JSON file
            assert is != null;
            Object obj=jsonParser.parse(new InputStreamReader(is));
            JSONObject po=(JSONObject) obj;
            //read the specific Personal Objective
            JSONObject poDetails = switch (n) {
                case 1 -> (JSONObject) po.get("PO1");
                case 2 -> (JSONObject) po.get("PO2");
                case 3 -> (JSONObject) po.get("PO3");
                case 4 -> (JSONObject) po.get("PO4");
                case 5 -> (JSONObject) po.get("PO5");
                case 6 -> (JSONObject) po.get("PO6");
                case 7 -> (JSONObject) po.get("PO7");
                case 8 -> (JSONObject) po.get("PO8");
                case 9 -> (JSONObject) po.get("PO9");
                case 10 -> (JSONObject) po.get("PO10");
                case 11 -> (JSONObject) po.get("PO11");
                default -> (JSONObject) po.get("PO12");
            };
            //read the specific color
            JSONArray poDetailsColor;
            int x,y;

            poDetailsColor=(JSONArray) poDetails.get("PINK");
            //read the specific position for the color
            x=Integer.parseInt( poDetailsColor.get(0).toString() );
            y=Integer.parseInt( poDetailsColor.get(1).toString() );
            card.put(new Point(x,y), Tiles.PINK);

            poDetailsColor=(JSONArray) poDetails.get("BLUE");
            //read the specific position for the color
            x=Integer.parseInt( poDetailsColor.get(0).toString() );
            y=Integer.parseInt( poDetailsColor.get(1).toString() );
            card.put(new Point(x,y), Tiles.BLUE);

            poDetailsColor=(JSONArray) poDetails.get("GREEN");
            //read the specific position for the color
            x=Integer.parseInt( poDetailsColor.get(0).toString() );
            y=Integer.parseInt( poDetailsColor.get(1).toString() );
            card.put(new Point(x,y), Tiles.GREEN);

            poDetailsColor=(JSONArray) poDetails.get("WHITE");
            //read the specific position for the color
            x=Integer.parseInt( poDetailsColor.get(0).toString() );
            y=Integer.parseInt( poDetailsColor.get(1).toString() );
            card.put(new Point(x,y), Tiles.WHITE);

            poDetailsColor=(JSONArray) poDetails.get("YELLOW");
            //read the specific position for the color
            x=Integer.parseInt( poDetailsColor.get(0).toString() );
            y=Integer.parseInt( poDetailsColor.get(1).toString() );
            card.put(new Point(x,y), Tiles.YELLOW);

            poDetailsColor=(JSONArray) poDetails.get("LIGHT_BLUE");
            //read the specific position for the color
            x=Integer.parseInt( poDetailsColor.get(0).toString() );
            y=Integer.parseInt( poDetailsColor.get(1).toString() );
            card.put(new Point(x,y), Tiles.LIGHT_BLUE);


        }
        catch (IOException | ParseException e) {
            System.out.println("Json do not open");
        }
    }

    /**
     * Method to return the number of position that match with the PersonalObjective's card.
     *
     * @param bookshelf the bookshelf whose correct position number you want to calculate.
     * @return the number of position that match with the PersonalObjective's card.
     */
    private int checkCondition(Bookshelf bookshelf){
        int result=0;
        for (Point key: card.keySet()){
            if (bookshelf.getTiles().getTile(key).equals(card.get(key))){
                result++;
            }
        }
        return result;
    }

    /**
     * Method to return the score of PersonalObjective's card.
     *
     * @param player the player whose score you want to calculate.
     * @return the score of PersonalObjective's card.
     */
    public int personalObjectivePoint(Player player){
        return personalObjectivePoint(player.getBookshelf());
    }

    /**
     * Method to return the score of PersonalObjective's card.
     *
     * @param bookshelf the bookshelf whose score you want to calculate.
     * @return the score of PersonalObjective's card.
     */
    public int personalObjectivePoint(Bookshelf bookshelf){
        return switch (checkCondition(bookshelf)) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 9;
            case 6 -> 12;
            default -> 0;
        };
    }
}

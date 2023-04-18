package it.polimi.ingsw.server.PersonalObjective;

import it.polimi.ingsw.server.Tiles;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PersonalObjectiveJSON {
    public static void create1(){
        JSONObject obj=new JSONObject();

        HashMap<String, ArrayList> po1=new HashMap<>();
        ArrayList<Integer> lP1=new ArrayList<>();





        HashMap<String, ArrayList> m1=new HashMap<>();
        ArrayList<Integer> l1=new ArrayList<>();

        l1.add(0, 11); //x
        l1.add(1, 0); //y
        m1.put("PINK", l1);
        ArrayList<Integer> l2=new ArrayList<>();
        l2.add(0, 1); //x
        l2.add(1, 0); //y
        m1.put("BLUE", l2);

        HashMap<String, ArrayList> m2=new HashMap<>();
        ArrayList<Integer> l3=new ArrayList<>();

        l3.add(0, 1); //x
        l3.add(1, 0); //y
        m2.put("PINK", l3);
        ArrayList<Integer> l4=new ArrayList<>();
        l4.add(0, 1); //x
        l4.add(1, 1); //y
        m2.put("BLUE", l4);




        obj.put("PO1", m1);
        obj.put("PO2", m2);


        try {
            FileWriter file=new FileWriter("src/main/java/it/polimi/ingsw/server/PersonalObjective/PersonalObjective.json");
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void read1(){
        HashMap<Point, Tiles> card=new HashMap<>();
        JSONParser jsonParser=new JSONParser();

        try {
            FileReader reader=new FileReader("src/main/java/it/polimi/ingsw/server/PersonalObjective/PersonalObjective.json");

            Object obj=jsonParser.parse(reader);
            JSONObject po=(JSONObject) obj;

            JSONObject poDetails=(JSONObject) po.get("PO1");

            JSONArray poDetailsColor;
            int x,y;

            poDetailsColor=(JSONArray) poDetails.get("PINK");
            //read the specific position for the color
            x=Integer.parseInt( poDetailsColor.get(0).toString() );
            y=Integer.parseInt( poDetailsColor.get(1).toString() );
            card.put(new Point(x,y), Tiles.PINK);
            System.out.println(card);



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {


//        create1();
        read1();
    }

}

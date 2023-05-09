package it.polimi.ingsw.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.*;


public class JsonReader {
    private static JSONParser parser = new JSONParser();
    private FileReader reader;
    JSONObject config;

    public JsonReader(InputStream is) throws IOException, ParseException {
//        reader=new FileReader(url);
//        config= (JSONObject) parser.parse(reader);
         config=(JSONObject) parser.parse(new InputStreamReader(is));
    }

    public String getString(String reference){
        return (String) config.get(reference);
    }
    public int getInt(String reference){
        String Numero= (String) config.get(reference);
        return Integer.parseInt(Numero);
    }
}

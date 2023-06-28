package it.polimi.ingsw.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.*;

/**
 * Class used to read int number from a file Json.
 */
public class JsonReader {
    private final static JSONParser parser = new JSONParser();
    JSONObject config;

    /**
     * Initialize the class with the position of te file Json.
     * @param is position of the file json.
     * @throws IOException in case there are some problem with input.
     * @throws ParseException in case there are some problem with the parsing operation.
     */
    public JsonReader(InputStream is) throws IOException, ParseException {
         config=(JSONObject) parser.parse(new InputStreamReader(is));
    }

    /**
     * <strong>Getter</strong> -> Returns the int referenced to the reference given.
     * @param reference position of the int.
     * @return the number referenced from the reference.
     */
    public int getInt(String reference){
        String Numero= (String) config.get(reference);
        return Integer.parseInt(Numero);
    }
}

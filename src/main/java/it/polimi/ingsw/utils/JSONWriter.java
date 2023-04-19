package it.polimi.ingsw.utils;

import it.polimi.ingsw.server.Model.Board;
import it.polimi.ingsw.server.Model.Bookshelf;
import it.polimi.ingsw.server.VirtualView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;


public class JSONWriter {
    private JSONObject jsonFile;

    public JSONObject writeVirtualView(VirtualView view){
        JSONObject viewJson = new JSONObject();

        viewJson.put("Username", nameToJSON(view.getPlayerNames()));
        viewJson.put("Board", boardToJSON(view.getBoard()));
        viewJson.put("Bookshelf", bookShelfToJSON(view.getBookshelves()));
        viewJson.put("Points", pointsToJSON(view.getPrivatePoints(),view.getPublicPoints()));



        return viewJson;

    }

    private JSONObject boardToJSON(Matrix board){
        JSONObject boardJson = new JSONObject();

        for(int i=0;i<board.getNumRows();i++){
            for(int j=0;i< board.getNumCols();i++){


            }
        }


        return boardJson;
    }

    private JSONObject bookShelfToJSON(ArrayList<Matrix> bookshelf){
        JSONObject bookJSON = new JSONObject();

        return bookJSON;
    }

    private JSONObject pointsToJSON(ArrayList<Integer> privatePoints,ArrayList<Integer> publicPoints){
        JSONObject pointsJson = new JSONObject();

        return pointsJson;
    }
    private JSONObject nameToJSON(ArrayList<String> usernames){
        JSONObject usernamesJSON = new JSONObject();

        return usernamesJSON;
    }
}

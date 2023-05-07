package it.polimi.ingsw.client.Messages;

import org.json.simple.JSONObject;

public class ViewMessage extends Message {

    private JSONObject View;

    public JSONObject getView() {
        return View;
    }

    public void setView(JSONObject view) {
        View = view;
    }
}

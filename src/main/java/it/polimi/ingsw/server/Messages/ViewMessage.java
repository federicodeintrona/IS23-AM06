package it.polimi.ingsw.server.Messages;

import it.polimi.ingsw.utils.Matrix;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ViewMessage extends Message{

    private String objectName;
    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}

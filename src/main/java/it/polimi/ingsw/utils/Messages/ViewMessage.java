package it.polimi.ingsw.utils.Messages;

/**
 * <p> Class for message sent by client and server which will modify the client state</p>
 */
public class ViewMessage extends Message {

    private String objName;
    private Object content;

    /**
     * <strong>Getter</strong> -> Returns the content of the view message
     * @return Content of the view message
     */
    public Object getContent() {
        return content;
    }
    /**
     * <strong>Setter</strong> -> Sets the content of the view message
     * @param content content of the view message
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * <strong>Getter</strong> -> Returns the type of the view message
     * @return type of the view message
     */
    public String getObjName() {
        return objName;
    }

    /**
     * <strong>Setter</strong> -> Sets the type of the view message
     * @param objName type of the view message
     */
    public void setObjName(String objName) {
        this.objName = objName;
    }
}

package it.polimi.ingsw.client.View.GUI;

//serve per fare in modo che i controller di scena abbiano il riferimento al GUIController
//in modo che riescano a inviare messaggi al server
//e a ricevere messaggi da server
public class GUIControllerStatic {
    private static GUIController guiController;
    public static GUIController getGuiController() {
        return guiController;
    }
    public static void setGuiController(GUIController guiController) {
        GUIControllerStatic.guiController = guiController;
    }

}

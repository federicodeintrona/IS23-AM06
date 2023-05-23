package it.polimi.ingsw.client.View.GUI;

public class GUIFactory {
    private static GUIController guiController;

    public static GUIController getGuiController() {
        return guiController;
    }

    public static void setGuiController(GUIController guiController) {
        GUIFactory.guiController = guiController;
    }
}

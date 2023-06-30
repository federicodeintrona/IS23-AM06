package it.polimi.ingsw.client.View.GUI;

/**
 * Class that is used to make the scene controllers have the reference to the GUIController,
 * it is used to make so that they can send messages to the server and receive messages from the server.
 */
public class GUIControllerStatic {

    /**
     * The instance of GUIController.
     */
    private static GUIController guiController;

    /**
     * Default constructor.
     */
    public GUIControllerStatic() {
    }

    /**
     * <strong>Getter</strong> -> Returns the GUIController.
     *
     * @return the GUIController.
     */
    public static GUIController getGuiController() {
        return guiController;
    }


    /**
     * <strong>Setter</strong> -> Sets the GUIController.
     *
     * @param guiController the GUIController to set.
     */
    public static void setGuiController(GUIController guiController) {
        GUIControllerStatic.guiController = guiController;
    }

}

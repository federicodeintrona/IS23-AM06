package it.polimi.ingsw.client.View.GUI.Scene;

//enumeration per gestire le scene del gioco

/**
 * Enumeration of the Graphic User Interface scene
 * <ul>
 *     <li>Initial request scene;</li>
 *     <li>Login scene;</li>
 *     <li>number of player scene;</li>
 *     <li>waiting for other players scene;</li>
 *     <li>game scene;</li>
 *     <li>end game scene;</li>
 *     <li>end game for disconnection scene.</li>
 * </ul>
 */
public enum Scenes {

    InitialRequest("/fxml/InitialRequest.fxml", "Connection"),
    Login("/fxml/loginGriglia.fxml","Login Page"),
    NumOfPlayers("/fxml/numberOfPlayer.fxml","New Game"),
    Waiting("/fxml/waiting.fxml","Waiting Page"),
    Game("/fxml/gameCompleto.fxml","MyShelfie"),
    DisconnectionEnd("/fxml/EndGameForDisconnection.fxml","Winner Page"),
    Endgame("/fxml/endGame.fxml","Winner Page");


    /**
     * Attribute used to save the path of the scene.
     */
    private final String path;
    /**
     * Attribute used to save the title of the scene.
     */
    private final String title;



    /**
     * Initialize the correct path and the correct title of the scene.
     *
     * @param path the path of the scene.
     * @param title the title of the scene.
     */
    Scenes(String path, String title){
        this.path = path;
        this.title=title;
    }



    /**
     * <strong>Getter</strong> -> Returns the path of the scene.
     *
     * @return the path of the scene.
     */
    public  String getPath(){
        return path;
    }

    /**
     * <strong>Getter</strong> -> Returns the title of the scene.
     *
     * @return the title of the scene.
     */
    public String getTitle() {
        return title;
    }
}

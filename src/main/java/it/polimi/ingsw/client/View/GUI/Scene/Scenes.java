package it.polimi.ingsw.client.View.GUI.Scene;

//enumeration per gestire le scene del gioco
public enum Scenes {
    Login("/fxml/loginGriglia.fxml","Login Page"),
    NumOfPlayers("/fxml/numberOfPlayer.fxml","New Game"),
    Waiting("/fxml/waiting.fxml","Waiting Page"),
    Game("/fxml/gameCompleto.fxml","MyShelfie"),
    Endgame("/fxml/endGame.fxml","Winner Page");

    private final String name;
    private final String title;

    Scenes(String name,String title){
        this.name=name;
        this.title=title;
    }

    public  String getName(){
        return name;
    }

    public String getTitle() {
        return title;
    }
}

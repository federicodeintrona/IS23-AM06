package it.polimi.ingsw.client.View.GUI.Scene;

public enum Scenes {
    Login("/fxml/loginGriglia.fxml","Login Page"),
    NumOfPlayers("/fxml/numberOfPlayer.fxml","New Game"),
    Waiting("/fxml/waiting.fxml","Waiting Page"),
    Game("",""),
    Endgame("","");

    private String name;
    private String title;
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

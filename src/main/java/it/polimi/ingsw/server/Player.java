package it.polimi.ingsw.server;

import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;

public class Player {
    private final String username;
    private Bookshelf bookshelf = new Bookshelf();
    private PersonalObjective personalObjective;
    private int publicPoint;
    private int privatePoint;
    private int commonObjectivePoint;
    private int personalObjectivePoint;
    private int vicinityPoint;
    private int winnerPoint ;
    private boolean chair;

    public Player(String userName) {
        this.username = userName;
        publicPoint = 0;
        privatePoint = 0;
        commonObjectivePoint = 0;
        personalObjectivePoint = 0;
        vicinityPoint = 0;
        winnerPoint = 0;
    }

    public Player(String userName,boolean chair) {
        this.username = userName;
        publicPoint = 0;
        privatePoint = 0;
        commonObjectivePoint = 0;
        personalObjectivePoint = 0;
        vicinityPoint = 0;
        winnerPoint = 0;
        this.chair = chair;
    }
    public Bookshelf getBookshelf(){
        return bookshelf;
    }

    public int getPublicPoint() {
        return publicPoint;
    }

    private void setPublicPoint() {
        publicPoint= (vicinityPoint + commonObjectivePoint + winnerPoint) ;
    }

    public int getCommonObjectivePoint() {
        return commonObjectivePoint;
    }

    public void setCommonObjectivePoint(int commonObjectivePoint) {
        this.commonObjectivePoint = commonObjectivePoint;
    }

    public int getPrivatePoint() {
        return privatePoint;
    }

    public void setPrivatePoint() {
        privatePoint=personalObjectivePoint+vicinityPoint+commonObjectivePoint + winnerPoint;
    }

    public int getPersonalObjectivePoint() {
        return personalObjectivePoint;
    }

    public void setPersonalObjectivePoint(int personalObjectivePoint) {
        this.personalObjectivePoint = personalObjectivePoint;
    }

    public int getVicinityPoint() {
        return vicinityPoint;
    }

    public void setVicinityPoint(int vicinityPoint) {
        this.vicinityPoint = vicinityPoint;
    }

    public boolean isChair() {
        return chair;
    }

    public PersonalObjective getPersonalObjective() {
        return personalObjective;
    }

    public void setPersonalObjective(PersonalObjective personalObjective) {
        this.personalObjective = personalObjective;
    }

    public int getWinnerPoint() {
        return winnerPoint;
    }

    public void setWinnerPoint(int winnerPoint) {
        this.winnerPoint = winnerPoint;
    }

    public void setPersonalObjectivePoint(){
        personalObjectivePoint=personalObjective.personalObjectivePoint(this);
    }

    public String getUsername() {
        return username;
    }
}

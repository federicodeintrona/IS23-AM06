package it.polimi.ingsw;

public class Player {
    private final String userName;
    private Bookshelf bookshelf = new Bookshelf();
    private int publicPoint;
    private int privatePoint;
    private int commonObjectivePoint;
    private int personalObjectivePoint;
    private int vicinityPoint;
    private final boolean chair;

    public Player(String userName, boolean chair) {
        this.userName = userName;
        publicPoint = 0;
        privatePoint = 0;
        commonObjectivePoint = 0;
        personalObjectivePoint = 0;
        vicinityPoint = 0;
        this.chair=chair;
    }

    public int getPublicPoint() {
        return publicPoint;
    }

    private void setPublicPoint() {
        publicPoint= (vicinityPoint+commonObjectivePoint) ;
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
        privatePoint=personalObjectivePoint+vicinityPoint+commonObjectivePoint;
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
}

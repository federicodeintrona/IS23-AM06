package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;

/**
 * Class to save the information related to the player
 * and to count points of the player
 */

public class Player {
    private final String username;
    private boolean disconnected = false;
    private final Bookshelf bookshelf = new Bookshelf();
    private PersonalObjective personalObjective;
    private int privatePoint;
    private int commonObjectivePoint;
    private int personalObjectivePoint;
    private int vicinityPoint;
    private int winnerPoint ;
    private int publicPoint;


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
    }

    /**
     * Getter -> returns the bookshelf
     * @return Bookshelf of the player
     */
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    /**
     * Getter -> returns public points of the player
     * @return Public points of the player
     */
    public int getPublicPoint() {
        return publicPoint;
    }

    /**
     * Setter -> sets public points of the player
     */
    public void setPublicPoint() {
        publicPoint= (vicinityPoint + commonObjectivePoint + winnerPoint) ;
    }
    /**
     * Getter -> returns common objective points of the player
     * @return Common objective points of the player
     */

    public int getCommonObjectivePoint() {
        return commonObjectivePoint;
    }
    /**
     * Setter -> sets common objective points of the player
     */

    public void setCommonObjectivePoint(int commonObjectivePoint) {
        this.commonObjectivePoint = commonObjectivePoint;
    }
    /**
     * Getter -> returns private points of the player
     * @return Private points of the player
     */

    public int getPrivatePoint() {
        return privatePoint;
    }


    /**
     * Setter -> sets personal objective points of the player
     */
    public void setPersonalObjectivePoint(int personalObjectivePoint) {
        this.personalObjectivePoint = personalObjectivePoint;
    }


    /**
     * Setter -> sets vicinity points of the player
     */
    public void setVicinityPoint(int vicinityPoint) {
        this.vicinityPoint = vicinityPoint;
    }


    /**
     * Getter -> returns personal objective of the player
     * @return Personal objective of the player
     */

    public PersonalObjective getPersonalObjective() {
        return personalObjective;
    }
    /**
     * Setter -> sets personal objective of the player
     */

    public void setPersonalObjective(PersonalObjective personalObjective) {
        this.personalObjective = personalObjective;
    }

    /**
     * Setter -> sets winner points of the player
     */

    public void setWinnerPoint(int winnerPoint) {
        this.winnerPoint = winnerPoint;
    }

    /**
     * Getter -> returns username of the player
     *
     * @return Username of the player
     */

    public String getUsername() {
        return username;
    }

    /**
     * Method to update public points adding vicinity point, commonObjective point and winner point
     * and to updates private points adding vicinity point, commonObjective point ,winner point and personal objective point
     */
    public void updatePoints(){
        this.publicPoint= vicinityPoint + commonObjectivePoint + winnerPoint;
        this.privatePoint = personalObjectivePoint + vicinityPoint + commonObjectivePoint + winnerPoint;
    }

    /**
     * Getter -> returns the flag disconnected
     *
     * @return flag disconnected
     */
    public boolean isDisconnected() {
        return disconnected;
    }
    /**
     * Setter -> sets the flag disconnected
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }
}

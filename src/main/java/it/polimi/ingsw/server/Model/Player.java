package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;

public class Player {
    private final String username;
    private boolean disconnected = false;
    private Bookshelf bookshelf = new Bookshelf();
    private PersonalObjective personalObjective;
    private int privatePoint;
    private int commonObjectivePoint;
    private int personalObjectivePoint;
    private int vicinityPoint;
    private int winnerPoint ;

    private int publicPoint;
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

    /**
     * Returns the bookshelf
     * @return Bookshelf of the player
     */
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    /**
     * Returns public points of the player
     * @return Public points of the player
     */
    public int getPublicPoint() {
        return publicPoint;
    }

    /**
     * Sets public points of the player
     */
    public void setPublicPoint() {
        publicPoint= (vicinityPoint + commonObjectivePoint + winnerPoint) ;
    }
    /**
     * Returns common objective points of the player
     * @return Common objective points of the player
     */

    public int getCommonObjectivePoint() {
        return commonObjectivePoint;
    }
    /**
     * Sets common objective points of the player
     */

    public void setCommonObjectivePoint(int commonObjectivePoint) {
        this.commonObjectivePoint = commonObjectivePoint;
    }
    /**
     * Returns private points of the player
     * @return Private points of the player
     */

    public int getPrivatePoint() {
        return privatePoint;
    }

    /**
     * Sets private points of the player
     */
    public void setPrivatePoint() {
        privatePoint=personalObjectivePoint+vicinityPoint+commonObjectivePoint+winnerPoint;
    }
    /**
     * Returns personal objective points of the player
     * @return Personal objective points of the player
     */

    public int getPersonalObjectivePoint() {
        return personalObjectivePoint;
    }
    /**
     * Sets personal objective points of the player from personal objective
     */

    public void setPersonalObjectivePoint(){
        personalObjectivePoint=personalObjective.personalObjectivePoint(this);
    }
    /**
     * Sets personal objective points of the player
     */
    public void setPersonalObjectivePoint(int personalObjectivePoint) {
        this.personalObjectivePoint = personalObjectivePoint;
    }
    /**
     * Returns vicinity points of the player
     * @return Vicinity points of the player
     */

    public int getVicinityPoint() {
        return vicinityPoint;
    }

    /**
     * Sets vicinity points of the player
     */
    public void setVicinityPoint(int vicinityPoint) {
        this.vicinityPoint = vicinityPoint;
    }

    /**
     * Check if it has chair
     * @return True if chair is true
     */

    public boolean isChair() {
        return chair;
    }
    /**
     * Returns personal objective of the player
     * @return Personal objective of the player
     */

    public PersonalObjective getPersonalObjective() {
        return personalObjective;
    }
    /**
     * Sets personal objective of the player
     */

    public void setPersonalObjective(PersonalObjective personalObjective) {
        this.personalObjective = personalObjective;
    }
    /**
     * Returns winner points of the player
     * @return Winner points of the player
     */

    public int getWinnerPoint() {
        return winnerPoint;
    }
    /**
     * Sets winner points of the player
     */

    public void setWinnerPoint(int winnerPoint) {
        this.winnerPoint = winnerPoint;
    }

    /**
     * Returns username of the player
     * @return Username of the player
     */

    public String getUsername() {
        return username;
    }


    public void updatePoints(){
        this.publicPoint= vicinityPoint + commonObjectivePoint + winnerPoint;
        this.privatePoint = personalObjectivePoint + vicinityPoint + commonObjectivePoint + winnerPoint;
    }


    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }
}

package it.polimi.ingsw.server.Model;

import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;

/**
 * <p>Class used to manage player's personal information.</p>
 * In particular:
 *
 * <ul>
 *     <li>save the information related to the player(like username and bookshelf);</li>
 *     <li>to count points of the player.</li>
 * </ul>
 */

public class Player {
    private final String username;

    private final String CapUser;
    private boolean disconnected = false;
    private final Bookshelf bookshelf = new Bookshelf();
    private PersonalObjective personalObjective;
    private int privatePoint;
    private int commonObjectivePoint;
    private int personalObjectivePoint;
    private int vicinityPoint;
    private int winnerPoint ;
    private int publicPoint;

    /**
     * Initialize all the points to zero and the given username.
     *
     * @param username username.
     */
    public Player(String username) {
        this.username = username.toLowerCase();
        this.CapUser = username;
        publicPoint = 0;
        privatePoint = 0;
        commonObjectivePoint = 0;
        personalObjectivePoint = 0;
        vicinityPoint = 0;
        winnerPoint = 0;
    }

    /**
     * Initialize all the points to zero and the given username.
     *
     * @param username      username without caps.
     * @param Cap       username with caps.
     */
    public Player(String username,String Cap) {
        this.username = username;
        this.CapUser=Cap;
        publicPoint = 0;
        privatePoint = 0;
        commonObjectivePoint = 0;
        personalObjectivePoint = 0;
        vicinityPoint = 0;
        winnerPoint = 0;
    }

    /**
     * <strong>Getter</strong> -> returns the bookshelf.
     * @return <i>Bookshelf</i> of the player.
     */
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    /**
     * <strong>Getter</strong> -> returns public points of the player.
     * @return Public points of the player.
     */
    public int getPublicPoint() {
        return publicPoint;
    }

    /**
     * <strong>Setter</strong> -> sets public points of the player.
     */
    public void setPublicPoint() {
        publicPoint= (vicinityPoint + commonObjectivePoint + winnerPoint) ;
    }
    /**
     * <strong>Getter</strong> -> returns common objective points of the player.
     * @return Common objective points of the player.
     */

    public int getCommonObjectivePoint() {
        return commonObjectivePoint;
    }
    /**
     * <strong>Setter</strong> -> sets common objective points of the player.
     * @param commonObjectivePoint common objective points of the player.
     */

    public void setCommonObjectivePoint(int commonObjectivePoint) {
        this.commonObjectivePoint += commonObjectivePoint;
    }
    /**
     * <strong>Getter</strong> -> returns private points of the player.
     * @return Private points of the player.
     */

    public int getPrivatePoint() {
        return privatePoint;
    }


    /**
     * <strong>Setter</strong> -> sets personal objective points of the player.
     * @param personalObjectivePoint personal objective points of the player.
     */
    public void setPersonalObjectivePoint(int personalObjectivePoint) {
        this.personalObjectivePoint = personalObjectivePoint;
    }


    /**
     * <strong>Setter</strong> -> sets vicinity points of the player.
     * @param vicinityPoint vicinity points of the player.
     */
    public void setVicinityPoint(int vicinityPoint) {
        this.vicinityPoint = vicinityPoint;
    }


    /**
     * <strong>Getter</strong> -> returns personal objective of the player.
     * @return <i>PersonalObjective</i> of the player.
     */

    public PersonalObjective getPersonalObjective() {
        return personalObjective;
    }
    /**
     * <strong>Setter</strong> -> sets personal objective of the player.
     * @param personalObjective personal objective of the player.
     */

    public void setPersonalObjective(PersonalObjective personalObjective) {
        this.personalObjective = personalObjective;
    }

    /**
     * <strong>Setter</strong> -> sets winner points of the player.
     * @param winnerPoint winner point.
     */

    public void setWinnerPoint(int winnerPoint) {
        this.winnerPoint = winnerPoint;
    }

    /**
     * <strong>Getter</strong> -> returns username of the player.
     *
     * @return Username of the player.
     */

    public String getUsername() {
        return username;
    }

    /**
     * Method to update public points adding vicinity point, commonObjective point and winner point
     * and to updates private points adding vicinity point, commonObjective point ,winner point and personal objective point.
     */
    public void updatePoints(){
        this.publicPoint = vicinityPoint + commonObjectivePoint + winnerPoint;
        this.privatePoint = personalObjectivePoint + vicinityPoint + commonObjectivePoint + winnerPoint;
    }

    /**
     * <strong>Getter</strong> -> returns the flag disconnected.
     *
     * @return flag disconnected.
     */
    public boolean isDisconnected() {
        return disconnected;
    }
    /**
     * <strong>Setter</strong> -> sets the flag disconnected.
     * @param disconnected flag disconnected.
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    /**
     * Return the username of the player with original characters.
     * @return The username.
     */
    public String getCapUser() {
        return CapUser;
    }
}

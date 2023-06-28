package it.polimi.ingsw.utils;

/**
 * Enumeration which contains all constant number used in the programme.
 */
public enum Define {

    /**
     * Number of bookshelf's rows.
     */
    NUMBEROFROWS_BOOKSHELF (6),
    /**
     * Number of bookshelf's columns.
     */
    NUMBEROFCOLUMNS_BOOKSHELF (5),
    /**
     * Maximum number of tiles that the bookshelf can hold. (Number of rows * number of columns).
     */
    MAXNUMBEROFTILES_BOOKSHELF (NUMBEROFROWS_BOOKSHELF.i*NUMBEROFCOLUMNS_BOOKSHELF.i),
    /**
     * Number of board's rows.
     */
    NUMBEROFROWS_BOARD (9),
    /**
     * Number of board's columns.
     */
    NUMBEROFCOLUMNS_BOARD (9),
    /**
     * Number of personal objective.
     */
    NUMBEROFPERSONALOBJECTIVE (12),
    /**
     * Number of common objective in the game.
     */
    NUMBEROFCOMMONOBJECTIVE (2),
    /**
     * Maximum number of tiles per color in the sachet.
     */
    NUMBEROFTILEPERCOLOR_SACHET (22),
    /**
     * Number of tiles' color in the sachet.
     */
    NUMBEROFCOLOR_SACHET (6),
    /**
     * Maximum number of tiles in the sachet.
     */
    MAXNUMBEROFTILES_SACHET (NUMBEROFTILEPERCOLOR_SACHET.i* NUMBEROFCOLOR_SACHET.i),
    /**
     * Maximum number of tiles that a player can pick from the board.
     */
    MAXNUMBEROFTILESPICKABLE (3),
    /**
     * Number of image of the tile.
     */
    NUMBEROFTILEIMAGES(3);



    /**
     * The constant number.
     */
    private final int i;



    /**
     * Initialize the defined number with the right number.
     *
     * @param i the number to define.
     */
    Define(int i) {
        this.i=i;
    }



    /**
     * <strong>Getter</strong> -> Returns the corresponding content.
     *
     * @return Content of the defined number.
     */
    public int getI() {
        return i;
    }
}


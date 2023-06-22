package it.polimi.ingsw.utils;

/**
 * Enumeration which contains all constant number used in the programme
 */
public enum Define {
    NUMBEROFROWS_BOOKSHELF (6),
    NUMBEROFCOLUMNS_BOOKSHELF (5),
    MAXNUMBEROFTILES_BOOKSHELF (NUMBEROFROWS_BOOKSHELF.i*NUMBEROFCOLUMNS_BOOKSHELF.i),
    NUMBEROFROWS_BOARD (9),
    NUMBEROFCOLUMNS_BOARD (9),
    NUMBEROFPERSONALOBJECTIVE (12),
    NUMBEROFCOMMONOBJECTIVE (2),
    NUMBEROFTILEPERCOLOR_SACHET (22),
    NUMBEROFCOLOR_SACHET (6),
    MAXNUMBEROFTILES_SACHET (NUMBEROFTILEPERCOLOR_SACHET.i* NUMBEROFCOLOR_SACHET.i),
    MAXNUMBEROFTILESPICKABLE (3),
    NUMBEROFTILEIMAGES(3);


    private final int i;

    /**
     * Initialize the definied number with the right number
     * @param i the number to define
     */
    Define(int i) {
        this.i=i;
    }

    /**
     * <strong>Getter</strong> -> Returns the corresponding content
     * @return Content of the defined number
     */
    public int getI() {
        return i;
    }
}

//todo javadoc

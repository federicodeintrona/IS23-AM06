package it.polimi.ingsw.client.View.CLI;

/**
 * Enumeration of ANSI escaped codes used for Command User Interface's color.
 * <ul>
 *     <li>Reset;</li>
 *     <li>clear screen;</li>
 *     <li>foreground color code;</li>
 *     <li>background color code;</li>
 *     <li>bold;</li>
 *     <li>underline;</li>
 *     <li>reversed.</li>
 * </ul>
 */
public enum ColorCLI {

    /**
     * ANSI escaped code which reset all previous style.
     */
    RESET ("\u001b[0m"),

    /**
     * ANSI escaped code which moves cursor to home position (0, 0).
     */
    CLEAR ("\033[H\033[2J"),



    //Foreground Color Code
    /**
     * ANSI escaped code of the Tiles.NOT_ALLOWED's foreground color.
     */
    NOTALLOWED ("\u001b[30m"),

    /**
     * ANSI escaped code of the Tiles.GREEN's foreground color.
     */
    GREEN1 ("\u001B[38;2;145;165;65m"), //145;165;65

    /**
     * ANSI escaped code of the Tiles.YELLOW's foreground color.
     */
    YELLOW1 ("\u001B[38;2;223;159;69m"), //223;159;69

    /**
     * ANSI escaped code of the Tiles.BLUE's foreground color.
     */
    BLUE1 ("\u001B[38;2;0;103;105m"), //0;103;105

    /**
     * ANSI escaped code of the Tiles.PINK's foreground color.
     */
    PINK1 ("\u001B[38;2;204;77;124m"), //204;77;124

    /**
     * ANSI escaped code of the Tiles.LIGHT_BLUE's foreground color.
     */
    LIGHT_BLUE1 ("\u001B[38;2;106;183;183m"), //106;183;183

    /**
     * ANSI escaped code of the Tiles.WHITE's foreground color.
     */
    WHITE1 ("\u001B[38;2;237;226;191m"), //237;226;191

    /**
     * ANSI escaped code of the Tiles.EMPTY's foreground color.
     */
    EMPTY1 ("\u001b[38;2;77;48;35m"), //77;48;35

    /**
     * ANSI escaped code of the Tiles.POSITION's foreground color (used by print common objective in Command Line Interface).
     */
    POSITION ("\u001b[31;1m"),

    /**
     * ANSI escaped code used for title and common objective.
     */
    RED ("\u001b[31m"),



    //Background Color Code
    /**
     * ANSI escaped code of the Tiles.NOT_ALLOWED's background color.
     */
    NOTALLOWEDBG (""),

    /**
     * ANSI escaped code of the Tiles.GREEN's background color.
     */
    GREENBG1 ("\u001B[48;2;145;165;65m"), //145;165;65

    /**
     * ANSI escaped code of the Tiles.YELLOW's background color.
     */
    YELLOWBG1 ("\u001B[48;2;223;159;69m"), //223;159;69

    /**
     * ANSI escaped code of the Tiles.BLUE's background color.
     */
    BLUEBG1 ("\u001B[48;2;0;103;105m"), //0;103;105

    /**
     * ANSI escaped code of the Tiles.PINK's background color.
     */
    PINKBG1 ("\u001B[48;2;204;77;124m"), //204;77;124

    /**
     * ANSI escaped code of the Tiles.LIGHT_BLUE's background color.
     */
    LIGHT_BLUEBG1 ("\u001B[48;2;106;183;183m"), //106;183;183

    /**
     * ANSI escaped code of the Tiles.WHITE's background color.
     */
    WHITEBG1 ("\u001B[48;2;237;226;191m"), //237;226;191

    /**
     * ANSI escaped code of the Tiles.EMPTY's background color.
     */
    EMPTYBG1 ("\u001b[48;2;77;48;35m"), //77;48;35

    /**
     * ANSI escaped code of the Tiles.POSITION's background color (used by print common objective in Command Line Interface).
      */
    POSITIONBG ("\u001b[41;1m"),



    //Bold, UnderLine, Reversed
    /**
     * ANSI escaped code used for bold.
     */
    BOLD ("\u001b[1m"),

    /**
     * ANSI escaped code used for underline.
     */
    UNDERLINE ("\u001b[4m"),

    /**
     * ANSI escaped code used for reversed.
     */
    REVERSED ("\u001b[7m"),
    ;


    /**
     * Attribute used to save the ANSI escape code.
     */
    private final String code;


    /**
     * Initialize the correct ANSI escape code.
     *
     * @param code the ANSI escape code.
     */
    ColorCLI(String code) {
        this.code = code;
    }

    /**
     * Method to return a string representation of the object.
     *
     * @return the ANSI escape code of the color.
     */
    @Override
    public String toString() {
        return code;
    }
}


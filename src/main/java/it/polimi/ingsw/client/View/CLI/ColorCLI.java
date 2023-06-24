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
    //reset all style
    RESET ("\u001b[0m"),
    //moves cursor to home position (0, 0)
    CLEAR ("\033[H\033[2J"),



    //Foreground Color Code
    NOTALLOWED ("\u001b[30m"),
    GREEN1 ("\u001B[38;2;145;165;65m"), //145;165;65
    YELLOW1 ("\u001B[38;2;223;159;69m"), //223;159;69
    BLUE1 ("\u001B[38;2;0;103;105m"), //0;103;105
    PINK1 ("\u001B[38;2;204;77;124m"), //204;77;124
    LIGHT_BLUE1 ("\u001B[38;2;106;183;183m"), //106;183;183
    WHITE1 ("\u001B[38;2;237;226;191m"), //237;226;191
    EMPTY1 ("\u001b[38;2;77;48;35m"), //77;48;35
    POSITION ("\u001b[31;1m"),
    RED ("\u001b[31m"),



    //Background Color Code
    NOTALLOWEDBG (""),
    GREENBG1 ("\u001B[48;2;145;165;65m"), //145;165;65
    YELLOWBG1 ("\u001B[48;2;223;159;69m"), //223;159;69
    BLUEBG1 ("\u001B[48;2;0;103;105m"), //0;103;105
    PINKBG1 ("\u001B[48;2;204;77;124m"), //204;77;124
    LIGHT_BLUEBG1 ("\u001B[48;2;106;183;183m"), //106;183;183
    WHITEBG1 ("\u001B[48;2;237;226;191m"), //237;226;191
    EMPTYBG1 ("\u001b[48;2;77;48;35m"), //77;48;35
    POSITIONBG ("\u001b[41;1m"),



    //Bold, UnderLine, Reversed
    BOLD ("\u001b[1m"),
    UNDERLINE ("\u001b[4m"),
    REVERSED ("\u001b[7m"),
    ;


    /**
     * Attribute used to save the ANSI escape code
     */
    private final String code;


    /**
     * Initialize the correct ANSI escape code
     *
     * @param code  ANSI escape code
     */
    ColorCLI(String code) {
        this.code = code;
    }

    /**
     * Method to return a string representation of the object
     *
     * @return String   return the ANSI escape code of the color
     */
    @Override
    public String toString() {
        return code;
    }
}


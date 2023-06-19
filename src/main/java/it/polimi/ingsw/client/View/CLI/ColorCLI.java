package it.polimi.ingsw.client.View.CLI;

/**
 * Enumeration of ANSI escaped codes used for Command User Interface' color
 * - reset
 * - clear screen
 * - foreground color code
 * - background color code
 * - bold
 * - underline
 * - reversed
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







    GREEN ("\u001B[38;5;46m"),
    YELLOW ("\u001B[38;5;220m"),
    BLUE ("\u001B[38;5;21m"),
    PINK ("\u001B[38;5;204m"),
    LIGHT_BLUE ("\u001B[38;5;39m"),
    WHITE ("\u001B[38;5;231m"),
    EMPTY ("\u001b[38;5;94m"),


    GREENBG ("\u001B[48;5;46m"), //145;165;65
    YELLOWBG ("\u001B[48;5;220m"), //223;159;69
    BLUEBG ("\u001B[48;5;21m"), //0;103;105
    PINKBG ("\u001B[48;5;204m"), //204;77;124
    LIGHT_BLUEBG ("\u001B[48;5;39m"), //106;183;183
    WHITEBG ("\u001B[48;5;231m"), //237;226;191
    EMPTYBG ("\u001b[48;5;94m"),
    EMPTYBG2 ("\u001b[48;2;103;76;65m"), //103;76;65

    ;


    /**
     * Attribute used to save the ANSI escape code
     */
    private final String code;


    /**
     * Constructor --> assign the correct ANSI escape code
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

//todo scegliere colore

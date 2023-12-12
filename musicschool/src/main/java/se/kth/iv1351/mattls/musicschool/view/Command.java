package se.kth.iv1351.mattls.musicschool.view;

/**
 * Defines all commands that can be performed by a user of the chat application.
 */
public enum Command {
     /**
     * Lists all availible instruments.
     */
    LIST,
    /**
     * Rents an available instrument.
     */
    RENT,
    /**
     * Terminates an rented instrument.
     */
    TERMINATE,
    /**
     * Lists all commands.
     */
    HELP,
    /**
     * Leave the chat application.
     */
    QUIT,
    /**
     * None of the valid commands above was specified.
     */
    ILLEGAL_COMMAND
}

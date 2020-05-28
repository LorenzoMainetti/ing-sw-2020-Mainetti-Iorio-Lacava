
package it.polimi.ingsw.PSP41.view.CLIPackage;

import it.polimi.ingsw.PSP41.model.Color;

public enum ColorCLI {
    ANSI_RED("\u001B[31m"), ANSI_BLUE("\u001B[34m"), ANSI_YELLOW("\u001B[33m"), ANSI_MAGENTA("\u001B[35m");

    public static final String RESET = "\u001B[0m";
    public static final String CLEAR_LINE = "\33[1A\33[2K";

    private String escape;

    ColorCLI( String escape) {
        this.escape = escape;
    }

    public String getEscape() {
        return escape;
    }

    @Override
    public String toString () {
        return escape;
    }

    public static ColorCLI colorCLI(Color color) {
        switch (color) {
            case RED:
                return ANSI_RED;
            case BLUE:
                return ANSI_BLUE;
            case YELLOW:
                return ANSI_YELLOW;
            default:
                return ANSI_MAGENTA;
        }
    }

}
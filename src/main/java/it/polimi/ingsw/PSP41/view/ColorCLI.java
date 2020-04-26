package it.polimi.ingsw.PSP41.view;

public enum ColorCLI {
    ANSI_RED("\u001B[31m"), ANSI_BLUE("\u001B[34m"), ANSI_YELLOW("\u001B[33m"), ANSI_GREEN("\u001B[32m");

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

}

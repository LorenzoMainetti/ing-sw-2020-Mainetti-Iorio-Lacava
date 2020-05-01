package it.polimi.ingsw.PSP41.model;


public enum Color {
    RED, YELLOW, BLUE;

    public Color next() {
        return values()[(ordinal()+1) % values().length];
    }
}
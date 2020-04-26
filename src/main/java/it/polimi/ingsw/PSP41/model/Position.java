package it.polimi.ingsw.PSP41.model;

public class Position {
    private final int posRow;
    private final int posColumn;

    public Position(int row, int column) {
        this.posRow = row;
        this.posColumn = column;
    }

    public int getPosRow() {
        return posRow;
    }

    public int getPosColumn() {
        return posColumn;
    }
}

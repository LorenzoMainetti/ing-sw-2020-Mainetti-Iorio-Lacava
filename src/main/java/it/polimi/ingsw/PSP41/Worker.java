package it.polimi.ingsw.PSP41;

public class Worker {
    private Color color;
    private int row;
    private int column;
    private int number;

    /**
     * Worker constructor: it sets the color of the worker, the position will be set later by the player
     * @param color worker color decided by the player
     */
    public Worker(Color color, int number) {
        this.color = color;
        this.row = -1;
        this.column = -1;
        this.number = number;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() { return column; }

    public int getNumber() {
        return number;
    }

    public void setPosition(int row, int column) {
        //exception if inputs are not part of the Board
        this.row = row;
        this.column = column;
    }

    public Color getColor() {
        return color;
    }
}
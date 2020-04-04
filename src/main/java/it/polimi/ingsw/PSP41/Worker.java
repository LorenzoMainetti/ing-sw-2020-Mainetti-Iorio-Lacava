package it.polimi.ingsw.PSP41;

public class Worker {
    private Color color;
    private int row;
    private int column;

    /**
     * Worker constructor: it sets the color of the worker, the position will be set later by the player
     * @param color worker color decided by the player
     */
    public Worker(Color color) {
        this.color = color;
        this.row = -1;
        this.column = -1;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() { return column; }

    public Color getColor() {
        return color;
    }

    /**
     * Set worker position equals to input cell
     * @param board board state
     * @param row of the worker position
     * @param column of the worker position
     */
    public void setPosition(Board board, int row, int column) throws IllegalStateException, ArrayIndexOutOfBoundsException  {
        if(board.getCell(row, column).isOccupied())
            throw new IllegalStateException("Position taken.");
        else if (!board.inBound(row, column))
            throw new ArrayIndexOutOfBoundsException("Invalid position.");
        else {
            this.row = row;
            this.column = column;
        }
    }

}
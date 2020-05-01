package it.polimi.ingsw.PSP41.model;


import it.polimi.ingsw.PSP41.observer.ModelObservable;

import java.io.Serializable;


public class Worker extends ModelObservable implements Serializable {

    private static final long serialVersionUID = -3733473510084905679L;
    private final Color color;
    private final int number;
    private int row;
    private int column;

    /**
     * Worker constructor: it sets worker's color and number, the position will be set later by the player
     * @param color worker color decided by the player
     */
    public Worker(Color color, int number) {
        this.color = color;
        this.number = number;
        row = -1;
        column = -1;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() { return number; }

    public int getRow() {
        return row;
    }

    public int getColumn() { return column; }

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
            board.getCell(row, column).attachWorker(this);

            notifyBoard(board.clone());
        }
    }

}
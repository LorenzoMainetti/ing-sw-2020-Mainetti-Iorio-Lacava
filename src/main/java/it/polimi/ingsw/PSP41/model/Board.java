package it.polimi.ingsw.PSP41.model;

import java.io.Serializable;

public class Board implements Cloneable, Serializable {
    private static final long serialVersionUID = -7299950514822114480L;

    private final Cell[][] grid;
    public final static int MAX_SIZE = 5;

    /**
     * Initialize the Board creating a 5x5 matrix of Cells
     */
    public Board() {
        grid = new Cell[MAX_SIZE][MAX_SIZE];
        for(int i=0; i<MAX_SIZE; i++) {
            for(int j=0; j<MAX_SIZE; j++) { grid[i][j] = new Cell(); }
        }
    }

    /**
     * @return the matrix of Cells
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Getter for a Cell in the Board
     * @param row of the wanted Cell
     * @param column of the wanted Cell
     * @return the Cell identified by the given row and column
     */
    public Cell getCell(int row, int column) throws ArrayIndexOutOfBoundsException {
        if (inBound(row, column))
            return grid[row][column];
        else
            throw new ArrayIndexOutOfBoundsException("Invalid position.");
    }

    /**
     * Checks if the given position is part of the grid
     * @param row given row
     * @param column given column
     * @return true if it is part of the grid
     */
    public boolean inBound(int row, int column) {
        return (row >= 0 && row < MAX_SIZE && column >= 0 && column < MAX_SIZE);
    }

    /**
     * Redefinition of the clone method of the class Object
     * @return a shallow copy of the Board
     */
    @Override
    public Board clone() {
        try {
            return (Board) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}



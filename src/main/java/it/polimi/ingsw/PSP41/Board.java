package it.polimi.ingsw.PSP41;


public class Board {
    private Cell[][] grid;
    private static final int MAX_SIZE = 5;

    /**
     * Initialize the Board creating a 5x5 matrix of Cells
     */
    public Board() {
        grid = new Cell[MAX_SIZE][MAX_SIZE];
        for(int i=0; i<MAX_SIZE; i++) {
            for(int j=0; j<MAX_SIZE; j++) { grid[i][j] = new Cell(); }
        }
    }

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
        if (row < 0 || row >= MAX_SIZE || column < 0 || column >= MAX_SIZE )
            throw new ArrayIndexOutOfBoundsException("Invalid position.");
        else
            return grid[row][column];
    }

    public boolean inBound( int row, int column){
        return (row >= 0 && row < MAX_SIZE && column >= 0 && column < MAX_SIZE);
    }

}



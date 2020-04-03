package it.polimi.ingsw.PSP41;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ActionManager {

    public ActionManager() {
    }

    /**
     * Find all the neighbouring Cells of a given Cell position, considering the perimeter
     * @param row of the Cell of which I need the neighbors
     * @param column of the Cell of which I need the neighbors
     * @return list of the neighbouring Cells of the given Cell
     */

    private List<Cell> getNeighbouringCells(Board board, int row, int column) {
        List<Cell> neighbouringCells = new ArrayList<>();
        final int MIN_X = 0, MIN_Y = 0, MAX_X = 4, MAX_Y = 4;
        int startPosX = (row - 1 < MIN_X) ? row : row - 1;
        int startPosY = (column - 1 < MIN_Y) ? column : column - 1;
        int endPosX = (row + 1 > MAX_X) ? row : row + 1;
        int endPosY = (column + 1 > MAX_Y) ? column : column + 1;

        for (int i=startPosX; i<=endPosX; i++) {
            for (int j=startPosY; j<=endPosY; j++) {
                //passando una copia evito modifiche alla board (board.getCell(i, j).clone())
                //meglio usare unmodifiable List
                if(!(i == row && j == column)) neighbouringCells.add(board.getCell(i, j));
            }
        }
        return neighbouringCells;
    }


    /**
     * Find all the valid Cells where the Worker, in the specified position, can move
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Cells where it is allowed to move in
     */
    public List<Cell> getValidMoves(Board board, int row, int column) {
        int currLevel = board.getCell(row, column).getLevel();

        return this.getNeighbouringCells(board, row, column).
                stream().
                filter(moves -> (moves.getLevel() < currLevel + 2)).
                filter(moves -> !moves.isDome()).
                filter(moves -> !moves.isOccupied()).
                collect(Collectors.toUnmodifiableList());
    }

    /**
     * Find all the valid Cells where the Worker, in the specified position, can build
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Cells where it is allowed to build
     */
    public List<Cell> getValidBuilds(Board board, int row, int column) {
        return this.getNeighbouringCells(board, row, column).
                stream().
                filter(builds -> !builds.isDome()).
                filter(builds -> !builds.isOccupied()).
                collect(Collectors.toUnmodifiableList());
    }

    /**
     * Find all the Cells where are placed Opponent's workers
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Cells where are placed Opponent's workers
     */
    public List<Cell> getNeighbouringOpponentWorkers(Board board, int row, int column) {
        Color currColor = board.getCell(row, column).getWorker().getColor();

        return this.getNeighbouringCells(board, row, column).
                stream().
                filter(Cell::isOccupied).
                filter(pos -> (pos.getWorker().getColor() != currColor)).
                collect(Collectors.toUnmodifiableList());
    }

}


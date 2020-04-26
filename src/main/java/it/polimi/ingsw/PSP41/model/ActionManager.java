package it.polimi.ingsw.PSP41.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//ritornare lista di posizioni invece che lista di celle

public class ActionManager {

    /**
     * Find all the neighbouring Positions of a given Cell position, considering the perimeter
     * @param row of the Cell of which I need the neighbors
     * @param column of the Cell of which I need the neighbors
     * @return list of the neighbouring Positions of the given Cell
     */
    private List<Position> getNeighbouringCells(int row, int column) {
        List<Position> neighbouringCells = new ArrayList<>();
        final int MIN_X = 0, MIN_Y = 0, MAX_X = 4, MAX_Y = 4;
        int startPosX = (row - 1 < MIN_X) ? row : row - 1;
        int startPosY = (column - 1 < MIN_Y) ? column : column - 1;
        int endPosX = (row + 1 > MAX_X) ? row : row + 1;
        int endPosY = (column + 1 > MAX_Y) ? column : column + 1;

        for (int i=startPosX; i<=endPosX; i++) {
            for (int j=startPosY; j<=endPosY; j++) {
                if(!(i == row && j == column)) neighbouringCells.add(new Position(i, j));
            }
        }
        return neighbouringCells;
    }


    /**
     * Find all the valid Positions where the Worker, in the specified position, can move
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @param notHigher report if to return all valid moves or just the ones on a same/lower level
     * @return list of the Positions where it is allowed to move in
     */
    public List<Position> getValidMoves(Board board, int row, int column, boolean notHigher) {
        int currLevel = board.getCell(row, column).getLevel();

        return getNeighbouringCells(row, column).
                stream().
                filter(p -> (notHigher ? board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() <= currLevel : board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() < currLevel + 2)).
                filter(p -> !board.getCell(p.getPosRow(), p.getPosColumn()).isDome()).
                filter(p -> !board.getCell(p.getPosRow(), p.getPosColumn()).isOccupied()).
                collect(Collectors.toList());
    }

    /**
     * Find all the valid Positions where the Worker, in the specified position, can build
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Positions where it is allowed to build
     */
    public List<Position> getValidBuilds(Board board, int row, int column) {
        return getNeighbouringCells(row, column).
                stream().
                filter(p -> !board.getCell(p.getPosRow(), p.getPosColumn()).isDome()).
                filter(p -> !board.getCell(p.getPosRow(), p.getPosColumn()).isOccupied()).
                collect(Collectors.toList());
    }

    /**
     * Find all the Positions where are placed Opponent's workers
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @param notHigher report if to return all valid moves or just the ones on a same/lower level
     * @return list of the Positions where are placed Opponent's workers
     */
    // Metodo utilizzato per Minotaur
    public List<Position> getNeighbouringOpponentWorkers(Board board, int row, int column, boolean notHigher) {
        Color currColor = board.getCell(row, column).getWorker().getColor();
        int currLevel = board.getCell(row, column).getLevel();

        return getNeighbouringCells(row, column).
                stream().
                filter(p -> board.getCell(p.getPosRow(), p.getPosColumn()).isOccupied()).
                filter(p -> (notHigher ? board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() <= currLevel : board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() < currLevel + 2)).
                filter(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getWorker().getColor() != currColor)).
                collect(Collectors.toUnmodifiableList());
    }

    /**
     * Find all the Positions where are placed Opponent's workers and where is possible to build from
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @param notHigher report if to return all valid moves or just the ones on a same/lower level
     * @return list of the Positions
     * where are placed Opponent's workers surrounded by at least one cell where building is possible
     */
    // Metodo utilizzato per Apollo
    public List<Position> getActiveOpponentWorkers(Board board, int row, int column, boolean notHigher) {
        Color currColor = board.getCell(row, column).getWorker().getColor();
        int currLevel = board.getCell(row, column).getLevel();

        return getNeighbouringCells(row, column).
                stream().
                filter(p -> board.getCell(p.getPosRow(), p.getPosColumn()).isOccupied()).
                filter(p -> (notHigher ? board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() <= currLevel : board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() < currLevel + 2)).
                filter(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getWorker().getColor() != currColor)).
                filter(p -> !getValidBuilds(board, board.getCell(p.getPosRow(), p.getPosColumn()).getWorker().getRow(), board.getCell(p.getPosRow(), p.getPosColumn()).getWorker().getColumn()).isEmpty()).
                collect(Collectors.toUnmodifiableList());
    }

}


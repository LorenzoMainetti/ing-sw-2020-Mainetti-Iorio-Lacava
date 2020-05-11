package it.polimi.ingsw.PSP41.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
     * @return list of the Positions where it is allowed to move in
     */
    public List<Position> getValidMoves(Board board, int row, int column) {
        int currLevel = board.getCell(row, column).getLevel();

        return getNeighbouringCells(row, column).
                stream().
                filter(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() < currLevel + 2)).
                filter(p -> !board.getCell(p.getPosRow(), p.getPosColumn()).isDome()).
                filter(p -> !board.getCell(p.getPosRow(), p.getPosColumn()).isOccupied()).
                collect(Collectors.toList());
    }

    /**
     * Find all the valid Positions that are on a not higher level than the specified one
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Positions where it is allowed to move in on a same or lower level
     */
    public List<Position> getNotHigherCells(Board board, int row, int column) {
        return getValidMoves(board, row, column).
                stream().
                filter(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() <=
                        board.getCell(row, column).getLevel())).
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
     * Find all the valid Positions where the Worker, in the specified position, can remove a block
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Positions where it is allowed to remove block
     */
    public List<Position> getValidRemovableBlocks(Board board, int row, int column) {
        return getValidBuilds(board, row, column).
                stream().
                filter(p -> board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() > 0).
                collect(Collectors.toUnmodifiableList());
    }

    /**
     * Find all the Positions where are placed Opponent's workers
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Positions where are placed Opponent's workers
     */
    private List<Position> findOpponentWorker(Board board, int row, int column) {
        Color currColor = board.getCell(row, column).getWorker().getColor();
        int currLevel = board.getCell(row, column).getLevel();

        return getNeighbouringCells(row, column).
                stream().
                filter(p -> board.getCell(p.getPosRow(), p.getPosColumn()).isOccupied()).
                filter(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() < currLevel + 2)).
                filter(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getWorker().getColor() != currColor)).
                collect(Collectors.toList());
    }

    /**
     * Make the list of Positions unmodifiable
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Positions where are placed Opponent's workers
     */
    public List<Position> getOpponentWorkers(Board board, int row, int column) {
        return findOpponentWorker(board, row, column).
                stream().
                collect(Collectors.toUnmodifiableList());
    }

    /**
     * Find all the Positions where are placed Opponent's workers and where is possible to build from
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return list of the Positions
     * where are placed Opponent's workers surrounded by at least one cell where building is possible
     */
    public List<Position> getActiveOpponentWorkers(Board board, int row, int column) {
        return findOpponentWorker(board, row, column).
                stream().
                filter(p -> !getValidBuilds(board, p.getPosRow(), p.getPosColumn()).isEmpty()).
                collect(Collectors.toUnmodifiableList());
    }

    /**
     * Given a Player's worker find the other one
     * @param board current board state
     * @param row current Worker's row
     * @param column current Worker's column
     * @return Position where it is the other Worker of the same Player
     */
    public Position getOtherWorker(Board board, int row, int column) {
        Color color = board.getCell(row, column).getWorker().getColor();

        List<Position> otherWorker = Arrays.stream(board.getGrid()).
                flatMap(Arrays::stream).
                filter(Cell::isOccupied).
                filter(cell -> cell.getWorker().getColor()==color).
                filter(cell -> cell.getWorker().getRow()!=row || cell.getWorker().getColumn()!=column).
                map(cell -> new Position(cell.getWorker().getRow(), cell.getWorker().getColumn())).
                collect(Collectors.toList());

        return otherWorker.get(0);


        /*for(int i=0; i<Board.MAX_SIZE; i++) {
            for(int j=0; j<Board.MAX_SIZE; j++) {
                if(board.getCell(i, j).isOccupied()) {
                    if(board.getCell(i, j).getWorker().getColor() == color && (i!=row || j!=column)) {
                        return new Position(i, j);
                    }
                }
            }
        }
        return null;*/
    }

}


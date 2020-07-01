package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.ArrayList;
import java.util.List;

public class Minotaur extends GodPower {

    public Minotaur() {
        affectPhase = TurnPhase.NONE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true if the chosen worker is neighboring an opponent's worker that has an unoccupied position behind it
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        return !am.getOpponentWorkers(board, worker.getRow(), worker.getColumn()).isEmpty() && !getPowerCells(board, worker).isEmpty();
    }

    /**
     * Finds workers placed in a cell where the godPower is actionable, if there are any
     * @param board current board state
     * @param player chosen worker
     * @return no available workers (-1), worker1 (1), worker2 (2), user's choice (0)
     */
    @Override
    public int godPowerRequired(Board board, Player player) {
        if(!isActionable(board, player.getWorker1()) && !isActionable(board, player.getWorker2()))
            return -1;
        else {
            if (!isActionable(board, player.getWorker1()))
                return 2;
            else if (!isActionable(board, player.getWorker2()))
                return 1;
            else
                return 0;
        }
    }

    /**
     * When actionable adds pushable opponent's workers' positions to the available ones
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isActionable(board, worker) && phase==TurnPhase.MOVE) {
            positions.addAll(getPowerCells(board, worker));
        }
    }

    /**
     * Minotaur strategy for MOVE: "Your Worker may move into an opponent Worker's space,
     * if their Worker can be forced one space straight backwards to an unoccupied space at any level"
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    @Override
    public void move(Worker worker, Board board, int row, int column) {
        if(board.getCell(row, column).isOccupied()) {
            Worker opponentWorker = board.getCell(row, column).getWorker();
            int deltaRow = row - worker.getRow();
            int deltaColumn = column - worker.getColumn();
            // Move of the opponent's worker "back" of one cell
            super.move(opponentWorker, board, row + deltaRow, column + deltaColumn);
            // Move of my worker into the first cell occupied by the opponent's worker
        }
        super.move(worker, board, row, column);
    }

    /**
     * @param board board state
     * @param worker chosen worker
     * @return list of pushable workers' positions
     */
    private List<Position> getPowerCells(Board board, Worker worker) {
        int deltaRow;
        int deltaColumn;
        List<Position> powerCells = new ArrayList<>();
        // show which cells between the occupied ones are eligible to force "back" the opponent's worker
        for (Position pos: am.getOpponentWorkers(board, worker.getRow(), worker.getColumn())) {
            deltaRow = pos.getPosRow() - worker.getRow();
            deltaColumn = pos.getPosColumn() - worker.getColumn();
            // if the cell "back" to the opponent's worker is free and valid I add the opponent's worker to the list
            // of cells to choose from for my worker's move
            if (0 <= worker.getRow() + 2*deltaRow && worker.getRow() + 2*deltaRow <= 4 &&
                    0 <= worker.getColumn() + 2*deltaColumn && worker.getColumn() + 2*deltaColumn <= 4 &&
                    !board.getCell(worker.getRow() + 2*deltaRow, worker.getColumn() + 2*deltaColumn).isOccupied() &&
                    !board.getCell(worker.getRow() + 2*deltaRow, worker.getColumn() + 2*deltaColumn).isDome()) {
                powerCells.add(pos);
            }
        }
        return powerCells;
    }

}
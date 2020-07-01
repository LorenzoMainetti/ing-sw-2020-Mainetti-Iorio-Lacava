package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Apollo extends GodPower {

    public Apollo() {
        affectPhase = TurnPhase.NONE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true if the chosen worker is neighboring an opponent's worker that is able to move
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        return !am.getActiveOpponentWorkers(board, worker.getRow(), worker.getColumn()).isEmpty();
    }

    /**
     * Finds workers placed where the godPower is actionable, if there are any
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
     * When actionable adds neighboring opponent's workers' positions to the available ones
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isActionable(board,worker) && phase==TurnPhase.MOVE) {
            positions.addAll(am.getActiveOpponentWorkers(board, worker.getRow(), worker.getColumn()));
        }
    }

    /**
     * Apollo strategy for MOVE: "Your Worker may move into an opponent Worker's space
     * by forcing their Worker to the space yours just vacated"
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    @Override
    public void move(Worker worker, Board board, int row, int column) {
        if (board.getCell(row, column).isOccupied()) {
            Worker opponentWorker = board.getCell(row, column).getWorker();
            int oldRow = worker.getRow();
            int oldColumn = worker.getColumn();

            // Delete opponent's worker from the cell it is situated
            board.getCell(row, column).detachWorker();
            // Move my worker to the cell it has to move to
            super.move(worker, board, row, column);
            // Force opponent's worker into the cell that was occupied by mine
            opponentWorker.setPosition(board, oldRow, oldColumn);

        }
        else
            super.move(worker, board, row, column);
    }

}
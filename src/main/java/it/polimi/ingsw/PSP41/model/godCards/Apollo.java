package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Apollo extends GodPower {

    public Apollo() {
        affectPhase = TurnPhase.NONE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        return !am.getActiveOpponentWorkers(board, worker.getRow(), worker.getColumn()).isEmpty();
    }

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

    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isActionable(board,worker) && phase==TurnPhase.MOVE) {
            positions.addAll(am.getActiveOpponentWorkers(board, worker.getRow(), worker.getColumn()));
        }
    }

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

    @Override
    public String toString() {
        return ("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated");
    }

}
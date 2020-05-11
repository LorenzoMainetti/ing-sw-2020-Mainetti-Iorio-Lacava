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

    @Override
    public boolean isActionable(Board board, Worker worker) {
        return !am.getOpponentWorkers(board, worker.getRow(), worker.getColumn()).isEmpty() && !getPowerCells(board, worker).isEmpty();
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
        if(isActionable(board, worker) && phase==TurnPhase.MOVE) {
            positions.addAll(getPowerCells(board, worker));
        }
    }

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

    private List<Position> getPowerCells(Board board, Worker worker) {
        int deltaRow;
        int deltaColumn;
        List<Position> powerCells = new ArrayList<>();
        // I show which cells between the occupied ones are eligible to force "back" the opponent's worker
        for (Position pos: am.getOpponentWorkers(board, worker.getRow(), worker.getColumn())) {
            deltaRow = pos.getPosRow() - worker.getRow();
            deltaColumn = pos.getPosColumn() - worker.getColumn();
            // If the cell "back" to the opponent's worker is free and valid I add the opponent's worker to the list
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

    @Override
    public String toString() {
        return ("Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.");
    }

}
package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Athena extends GodPower {
    private boolean athenaPower = false;

    public Athena() {
        actionable = false;
        affectPhase = TurnPhase.MOVE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * If a worker moved up, remove higher positions from opponent's available ones
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     */
    @Override
    public void applyOpponentConstraints(List<Position> positions, Board board, Worker worker) {
        if(athenaPower) {
            positions.removeIf(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() >
                    board.getCell(worker.getRow(), worker.getColumn()).getLevel()));
        }
    }

    /**
     * Perform a regular move and register if the worker moved up
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    @Override
    public void move(Worker worker, Board board, int row, int column) {
        athenaPower = board.getCell(row, column).getLevel() > board.getCell(worker.getRow(), worker.getColumn()).getLevel();
        super.move(worker, board, row, column);
    }

}

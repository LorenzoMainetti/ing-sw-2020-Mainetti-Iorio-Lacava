package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;


public class Triton extends GodPower {

    public Triton() {
        affectPhase = TurnPhase.MOVE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.MOVE); //so the while in controller can keep going
        phases.add(TurnPhase.BUILD);
    }

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true if worker is on the perimeter and there are available moves
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        return (new Position(worker.getRow(), worker.getColumn()).isPerimeter() &&
                !am.getValidMoves(board, worker.getRow(), worker.getColumn()).isEmpty());
    }

    /**
     * Adds move phase, resets to default when not triggered
     */
    @Override
    public void addPhase() {
        if(isTriggered()) {
            phases.add(1, TurnPhase.MOVE);
        }
        else {
            //reset to default
            while(phases.size() > 3)
                phases.remove(TurnPhase.MOVE);
        }
    }

    /**
     * Performs a regular move and resets trigger in order to re-ask activation
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    @Override
    public void move(Worker worker, Board board, int row, int column) {
        triggered = false;
        super.move(worker, board, row, column);
    }

}

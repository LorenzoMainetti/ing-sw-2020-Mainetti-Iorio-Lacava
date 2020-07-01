package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Zeus extends GodPower {

    public Zeus() {
        affectPhase = TurnPhase.NONE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true if the chosen worker's level is lower then the third
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        return board.getCell(worker.getRow(), worker.getColumn()).getLevel() < 3;
    }

    /**
     * When actionable adds current worker's position to the available ones
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isActionable(board, worker) && phase==TurnPhase.BUILD)
            positions.add(new Position(worker.getRow(), worker.getColumn()));
    }

}

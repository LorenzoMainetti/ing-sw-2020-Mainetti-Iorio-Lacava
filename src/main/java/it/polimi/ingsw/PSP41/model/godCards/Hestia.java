package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Hestia extends GodPower {

    public Hestia() {
        affectPhase = TurnPhase.BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true if there is a valid build position not on the perimeter
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        List<Position> pos = am.getValidBuilds(board, worker.getRow(), worker.getColumn());
        pos.removeIf(Position::isPerimeter);
        return !pos.isEmpty();
    }

    /**
     * When triggered remove positions on the perimeter from the available ones
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isTriggered() && phase == affectPhase)
            positions.removeIf(Position::isPerimeter);
    }

    /**
     * Add build phase, reset to default when not triggered
     */
    @Override
    public void addPhase() {
        if(isTriggered()) {
            if(phases.size() < 3)
                phases.add(TurnPhase.BUILD);
        }
        else {
            //reset to default
            if(phases.size() > 2)
                phases.remove(TurnPhase.BUILD);
        }
    }

}

package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Hestia extends GodPower {

    public Hestia() {
        affectPhase = TurnPhase.BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        List<Position> pos = am.getValidBuilds(board, worker.getRow(), worker.getColumn());
        pos.removeIf(Position::isPerimeter);
        return !pos.isEmpty();
    }

    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isTriggered() && phase == affectPhase)
            positions.removeIf(Position::isPerimeter);
    }

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

    @Override
    public String toString() {
        return ("Your worker may build one additional time, but this cannot be on a perimeter space.");
    }

}

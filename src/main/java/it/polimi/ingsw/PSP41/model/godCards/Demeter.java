package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Demeter extends GodPower {
    private int rowConstraint;
    private int colConstraint;

    public Demeter() {
        affectPhase = TurnPhase.BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        List<Position> pos = am.getValidBuilds(board, worker.getRow(), worker.getColumn());
        pos.removeIf(p -> (p.getPosRow()==rowConstraint && p.getPosColumn()==colConstraint));
        return !pos.isEmpty();
    }

    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isTriggered() && phase == affectPhase)
            positions.removeIf(p -> (p.getPosRow()==rowConstraint && p.getPosColumn()==colConstraint));
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
    public void build(Board board, int row, int column) {
        rowConstraint = row;
        colConstraint = column;
        super.build(board, row, column);
    }

    @Override
    public String toString() {
        return ("Your Worker may build one additional time, but not on the same space.");
    }

}
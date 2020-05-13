package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Artemis extends GodPower {
    private int rowConstraint;
    private int colConstraint;

    public Artemis() {
        affectPhase = TurnPhase.MOVE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        List<Position> pos = am.getValidMoves(board, worker.getRow(), worker.getColumn());
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
                phases.add(1, TurnPhase.MOVE);
        }
        else {
            //reset to default
            if(phases.size() > 2)
                phases.remove(TurnPhase.MOVE);
        }
    }

    @Override
    public void move(Worker worker, Board board, int row, int column) {
        rowConstraint = worker.getRow();
        colConstraint = worker.getColumn();
        super.move(worker, board, row, column);
    }

    @Override
    public String toString() {
        return ("Your Worker may move one additional time, but not back to its initial space.");
    }

}
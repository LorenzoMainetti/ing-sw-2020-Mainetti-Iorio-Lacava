package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Ares extends GodPower {

    public Ares() {
        affectPhase = TurnPhase.BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        Position otherWorker = am.getOtherWorker(board, worker.getRow(), worker.getColumn());
        return !am.getValidRemovableBlocks(board, otherWorker.getPosRow(), otherWorker.getPosColumn()).isEmpty();
    }

    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isTriggered() && phase == affectPhase) {
            positions.removeIf(p -> board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() == 0);
        }
    }

    @Override
    public boolean switchWorker() {
        return isTriggered();
    }

    @Override
    public void addPhase() {
        if(isTriggered()) {
            if(phases.size() < 3)
                phases.add(TurnPhase.BUILD);
        }
        else {
            if(phases.size() > 2)
                phases.remove(TurnPhase.BUILD);
        }
    }

    @Override
    public void build(Board board, int row, int column) {
        if(isTriggered()) {
            board.getCell(row, column).removeLevel();
        }
        else
            super.build(board, row, column);
    }

    @Override
    public String toString() {
        return ("You may remove an unoccupied block (not dome) neighbouring your unmoved worker.");
    }

}

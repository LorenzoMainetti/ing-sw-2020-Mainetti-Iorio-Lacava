package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

public class Poseidon extends GodPower {
    private Position otherWorker;
    private boolean firstTime = true;

    public Poseidon() {
        affectPhase = TurnPhase.BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
        phases.add(TurnPhase.BUILD); //so the while in controller can keep going
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        if(firstTime) {
            firstTime = false;
            otherWorker = am.getOtherWorker(board, worker.getRow(), worker.getColumn());
        }
        if(board.getCell(otherWorker.getPosRow(), otherWorker.getPosColumn()).getLevel() == 0)
            return !am.getValidBuilds(board, otherWorker.getPosRow(), otherWorker.getPosColumn()).isEmpty();
        else
            return false;
    }

    @Override
    public boolean switchWorker() {
        return isTriggered();
    }

    @Override
    public void addPhase() {
        if(isTriggered()) {
            if(phases.size() < 5)
                phases.add(TurnPhase.BUILD);
        }
        else {
            while(phases.size() > 3)
                phases.remove(TurnPhase.BUILD);
        }
    }

    @Override
    public void reset() {
        super.reset();
        firstTime = true;
    }

    @Override
    public void build(Board board, int row, int column) {
        triggered = false;
        super.build(board, row, column);
    }

    @Override
    public String toString() {
        return ("If your unmoved worker is on the ground level, it may build up to three times.");
    }

}

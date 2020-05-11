package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;


public class Triton extends GodPower {

    public Triton() {
        affectPhase = TurnPhase.MOVE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.MOVE); //so the while in controller can keep going
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        return (new Position(worker.getRow(), worker.getColumn()).isPerimeter() &&
                !am.getValidMoves(board, worker.getRow(), worker.getColumn()).isEmpty());
    }

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

    @Override
    public void move(Worker worker, Board board, int row, int column) {
        triggered = false;
        super.move(worker, board, row, column);
    }

    @Override
    public String toString() {
        return ("Each time your worker moves into a perimeter space, it may immediately move again.");
    }

}

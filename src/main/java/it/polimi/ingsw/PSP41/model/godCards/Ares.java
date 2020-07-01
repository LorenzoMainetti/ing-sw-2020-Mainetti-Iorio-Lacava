package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Ares extends GodPower {

    public Ares() {
        affectPhase = TurnPhase.BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true when there is at least a removable block (not dome)
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        Position otherWorker = am.getOtherWorker(board, worker.getRow(), worker.getColumn());
        return !am.getValidRemovableBlocks(board, otherWorker.getPosRow(), otherWorker.getPosColumn()).isEmpty();
    }

    /**
     * When triggered removes ground level positions
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
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

    /**
     * Adds build phase, resets to default when not triggered
     */
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

    /**
     * Ares strategy for BUILD: "You may remove an unoccupied block
     * (not dome) neighbouring your unmoved worker"
     * @param board board state
     * @param row where the player wants to build
     * @param column where the player wants to build
     */
    @Override
    public void build(Board board, int row, int column) {
        if(isTriggered()) {
            board.getCell(row, column).removeLevel();
        }
        else
            super.build(board, row, column);
    }

}

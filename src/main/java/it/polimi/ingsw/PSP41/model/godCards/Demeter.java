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

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true if there is a valid build position different from the previous one
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        List<Position> pos = am.getValidBuilds(board, worker.getRow(), worker.getColumn());
        pos.removeIf(p -> (p.getPosRow()==rowConstraint && p.getPosColumn()==colConstraint));
        return !pos.isEmpty();
    }

    /**
     * When triggered removes previous build position from the available ones
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isTriggered() && phase == affectPhase)
            positions.removeIf(p -> (p.getPosRow()==rowConstraint && p.getPosColumn()==colConstraint));
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
            //reset to default
            if(phases.size() > 2)
                phases.remove(TurnPhase.BUILD);
        }
    }

    /**
     * Save current build position and perform a regular build
     * @param board board state
     * @param row where the player wants to build
     * @param column where the player wants to build
     */
    @Override
    public void build(Board board, int row, int column) {
        rowConstraint = row;
        colConstraint = column;
        super.build(board, row, column);
    }

}
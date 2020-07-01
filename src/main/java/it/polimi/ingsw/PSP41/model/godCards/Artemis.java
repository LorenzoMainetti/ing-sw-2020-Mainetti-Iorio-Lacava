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

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true if there is a valid move position that is not the worker's starting one
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        List<Position> pos = am.getValidMoves(board, worker.getRow(), worker.getColumn());
        pos.removeIf(p -> (p.getPosRow()==rowConstraint && p.getPosColumn()==colConstraint));
        return !pos.isEmpty();
    }

    /**
     * When triggered removes worker's starting position from the available ones
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
     * Adds move phase, resets to default when not triggered
     */
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

    /**
     * Saves worker's starting position and performs a regular move
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    @Override
    public void move(Worker worker, Board board, int row, int column) {
        rowConstraint = worker.getRow();
        colConstraint = worker.getColumn();
        super.move(worker, board, row, column);
    }

}
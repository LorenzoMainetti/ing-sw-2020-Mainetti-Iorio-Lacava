package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Hephaestus extends GodPower {
    private int rowConstraint;
    private int colConstraint;

    public Hephaestus() {
        affectPhase = TurnPhase.BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true if previous build position's level is lower then the third
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        return board.getCell(rowConstraint, colConstraint).getLevel() < 3;
    }

    /**
     * When triggered make only available position the one where it has been built previously
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isTriggered() && phase == affectPhase) {
            positions.clear();
            positions.add(new Position(rowConstraint, colConstraint));
        }
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
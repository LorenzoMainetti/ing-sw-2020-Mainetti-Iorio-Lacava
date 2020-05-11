package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Hephaestus extends GodPower {
    private Position constraint;

    public Hephaestus() {
        affectPhase = TurnPhase.BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        return board.getCell(constraint.getPosRow(), constraint.getPosColumn()).getLevel() < 3;
    }

    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isTriggered() && phase == affectPhase) {
            positions.clear();
            positions.add(constraint);
        }
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

    //TODO mi chiede dove costruire anche se non necessario, potrei chiedergli l'attivazione Before Build e fare +2lev
    @Override
    public void build(Board board, int row, int column) {
        constraint = new Position(row, column);
        super.build(board, row, column);
    }

    @Override
    public String toString() {
        return ("Your Worker may build one additional block (not dome) on top of your first block.");
    }
}
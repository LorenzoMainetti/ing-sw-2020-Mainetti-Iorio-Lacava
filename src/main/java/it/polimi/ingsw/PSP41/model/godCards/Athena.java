package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Athena extends GodPower {
    private boolean athenaPower = false;

    public Athena() {
        actionable = false;
        affectPhase = TurnPhase.MOVE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public void applyOpponentConstraints(List<Position> positions, Board board, Worker worker) {
        if(athenaPower) {
            positions.removeIf(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() >
                    board.getCell(worker.getRow(), worker.getColumn()).getLevel()));
        }
    }

    @Override
    public void move(Worker worker, Board board, int row, int column) {
        athenaPower = board.getCell(row, column).getLevel() > board.getCell(worker.getRow(), worker.getColumn()).getLevel();
        super.move(worker, board, row, column);
    }

    @Override
    public String toString() {
        return ("If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.");
    }

}

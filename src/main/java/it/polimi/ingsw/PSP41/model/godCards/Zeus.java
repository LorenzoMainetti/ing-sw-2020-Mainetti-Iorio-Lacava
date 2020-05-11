package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Zeus extends GodPower {

    public Zeus() {
        affectPhase = TurnPhase.NONE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        return board.getCell(worker.getRow(), worker.getColumn()).getLevel() < 3;
    }

    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isActionable(board, worker) && phase==TurnPhase.BUILD)
            positions.add(new Position(worker.getRow(), worker.getColumn()));
    }

    @Override
    public boolean checkWinCondition(Cell startCell, Cell endCell) {
        if(startCell.equals(endCell))
            return false;
        else
            return super.checkWinCondition(startCell, endCell);
    }

    @Override
    public String toString() {
        return ("Your worker may build a block under itself. You do not win by forcing yourself up to the third level");
    }

}

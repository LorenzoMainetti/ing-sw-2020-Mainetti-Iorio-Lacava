package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Prometheus extends GodPower {

    public Prometheus() {
        affectPhase = TurnPhase.BEFORE_MOVE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    @Override
    public boolean isActionable(Board board, Worker worker) {
        List<Position> notHigherCells = am.getNotHigherCells(board, worker.getRow(), worker.getColumn());

        if(notHigherCells.isEmpty())
            return false;

        return (notHigherCells.size() != 1 || board.getCell(notHigherCells.get(0).getPosRow(),
                notHigherCells.get(0).getPosColumn()).getLevel() != board.getCell(worker.getRow(), worker.getColumn()).getLevel()
                || am.getValidBuilds(board, worker.getRow(), worker.getColumn()).size() != 1);
    }

    @Override
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) {
        if(isTriggered()) {
            if(phase == TurnPhase.MOVE) {
                positions.removeIf(p -> (board.getCell(p.getPosRow(), p.getPosColumn()).getLevel() >
                        board.getCell(worker.getRow(), worker.getColumn()).getLevel()));
            }

            if(phase == TurnPhase.BUILD) {
                List<Position> notHigherCells = am.getNotHigherCells(board, worker.getRow(), worker.getColumn());

                if (notHigherCells.size() == 1 && board.getCell(notHigherCells.get(0).getPosRow(),
                        notHigherCells.get(0).getPosColumn()).getLevel() == board.getCell(worker.getRow(), worker.getColumn()).getLevel())
                positions.remove(notHigherCells.get(0));
            }
        }
    }

    @Override
    public void addPhase() {
        if(isTriggered()) {
            if(phases.size() < 3)
                phases.add(0, TurnPhase.BUILD);
        }
        else {
            if(phases.size() > 2)
                phases.remove(0);
        }
    }

    @Override
    public String toString() {
        return ("If your Worker does not move up, it may build both before and after moving.");
    }

}

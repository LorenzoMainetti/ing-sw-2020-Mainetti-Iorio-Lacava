package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Prometheus extends GodPower {

    public Prometheus() {
        affectPhase = TurnPhase.BEFORE_MOVE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return true when there are some not higher cells available for building
     * (if there is only one, either its level needs to be lower than the worker's one,
     * or there needs to be some other valid builds)
     */
    @Override
    public boolean isActionable(Board board, Worker worker) {
        List<Position> notHigherCells = am.getNotHigherCells(board, worker.getRow(), worker.getColumn());

        if(notHigherCells.isEmpty())
            return false;

        return (notHigherCells.size() != 1 || board.getCell(notHigherCells.get(0).getPosRow(),
                notHigherCells.get(0).getPosColumn()).getLevel() != board.getCell(worker.getRow(), worker.getColumn()).getLevel()
                || am.getValidBuilds(board, worker.getRow(), worker.getColumn()).size() != 1);
    }

    /**
     * When triggered: if it's build phase handle corner case (only one not higher cell available),
     * if it's move phase remove higher cells from the available moves
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
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
                        notHigherCells.get(0).getPosColumn()).getLevel() == board.getCell(worker.getRow(), worker.getColumn()).getLevel()) {
                    positions.removeIf(p -> (p.getPosRow() == notHigherCells.get(0).getPosRow() &&
                            p.getPosColumn() == notHigherCells.get(0).getPosColumn()));
                }
            }
        }
    }

    /**
     * Add build phase, reset to default when not triggered
     */
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

}

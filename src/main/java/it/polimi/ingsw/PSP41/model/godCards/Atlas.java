package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.TurnPhase;

public class Atlas extends GodPower {

    public Atlas() {
        affectPhase = TurnPhase.BEFORE_BUILD;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * Atlas strategy for BUILD: "Your Worker may build a dome at any level"
     * @param board board state
     * @param row where the player wants to build
     * @param column where the player wants to build
     */
    @Override
    public void build(Board board, int row, int column) {
        if(isTriggered()) {
            board.getCell(row, column).setDome(true);
        }
        else
            super.build(board, row, column);
    }

}
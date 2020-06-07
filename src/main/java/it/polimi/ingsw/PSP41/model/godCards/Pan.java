package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.Cell;
import it.polimi.ingsw.PSP41.model.TurnPhase;

public class Pan extends GodPower {

    public Pan() {
        affectPhase = TurnPhase.NONE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }

    /**
     * Pan strategy for WIN CONDITION: "You also win if your Worker moves down two or more levels"
     * @param startCell worker current cell
     * @param endCell worker future cell
     */
    @Override
    public boolean checkWinCondition(Cell startCell, Cell endCell) {
        return startCell.getLevel() == 2 && endCell.getLevel() == 3 || (startCell.getLevel() - endCell.getLevel() >= 2);
    }

}
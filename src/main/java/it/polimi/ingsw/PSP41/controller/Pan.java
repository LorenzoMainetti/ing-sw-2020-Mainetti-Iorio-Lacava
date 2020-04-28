package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Cell;

public class Pan extends GodPower {

    public Pan(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers inherited from GodPower

    // Normal move inherited from GodPower

    // Normal build inherited from GodPower


    /**
     * Win Condition: You also win if your Worker moves down two or more levels
     * @param startCell worker current cell
     * @param endCell worker future cell
     */
    @Override
    public void checkWinCondition(Cell startCell, Cell endCell) {
        if (startCell.getLevel() == 2 && endCell.getLevel() == 3 || (startCell.getLevel() - endCell.getLevel() >= 2)) {
            player.setWinner(true);
        }
    }

    @Override
    public String toString() {
        return ("You also win if your Worker moves down two or more levels.");
    }

}
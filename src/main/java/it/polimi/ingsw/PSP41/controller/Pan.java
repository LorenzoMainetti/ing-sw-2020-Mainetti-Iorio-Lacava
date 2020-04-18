package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Cell;

public class Pan extends GodPower {

    public Pan(Player player, ActionManager am, UserInputManager uim) {
        this.player = player;
        this.am = am;
        this.uim = uim;
    }

    // Normale gestione worker attivi ereditata da GodPower

    // Normale move ereditata da GodPower

    // Normale build ereditata da GodPower


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
}
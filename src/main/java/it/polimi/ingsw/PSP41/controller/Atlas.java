package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;

public class Atlas extends GodPower {

    public Atlas(Player player, ActionManager am, UserInputManager uim) {
        this.player = player;
        this.am = am;
        this.uim = uim;
    }

    // Normale gestione worker attivi ereditata da GodPower

    // Normale move ereditata da GodPower

    /**
     * Your Build: Your Worker may build a dome at any level
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {

        uim.readPower();
        // Se il potere è attivo, costruisco una dome
        if (uim.isPower()) {
            uim.readChosenCell(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()));
            board.getCell(uim.getChosenRow(), uim.getChosenColumn()).addLevel();
            board.getCell(uim.getChosenRow(), uim.getChosenColumn()).setDome(true);
        }
        // Se il potere non è attivo, build normale
        else {
            super.buildBehaviour(board);
        }
    }
}
package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;

public class Atlas extends GodPower {

    public Atlas(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers inherited from GodPower

    // Normal move inherited from GodPower

    /**
     * Your Build: Your Worker may build a dome at any level
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {

        uim.readPower();
        // If the power is active I build a dome
        if (uim.isPower()) {
            uim.readChosenDirection(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()), currWorker.getRow(), currWorker.getColumn());
            player.buildDome(board, uim.getChosenRow(), uim.getChosenColumn());
        }
        // If the power isn't active, normal build
        else {
            super.buildBehaviour(board);
        }
    }

    @Override
    public String toString() {
        return ("Your Worker may build a dome at any level.");
    }

}
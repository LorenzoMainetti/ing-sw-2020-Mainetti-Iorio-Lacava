package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Board;

public class Hephaestus extends GodPower {

    public Hephaestus(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normale gestione worker attivi ereditata da GodPower

    // Normale move ereditata da GodPower

    /**
     * Your Build: Your Worker may build one additional block (not dome) on top of your first block
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {

        super.buildBehaviour(board);

        // Posso fare la seconda build nella stessa cella solo se non devo costruire una dome
        if (board.getCell(uim.getChosenRow(), uim.getChosenColumn()).getLevel() < 3) {
            uim.readPower();
            if (uim.isPower()) {
                player.build(board, uim.getChosenRow(), uim.getChosenColumn());
            }
        }
    }

}
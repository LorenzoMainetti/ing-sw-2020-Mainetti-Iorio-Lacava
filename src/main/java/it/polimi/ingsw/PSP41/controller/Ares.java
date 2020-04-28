package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;
import java.util.List;

public class Ares extends GodPower {

    public Ares(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers inherited from GodPower

    // Normal move inherited from GodPower

    /**
     * After your turn: Your Worker may remove an unoccupied block (not dome) neighbouring the unmoved worker
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {
        Worker otherWorker;

        // Normal behaviour of a worker's builds
        uim.readChosenDirection(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()), currWorker.getRow(), currWorker.getColumn());
        player.build(board, uim.getChosenRow(), uim.getChosenColumn());

        if(uim.isChosenWorker())
            otherWorker = player.getWorker2();
        else
            otherWorker = player.getWorker1();

        // At the end of the turn, if there are moves available, ask the player if he wants to activate the power
        if(!am.getValidRemovableBlocks(board, otherWorker.getRow(), otherWorker.getColumn()).isEmpty())
        {
            uim.readPower();
            // If the power is active, remove a level from a cell neighbouring the unmoved worker
            if (uim.isPower()) {
                List<Position> removableBlocks = am.getValidRemovableBlocks(board, otherWorker.getRow(), otherWorker.getColumn());

                uim.readChosenDirection(removableBlocks, otherWorker.getRow(), otherWorker.getColumn());
                player.removeLevel(board, uim.getChosenRow(), uim.getChosenColumn());
            }
        }
    }


    @Override
    public String toString() {
        return ("You may remove an unoccupied block (not dome) neighbouring your unmoved worker.");
    }

}

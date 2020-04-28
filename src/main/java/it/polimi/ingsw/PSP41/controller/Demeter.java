package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Demeter extends GodPower {

    public Demeter(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers inherited from GodPower

    // Normal move inherited from GodPower

    /**
     * Your Build: Your Worker may build one additional time, but not on the same space
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {

        super.buildBehaviour(board);

        // To activate the power it is necessary that the worker could still build in an adjacent cell except from the one it has already built in
        List<Position> secondBuildCells = am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn());
        secondBuildCells.removeIf(p -> (p.getPosRow()==uim.getChosenRow() && p.getPosColumn()==uim.getChosenColumn()));

        if(!secondBuildCells.isEmpty()) {
            uim.readPower();
            // If the power is active, build again excluding the cell it has already been built in from the possible cells where to build again
            if (uim.isPower()) {
                uim.readChosenDirection(secondBuildCells, currWorker.getRow(), currWorker.getColumn());
                player.build(board, uim.getChosenRow(), uim.getChosenColumn());
            }
        }
    }

    @Override
    public String toString() {
        return ("Your Worker may build one additional time, but not on the same space.");
    }

}
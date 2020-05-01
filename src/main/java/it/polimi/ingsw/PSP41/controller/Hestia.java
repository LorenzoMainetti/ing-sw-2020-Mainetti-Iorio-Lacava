package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.Position;

import java.util.List;

public class Hestia extends GodPower {

    public Hestia(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }


    // Normal management of active workers inherited from GodPower

    // Normal move inherited from GodPower

    /**
     * Your Build: Your Worker may build an additional time, but not on the perimeter
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {

        super.buildBehaviour(board);

        // To activate the power it is necessary that the worker could still build in an adjacent cell except from the ones on the perimeter
        List<Position> secondBuildCells = am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn());
        secondBuildCells.removeIf(Position::isPerimeter);

        if(!secondBuildCells.isEmpty()) {
            uim.readPower();
            // If the power is active, build again excluding the cells on the perimeter of the board
            if (uim.isPower()) {
                uim.readChosenDirection(secondBuildCells, currWorker.getRow(), currWorker.getColumn());
                player.build(board, uim.getChosenRow(), uim.getChosenColumn());
            }
        }
    }

    @Override
    public String toString() {
        return ("Your worker may build one additional time, but this cannot be on a perimeter space.");
    }

}

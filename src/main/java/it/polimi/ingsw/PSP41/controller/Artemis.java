package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Artemis extends GodPower {

    public Artemis(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers, inherited from GodPower

    /**
     * Your Move: Your Worker may move one additional time, but not back to its initial position
     * @param board current board state
     */
    @Override
    public void moveBehaviour(Board board) {

        // Saving the initial position of the worker in case the player wants to activate the power
        int startRow = currWorker.getRow();
        int startColumn = currWorker.getColumn();

        // If the power isn't active, normal turn
        super.moveBehaviour(board);

        List<Position> secondMoveCells = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);
        secondMoveCells.removeIf(p -> (p.getX()==startRow && p.getY()==startColumn));
        // To activate the power it is necessary that the worker is able to move in one of the adjacent cells except from the one it has started from
        if(!secondMoveCells.isEmpty()) {
            uim.readPower();
            // If the power is active, make the second move excluding the starting cell from the cells available to move to
            if (uim.isPower()) {
                uim.readChosenDirection(secondMoveCells, currWorker.getRow(), currWorker.getColumn());
                int chosenRow = uim.getChosenRow();
                int chosenColumn = uim.getChosenColumn();
                checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
                player.move(currWorker, board, chosenRow, chosenColumn);
            }
        }
    }

    // Normal build inherited from GodPower

    @Override
    public String toString() {
        return ("Your Worker may move one additional time, but not back to its initial space.");
    }

}
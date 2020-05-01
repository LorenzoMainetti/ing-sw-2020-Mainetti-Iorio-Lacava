package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.Position;

import java.util.List;

public class Triton extends GodPower {

    public Triton(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers inherited from GodPower

    /**
     * Your Move: If your Worker moves into a perimeter space, it may move again
     * @param board current board state
     */
    //TODO mettere un while, tritone può continuare a muoversi fintanto che è sul perimetro
    @Override
    public void moveBehaviour(Board board) {
        // If the power isn't active, normal turn
        super.moveBehaviour(board);

        Position currPosition = new Position(currWorker.getRow(), currWorker.getColumn());

        if(currPosition.isPerimeter()) {
            List<Position> secondMoveCells = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);

            // To activate the power it is necessary that the worker is able to move in one of the adjacent cells
            if (!secondMoveCells.isEmpty()) {
                uim.readPower();
                // If the power is active, make the second move
                if (uim.isPower()) {
                    uim.readChosenDirection(secondMoveCells, currWorker.getRow(), currWorker.getColumn());
                    int chosenRow = uim.getChosenRow();
                    int chosenColumn = uim.getChosenColumn();
                    checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
                    player.move(currWorker, board, chosenRow, chosenColumn);
                }
            }
        }
    }

    // Normal build inherited from GodPower

    @Override
    public String toString() {
        return ("Each time your worker moves into a perimeter space, it may immediately move again.");
    }

}

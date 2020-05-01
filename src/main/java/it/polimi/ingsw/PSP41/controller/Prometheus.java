package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Prometheus extends GodPower {

    public Prometheus(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers, inherited from GodPower

    /**
     * Your Turn: If your Worker does not move up, it may build both before and after moving
     * @param board current board state
     */
    @Override
    public void moveBehaviour(Board board) {

        // To be able to activate the power, the list of adjacent cells at the same or a lower lever than the worker's one can't be empty
        List<Position> notHigherCells = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), true);

        // It is not possible to activate the power if the list is empty or if the only cell it can be built in is at the same level of the worker's one
        if(notHigherCells.isEmpty())
            super.moveBehaviour(board);
        // If there are more than one cell at the same level, if the power active I build with no limits and move at the same or at a lower level,
        // if the power isn't active I make a normal move
        else {
            if(notHigherCells.size() == 1 && board.getCell(notHigherCells.get(0).getPosRow(), notHigherCells.get(0).getPosColumn()).getLevel() ==
                    board.getCell(currWorker.getRow(), currWorker.getColumn()).getLevel()) {
                 // If there is only a cell available to build in, then it is for sure the cell of notHigherCells -> normal move
                 if(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()).size() == 1)
                        super.moveBehaviour(board);
                 // If there is only one cell at a not higher level of the worker but the cells to build in are more that one, if the power is active I can't
                 // allow the built on a cell not at a higher level
                 else{
                     uim.readPower();
                     // If the power is active I build and move, but I can't allow the construction inside the cell at the same level of the worker,
                     // if the power isn't active I make a normal move
                     if (uim.isPower()) {
                         List<Position> buildCells = am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn());
                         buildCells.remove(notHigherCells.get(0));
                         uim.readChosenDirection(buildCells, currWorker.getRow(), currWorker.getColumn());
                         player.build(board, uim.getChosenRow(), uim.getChosenColumn());

                         uim.readChosenDirection(notHigherCells, currWorker.getRow(), currWorker.getColumn());
                         int chosenRow = uim.getChosenRow();
                         int chosenColumn = uim.getChosenColumn();
                         checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
                         player.move(currWorker, board, chosenRow, chosenColumn);
                     }
                     else
                         super.moveBehaviour(board);
                 }
            }
            else {
                uim.readPower();
                if (uim.isPower()) {
                    uim.readChosenDirection(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()), currWorker.getRow(), currWorker.getColumn());
                    player.build(board, uim.getChosenRow(), uim.getChosenColumn());

                    uim.readChosenDirection(am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), true), currWorker.getRow(), currWorker.getColumn());
                    int chosenRow = uim.getChosenRow();
                    int chosenColumn = uim.getChosenColumn();
                    checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
                    player.move(currWorker, board, chosenRow, chosenColumn);
                } else
                    super.moveBehaviour(board);
            }
        }
    }


    // Normal build inherited from GodPower

    @Override
    public String toString() {
        return ("If your Worker does not move up, it may build both before and after moving.");
    }

}

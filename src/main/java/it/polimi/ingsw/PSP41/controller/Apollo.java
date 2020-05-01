package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Apollo extends GodPower {

    public Apollo(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    /**
     * Management of the worker choice at the start of a turn aware of all the possible moves Apollo implies: the player can only choose workers that
     * are able to move, if both workers are blocked the player loses the game
     * @param board current board state
     */
    @Override
    public void activeWorkers(Board board) {
        // Check if workers can move in a fee cell and/or in a valid occupied one
        if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
           am.getActiveOpponentWorkers(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
           am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty() &&
           am.getActiveOpponentWorkers(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty()) {
            // Detach workers from their cells
            board.getCell(player.getWorker1().getRow(), player.getWorker1().getColumn()).detachWorker();
            board.getCell(player.getWorker2().getRow(), player.getWorker2().getColumn()).detachWorker();
            player.setStuck(true, board);
        }
        else if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
                am.getActiveOpponentWorkers(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty()) {
            currWorker = player.getWorker2();
        }

        else if(am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty() &&
                am.getActiveOpponentWorkers(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty()) {
            currWorker = player.getWorker1();
        }

        else {
            uim.readChosenWorker();
            if (uim.isChosenWorker()) { currWorker = player.getWorker1(); }
            else { currWorker = player.getWorker2(); }
        }
    }

    /**
     * Your move: Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated
     * @param board current board state
     */
    @Override
    public void moveBehaviour(Board board) {

        // To be able to activate the power, the opponent's workers in adjacent cells need to have the possibility to build
        List<Position> validOccupiedCells = am.getActiveOpponentWorkers(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);
        List<Position> validMoves = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);

        // If no adjacent occupied cell is valid and it is possible to make at least one one normal move, the power is not going to be able to be activated
        if (validOccupiedCells.isEmpty())
            uim.updatePower(false);

            // If there is at least one valid adjacent occupied cell and there aren't normal moves available, the power has to be activated
        else {
            if (validMoves.isEmpty())
                uim.updatePower(true);

                // If there is at least one valid adjacent occupied cell and it is possible to make at least one normal move, ask the player if he wants to activate the power
            else
                uim.readPower();
        }

        int chosenColumn;
        int chosenRow;
        // If the power is active, show the adjacent occupied valid cells
        if (uim.isPower()) {
            uim.readChosenDirection(validOccupiedCells, currWorker.getRow(), currWorker.getColumn());
            chosenRow = uim.getChosenRow();
            chosenColumn = uim.getChosenColumn();

            checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));

            Worker opponentWorker = board.getCell(chosenRow, chosenColumn).getWorker();
            int oldRow = currWorker.getRow();
            int oldColumn = currWorker.getColumn();

            // Delete opponent's worker from the cell it is situated
            board.getCell(chosenRow, chosenColumn).detachWorker();
            // Move my worker to the cell it has to move to
            player.move(currWorker, board, chosenRow, chosenColumn);
            // Force opponent's worker into the cell that was occupied by mine
            opponentWorker.setPosition(board, oldRow, oldColumn);

        }
        // If the power isn't active, make a normal move
        else
            super.moveBehaviour(board);
    }

    // Normal build inherited from GodPower

    @Override
    public String toString() {
        return ("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated");
    }

}
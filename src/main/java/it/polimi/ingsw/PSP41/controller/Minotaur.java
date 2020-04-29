package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;

import java.util.ArrayList;
import java.util.List;

public class Minotaur extends GodPower {

    public Minotaur(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    /**
     * Management of the worker choice at the start of a turn aware of all the possible moves Minotaur implies: the player can only choose workers that
     * are able to move, if both workers are blocked the player lose the game
     * @param board current board state
     */
    @Override
    public void activeWorkers(Board board) {
        // Check to see if the workers can move in a free cell and/or in an occupied one
        if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
                am.getNeighbouringOpponentWorkers(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
                am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty() &&
                am.getNeighbouringOpponentWorkers(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty()) {
            player.setStuck(true);
            // Detach worker form corresponding cells
            board.getCell(player.getWorker1().getRow(), player.getWorker1().getColumn()).detachWorker();
            board.getCell(player.getWorker2().getRow(), player.getWorker2().getColumn()).detachWorker();
        }
        else if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
                am.getNeighbouringOpponentWorkers(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty()) {
            currWorker = player.getWorker2();
        }

        else if(am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty() &&
                am.getNeighbouringOpponentWorkers(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty()) {
            currWorker = player.getWorker1();
        }

        else {
            uim.readChosenWorker();
            if (uim.isChosenWorker()) { currWorker = player.getWorker1(); }
            else { currWorker = player.getWorker2(); }
        }
    }

    /**
     * Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied
     * space at any level
     * @param board current board state
     */
    @Override
    public void moveBehaviour(Board board) {

        int deltaRow;
        int deltaColumn;
        List<Position> powerCells = new ArrayList<>();
        // I show which cells between the occupied ones are eligible to force "back" the opponent's worker
        for (Position pos: am.getNeighbouringOpponentWorkers(board, currWorker.getRow(), currWorker.getColumn(), athenaPower)) {
            deltaRow = board.getCell(pos.getPosRow(), pos.getPosColumn()).getWorker().getRow() - currWorker.getRow();
            deltaColumn = board.getCell(pos.getPosRow(), pos.getPosColumn()).getWorker().getColumn() - currWorker.getColumn();
            // If the cell "back" to the opponent's worker is free and valid I add the opponent's worker to the list
            // of cells to choose from for my worker's move
            if (0 <= currWorker.getRow() + 2*deltaRow && currWorker.getRow() + 2*deltaRow <= 4 &&
                0 <= currWorker.getColumn() + 2*deltaColumn && currWorker.getColumn() + 2*deltaColumn <= 4 &&
                !board.getCell(currWorker.getRow() + 2*deltaRow, currWorker.getColumn() + 2*deltaColumn).isOccupied() &&
                    !board.getCell(currWorker.getRow() + 2*deltaRow, currWorker.getColumn() + 2*deltaColumn).isDome()) {
                powerCells.add(pos);
            }
        }

        int chosenRow;
        int chosenColumn;
        // To be able to activate the power, the list of occupied cells for which it is possible to force "back" the opponent's worker can't be empty
        if (!powerCells.isEmpty()) {
            uim.readPower();
            // If the power is active, I move the two workers
            if (uim.isPower()) {
                uim.readChosenDirection(powerCells, currWorker.getRow(), currWorker.getColumn());
                chosenRow = uim.getChosenRow();
                chosenColumn = uim.getChosenColumn();
                Worker opponentWorker = board.getCell(chosenRow, chosenColumn).getWorker();
                deltaRow = chosenRow - currWorker.getRow();
                deltaColumn = uim.getChosenColumn() - currWorker.getColumn();
                // Move of the opponent's worker "back" of one cell
                player.move(opponentWorker, board, chosenRow + deltaRow, chosenColumn + deltaColumn);
                checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
                // Move of my worker into the first cell occupied by the opponent's worker
                player.move(currWorker, board, chosenRow, chosenColumn);
            }
            // If the power isn't active the move is going to be normal
            else
                super.moveBehaviour(board);
        }
        // If the list of occupied cells for which it is possible to force "back" the opponent's worker is empty, the move is going to be normal
        else
            super.moveBehaviour(board);
    }

    // Normal build inherited from GodPower

    @Override
    public String toString() {
        return ("Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.");
    }

}
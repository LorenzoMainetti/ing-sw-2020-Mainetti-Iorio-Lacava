package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;

public class Athena extends GodPower {

    public Athena(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    /**
     * Normal management of the worker choice at the start of a turn setting athenaPower false: the player can only choose workers that
     * are able to move, if both workers are blocked the player lose the game
     * @param board current board state
     */
    @Override
    public void activeWorkers(Board board) {
        athenaPower = false;
        super.activeWorkers(board);
    }

    /**
     * Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn
     * @param board current board state
     */
    @Override
    public void moveBehaviour(Board board) {

        int chosenRow;
        int chosenColumn;
        uim.readChosenDirection(am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower), currWorker.getRow(), currWorker.getColumn());
        chosenRow = uim.getChosenRow();
        chosenColumn = uim.getChosenColumn();
        // Se mi muovo verso l'alto, attivo la variabile statica athenaPower per impedire che i giocatori possano muoversi verso l'alto (all'inizio del
        // mio prossimo turno, quando chiamo activeWorkers, porrò athenaPower falsa)
        if (board.getCell(chosenRow, chosenColumn).getLevel() > board.getCell(currWorker.getRow(), currWorker.getColumn()).getLevel()) {
            athenaPower = true;
        }
        checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
        player.move(currWorker, board, chosenRow, chosenColumn);
    }

    // Normale build ereditata da GodPower

}

package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Artemis extends GodPower {

    public Artemis(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normale gestione worker attivi ereditata da GodPower

    /**
     * Your Move: Your Worker may move one additional time, but not back to its initial space
     * @param board current board state
     */
    @Override
    public void moveBehaviour(Board board) {

        // Salvo posizione iniziale worker nel caso si voglia attivare il potere
        int startRow = currWorker.getRow();
        int startColumn = currWorker.getColumn();

        // Se potere non è attivo, turno normale
        super.moveBehaviour(board);

        List<Position> secondMoveCells = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);
        secondMoveCells.removeIf(p -> (p.getX()==startRow && p.getY()==startColumn));
        // Per attivare il potere è necessario che il worker si possa muovere in una delle celle adiacenti esclusa quella di partenza
        if(!secondMoveCells.isEmpty()) {
            uim.readPower();
            // Se potere attivo, faccio la seconda move escludendo la casella iniziale dalle possibili celle di arrivo
            if (uim.isPower()) {
                uim.readChosenDirection(secondMoveCells, currWorker.getRow(), currWorker.getColumn());
                int chosenRow = uim.getChosenRow();
                int chosenColumn = uim.getChosenColumn();
                checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
                player.move(currWorker, board, chosenRow, chosenColumn);
            }
        }
    }

    // Normale build ereditata da GodPower

}
package it.polimi.ingsw.PSP41;

import java.util.List;

public class Artemis extends GodPower {

    public Artemis(Player player, ActionManager am, UserInputManager uim) {
        this.player = player;
        this.am = am;
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

        int chosenRow;
        int chosenColumn;
        // Se potere non è attivo, turno normale
        uim.readChosenCell(am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower));
        chosenRow = uim.getChosenRow();
        chosenColumn = uim.getChosenColumn();
        checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
        player.move(currWorker, board, chosenRow, chosenColumn);

        List<Cell> secondMoveCells;
        secondMoveCells = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);
        secondMoveCells.remove(board.getCell(startRow, startColumn));
        // Per attivare il potere è necessario che il worker si possa muovere in una delle celle adiacenti esclusa quella di partenza
        if(!secondMoveCells.isEmpty()) {
            uim.readPower();
            // Se potere attivo, faccio la seconda move escludendo la casella iniziale dalle possibili celle di arrivo
            if (uim.isPower()) {
                uim.readChosenCell(secondMoveCells);
                chosenRow = uim.getChosenRow();
                chosenColumn = uim.getChosenColumn();
                checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
                player.move(currWorker, board, chosenRow, chosenColumn);
            }
        }
    }

    // Normale build ereditata da GodPower

}
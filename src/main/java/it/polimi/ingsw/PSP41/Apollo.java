package it.polimi.ingsw.PSP41;

import java.util.List;

public class Apollo extends GodPower {

    public Apollo(Player player, ActionManager am, UserInputManager uim) {
        this.player = player;
        this.am = am;
        this.uim = uim;
    }

    /**
     * Management of the worker choice at the start of a turn aware of all the possible moves Apollo implies: the player can only choose workers that
     * are able to move, if both workers are blocked the player lose the game
     * @param board current board state
     */
    @Override
    public void activeWorkers(Board board) {
        // Controllo che i worker possano muoversi in una cella libera e/o in una occupata valida
        if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
           am.getActiveOpponentWorkers(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
           am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty() &&
           am.getActiveOpponentWorkers(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty()) {
            // Implementare rimozione player da una partita: magari una variabile che dice se è ancora in gioco, da controllare
            // prima dell'inizio del turno. Se rimane un solo giocatore a non aver perso bisogna assegnargli vittoria
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

        // Per poter attivare il potere, i worker avversari in celle adiacenti devono avere possibilità di fare una build (celle valide)
        List<Cell> validOccupiedCells = am.getActiveOpponentWorkers(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);
        List<Cell> validMoves = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);

        // Se nessuna cella adiacente occupata è valida ed è possibile fare almeno una move normale, sicuramente il potere non potrà essere attivato
        if (validOccupiedCells.isEmpty() && !validMoves.isEmpty()) {
            uim.setPower(false);
        }

        // Se esiste almeno una cella adiacente occupata valida e non ci sono move normali disponibili, sicuramente il potere dovrà essere attivato
        else if (!validOccupiedCells.isEmpty() && validMoves.isEmpty()) {
            uim.setPower(true);
        }

        // Se esiste almeno una cella adiacente valida occupata ed è possibile fare almeno una move normale, chiedo se attivare il potere
        else if (!validOccupiedCells.isEmpty() /*&& !validMoves.isEmpty()*/) { // Basta else?
            uim.readPower();
        }

        int chosenColumn;
        int chosenRow;
        // Se il potere è attivo, mostro tutte le celle adiacenti valide occupate
        if (uim.isPower()) {
            uim.readChosenCell(validOccupiedCells);
            chosenRow = uim.getChosenRow();
            chosenColumn = uim.getChosenColumn();
            Worker opponentWorker = board.getCell(chosenRow, chosenColumn).getWorker();
            checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
            // Elimina mio worker dalla cella in cui si trova
            board.getCell(currWorker.getRow(), currWorker.getColumn()).detachWorker();
            // Move del worker avversario nella cella prima occupata dal mio worker
            player.move(opponentWorker, board, currWorker.getRow(), currWorker.getColumn());
            // Sposta il mio worker alla cella in cui si deve muovere e aggiorna posizione worker
            currWorker.setPosition(board, chosenRow, chosenColumn);
            board.getCell(chosenRow, chosenColumn).attachWorker(currWorker);
        }
        // Se il potere non è attivo, move normale
        else {
            uim.readChosenCell(am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower));
            chosenRow = uim.getChosenRow();
            chosenColumn = uim.getChosenColumn();
            checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
            player.move(currWorker, board, chosenRow, chosenColumn);
        }
    }

    // Normale build ereditata da GodPower

}
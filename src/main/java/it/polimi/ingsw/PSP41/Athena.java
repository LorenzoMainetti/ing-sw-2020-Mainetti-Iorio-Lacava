package it.polimi.ingsw.PSP41;

public class Athena extends GodPower {

    public Athena(Player player, ActionManager am, UserInputManager uim) {
        this.player = player;
        this.am = am;
        this.uim = uim;
    }

    /**
     * Normal management of the worker choice at the start of a turn setting athenaPower false: the player can only choose workers that
     * are able to move, if both workers are blocked the player lose the game
     * @param board current board state
     */
    public void activeWorkers(Board board) {

        athenaPower = false;

        if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
           am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty()) {
            // Implementare rimozione player da una partita: magari una variabile che dice se è ancora in gioco, da controllare
            // prima dell'inizio del turno. Se rimane un solo giocatore a non aver perso bisogna assegnargli vittoria
        }
        else if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty()) {
            currWorker = player.getWorker2();
        }

        else if(am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty()) {
            currWorker = player.getWorker1();
        }

        else {
            uim.readChosenWorker();
            if (uim.isChosenWorker()) { currWorker = player.getWorker1(); }
            else { currWorker = player.getWorker2(); }
        }
    }

    /**
     * Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn
     * @param board current board state
     */
    @Override
    public void moveBehaviour(Board board) {

        int chosenRow;
        int chosenColumn;
        uim.readChosenCell(am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower));
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

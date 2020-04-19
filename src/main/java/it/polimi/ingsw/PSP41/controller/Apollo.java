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
            player.setStuck(true);
            //detach worker dalle celle corrispondenti
            board.getCell(player.getWorker1().getRow(), player.getWorker1().getColumn()).detachWorker();
            board.getCell(player.getWorker2().getRow(), player.getWorker2().getColumn()).detachWorker();
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
        List<Position> validOccupiedCells = am.getActiveOpponentWorkers(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);
        List<Position> validMoves = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower);

        // Se nessuna cella adiacente occupata è valida ed è possibile fare almeno una move normale, sicuramente il potere non potrà essere attivato
        if (validOccupiedCells.isEmpty())
            uim.updatePower(false);

        // Se esiste almeno una cella adiacente occupata valida e non ci sono move normali disponibili, sicuramente il potere dovrà essere attivato
        else {
            if (validMoves.isEmpty())
                uim.updatePower(true);

            // Se esiste almeno una cella adiacente valida occupata ed è possibile fare almeno una move normale, chiedo se attivare il potere
            else
                uim.readPower();
        }

        int chosenColumn;
        int chosenRow;
        // Se il potere è attivo, mostro tutte le celle adiacenti valide occupate
        if (uim.isPower()) {
            uim.readChosenDirection(validOccupiedCells, currWorker.getRow(), currWorker.getColumn());
            chosenRow = uim.getChosenRow();
            chosenColumn = uim.getChosenColumn();

            checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
            player.swap(currWorker, board, chosenRow, chosenColumn);


        }
        // Se il potere non è attivo, move normale
        else
            super.moveBehaviour(board);
    }

    // Normale build ereditata da GodPower

}
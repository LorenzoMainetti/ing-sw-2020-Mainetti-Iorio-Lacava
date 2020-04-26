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
        // Controllo che i worker possano muoversi in una cella libera e/o in una occupata
        if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
                am.getNeighbouringOpponentWorkers(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
                am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty() &&
                am.getNeighbouringOpponentWorkers(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty()) {
            player.setStuck(true);
            //detach worker dalle celle corrispondenti
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
        // Tra le celle occupate mostro quelle per cui sia possibile forzare "indietro" il worker avversario
        for (Position pos: am.getNeighbouringOpponentWorkers(board, currWorker.getRow(), currWorker.getColumn(), athenaPower)) {
            deltaRow = board.getCell(pos.getPosRow(), pos.getPosColumn()).getWorker().getRow() - currWorker.getRow();
            deltaColumn = board.getCell(pos.getPosRow(), pos.getPosColumn()).getWorker().getColumn() - currWorker.getColumn();
            // Se la cella "dietro" al worker avversario è libera e valida allora aggiungo la posizione del worker avversario alla lista
            // di celle da scegliere per la move del mio worker
            if (0 <= currWorker.getRow() + 2*deltaRow && currWorker.getRow() + 2*deltaRow <= 4 &&
                0 <= currWorker.getColumn() + 2*deltaColumn && currWorker.getColumn() + 2*deltaColumn <= 4 &&
                !board.getCell(currWorker.getRow() + 2*deltaRow, currWorker.getColumn() + 2*deltaColumn).isOccupied() &&
                    !board.getCell(currWorker.getRow() + 2*deltaRow, currWorker.getColumn() + 2*deltaColumn).isDome()) {
                powerCells.add(pos);
            }
        }

        int chosenRow;
        int chosenColumn;
        // Per poter attivare il potere, la lista di celle occupate per cui sia possibile forzare "indietro" il worker avversario non deve essere vuota
        if (!powerCells.isEmpty()) {
            uim.readPower();
            // Se il potere è attivo, sposto i due worker
            if (uim.isPower()) {
                uim.readChosenDirection(powerCells, currWorker.getRow(), currWorker.getColumn());
                chosenRow = uim.getChosenRow();
                chosenColumn = uim.getChosenColumn();
                Worker opponentWorker = board.getCell(chosenRow, chosenColumn).getWorker();
                deltaRow = chosenRow - currWorker.getRow();
                deltaColumn = uim.getChosenColumn() - currWorker.getColumn();
                // Move del worker avversario "indietro" di una cella
                player.move(opponentWorker, board, chosenRow + deltaRow, chosenColumn + deltaColumn);
                checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
                // Move del mio worker nella cella prima occupata dal worker avversario
                player.move(currWorker, board, chosenRow, chosenColumn);
            }
            // Se il potere non è attivo faccio una move normale
            else
                super.moveBehaviour(board);
        }
        // Se la lista di celle occupate per cui sia possibile forzare "indietro" il worker avversario è vuota, faccio una move normale
        else
            super.moveBehaviour(board);
    }

    // Normale build ereditata da GodPower

}
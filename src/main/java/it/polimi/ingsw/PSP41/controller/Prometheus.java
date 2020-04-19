package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Prometheus extends GodPower {

    public Prometheus(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    /**
     * Your Turn: If your Worker does not move up, it may build both before and after moving
     * @param board current board state
     */
    @Override
    public void moveBehaviour(Board board) {

        // Per poter attivare il potere, la lista di celle adiacenti ad un livello pari o inferiore a quello del worker non deve essere vuota
        List<Position> notHigherCells = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), true);

        // Non è possibile attivare il potere se la lista è vuota oppure se l'unica cella in cui si può costruire è ad un livello UGUALE a quello del worker
        if(notHigherCells.isEmpty())
            super.moveBehaviour(board);
        // Se c'è più di una cella ad un livello non più alto, se il potere è attivo faccio build senza porre limitazioni e move allo stesso livello o minore,
        // se il potere non è attivo faccio solo la normale move
        else {
            if(notHigherCells.size() == 1 && board.getCell(notHigherCells.get(0).getX(), notHigherCells.get(0).getY()).getLevel() ==
                    board.getCell(currWorker.getRow(), currWorker.getColumn()).getLevel()) {
                 // se c'è una sola cella disponibile per costruire, allora è sicuramente la cella di notHigherCells -> move normale
                 if(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()).size() == 1)
                        super.moveBehaviour(board);
                 // Se c'è una sola cella ad un livello non più alto del worker ma le celle dove costruire sono più di una, se il potere è attivo non devo
                 // permettere la costruzione sulla cella ad un livello non più alto
                 else{
                     uim.readPower();
                     // Se il potere è attivo faccio build e move, ma non devo permettere la costruzione nella cella ad un livello uguale a quello del worker,
                     // se il potere non è attivo faccio solo una move normale
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


    // Normale build ereditata da GodPower

}

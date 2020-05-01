package it.polimi.ingsw.PSP41.controller;


import it.polimi.ingsw.PSP41.model.*;

public abstract class GodPower {
    static boolean athenaPower = false;
    Player player;
    ActionManager am;
    UserInputManager uim;
    Worker currWorker;


    public Player getPlayer() {
        return player;
    }

    /*public static boolean getAthenaPower() {
        return athenaPower;
    }*/


    // The player isn't going to be able to make an auto-blocking move, but he can make a move
    // that blocks him for the next turn

    /**
     * Normal management of the worker choice at the start of a turn: the player can only choose workers that are able to move,
     * if both workers are blocked the player loses the game
     * @param board current board state
     */
    public void activeWorkers(Board board) {
        // Worker management in case no cells are available: (inside the class that manages the turns)
        // before calling behaviour it is necessary to check the available moves so that the player
        // knows which are the workers that are able to move. In case none of them is able to move, the player is beaten

        if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
                am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty()) {
            // Detach worker from the cells
            board.getCell(player.getWorker1().getRow(), player.getWorker1().getColumn()).detachWorker();
            board.getCell(player.getWorker2().getRow(), player.getWorker2().getColumn()).detachWorker();
            player.setStuck(true, board);
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
     * Normal move behaviour of a god power chosen by the player
     * @param board game board
     */
    public void moveBehaviour(Board board) {
        uim.readChosenDirection(am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower), currWorker.getRow(), currWorker.getColumn());
        int chosenRow = uim.getChosenRow();
        int chosenColumn = uim.getChosenColumn();
        checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
        player.move(currWorker, board, chosenRow, chosenColumn);
    }

    /**
     * Normal build behaviour of a god power chosen by the player
     * @param board current board state
     */
    // It is not necessary to check the build because after a normal move the worker can always build in the cell it has moved from
    public void buildBehaviour(Board board) {
        uim.readChosenDirection(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()), currWorker.getRow(), currWorker.getColumn());
        player.build(board, uim.getChosenRow(), uim.getChosenColumn());
    }

    /**
     * Check player win condition comparing the position of the worker is about to move with the position chosen for the movement
     * @param startCell worker present cell
     * @param endCell worker future cell
     */
    void checkWinCondition(Cell startCell, Cell endCell) {
        if (startCell.getLevel() == 2 && endCell.getLevel() == 3) {
            player.setWinner(true);
        }
    }
}
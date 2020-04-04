package it.polimi.ingsw.PSP41;

public abstract class GodPower {
    public static boolean athenaPower;
    protected Player player;
    protected ActionManager am;
    protected UserInputManager uim;
    protected Worker currWorker;

    /*public GodPower(Player player, UserInputManager uim) {
        this.player = player;
        this.am = am;
        this.uim = uim;
    }*/

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // Non permetto ad un giocatore di fare una mossa "autobloccante" (che non gli permettere di concludere il suo turno), ma gli permetto di fare una
    // mossa che lo blocchi per il turno successivo

    /**
     * Normal management of the worker choice at the start of a turn: the player can only choose workers that are able to move,
     * if both workers are blocked the player lose the game
     * @param board current board state
     */
    public void activeWorkers(Board board) {
        // Gestione in caso di mancanza di celle disponibili per un worker: (nella classe che gestisce lo scorrere dei
        // turni) prima di chiamare behavior controllo le mosse disponibili per i worker in modo tale da fornire al player
        // solo quello/i che può/possono muoversi. Nel caso nessun worker si possa muovere, il player è dichiarato sconfitto
        if(am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn(), athenaPower).isEmpty() &&
           am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn(), athenaPower).isEmpty()) {
            // Implementare rimozione player da una partita: magari ritornare una variabile che dice se è ancora in gioco, da controllare
            // all'inizio del turno. Se rimane un solo giocatore a non aver perso bisogna assegnargli vittoria
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
        //Normale comportamento della move di un worker
        uim.readChosenCell(am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn(), athenaPower));
        int chosenRow = uim.getChosenRow();
        int chosenColumn = uim.getChosenColumn();
        checkWinCondition(board.getCell(currWorker.getRow(), currWorker.getColumn()), board.getCell(chosenRow, chosenColumn));
        player.move(currWorker, board, chosenRow, chosenColumn);
    }

    /**
     * Normal build behaviour of a god power chosen by the player
     * @param board current board state
     */
    // Non è necessario nessun controllo generale sulla build perchè con una normale move un worker può semore costruire nella cella da cui è partito
    public void buildBehaviour(Board board) {
        //Normale comportamento della build
        uim.readChosenCell(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()));
        player.build(board, uim.getChosenRow(), uim.getChosenColumn());
    }

    /**
     * Check player win condition comparing the position of the worker is about to move with the position chosen for the movement
     * @param startCell worker present cell
     * @param endCell worker future cell
     */
    public void checkWinCondition(Cell startCell, Cell endCell) {
        if (startCell.getLevel() == 2 && endCell.getLevel() == 3) {
            player.setWinner(true);
        }
    }
}
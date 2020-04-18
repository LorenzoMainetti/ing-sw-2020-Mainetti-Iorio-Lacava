package it.polimi.ingsw.PSP41.model;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

public class Player {
    private final String nickname;
    private final Color color;
    private final Worker worker1;
    private final Worker worker2;
    //private final ArrayList<Worker> workers;
    private boolean winner;
    private boolean stuck;

    /**
     * Player constructor: it sets the name and the color of the player and creates two workers
     * @param nickname name chosen by the player
     * @param color color chosen by the player
     */
    public Player(String nickname, Color color) {
        this.nickname = nickname;
        this.color = color;
        worker1 = new Worker(color, 1);
        worker2 = new Worker(color, 2);
        //this.workers = new ArrayList<>();
        //this.workers.add(new Worker(color));
        //this.workers.add(new Worker(color));
        winner = false;
        stuck = false;
    }

    public String getNickname() {
        return nickname;
    }

    public Color getColor() {
        return color;
    }

    public Worker getWorker1() {
        return worker1;
    }

    public Worker getWorker2() {
        return worker2;
    }

    /*public Worker getWorker(int row, int column) {
        int i = -1;
        for(Worker worker : workers) {
            if (worker.getRow() == row && worker.getColumn() == column)
                 i = workers.indexOf(worker);
        }
        return workers.get(i);
    }

    public List<Worker> getWorkerList() {
        return Collections.unmodifiableList(workers);
    }*/

    /* Mancano condizioni di vittoria */

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isStuck() {
        return stuck;
    }

    public void setStuck(boolean stuck) {
        this.stuck = stuck;
    }

    /**
     * Set worker position equals to input cell
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    public void move(Worker worker, Board board, int row, int column) throws IllegalStateException, ArrayIndexOutOfBoundsException {
            if (row != worker.getRow() || column != worker.getColumn()) {
                    //elimina worker dalla cella in cui si trovava
                    board.getCell(worker.getRow(), worker.getColumn()).detachWorker();
                    //aggiungi worker alla cella in cui si deve muovere e aggiorna posizione worker
                    worker.setPosition(board, row, column);
                    //notify observers
            }
    }

    /**
     * Swap two workers
     * @param worker that the player wants to swap
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    public void swap(Worker worker,  Board board, int row, int column) throws IllegalStateException, ArrayIndexOutOfBoundsException{
        if (row != worker.getRow() || column != worker.getColumn()) {
            int oldRow = worker.getRow();
            int oldColumn = worker.getColumn();
            Worker opponent = board.getCell(row, column).getWorker();
            // Elimina worker avversario dalla cella in cui si trova
            board.getCell(row, column).detachWorker();
            // Move mio worker alla cella in cui si deve muovere
            board.getCell(worker.getRow(), worker.getColumn()).detachWorker();
            worker.setPosition(board, row, column);
            // Force opponent's worker nella cella prima occupata dal mio worker
            opponent.setPosition(board, oldRow, oldColumn);
            //notify observers
        }
    }

    /**
     * Build a level on the input cell
     * @param board board state
     * @param row where the player wants to build
     * @param column where the player wants to build
     */
    public void build(Board board, int row, int column) throws IllegalStateException {
        board.getCell(row, column).addLevel();
        //notify observers
    }

}
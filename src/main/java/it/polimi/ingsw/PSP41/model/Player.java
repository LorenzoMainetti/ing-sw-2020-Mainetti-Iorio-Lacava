package it.polimi.ingsw.PSP41.model;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
import it.polimi.ingsw.PSP41.observer.ModelObservable;



public class Player extends ModelObservable {
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


    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
        notifyWinner(this.nickname);
    }

    public boolean isStuck() {
        return stuck;
    }

    public void setStuck(boolean stuck, Board board) {
        this.stuck = stuck;
        notifyLoser(this.nickname);
        notifyBoard(board.clone());
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
            // Delete worker from the cell it is situated
            board.getCell(worker.getRow(), worker.getColumn()).detachWorker();

            // Add worker to the cell it has to move to and update worker's position
            worker.setPosition(board, row, column);
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

        notifyBoard(board.clone());
    }

    /**
     * Build a dome at any level
     * @param board board board state
     * @param row where the player wants to build
     * @param column where the player wants to build
     */
    public void buildDome(Board board, int row, int column) {
        board.getCell(row, column).addLevel();
        board.getCell(row, column).setDome(true);

        notifyBoard(board.clone());
    }

    /**
     * Remove a level
     * @param board board state
     * @param row of the cell the player wants to remove a level from
     * @param column of the cell the player wants to remove a level from
     */
    public void removeLevel(Board board, int row, int column) {
        board.getCell(row, column).removeLevel();

        notifyBoard(board.clone());
    }

}
package it.polimi.ingsw.PSP41;

import java.util.Observable;


public class Player extends Observable {

    private String nickname;
    private Color color;
    //lista di worker => più estendibile
    private Worker worker1;
    private Worker worker2;

    /* La posizione dei worker viene settata subito dopo nel main tramite nomeplayer.getWorker().setPosition(cella) */

    /**
     * Player constructor: it sets the name and the color of the player and creates two workers
     * @param nickname name chosen by the player
     * @param color color chosen by the player
     */
    public Player(String nickname, Color color) {
        this.nickname = nickname;
        this.color = color;
        this.worker1 = new Worker(color, 1);
        this.worker2 = new Worker(color, 2);
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

    /* Mancano condizioni di vittoria */

    /**
     * Set worker position equals to input cell
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    public void move(Worker worker, Board board, int row, int column) {
        if (row != worker.getRow() || column != worker.getColumn()) {
            //elimina worker dalla cella in cui si trovava
            board.getCell(worker.getRow(), worker.getColumn()).detachWorker();
            //aggiungi worker alla cella in cui si deve muovere e aggiorna posizione worker
            worker.setPosition(row, column);
            board.getCell(row, column).attachWorker(worker);

            setChanged();
            notifyObservers(board);
        }
    }

    /**
     * Build a level on the input cell
     * @param board board state
     * @param row where the player wants to build
     * @param column where the player wants to build
     */
    public void build(Board board, int row, int column) {
        board.getCell(row, column).addLevel();

        setChanged();
        notifyObservers(board);
    }

}
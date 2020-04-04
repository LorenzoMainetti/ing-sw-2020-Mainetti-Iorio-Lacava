package it.polimi.ingsw.PSP41;

public class Player {

    private String nickname;
    private Color color;
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
        this.worker1 = new Worker(color);
        this.worker2 = new Worker(color);
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
    public void move(Worker worker, Board board, int row, int column) throws IllegalStateException, ArrayIndexOutOfBoundsException {
            if (row != worker.getRow() || column != worker.getColumn()) {
                if(!board.getCell(row,column).isOccupied()) {
                    //elimina worker dalla cella in cui si trovava
                    board.getCell(worker.getRow(), worker.getColumn()).detachWorker();
                    //aggiungi worker alla cella in cui si deve muovere e aggiorna posizione worker
                    worker.setPosition(board, row, column);
                    board.getCell(row, column).attachWorker(worker);
                }
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
    }

}
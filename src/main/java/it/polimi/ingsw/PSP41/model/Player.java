package it.polimi.ingsw.PSP41.model;

import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import it.polimi.ingsw.PSP41.observer.ModelObservable;

public class Player extends ModelObservable {
    private final String nickname;
    private final Color color;
    private final Worker worker1;
    private final Worker worker2;
    private final GodPower godCard;
    private boolean winner;
    private boolean stuck;

    /**
     * Player constructor: it sets the name and the color of the player and creates two workers
     * @param nickname name chosen by the player
     * @param color color chosen by the player
     */
    public Player(String nickname, Color color, GodPower godCard) {
        this.nickname = nickname;
        this.color = color;
        worker1 = new Worker(color, 1);
        worker2 = new Worker(color, 2);
        this.godCard = godCard;
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

    public GodPower getGodCard() { return godCard; }

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
     * Applies GodPower's strategy for the MOVE phase
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    public void move(Worker worker, Board board, int row, int column) {
        godCard.move(worker, board, row, column);
    }

    /**
     * Apply GodPower's strategy for the BUILD phase
     * @param board board state
     * @param row where the player wants to build
     * @param column where the player wants to build
     */
    public void build(Board board, int row, int column) throws IllegalStateException {
        godCard.build(board, row, column);

        notifyBoard(board.clone());
    }

}
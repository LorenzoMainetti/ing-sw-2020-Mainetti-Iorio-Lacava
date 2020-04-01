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
     * @param cell cell selected by the player where the worker will move
     */
    public void move(Worker worker, Cell cell) {
        if (cell != worker.getPosition()) {
            worker.getPosition().setOccupied(false);
            worker.setPosition(cell);
            cell.setOccupied(true);
            cell.setColor(color);
        }
    }

    /**
     * Build a level on the input cell
     * @param cell cell where the player wants to build
     */
    public void build(Cell cell) {
        cell.addLevel();
    }

}
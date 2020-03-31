package it.polimi.ingsw.PSP41;

public class Worker {
    private Color color;
    private Cell position;

    /**
     * Worker constructor: it sets the color of the worker, the position will be set later by the player
     * @param color worker color decided by the player
     */
    public Worker(Color color) {
        this.color = color;
    }

    /* Posizione con riga e colonna? */
    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }
}
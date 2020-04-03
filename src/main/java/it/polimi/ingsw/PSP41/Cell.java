package it.polimi.ingsw.PSP41;


//import java.util.ArrayList;


public class Cell implements Cloneable {
    //private ArrayList<String> level; (gestisci level con list e enum ?)
    private int level;
    private boolean dome;
    private boolean occupied;
    Worker worker;

    /**
     * Initialize the Cell setting the attributes to default value
     */
    public Cell() {
        this.level = 0;
        this.occupied = false;
        this.dome = false;
        this.worker = null;
    }

    public boolean isDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public int getLevel() { return level; }

    /**
     * Increase the Cell level by one if there isn't a dome already
     */
    public void addLevel() {
        if (!isDome() && this.level < 4) {
            if (this.level == 3) this.dome = true;
            this.level ++;
        }
    }

    /**
     * Decrease the Cell level by one if it isn't the ground level
     */
    public void removeLevel() {
        if (this.level > 0) this.level--;
    }

    /**
     * Gets the Worker if there is one in the Cell
     * @return the worker placed in the Cell, null if there is no Worker
     */
    public Worker getWorker() {
        if (isOccupied())
            return worker;
        else //exception
            return null;
    }

    /**
     * Set a Worker in the Cell
     * @param worker to be placed it the Cell
     */
    public void attachWorker(Worker worker) {
        this.worker = worker;
        this.occupied = worker != null;
       // else input = null => throw exception ?
    }

    public void detachWorker() {
        this.worker = null;
        this.occupied = false;
    }

}

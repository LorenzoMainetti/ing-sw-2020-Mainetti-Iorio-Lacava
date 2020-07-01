package it.polimi.ingsw.PSP41.model;

import java.io.Serializable;
import java.lang.*;

public class Cell implements Serializable {
    private static final long serialVersionUID = -7341850408676706015L;

    private int level;
    private boolean dome;
    private boolean occupied;
    private Worker worker;

    /**
     * Initializes the Cell setting the attributes to default values
     */
    public Cell() {
        level = 0;
        occupied = false;
        dome = false;
        worker = null;
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
     * Increases the Cell level by one if there isn't a dome already
     */
    public void addLevel() throws IllegalStateException {
        if(!isDome() && level < 4) {
            if(level == 3)
                dome = true;
            else
                level ++;
        }
        else
            throw new IllegalStateException("You can't add another level.");
    }

    /**
     * Decreases the Cell level by one if it isn't the ground level
     */
    public void removeLevel() throws IllegalStateException {
        if (level > 0)
            level--;
        else
            throw new IllegalStateException("There are no levels to remove.");
    }


    /**
     * Gets the Worker if there is one in the Cell
     * @return the worker placed in the Cell if it exists, otherwise return null
     */
    public Worker getWorker() {
        if (isOccupied())
            return worker;
        else
            return null;
    }

    /**
     * Sets Worker in the Cell
     * @param worker to be placed in the Cell
     */
    public void attachWorker(Worker worker) throws IllegalStateException {
        if (isOccupied()) {
            throw new IllegalStateException("Cell occupied.");
        }
        else {
            if (worker != null) {
                this.worker = worker;
                occupied = true;
            }
        }
    }

    /**
     * Removes Worker from the Cell
     */
    public void detachWorker() throws IllegalStateException {
        if (isOccupied()) {
            worker = null;
            occupied = false;
        }
        else {
            throw new IllegalStateException("There isn't a worker in this cell.");
        }
    }

}

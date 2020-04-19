package it.polimi.ingsw.PSP41.model;

import java.lang.*;

public class Cell {
    private int level;
    private boolean dome;
    private boolean occupied;
    private Worker worker;

    /**
     * Initialize the Cell setting the attributes to default values
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
     * Increase the Cell level by one if there isn't a dome already
     */
    public void addLevel() throws IllegalStateException {
        if(!isDome() && level < 4) {
            if(level == 3) dome = true;
            level ++;
        }
        else
            throw new IllegalStateException("You can't add another level.");
    }

    /**
     * Decrease the Cell level by one if it isn't the ground level
     */
    public void removeLevel() throws IllegalStateException {
        if (level > 0)
            level--;
            //notify obs
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
     * Set Worker in the Cell
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
     * Remove worker from the Cell
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

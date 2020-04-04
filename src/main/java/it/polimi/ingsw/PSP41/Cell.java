package it.polimi.ingsw.PSP41;

import java.lang.*;
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
    public void addLevel() throws IllegalStateException {
        if(!isDome() && this.level < 4) {
            if(this.level == 3) this.dome = true;
            this.level ++;
        }
        else
            throw new IllegalStateException("You can't add another level.");
    }


    /**
     * Decrease the Cell level by one if it isn't the ground level
     */
    public void removeLevel() throws IllegalStateException {
        if(this.level > 0)
            this.level--;
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
     * Set a Worker in the Cell
     * @param worker to be placed in the Cell
     */
    public void attachWorker(Worker worker) throws IllegalStateException {
        if (this.isOccupied()) {
            throw new IllegalStateException("Cell occupied.");
        }
        else {
            this.worker = worker;
            this.occupied = true;
        }
    }


    /**
     * Remove worker from the Cell
     */

    public void detachWorker() throws IllegalStateException {
        if (this.isOccupied()) {
            this.worker = null;
            this.occupied = false;
        }
        else {
            throw new IllegalStateException("There isn't a worker in this cell.");
        }

    }

}

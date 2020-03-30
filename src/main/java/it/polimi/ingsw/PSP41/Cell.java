package it.polimi.ingsw.PSP41;


import java.util.ArrayList;

/**
 * @author Lorenzo Mainetti
 */
public class Cell implements Cloneable {
    //private ArrayList<String> level; (gestisci level con list e enum)
    private int level;
    private int row;
    private int column;
    private boolean dome;
    private boolean occupied;

    /**
     * Initialize the Cell with the given row and column and setting other attributes to default value
     * @param row given row of the Cell in the Board
     * @param column given column of the Cell in the Board
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.level = 0;
        this.occupied = false;
        this.dome = false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
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

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Increase the Cell level by one if there isn't a dome already
     * @param currLevel initial level of the Cell
     */
    public void addLevel(int currLevel) {
        if(!isDome()) {
            if(currLevel == 3) this.dome = true;
            this.level = currLevel + 1;
        }
    }

    /**
     * Decrease the Cell level by one if it isn't the ground level
     * @param currLevel initial level of the Cell
     */
    public void removeLevel(int currLevel) {
        if(currLevel > 0) this.level = currLevel - 1;
    }

    /**
     * Redefinition of the clone method of the class Object
     * @return a shallow copy of the Cell
     */
    @Override
    public Cell clone() {
        try {
            return (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

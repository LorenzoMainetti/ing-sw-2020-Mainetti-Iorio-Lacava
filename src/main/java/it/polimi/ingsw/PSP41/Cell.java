package it.polimi.ingsw.PSP41;


//import java.util.ArrayList;


public class Cell implements Cloneable {
    //private ArrayList<String> level; (gestisci level con list e enum ?)
    private int level;
    private boolean dome;
    private boolean occupied;
    Color color;

    /**
     * Initialize the Cell with the given row and column and set other attributes to default value
     */
    public Cell() {
        this.level = 0;
        this.occupied = false;
        this.dome = false;
        this.color = Color.NONE;
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
     */
    public void addLevel() {
        if(!isDome() && this.level < 4) {
            if(this.level == 3) this.dome = true;
            this.level ++;
        }
    }

    /**
     * Decrease the Cell level by one if it isn't the ground level
     */
    public void removeLevel() {
        if(this.level > 0) this.level--;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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

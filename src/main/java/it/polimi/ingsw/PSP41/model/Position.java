package it.polimi.ingsw.PSP41.model;

public class Position {
    private final int x;
    private final int y;
    private boolean perimeter = false;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Return if the position is on the perimeter of the board or not
     */
    public boolean isPerimeter() {

        if(this.getX() == 0 || this.getY()== 0 || this.getX() == 4 || this.getY() == 4)
            perimeter = true;

            return perimeter;
    }

}

package it.polimi.ingsw.PSP41.model;

public class Position {
    private final int posRow;
    private final int posColumn;
    private boolean perimeter = false;

    public Position(int posRow, int posColumn) {
        this.posRow = posRow;
        this.posColumn = posColumn;
    }

    public int getPosRow() {
        return posRow;
    }

    public int getPosColumn() {
        return posColumn;
    }

    /**
     * Return if the position is on the perimeter of the board or not
     */
    public boolean isPerimeter() {

        if(this.getPosRow() == 0 || this.getPosColumn()== 0 || this.getPosRow() == 4 || this.getPosColumn() == 4)
            perimeter = true;

            return perimeter;
    }

}

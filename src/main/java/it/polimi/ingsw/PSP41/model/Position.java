package it.polimi.ingsw.PSP41.model;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = -7205220472160839606L;
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
     * @return if the position is on the perimeter of the board or not
     */
    public boolean isPerimeter() {

        if(posRow == 0 || posColumn== 0 || posRow == 4 || posColumn == 4)
            perimeter = true;

            return perimeter;
    }

}

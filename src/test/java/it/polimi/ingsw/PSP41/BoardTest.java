package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Board.
 */
public class BoardTest {
    Board board;
    Cell cell;

    @Before
    public void setup() { board = new Board(); }

    @Test
    public void getCell_exists() {
        try {
            cell = board.getCell(1, 3);
        }
        catch (ArrayIndexOutOfBoundsException ex){
            assertEquals("Invalid position.", ex.getMessage());
        }
        finally {
            assertSame(board.getGrid()[1][3], cell);
        }

    }

    @Test
    public void getCell_notExists() {
        try {
            cell = board.getCell(3, 5);
        }
        catch (ArrayIndexOutOfBoundsException ex){
            assertEquals("Invalid position.", ex.getMessage());
        }
    }

}



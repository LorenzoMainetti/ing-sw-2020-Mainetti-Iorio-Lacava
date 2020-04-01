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
        cell = board.getCell(1,3);
        assertSame(board.getGrid()[1][3], cell);
    }

    /*@Test
    public void getCell_notExists() {

    }*/

}



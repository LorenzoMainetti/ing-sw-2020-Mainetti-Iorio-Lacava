package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Cell;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Unit test for Worker.
 */
public class WorkerTest {
    Worker worker;
    Cell position;
    Board board;

    @Before
    public void setup() {
        position = new Cell();
        worker = new Worker(Color.RED, 1);
        board = new Board();
    }

    @Test
    public void testConstructor() {
        assertSame(Color.RED, worker.getColor());
        assertEquals(1, worker.getNumber());
        assertEquals(-1, worker.getRow());
        assertEquals(-1, worker.getColumn());
    }

    @Test
    public void setPositionTest() {
        try {
            worker.setPosition(board, 0, 3);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            assertEquals("Invalid position.", ex.getMessage());
        }
        catch (IllegalStateException ex){
            assertEquals("Position taken.", ex.getMessage());
        }
        finally {
            assertEquals(0, worker.getRow());
            assertEquals(3, worker.getColumn());
            assertTrue(board.getCell(0, 3).isOccupied());
            assertSame(board.getCell(0, 3).getWorker(), worker);
        }
    }

    @Test
    public void setPositionExceptionTest() {
        Worker anotherWorker = new Worker(Color.YELLOW, 1);

        try {
            worker.setPosition(board,5, 9);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            assertEquals("Invalid position.", ex.getMessage());
        }
        catch (IllegalStateException ex){
            assertEquals("Position taken.", ex.getMessage());
        }
        finally {
            assertEquals(-1, worker.getRow());
            assertEquals(-1, worker.getColumn());
        }


        try {
            worker.setPosition(board,2, 3);
            anotherWorker.setPosition(board, 2,3);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            assertEquals("Invalid position.", ex.getMessage());
        }
        catch (IllegalStateException ex){
            assertEquals("Position taken.", ex.getMessage());
        }
        finally {
            assertEquals(-1, anotherWorker.getRow());
            assertEquals(-1, anotherWorker.getColumn());
            assertEquals(2, worker.getRow());
            assertEquals(3, worker.getColumn());
        }
    }

}
package it.polimi.ingsw.PSP41;

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
        worker = new Worker(Color.RED);
        board = new Board();
    }

    @Test
    public void testConstructor() {
        assertSame(Color.RED, worker.getColor());
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
        }
    }

    @Test
    public void setPositionExceptionTest() {
        Worker anotherWorker = new Worker(Color.YELLOW);

        try {
            worker.setPosition(board,5, 9);
            board.getCell(5,9).attachWorker(worker);
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
            board.getCell(5,9).attachWorker(anotherWorker);
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
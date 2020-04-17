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

    @Before
    public void setup() {
        position = new Cell();
        worker = new Worker(Color.RED, 1);
    }

    @Test
    public void testConstructor() {
        assertSame(Color.RED, worker.getColor());
        assertEquals(-1, worker.getRow());
        assertEquals(-1, worker.getColumn());
    }

    @Test
    public void setPositionTest() {
        worker.setPosition(0, 3);
        assertEquals(0, worker.getRow());
        assertEquals(3, worker.getColumn());
    }
}
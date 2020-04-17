package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Cell.
 */
public class CellTest {
    Cell cell;
    Worker worker;

    @Before
    public void setup() {
        cell = new Cell();
        worker = new Worker(Color.RED, 1);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, cell.getLevel());
        assertFalse(cell.isDome());
        assertFalse(cell.isOccupied());
    }

    @Test
    public void addLevel_noDomeNoLev3_increaseLev() {
        cell.addLevel();
        assertEquals(1, cell.getLevel());
    }

    @Test
    public void addLevel_noDomeLev3_increaseLevAddDome() {
        cell.addLevel();
        cell.addLevel();
        cell.addLevel();
        cell.addLevel();
        assertEquals(4, cell.getLevel());
        assertTrue(cell.isDome());
    }

    @Test
    public void removeLevel_noGround_decreaseLevel() {
        cell.addLevel();
        cell.removeLevel();
        assertEquals(0, cell.getLevel());
    }

    @Test
    public void testWorkerMethods() {
        assertNull(cell.getWorker());

        cell.attachWorker(null);
        assertNull(cell.getWorker());
        cell.attachWorker(worker);
        assertTrue(cell.isOccupied());
        assertEquals(worker, cell.getWorker());

        cell.detachWorker();
        assertNull(cell.getWorker());
        assertFalse(cell.isOccupied());
    }

}

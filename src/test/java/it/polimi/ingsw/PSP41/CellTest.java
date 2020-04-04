package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.lang.*;

/**
 * Unit test for Cell.
 */
public class CellTest {
    Cell cell;
    Worker worker;

    @Before
    public void setup() {
        cell = new Cell();
        worker = new Worker(Color.RED);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, cell.getLevel());
        assertFalse(cell.isDome());
        assertFalse(cell.isOccupied());
    }

    @Test
    public void addLevel_noDomeNoLev3_increaseLev() {
        try {
            cell.addLevel();
        }
        catch (IllegalStateException ex) {
            assertEquals("You can't add another level.", ex.getMessage());
        }
        finally{
            assertEquals(1, cell.getLevel());
        }
    }

    @Test
    public void addLevel_noDomeLev3_increaseLevAddDome() {
        try {
            cell.addLevel();
            cell.addLevel();
            cell.addLevel();
            cell.addLevel();
        }
        catch (IllegalStateException ex) {
            assertEquals("You can't add another level.", ex.getMessage());
        }
        finally {
            assertEquals(4, cell.getLevel());
            assertTrue(cell.isDome());
        }

    }

    @Test
    public void removeLevel_noGround_decreaseLevel() {
        try{
            cell.addLevel();
            cell.removeLevel();
        }
        catch (IllegalStateException ex) {
            assertEquals("There are no levels to remove.", ex.getMessage());
        }
        finally {
            assertEquals(0, cell.getLevel());
        }
    }

    @Test
    public void testAddLevelException() {
        try {
            cell.addLevel();
            cell.addLevel();
            cell.addLevel();
            cell.addLevel();
            cell.addLevel();
        } catch (IllegalStateException ex) {
            assertEquals("You can't add another level.", ex.getMessage());
        } finally {
            assertEquals(4, cell.getLevel());
            assertTrue(cell.isDome());
        }
    }


    @Test
    public void testWorkerMethods() {
        try {
            assertNull(cell.getWorker());
            cell.attachWorker(worker);
        }
        catch (IllegalStateException ex) {
            assertEquals("Cell occupied.", ex.getMessage());
        }
        finally{
            assertTrue(cell.isOccupied());
            assertEquals(worker, cell.getWorker());
        }

        try {
            cell.detachWorker();
        }
        catch (IllegalStateException ex) {
            assertEquals("There isn't a worker in this cell.", ex.getMessage());
        }
        finally{
            assertNull(cell.getWorker());
            assertFalse(cell.isOccupied());
        }
    }

    @Test
    public void testWorkerMethodsExceptions() {
        Worker anotherWorker = new Worker(Color.YELLOW);

        try{
            assertNull(cell.getWorker());
            cell.attachWorker(worker);
            cell.attachWorker(anotherWorker);
        }
        catch (IllegalStateException ex) {
            assertEquals("Cell occupied.", ex.getMessage());
        }
        finally{
            assertTrue(cell.isOccupied());
            assertEquals(worker, cell.getWorker());
        }

        try {
            cell.detachWorker();
            cell.detachWorker();
        }
        catch (IllegalStateException ex) {
            assertEquals("There isn't a worker in this cell.", ex.getMessage());
        }
        finally{
            assertNull(cell.getWorker());
            assertFalse(cell.isOccupied());
        }
    }

}

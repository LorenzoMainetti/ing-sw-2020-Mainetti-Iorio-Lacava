package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Cell.
 */
public class CellTest {
    Cell cell;

    @Before
    public void setup() {
        cell = new Cell();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, cell.getLevel());
        assertFalse(cell.isDome());
        assertFalse(cell.isOccupied());
    }

    @Test
    public void testSetters() {
        cell.setOccupied(true);
        cell.setColor(Color.RED);
        assertTrue(cell.isOccupied());
        assertEquals(Color.RED, cell.getColor());
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
    public void testClone() {
        Cell copy = cell.clone();
        assertNotSame(cell, copy);
        assertEquals(cell.getLevel(), copy.getLevel());
        assertEquals(cell.isDome(), copy.isDome());
        assertEquals(cell.isOccupied(), copy.isOccupied());
        assertEquals(cell.getColor(), copy.getColor());
    }

    @Test
    public void TestCloneAfterChange(){
        Cell copy = cell.clone();
        copy.setDome(true);
        assertNotEquals(cell.isDome(), copy.isDome());
    }

}

package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Cell.
 */
public class CellTest
{
    Cell cell;

    @Before
    public void setup() {
        cell = new Cell(1,2);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getColumn());
        assertEquals(0, cell.getLevel());
        assertFalse(cell.isDome());
        assertFalse(cell.isOccupied());
    }

    @Test
    public void testSetters() {
        cell.setRow(3);
        cell.setColumn(0);
        cell.setOccupied(true);
        assertEquals(3, cell.getRow());
        assertEquals(0, cell.getColumn());
        assertTrue(cell.isOccupied());
    }

    @Test
    public void addLevel_noDomeNoLev3_increaseLev() {
        cell.addLevel(1);
        assertEquals(2, cell.getLevel());
    }

    @Test
    public void addLevel_noDomeLev3_increaseLevAddDome() {
        cell.addLevel(3);
        assertEquals(4, cell.getLevel());
        assertTrue(cell.isDome());
    }

    @Test
    public void removeLevel_noGround_decreaseLevel() {
        cell.addLevel(0);
        cell.removeLevel(1);
        assertEquals(0, cell.getLevel());
    }

    @Test
    public void testClone() {
        Cell copy = cell.clone();
        assertNotSame(cell, copy);
        assertEquals(cell.getRow(), copy.getRow());
        assertEquals(cell.getColumn(), copy.getColumn());
        assertEquals(cell.getLevel(), copy.getLevel());
        assertEquals(cell.isDome(), copy.isDome());
        assertEquals(cell.isOccupied(), copy.isOccupied());
    }

    @Test
    public void TestCloneAfterChange(){
        Cell copy = cell.clone();
        copy.setDome(true);
        assertNotEquals(cell.isDome(), copy.isDome());
    }

}

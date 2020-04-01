package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test for ActionManager.
 */
public class ActionManagerTest {
    ActionManager actionManager;
    Board board;

    @Before
    public void setup() {
        actionManager = new ActionManager();
        board = new Board();
    }

    /*@Test
    public void getNeighbouringCells_notPerimeter() {
        Cell c1 = board.getCell(0, 1);
        Cell c2 = board.getCell(0, 2);
        Cell c3 = board.getCell(0, 3);
        Cell c4 = board.getCell(1, 1);
        Cell c5 = board.getCell(1, 3);
        Cell c6 = board.getCell(2, 1);
        Cell c7 = board.getCell(2, 2);
        Cell c8 = board.getCell(2, 3);
        ArrayList<Cell> test = new ArrayList<>();
        test.add(c1);
        test.add(c2);
        test.add(c3);
        test.add(c4);
        test.add(c5);
        test.add(c6);
        test.add(c7);
        test.add(c8);
        assertArrayEquals(test.toArray(), actionManager.getNeighbouringCells(board, 1, 2).toArray());
    }

    @Test
    public void getNeighbouringCells_Perimeter() {
        Cell c1 = board.getCell(3, 1);
        Cell c2 = board.getCell(3, 2);
        Cell c3 = board.getCell(3, 3);
        Cell c4 = board.getCell(4, 1);
        Cell c5 = board.getCell(4, 3);
        ArrayList<Cell> test = new ArrayList<>();
        test.add(c1);
        test.add(c2);
        test.add(c3);
        test.add(c4);
        test.add(c5);
        assertArrayEquals(test.toArray(), actionManager.getNeighbouringCells(board, 4, 2).toArray());
    }

    @Test
    public void getNeighbouringCells_Angle() {
        Cell c1 = board.getCell(0, 1);
        Cell c2 = board.getCell(1, 0);
        Cell c3 = board.getCell(1, 1);
        ArrayList<Cell> test = new ArrayList<>();
        test.add(c1);
        test.add(c2);
        test.add(c3);
        assertArrayEquals(test.toArray(), actionManager.getNeighbouringCells(board, 0, 0).toArray());
    }*/

    @Test
    public void testGetValidMoves() {
        Cell c1 = board.getCell(0, 0);
        Cell c2 = board.getCell(1, 1);
        Cell c3 = board.getCell(1, 2);
        c1.setDome(true);
        c2.setOccupied(true);
        c3.addLevel();
        c3.addLevel();

        Cell c4 = board.getCell(0, 2);
        Cell c5 = board.getCell(1, 0);
        ArrayList<Cell> test = new ArrayList<>();
        test.add(c4);
        test.add(c5);
        assertArrayEquals(test.toArray(), actionManager.getValidMoves(board, 0, 1).toArray());
    }

    @Test
    public void testGetValidBuilds() {
        Cell c1 = board.getCell(3, 3);
        Cell c2 = board.getCell(3, 4);
        c1.setDome(true);
        c2.setOccupied(true);

        Cell c4 = board.getCell(4, 3);
        ArrayList<Cell> test = new ArrayList<>();
        test.add(c4);
        assertArrayEquals(test.toArray(), actionManager.getValidBuilds(board, 4, 4).toArray());
    }

    @Test
    public void getNeighbouringOpponentWorker() {
        Cell c1 = board.getCell(3, 3);
        Cell c2 = board.getCell(3, 4);
        Cell c3 = board.getCell(4, 4);
        c1.setOccupied(true);
        c1.setColor(Color.RED);
        c2.setOccupied(true);
        c2.setColor(Color.BLUE);
        c3.setOccupied(true);
        c3.setColor(Color.RED);

        ArrayList<Cell> test = new ArrayList<>();
        test.add(c2);
        assertArrayEquals(test.toArray(), actionManager.getNeighbouringOpponentWorker(board, 4, 4).toArray());
    }

}

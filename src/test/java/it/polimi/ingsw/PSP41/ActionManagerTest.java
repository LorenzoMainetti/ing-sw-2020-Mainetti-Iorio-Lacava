package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test for ActionManager.
 */
public class ActionManagerTest {
    ActionManager actionManager;
    Board board;
    Worker worker;
    Cell c;

    @Before
    public void setup() {
        actionManager = new ActionManager();
        board = new Board();
        worker = new Worker(Color.RED);
    }

    @Test
    public void testGetValidMoves() {
        c = board.getCell(1, 1);
        Cell c1 = board.getCell(0, 0);
        Cell c2 = board.getCell(0, 2);
        c.attachWorker(worker);
        c1.setDome(true);
        c2.addLevel();
        c2.addLevel();

        Cell c3 = board.getCell(1, 0);
        Cell c4 = board.getCell(1, 2);
        ArrayList<Cell> test = new ArrayList<>();
        test.add(c3);
        test.add(c4);
        assertArrayEquals(test.toArray(), actionManager.getValidMoves(board, 0, 1).toArray());
    }

    @Test
    public void testGetValidBuilds() {
        c = board.getCell(2, 2);
        Cell c1 = board.getCell(0, 2);
        c.attachWorker(worker);
        c1.setDome(true);

        Cell c2 = board.getCell(0, 0);
        Cell c3 = board.getCell(0, 1);
        Cell c4 = board.getCell(1, 0);
        Cell c5 = board.getCell(1, 2);
        Cell c6 = board.getCell(2, 0);
        Cell c7 = board.getCell(2, 1);
        c2.addLevel();
        c2.addLevel();
        c2.addLevel();

        ArrayList<Cell> test = new ArrayList<>();
        test.add(c2);
        test.add(c3);
        test.add(c4);
        test.add(c5);
        test.add(c6);
        test.add(c7);
        assertArrayEquals(test.toArray(), actionManager.getValidBuilds(board, 1, 1).toArray());
    }

    @Test
    public void testGetNeighbouringOpponentWorkers() {
        c = board.getCell(4, 4);
        Cell c1 = board.getCell(3, 3);
        Cell c2 = board.getCell(3, 4);
        Worker myWorker = new Worker(Color.RED);
        Worker oppWorker = new Worker(Color.BLUE);
        c.attachWorker(worker);
        c1.attachWorker(myWorker);
        c2.attachWorker(oppWorker);

        ArrayList<Cell> test = new ArrayList<>();
        test.add(c2);
        assertArrayEquals(test.toArray(), actionManager.getNeighbouringOpponentWorkers(board, 4, 4).toArray());
    }

    @After
    public void tearDown() {
        c.detachWorker();
    }

}

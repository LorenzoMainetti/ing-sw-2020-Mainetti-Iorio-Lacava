package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import it.polimi.ingsw.PSP41.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        worker = new Worker(Color.RED, 1);
    }

    @Test
    public void testGetValidMoves() {
        c = board.getCell(0, 0);
        c.attachWorker(worker);
        Cell c1 = board.getCell(0, 1);
        c1.setDome(true);
        Cell c2 = board.getCell(1, 1);
        c2.addLevel();
        c2.addLevel();

        List<Position> test;
        test = actionManager.getValidMoves(board, 0, 0);
        assertEquals(1, test.size());
        assertEquals(1, test.get(0).getPosRow());
        assertEquals(0, test.get(0).getPosColumn());
    }

    @Test
    public void testGetNotHigherCells() {
        c = board.getCell(0, 0);
        c.attachWorker(worker);
        Cell c1 = board.getCell(0, 1);
        c1.setDome(true);
        Cell c2 = board.getCell(1, 1);
        c2.addLevel();

        List<Position> test;
        test = actionManager.getNotHigherCells(board, 0, 0);
        assertEquals(1, test.size());
        assertEquals(1, test.get(0).getPosRow());
        assertEquals(0, test.get(0).getPosColumn());
    }

    @Test
    public void testGetValidBuilds() {
        c = board.getCell(4, 4);
        c.attachWorker(worker);
        Cell c1 = board.getCell(3, 4);
        c1.setDome(true);

        List<Position> test;
        test = actionManager.getValidBuilds(board, 4, 4);
        assertEquals(2, test.size());
        assertEquals(3, test.get(0).getPosRow());
        assertEquals(3, test.get(0).getPosColumn());
        assertEquals(4, test.get(1).getPosRow());
        assertEquals(3, test.get(1).getPosColumn());
    }

    @Test
    public void testGetValidRemovableBlocks() {
        c = board.getCell(4, 4);
        c.attachWorker(worker);
        Cell c1 = board.getCell(3, 4);
        c1.setDome(true);
        Cell c2 = board.getCell(3, 3);
        c2.addLevel();
        c2.addLevel();

        List<Position> test;
        test = actionManager.getValidRemovableBlocks(board, 4, 4);
        assertEquals(1, test.size());
        assertEquals(3, test.get(0).getPosRow());
        assertEquals(3, test.get(0).getPosColumn());
    }

    @Test
    public void testGetOpponentWorkers() {
        c = board.getCell(4, 4);
        c.attachWorker(worker);
        Cell c1 = board.getCell(3, 3);
        Worker myWorker = new Worker(Color.RED, 2);
        c1.attachWorker(myWorker);
        Cell c2 = board.getCell(3, 4);
        Worker oppWorker = new Worker(Color.BLUE, 1);
        c2.attachWorker(oppWorker);

        List<Position> test;
        test = actionManager.getOpponentWorkers(board, 4, 4);
        assertEquals(1, test.size());
        assertEquals(3, test.get(0).getPosRow());
        assertEquals(4, test.get(0).getPosColumn());
    }

    @Test
    public void testGetActiveOpponentWorkers() {
        c = board.getCell(4, 4);
        c.attachWorker(worker);
        Cell c1 = board.getCell(3, 3);
        Cell c2 = board.getCell(3, 4);
        Worker oppWorker1 = new Worker(Color.BLUE, 1);
        Worker oppWorker2 = new Worker(Color.BLUE, 2);
        c1.attachWorker(oppWorker1);
        c2.attachWorker(oppWorker2);
        board.getCell(2, 3).setDome(true);
        board.getCell(2, 4).setDome(true);
        board.getCell(4, 3).setDome(true);

        List<Position> test;
        test = actionManager.getActiveOpponentWorkers(board, 4, 4);
        assertEquals(1, test.size());
        assertEquals(3, test.get(0).getPosRow());
        assertEquals(3, test.get(0).getPosColumn());
    }

    @Test
    public void testGetOtherWorker() {
        c = board.getCell(4, 4);
        worker.setPosition(board, 4, 4);
        Worker myOtherWorker = new Worker(Color.RED, 2);
        myOtherWorker.setPosition(board, 1, 2);
        Worker oppWorker = new Worker(Color.BLUE, 1);
        oppWorker.setPosition(board, 0, 0);

        Position test;
        test = actionManager.getOtherWorker(board, 4, 4);
        assertEquals(1, test.getPosRow());
        assertEquals(2, test.getPosColumn());
    }

    @After
    public void tearDown() {
        c.detachWorker();
    }

}

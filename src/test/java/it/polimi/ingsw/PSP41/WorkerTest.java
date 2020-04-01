package it.polimi.ingsw.PSP41;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {
    Worker worker;
    Cell position;

    @Before
    public void setup() {
        position = new Cell();
        worker = new Worker(Color.RED);
    }

    @Test
    public void testConstructor() {
        assertSame(Color.RED, worker.getColor());
    }

    @Test
    public void setPositionTest() {
        Cell newPosition = new Cell();
        worker.setPosition(newPosition);
        assertSame(newPosition, worker.getPosition());
    }
}
package it.polimi.ingsw.PSP41;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player;
    Cell startPosition1 = new Cell(0, 0);
    Cell startPosition2 = new Cell(3, 3);

    @Before
    public void setup() {
        player = new Player("Olimpia", Color.RED);
        player.getWorker1().setPosition(startPosition1);
        player.getWorker2().setPosition(startPosition2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Olimpia", player.getNickname());
        assertSame(Color.RED, player.getColor());
        assertSame(Color.RED, player.getWorker1().getColor());
        assertSame(Color.RED, player.getWorker2().getColor());
    }

    @Test
    public void moveWorker1Test() {
        Cell newPosition1 = new Cell(1, 1);
        player.move(player.getWorker1(), newPosition1);
        assertFalse(startPosition1.isOccupied());
        assertSame(newPosition1, player.getWorker1().getPosition());
        assertTrue(player.getWorker1().getPosition().isOccupied());
    }

    @Test
    public void moveWorker2Test() {
        Cell newPosition2 = new Cell(4, 2);
        player.move(player.getWorker2(), newPosition2);
        assertFalse(startPosition2.isOccupied());
        assertSame(newPosition2, player.getWorker2().getPosition());
        assertTrue(player.getWorker2().getPosition().isOccupied());
    }

    @Test
    public void buildTest() {
        Cell buildPosition = new Cell(0,4);
        player.build(buildPosition);
        assertEquals(1, buildPosition.getLevel());
    }
}
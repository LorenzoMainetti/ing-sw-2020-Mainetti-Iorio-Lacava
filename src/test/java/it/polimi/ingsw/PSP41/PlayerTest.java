package it.polimi.ingsw.PSP41;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Player.
 */
public class PlayerTest {
    Player player;
    Board board;

    @Before
    public void setup() {
        board = new Board();
        player = new Player("Olimpia", Color.RED);
        player.getWorker1().setPosition(0, 3);
        player.getWorker2().setPosition(4, 4);
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
        player.move(player.getWorker1(), board, 0, 2);
        assertFalse(board.getCell(0, 3).isOccupied());
        assertEquals(0, player.getWorker1().getRow());
        assertEquals(2, player.getWorker1().getColumn());
        assertTrue(board.getCell(0, 2).isOccupied());
    }

    @Test
    public void buildTest() {
        player.build(board, 0, 3);
        assertEquals(1, board.getCell(0, 3).getLevel());
    }
}
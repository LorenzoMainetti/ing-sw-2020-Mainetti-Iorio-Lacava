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

        player.getWorker1().setPosition(board, 0, 3);
        board.getCell(0, 3).attachWorker(player.getWorker1());
        player.getWorker2().setPosition(board, 4, 4);
        board.getCell(4, 4).attachWorker(player.getWorker2());
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
        try {
            player.move(player.getWorker1(), board, 0, 2);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            assertEquals("Invalid position.", ex.getMessage());
        }
        catch (IllegalStateException ex) {
            assertEquals("Position taken.", ex.getMessage());
        }
        finally {
            assertFalse(board.getCell(0, 3).isOccupied());
            assertEquals(0, player.getWorker1().getRow());
            assertEquals(2, player.getWorker1().getColumn());
            assertTrue(board.getCell(0, 2).isOccupied());
        }
    }

    @Test
    public void moveWorker1TestException() {
        try {
            player.move(player.getWorker1(), board, 4, 4);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            assertEquals("Invalid position.", ex.getMessage());
        }
        catch (IllegalStateException ex) {
            assertEquals("Position taken.", ex.getMessage());
        }
        finally {
            assertTrue(board.getCell(0, 3).isOccupied());
            assertTrue(board.getCell(4, 4).isOccupied());
            assertEquals(0, player.getWorker1().getRow());
            assertEquals(3, player.getWorker1().getColumn());
        }
    }

    @Test
    public void buildTest() {
        player.build(board, 0, 3);
        assertEquals(1, board.getCell(0, 3).getLevel());
    }


}
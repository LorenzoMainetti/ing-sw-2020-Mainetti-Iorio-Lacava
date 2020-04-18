package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.Worker;
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
        player.getWorker2().setPosition(board, 4, 4);
    }

    @Test
    public void testConstructor() {
        assertEquals("Olimpia", player.getNickname());
        assertSame(Color.RED, player.getColor());
        assertSame(Color.RED, player.getWorker1().getColor());
        assertSame(Color.RED, player.getWorker2().getColor());
        assertFalse(player.isWinner());
        assertFalse(player.isStuck());
    }

    @Test
    public void testMove() {
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
    public void testMoveException() {
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
            assertFalse(board.getCell(0, 3).isOccupied());
            assertTrue(board.getCell(4, 4).isOccupied());
            assertEquals(0, player.getWorker1().getRow());
            assertEquals(3, player.getWorker1().getColumn());
        }
    }

    @Test
    public void testSwap() {
        Worker opponent = new Worker(Color.BLUE, 1);
        opponent.setPosition(board, 0, 2);
        try {
            player.swap(player.getWorker1(), board, 0, 2);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            assertEquals("Invalid position.", ex.getMessage());
        }
        catch (IllegalStateException ex) {
            assertEquals("Position taken.", ex.getMessage());
        }
        finally {
            assertSame(opponent, board.getCell(0, 3).getWorker());
            assertEquals(0, player.getWorker1().getRow());
            assertEquals(2, player.getWorker1().getColumn());
            assertTrue(board.getCell(0, 2).isOccupied());
        }

    }

    @Test
    public void buildTest() {
        player.build(board, 0, 3);
        assertEquals(1, board.getCell(0, 3).getLevel());
    }


}
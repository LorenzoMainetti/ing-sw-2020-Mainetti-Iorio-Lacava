package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.Cell;
import it.polimi.ingsw.PSP41.model.godCards.Default;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
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
        player = new Player("Olimpia", Color.RED, new Default());

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
    public void testSetters() {
        player.setWinner(true);
        assertTrue(player.isWinner());
        player.setStuck(true, board);
        assertTrue(player.isStuck());
    }

    @Test
    public void testMove() {
        player.move(player.getWorker1(), board, 0, 2);
        assertFalse(board.getCell(0, 3).isOccupied());
        assertEquals(0, player.getWorker1().getRow());
        assertEquals(2, player.getWorker1().getColumn());
        assertTrue(board.getCell(0, 2).isOccupied());

        player.getGodCard().move(player.getWorker2(), board, 3, 4);
        assertEquals(3, player.getWorker2().getRow());
        assertEquals(4, player.getWorker2().getColumn());
        assertTrue(board.getCell(3, 4).isOccupied());

    }

    @Test
    public void testBuild() {
        player.build(board, 0, 3);
        assertEquals(1, board.getCell(0, 3).getLevel());
    }

    @Test
    public void testBuild_withDome() {
        Cell c = board.getCell(0,3);
        c.addLevel();
        c.addLevel();
        c.addLevel();
        player.build(board, 0, 3);
        assertEquals(3, board.getCell(0, 3).getLevel());
        assertTrue(board.getCell(0, 3).isDome());
    }

}
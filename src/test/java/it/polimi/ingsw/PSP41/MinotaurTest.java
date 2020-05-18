package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Minotaur;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Minotaur GodPower.
 */
public class MinotaurTest {
    Board board;
    Worker opponent;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        opponent = new Worker(Color.BLUE, 1);
        godPower = new Minotaur();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
        opponent.setPosition(board, 3, 4);
    }

    @Test
    public void testIsActionable_True() {
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testIsActionable_False() {
        board.getCell(2, 4).setDome(true);
        assertFalse(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testGodPowerRequired_First() {
        assertEquals(1, godPower.godPowerRequired(board, player));
    }

    @Test
    public void testGodPowerRequired_Second() {
        player.move(opponent, board, 1, 2);
        assertEquals(2, godPower.godPowerRequired(board, player));
    }

    @Test
    public void testGodPowerRequired_Third() {
        Worker opp = new Worker(Color.BLUE, 2);
        opp.setPosition(board, 1, 2);
        assertEquals(0, godPower.godPowerRequired(board, player));
    }

    @Test
    public void testGodPowerRequired_Fourth() {
        player.move(opponent, board, 0, 4);
        assertEquals(-1, godPower.godPowerRequired(board, player));
    }

    @Test
    public void testApplyEffect() {
        List<Position> positions = actionManager.getValidMoves(board, 4, 4);
        godPower.applyEffect(positions, board, player.getWorker1(), TurnPhase.MOVE);
        assertEquals(3, positions.get(2).getPosRow());
        assertEquals(4, positions.get(2).getPosColumn());
    }

    @Test
    public void testMove() {
        godPower.move(player.getWorker1(), board, 3, 4);
        assertFalse(board.getCell(4,4).isOccupied());
        assertTrue(board.getCell(3,4).isOccupied());
        assertTrue(board.getCell(2,4).isOccupied());
        assertEquals(2, opponent.getRow());
        assertEquals(4, opponent.getColumn());
        assertEquals(3, player.getWorker1().getRow());
        assertEquals(4, player.getWorker1().getColumn());
    }

}

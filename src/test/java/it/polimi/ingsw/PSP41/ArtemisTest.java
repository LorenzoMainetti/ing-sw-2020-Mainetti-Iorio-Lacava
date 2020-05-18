package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Artemis;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Artemis GodPower.
 */
public class ArtemisTest {
    Board board;
    Worker opponent;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        opponent = new Worker(Color.BLUE, 1);
        godPower = new Artemis();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
        opponent.setPosition(board, 2, 4);
        player.move(player.getWorker1(), board, 3, 4);
    }

    @Test
    public void testIsActionable_True() {
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testIsActionable_False() {
        board.getCell(2, 3).setDome(true);
        board.getCell(3, 3).setDome(true);
        board.getCell(4, 3).setDome(true);
        assertFalse(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testApplyEffect() {
        godPower.setTriggered(true);
        board.getCell(2, 3).setDome(true);
        board.getCell(3, 3).setDome(true);
        List<Position> positions = actionManager.getValidMoves(board, 3, 4);
        godPower.applyEffect(positions, board, player.getWorker1(), TurnPhase.MOVE);
        assertEquals(1, positions.size());
        assertEquals(4, positions.get(0).getPosRow());
        assertEquals(3, positions.get(0).getPosColumn());
    }

    @Test
    public void testAddPhase() {
        godPower.setTriggered(true);
        godPower.addPhase();
        assertEquals(3, godPower.getPhases().size());
        assertEquals(TurnPhase.MOVE, godPower.getPhases().get(1));
        godPower.setTriggered(false);
        godPower.addPhase();
        assertEquals(2, godPower.getPhases().size());
    }

}

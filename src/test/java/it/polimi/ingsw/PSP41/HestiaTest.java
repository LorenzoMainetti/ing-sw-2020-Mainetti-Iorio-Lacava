package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Hestia;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Hestia GodPower.
 */
public class HestiaTest {
    Board board;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        godPower = new Hestia();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
        player.build(board, 3, 4);
    }

    @Test
    public void testIsActionable_True() {
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testIsActionable_False() {
        board.getCell(3,3).setDome(true);
        assertFalse(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testApplyEffect() {
        godPower.setTriggered(true);
        List<Position> positions = actionManager.getValidBuilds(board, 4, 4);
        godPower.applyEffect(positions, board, player.getWorker1(), TurnPhase.BUILD);
        assertEquals(1, positions.size());
        assertEquals(3, positions.get(0).getPosRow());
        assertEquals(3, positions.get(0).getPosColumn());
    }

    @Test
    public void testAddPhase() {
        godPower.setTriggered(true);
        godPower.addPhase();
        assertEquals(3, godPower.getPhases().size());
        assertEquals(TurnPhase.BUILD, godPower.getPhases().get(2));
        godPower.setTriggered(false);
        godPower.addPhase();
        assertEquals(2, godPower.getPhases().size());
    }

}

package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import it.polimi.ingsw.PSP41.model.godCards.Triton;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Triton GodPower.
 */
public class TritonTest {
    Board board;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        godPower = new Triton();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
    }

    @Test
    public void testIsActionable_True() {
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testIsActionable_False() { assertFalse(godPower.isActionable(board, player.getWorker2())); }

    @Test
    public void testAddPhase() {
        godPower.setTriggered(true);
        godPower.addPhase();
        assertEquals(4, godPower.getPhases().size());
        assertEquals(TurnPhase.MOVE, godPower.getPhases().get(1));
        godPower.setTriggered(false);
        godPower.addPhase();
        assertEquals(3, godPower.getPhases().size());
    }

}

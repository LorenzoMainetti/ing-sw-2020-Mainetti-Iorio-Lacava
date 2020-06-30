package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Poseidon;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Poseidon GodPower.
 */
public class PoseidonTest {
    Board board;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        godPower = new Poseidon();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
    }

    @Test
    public void testIsActionable_True() {
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    //other worker not able to build
    @Test
    public void testIsActionable_False_FirstCase() {
        board.getCell(3, 3).setDome(true);
        board.getCell(3, 4).setDome(true);
        board.getCell(4, 3).setDome(true);
        assertFalse(godPower.isActionable(board, player.getWorker2()));
    }

    //other worker not on ground level
    @Test
    public void testIsActionable_False_SecondCase() {
        board.getCell(4, 4).addLevel();
        assertFalse(godPower.isActionable(board, player.getWorker2()));
    }

    @Test
    public void testSwitchWorker() {
        godPower.switchWorker();
        assertFalse(godPower.switchWorker());
    }

    @Test
    public void testAddPhase() {
        godPower.setTriggered(true);
        godPower.addPhase();
        assertEquals(4, godPower.getPhases().size());
        assertEquals(TurnPhase.BUILD, godPower.getPhases().get(3));
        godPower.setTriggered(false);
        godPower.addPhase();
        assertEquals(3, godPower.getPhases().size());
    }

    @Test
    public void testReset() {
        godPower.isActionable(board, player.getWorker1());
        godPower.reset();
        assertFalse(godPower.isTriggered());
    }

    @Test
    public void testBuild() {
        player.build(board, 3, 4);
        assertFalse(godPower.isTriggered());
        assertEquals(1, board.getCell(3, 4).getLevel());
    }

}

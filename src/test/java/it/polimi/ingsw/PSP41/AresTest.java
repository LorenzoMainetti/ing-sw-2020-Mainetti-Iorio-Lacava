package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Ares;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Ares GodPower.
 */
public class AresTest {
    Board board;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        godPower = new Ares();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
    }

    @Test
    public void testIsActionable() {
        board.getCell(1, 2).addLevel();
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testApplyEffect() {
        godPower.setTriggered(true);
        board.getCell(1, 2).addLevel();
        board.getCell(2, 2).addLevel();
        List<Position> positions = actionManager.getValidMoves(board, 1, 1);
        godPower.applyEffect(positions, board, player.getWorker2(), TurnPhase.BUILD);
        assertEquals(2, positions.size());
        assertEquals(1, positions.get(0).getPosRow());
        assertEquals(2, positions.get(0).getPosColumn());
        assertEquals(2, positions.get(1).getPosRow());
        assertEquals(2, positions.get(1).getPosColumn());
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

    @Test
    public void testBuild_Triggered() {
        godPower.setTriggered(true);
        board.getCell(1, 2).addLevel();
        player.build(board, 1, 2);
        assertEquals(0, board.getCell(1, 2).getLevel());
    }

    @Test
    public void testBuild_NotTriggered() {
        player.build(board, 3, 4);
        assertEquals(1, board.getCell(3, 4).getLevel());
    }
}

package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Zeus;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Zeus GodPower.
 */
public class ZeusTest {
    Board board;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        godPower = new Zeus();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
    }

    @Test
    public void testIsActionable() {
        board.getCell(4, 4).addLevel();
        board.getCell(4, 4).addLevel();
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testApplyEffect() {
        godPower.setTriggered(true);
        board.getCell(3, 3).setDome(true);
        board.getCell(4, 3).setDome(true);
        List<Position> positions = actionManager.getValidMoves(board, 4, 4);
        godPower.applyEffect(positions, board, player.getWorker1(), TurnPhase.BUILD);
        assertEquals(2, positions.size());
        assertEquals(3, positions.get(0).getPosRow());
        assertEquals(4, positions.get(0).getPosColumn());
        assertEquals(4, positions.get(1).getPosRow());
        assertEquals(4, positions.get(1).getPosColumn());
    }

}

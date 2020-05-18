package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Prometheus;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Prometheus GodPower.
 */
public class PrometheusTest {
    Board board;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
       board = new Board();
       godPower = new Prometheus();
       player = new Player("Olimpia", Color.RED, godPower);
       actionManager = new ActionManager();
       player.getWorker1().setPosition(board, 4, 4);
       player.getWorker2().setPosition(board, 1, 1);
    }

    //isActionable is FALSE
    //first case: there are only higher cells around the chosen worker
    @Test
    public void testIsActionable_FirstCornerCase() {
        board.getCell(3, 4).addLevel();
        board.getCell(3, 3).addLevel();
        board.getCell(4, 3).addLevel();
        assertFalse(godPower.isActionable(board, player.getWorker1()));
    }

    //second case: there is only a cell where to build and it's on the same level
    @Test
    public void testIsActionable_SecondCornerCase() {
        board.getCell(3, 3).setDome(true);
        board.getCell(4, 3).setDome(true);
        assertFalse(godPower.isActionable(board, player.getWorker1()));
    }

    //isActionable is TRUE
    //third case: there is at least a cell on a same/lower level around the chosen worker
    @Test
    public void testIsActionable_ThirdCornerCase() {
        board.getCell(3, 4).addLevel();
        board.getCell(3, 3).addLevel();
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    //fourth case: there is only a cell where to build but it's on a lower level
    @Test
    public void testIsActionable_FourthCornerCase() {
        board.getCell(3, 3).setDome(true);
        board.getCell(4, 3).setDome(true);
        board.getCell(4, 4).addLevel();
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testApplyEffect_MovePhase() {
        godPower.setTriggered(true);
        board.getCell(3, 4).addLevel();
        board.getCell(3, 3).addLevel();
        List<Position> positions = actionManager.getValidMoves(board, 4, 4);
        godPower.applyEffect(positions, board, player.getWorker1(), TurnPhase.MOVE);
        assertEquals(1, positions.size());
        assertEquals(4, positions.get(0).getPosRow());
        assertEquals(3, positions.get(0).getPosColumn());
    }

    @Test
    public void testApplyEffect_BuildPhase() {
        godPower.setTriggered(true);
        board.getCell(3, 3).setDome(true);
        board.getCell(4, 3).addLevel();
        List<Position> positions = actionManager.getValidBuilds(board, 4, 4);
        godPower.applyEffect(positions, board, player.getWorker1(), TurnPhase.BUILD);
        assertEquals(1, positions.size());
        assertEquals(4, positions.get(0).getPosRow());
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

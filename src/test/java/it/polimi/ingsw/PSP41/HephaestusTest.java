package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Hephaestus;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Hephaestus GodPower.
 */
public class HephaestusTest {
    Board board;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
       board = new Board();
       godPower = new Hephaestus();
       player = new Player("Olimpia", Color.RED, godPower);
       actionManager = new ActionManager();
       player.getWorker1().setPosition(board, 4, 4);
       player.getWorker2().setPosition(board, 1, 1);
       player.build(board, 3, 4);
    }

    @Test
    public void testIsActionable() {
        board.getCell(3, 4).addLevel();
        assertTrue(godPower.isActionable(board, player.getWorker1()));
    }

    @Test
    public void testApplyEffect() {
        godPower.setTriggered(true);
        List<Position> positions = actionManager.getValidBuilds(board, 4, 4);
        godPower.applyEffect(positions, board, player.getWorker1(), TurnPhase.BUILD);
        assertEquals(1, positions.size());
        assertEquals(3, positions.get(0).getPosRow());
        assertEquals(4, positions.get(0).getPosColumn());
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

package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Atlas;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for Atlas GodPower.
 */
public class AtlasTest {
    Board board;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        godPower = new Atlas();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
    }

    @Test
    public void testBuild_Triggered() {
        godPower.setTriggered(true);
        player.build(board, 3, 4);
        assertTrue(board.getCell(3, 4).isDome());
    }

    @Test
    public void testBuild_NotTriggered() {
        player.build(board, 3, 4);
        assertEquals(1, board.getCell(3, 4).getLevel());
    }

}



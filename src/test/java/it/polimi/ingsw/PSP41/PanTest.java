package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Pan;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for Pan GodPower.
 */
public class PanTest {
    Board board;
    GodPower godPower;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        godPower = new Pan();
        actionManager = new ActionManager();
    }

    @Test
    public void testCheckWinCondition() {
        board.getCell(3, 3).addLevel();
        board.getCell(3, 3).addLevel();
        board.getCell(3, 3).addLevel();

        board.getCell(2, 3).addLevel();
        board.getCell(2, 3).addLevel();

        assertTrue(godPower.checkWinCondition(board.getCell(2, 3), board.getCell(3, 3)));
    }

    @Test
    public void testPanWinCondition() {
        board.getCell(3, 3).addLevel();
        board.getCell(3, 3).addLevel();

        assertTrue(godPower.checkWinCondition(board.getCell(3, 3), board.getCell(2, 3)));
    }

}

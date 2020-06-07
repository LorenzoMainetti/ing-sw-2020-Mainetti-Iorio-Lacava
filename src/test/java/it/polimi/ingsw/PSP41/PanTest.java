package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Pan;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Pan GodPower.
 */
public class PanTest {
    Board board;
    GodPower godPower;
    ActionManager actionManager;
    Player player;

    @Before
    public void setup() {
        board = new Board();
        godPower = new Pan();
        actionManager = new ActionManager();
        player = new Player("Olimpia", Color.RED, godPower);
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

    @Test
    public void testSuperClassMethods() {
        List<Position> pos = new ArrayList<>();
        pos.add(new Position(2, 2));

        assertTrue(godPower.isActionable(board, player.getWorker1()));
        assertEquals(-1, godPower.godPowerRequired(board, player));
        assertEquals(TurnPhase.NONE, godPower.getAffectPhase());
        assertFalse(godPower.switchWorker());
        godPower.applyOpponentConstraints(pos, board, player.getWorker1());
        godPower.applyEffect(pos, board, player.getWorker1(), TurnPhase.MOVE);
        godPower.addPhase();
    }

}

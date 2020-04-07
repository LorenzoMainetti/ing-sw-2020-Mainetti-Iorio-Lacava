package it.polimi.ingsw.PSP41;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for Pan GodPower.
 */

public class PanTest {
    GodPower godPower;
    Board board;
    Player player;
    ActionManager actionManager;
    UserInputManager inputManager;

    @Before
    public void setup() {
        board = new Board();
        player = new Player("Olimpia", Color.RED);
        player.getWorker1().setPosition(board, 0, 2);
        board.getCell(0, 2).attachWorker(player.getWorker1());
        player.getWorker2().setPosition(board, 4, 4);
        board.getCell(4, 4).attachWorker(player.getWorker2());
        actionManager = new ActionManager();
        inputManager = new UserInputManager(true, false, 0,1);
        godPower = new Pan(player, actionManager, inputManager);
    }

    @Test
    public void testCheckWinCondition() {
        board.getCell(3, 3).addLevel();
        board.getCell(3, 3).addLevel();
        board.getCell(3, 3).addLevel();

        board.getCell(2, 3).addLevel();
        board.getCell(2, 3).addLevel();

        godPower.checkWinCondition(board.getCell(2, 3), board.getCell(3, 3));

        assertTrue(godPower.getPlayer().isWinner());
    }

    @Test
    public void testPanWinCondition() {
        board.getCell(3, 3).addLevel();
        board.getCell(3, 3).addLevel();

        godPower.checkWinCondition(board.getCell(3, 3), board.getCell(2, 3));

        assertTrue(godPower.getPlayer().isWinner());
    }

}

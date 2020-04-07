package it.polimi.ingsw.PSP41;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Athena GodPower.
 */

public class AthenaTest {
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
        godPower = new Athena(player, actionManager, inputManager);
    }

    @Test
    public void noActivation_MoveBehaviour() {
        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());
        assertFalse(godPower.athenaPower);
    }

    @Test
    public void Activation_MoveBehaviour() {
        board.getCell(0, 1).addLevel();
        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());
        assertTrue(godPower.athenaPower);
    }

}

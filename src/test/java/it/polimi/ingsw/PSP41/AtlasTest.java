package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for Atlas GodPower.
 */

public class AtlasTest {
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
        godPower = new Atlas(player, actionManager, inputManager);
    }

    @Test
    public void notActivePower_buildBehaviour() {
        godPower.activeWorkers(board);
        godPower.buildBehaviour(board);

        assertEquals(1, board.getCell(0, 1).getLevel());
    }

    @Test
    public void activePower_buildBehaviour() {
        inputManager.setPower(true);
        GodPower atlas = new Atlas(player, actionManager, inputManager);
        atlas.activeWorkers(board);
        atlas.buildBehaviour(board);

        assertEquals(1, board.getCell(0, 1).getLevel());
        assertTrue(board.getCell(0, 1).isDome());
    }

}



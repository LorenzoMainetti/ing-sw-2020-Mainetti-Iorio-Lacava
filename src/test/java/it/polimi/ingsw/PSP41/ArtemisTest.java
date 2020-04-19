package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import it.polimi.ingsw.PSP41.controller.Artemis;
import it.polimi.ingsw.PSP41.controller.GodPower;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for Artemis GodPower.
 */
/*
public class ArtemisTest {
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
        player.getWorker2().setPosition(board, 4, 4);
        actionManager = new ActionManager();
        inputManager = new UserInputManager(true, false, 0,1);
        godPower = new Artemis(player, actionManager, inputManager);
    }

    @Test
    public void onlyWorker2_activeWorkers() {
        board.getCell(0, 1).setDome(true);
        board.getCell(0, 3).setDome(true);
        board.getCell(1, 1).setDome(true);
        board.getCell(1, 2).setDome(true);
        board.getCell(1, 3).setDome(true);

        godPower.activeWorkers(board);
        //assertFalse(godPower.whichWorker());
    }

    @Test
    public void onlyWorker1_activeWorkers() {
        board.getCell(3, 3).setDome(true);
        board.getCell(4, 3).setDome(true);
        board.getCell(3, 4).setDome(true);

        godPower.activeWorkers(board);
        //assertSame(godPower.getPlayer().getWorker1(), godPower.getCurrWorker());
    }

    @Test
    public void userChoice_activeWorkers() {
        godPower.activeWorkers(board);
        //assertSame(godPower.getPlayer().getWorker1(), godPower.getCurrWorker());
    }

    @Test
    public void notActivePower_moveBehaviour() {
        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());
    }

    @Test
    public void activePower_moveBehaviour() {
        inputManager.setPower(true);
        inputManager.setAdditionalPos(1, 0);
        GodPower artemis = new Artemis(player, actionManager, inputManager);
        artemis.activeWorkers(board);
        artemis.moveBehaviour(board);

        assertEquals(1, artemis.getPlayer().getWorker1().getRow());
        assertEquals(0, artemis.getPlayer().getWorker1().getColumn());
    }

    @Test
    public void testBuildBehaviour() {
        godPower.activeWorkers(board);
        godPower.buildBehaviour(board);

        assertEquals(1, board.getCell(0, 1).getLevel());
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


}*/

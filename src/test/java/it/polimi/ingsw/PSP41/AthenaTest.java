package it.polimi.ingsw.PSP41;


import it.polimi.ingsw.PSP41.controller.Athena;
import it.polimi.ingsw.PSP41.controller.GodPower;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.PSP41.controller.GodPower.*;
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
        player.getWorker2().setPosition(board, 4, 4);
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
        assertFalse(GodPower.getAthenaPower());
    }

    @Test
    public void Activation_MoveBehaviour() {
        board.getCell(0, 1).addLevel();
        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());
        assertTrue(GodPower.getAthenaPower());
    }

}

package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.controller.Demeter;
import it.polimi.ingsw.PSP41.controller.GodPower;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.view.CLI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Demeter GodPower.
 */

public class DemeterTest { /*
    GodPower godPower;
    Board board;
    Player player;
    UserInputManager inputManager;

    @Before
    public void setup() {
        board = new Board();
        player = new Player("Olimpia", Color.RED);
        player.getWorker1().setPosition(board, 0, 2);
        player.getWorker2().setPosition(board, 4, 4);
        inputManager = new UserInputManager(new CLI());
        godPower = new Demeter(player, inputManager);
    }

    @Test
    public void notActivePower_buildBehaviour() {
        godPower.activeWorkers(board);
        godPower.buildBehaviour(board);

        assertEquals(1, board.getCell(0, 1).getLevel());
    }

    @Test
    public void activePower_buildBehaviour() {
        inputManager.updatePower(true);
        GodPower demeter = new Demeter(player, inputManager);
        demeter.activeWorkers(board);
        demeter.buildBehaviour(board);

        assertEquals(1, board.getCell(0, 1).getLevel());
        assertEquals(1, board.getCell(1, 2).getLevel());
    }
*/
}
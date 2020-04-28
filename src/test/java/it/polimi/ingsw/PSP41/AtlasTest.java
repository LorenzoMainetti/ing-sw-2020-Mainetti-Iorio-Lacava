package it.polimi.ingsw.PSP41;

import static org.junit.Assert.*;

import it.polimi.ingsw.PSP41.controller.Atlas;
import it.polimi.ingsw.PSP41.controller.GodPower;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.view.CLI;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for Atlas GodPower.
 */

/*public class AtlasTest {
    Atlas godPower;
    Board board;
    Player player;
    ActionManager actionManager;
    UserInputManager inputManager;
    CLI theView;

    @Before
    public void setup() {
        board = new Board();
        player = new Player("Olimpia", Color.RED);
        player.getWorker1().setPosition(board, 0, 2);
        player.getWorker2().setPosition(board, 4, 4);
        actionManager = new ActionManager();
        theView = new CLI();
        inputManager = new UserInputManager(theView);
        godPower = new Atlas(player, inputManager);
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
        GodPower atlas = new Atlas(player, inputManager);
        atlas.activeWorkers(board);
        atlas.buildBehaviour(board);

        assertEquals(1, board.getCell(0, 1).getLevel());
        assertTrue(board.getCell(0, 1).isDome());
    }

}*/



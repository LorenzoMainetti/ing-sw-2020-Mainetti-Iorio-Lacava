package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.model.godCards.Athena;
import it.polimi.ingsw.PSP41.model.godCards.GodPower;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Athena GodPower.
 */
public class AthenaTest {
    Board board;
    Worker opponent;
    GodPower godPower;
    Player player;
    ActionManager actionManager;

    @Before
    public void setup() {
        board = new Board();
        opponent = new Worker(Color.BLUE, 1);
        godPower = new Athena();
        player = new Player("Olimpia", Color.RED, godPower);
        actionManager = new ActionManager();
        player.getWorker1().setPosition(board, 4, 4);
        player.getWorker2().setPosition(board, 1, 1);
        opponent.setPosition(board, 3, 4);
        board.getCell(4, 3).addLevel();
        player.move(player.getWorker1(), board, 4, 3);
    }

    @Test
    public void testApplyOpponentConstraints() {
        board.getCell(2, 3).addLevel();
        board.getCell(2, 4).addLevel();
        List<Position> positions = actionManager.getValidMoves(board, 3, 4);
        godPower.applyOpponentConstraints(positions, board, opponent);
        assertEquals(2, positions.size());
        assertEquals(3, positions.get(0).getPosRow());
        assertEquals(3, positions.get(0).getPosColumn());
        assertEquals(4, positions.get(1).getPosRow());
        assertEquals(4, positions.get(1).getPosColumn());
    }

}

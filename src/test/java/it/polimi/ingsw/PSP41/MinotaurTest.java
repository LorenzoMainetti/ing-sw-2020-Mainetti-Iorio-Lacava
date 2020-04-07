package it.polimi.ingsw.PSP41;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Minotaur GodPower.
 */

public class MinotaurTest {
    GodPower godPower;
    Board board;
    Player player;
    ActionManager actionManager;
    UserInputManager inputManager;
    Worker opponent;

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
        godPower = new Minotaur(player, actionManager, inputManager);
    }

    @Test
    public void notActivePower_moveBehaviour() {
        opponent = new Worker(Color.BLUE);
        opponent.setPosition(board, 0, 3);
        board.getCell(0,3).attachWorker(opponent);
        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());
    }

    @Test
    public void activePower_moveBehaviour_activated() {
        opponent = new Worker(Color.BLUE);
        opponent.setPosition(board, 0, 1);
        board.getCell(0,1).attachWorker(opponent);
        board.getCell(0,0).addLevel();
        board.getCell(0,0).addLevel();
        inputManager.setPower(true);
        GodPower minotaur = new Minotaur(player, actionManager, inputManager);
        minotaur.activeWorkers(board);
        minotaur.moveBehaviour(board);

        assertEquals(0, minotaur.getPlayer().getWorker1().getRow());
        assertEquals(1, minotaur.getPlayer().getWorker1().getColumn());
        assertEquals(0, opponent.getRow());
        assertEquals(0, opponent.getColumn());
    }

    @Test
    public void activePower_moveBehaviour_notActivated() {
        opponent = new Worker(Color.BLUE);
        opponent.setPosition(board, 0, 3);
        board.getCell(0,3).attachWorker(opponent);
        board.getCell(0,4).setDome(true);
        inputManager.setPower(true);
        GodPower minotaur = new Minotaur(player, actionManager, inputManager);
        minotaur.activeWorkers(board);
        minotaur.moveBehaviour(board);

        assertEquals(0, minotaur.getPlayer().getWorker1().getRow());
        assertEquals(1, minotaur.getPlayer().getWorker1().getColumn());
        assertEquals(0, opponent.getRow());
        assertEquals(3, opponent.getColumn());
    }

}

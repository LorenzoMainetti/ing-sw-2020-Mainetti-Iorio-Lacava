package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.controller.GodPower;
import it.polimi.ingsw.PSP41.controller.Prometheus;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.view.CLI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Unit test for Prometheus GodPower.
 */

public class PrometheusTest { /*
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
        godPower = new Prometheus(player, inputManager);
    }

    @Test
    public void onlyHigherCells_moveBehaviour() {
        board.getCell(0, 1).addLevel();
        board.getCell(0, 3).addLevel();
        board.getCell(1, 1).addLevel();
        board.getCell(1, 2).addLevel();
        board.getCell(1, 3).addLevel();

        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());
    }

    //c'è una sola cella allo stesso livello dove poter costruire -> move normale
    @Test
    public void firstCornerCase_moveBehaviour() {
        board.getCell(0, 1).setDome(true);
        board.getCell(0, 3).setDome(true);
        board.getCell(1, 1).setDome(true);
        board.getCell(1, 3).setDome(true);

        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());

    }

    //c'è una sola cella allo stesso livello per muoversi ma più di una dove posso costruire,
    //ma l'utente non vuole attivare il potere -> move normale
    @Test
    public void secondCornerCase_moveBehaviour_NoPower() {
        board.getCell(0, 1).setDome(true);
        board.getCell(0, 3).addLevel();
        board.getCell(1, 1).addLevel();
        board.getCell(1, 3).addLevel();

        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());

    }

    //c'è una sola cella allo stesso livello per muoversi ma più di una dove posso costruire,
    //e l'utente vuole attivare il potere -> build prima di move
    @Test
    public void secondCornerCase_moveBehaviour_Power() {
        board.getCell(0, 1).addLevel();
        board.getCell(0, 3).setDome(true);
        board.getCell(1, 1).addLevel();
        board.getCell(1, 3).addLevel();

        inputManager.updatePower(true);
        GodPower prometheus = new Prometheus(player, inputManager);
        prometheus.activeWorkers(board);
        prometheus.moveBehaviour(board);

        assertEquals(2, board.getCell(0,1).getLevel());
        assertEquals(1, prometheus.getPlayer().getWorker1().getRow());
        assertEquals(2, prometheus.getPlayer().getWorker1().getColumn());

    }

    @Test
    public void notActivePower_moveBehaviour() {
        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());
    }

    @Test
    public void activePower_moveBehaviour_activated() {
        inputManager.updatePower(true);
        GodPower prometheus = new Prometheus(player, inputManager);
        prometheus.activeWorkers(board);
        prometheus.moveBehaviour(board);

        assertEquals(1, board.getCell(0,1).getLevel());
        assertEquals(1, prometheus.getPlayer().getWorker1().getRow());
        assertEquals(1, prometheus.getPlayer().getWorker1().getColumn());
    }
*/
}

package it.polimi.ingsw.PSP41;


/**
 * Unit test for Apollo GodPower.
 */

public class ApolloTest { /*
    GodPower godPower;
    Board board;
    Player player;
    UserInputManager inputManager;
    Worker opponent;

    @Before
    public void setup() {
        board = new Board();
        player = new Player("Olimpia", Color.RED);
        player.getWorker1().setPosition(board, 0, 2);
        player.getWorker2().setPosition(board, 4, 4);
        inputManager = new UserInputManager(new CLI());
        godPower = new Apollo(player, inputManager);
    }

    @Test
    public void onlyWorker2_activeWorkers() {
        board.getCell(0, 1).setDome(true);
        board.getCell(0, 3).setDome(true);
        board.getCell(1, 1).setDome(true);
        board.getCell(1, 2).setDome(true);
        board.getCell(1, 3).setDome(true);

        godPower.activeWorkers(board);
        assertEquals(2, player.getWorker2().getNumber());
    }

    @Test
    public void onlyWorker1_activeWorkers() {
        board.getCell(3, 3).setDome(true);
        board.getCell(4, 3).setDome(true);
        board.getCell(3, 4).setDome(true);

        godPower.activeWorkers(board);
        assertEquals(1, player.getWorker1().getNumber());
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
        opponent = new Worker(Color.BLUE, 1);
        opponent.setPosition(board, 0, 1);
        inputManager.updatePower(true);
        GodPower apollo = new Apollo(player, inputManager);
        apollo.activeWorkers(board);
        apollo.moveBehaviour(board);

        assertEquals(0, apollo.getPlayer().getWorker1().getRow());
        assertEquals(1, apollo.getPlayer().getWorker1().getColumn());
        assertEquals(0, opponent.getRow());
        assertEquals(2, opponent.getColumn());
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
*/

}


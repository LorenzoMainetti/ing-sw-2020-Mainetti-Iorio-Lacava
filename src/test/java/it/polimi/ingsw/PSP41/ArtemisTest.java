package it.polimi.ingsw.PSP41;


/**
 * Unit test for Artemis GodPower.
 */

public class ArtemisTest { /*
    GodPower godPower;
    Board board;
    Player player;
    UserInputManager inputManager;
    InputStream inputTest;

    @Before
    public void setup() {
        board = new Board();
        player = new Player("Olimpia", Color.RED);
        player.getWorker1().setPosition(board, 0, 2);
        player.getWorker2().setPosition(board, 4, 4);
        inputManager = new UserInputManager(new CLI());
        godPower = new Artemis(player, inputManager);

        //inputTest = System.in;
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
    public void userChoice_activeWorkers() {
        String input = "2";
        inputTest = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputTest);

        godPower.activeWorkers(board);
        assertEquals(2, player.getWorker1().getNumber());
    }

    @Test
    public void notActivePower_moveBehaviour() {
        String input = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        godPower.activeWorkers(board);
        godPower.moveBehaviour(board);

        assertEquals(0, godPower.getPlayer().getWorker1().getRow());
        assertEquals(1, godPower.getPlayer().getWorker1().getColumn());
    }

    @Test
    public void activePower_moveBehaviour() {
        inputManager.updatePower(true);
        GodPower artemis = new Artemis(player, inputManager);
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
*/

}

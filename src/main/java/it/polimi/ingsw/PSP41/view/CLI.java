package it.polimi.ingsw.PSP41.view;

import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.observer.UiObservable;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;


import java.util.*;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;


public class CLI extends UiObservable implements Runnable {
    private Scanner in;
    private boolean needAnswer = false;
    private String answer;
    private boolean answerReady = false;


    public CLI(){
        this.in = new Scanner(System.in);
    }

    public void readInput() {
        while(true) {
            // ogni volta che ricevo un input dal client lo notifico al NetworkHandler
            if (in.hasNext()) {
                String fromClient = in.nextLine();
                if (needAnswer) {
                    answer = fromClient;
                    answerReady = true;
                    synchronized (this) {
                        notifyAll();
                    }
                }
                else System.out.println(wrongTurnMessage);
            }
            //notify(fromClient);
        }
    }

    public String getInput() {
        needAnswer = true;
        synchronized (this) {
            while (!answerReady) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println("Read: " + answer);
        answerReady = false;
        needAnswer = false;
        //System.out.println("Answer: "+ answer);
        return answer;
    }

    //SET UP methods

    public void setUpGame() {
        System.out.println("\n                                      Welcome to\n" +
                "     _______.     ___      .__   __. .___________.  ______   .______       __  .__   __.  __  \n" +
                "    /       |    /   \\     |  \\ |  | |           | /  __  \\  |   _  \\     |  | |  \\ |  | |  | \n" +
                "   |   (----`   /  ^  \\    |   \\|  | `---|  |----`|  |  |  | |  |_)  |    |  | |   \\|  | |  | \n" +
                "    \\   \\      /  /_\\  \\   |  . `  |     |  |     |  |  |  | |      /     |  | |  . `  | |  | \n" +
                ".----)   |    /  _____  \\  |  |\\   |     |  |     |  `--'  | |  |\\  \\----.|  | |  |\\   | |  | \n" +
                "|_______/    /__/     \\__\\ |__| \\__|     |__|      \\______/  | _| `._____||__| |__| \\__| |__| \n\n" +
                "                                      Board Game!\n");
    }

    /**
     * Ask the first user connected the number of Players that are going to play (2 or 3) and get the input
     */
    public void askPlayersNumber() {
        System.out.println("You are the first player in the lobby, Choose the number of players (2 or 3)");
        String players = getInput();
        while (!players.equals("2") && !players.equals("3")) {
            System.out.println("Sorry, we support only a 2 or 3 players game. Choose the number of players (2 or 3)");
            players = getInput();
        }

        notify(players);
    }

    /**
     * Ask the user his nickname and get the input
     */
    public void askNickname() {
        System.out.print("Enter your nickname: ");
        String nickname = getInput();

        notify(nickname);
    }

    public void displayTakenNickname() {
        System.out.println(takenNameMessage);
        askNickname();
    }

    /**
     * Ask the user where his workers want to be placed and get the input
     */
    public void askInitPosition() {
        System.out.println("Choose the initial position for your worker.");
        int row = askRow();
        int column = askColumn();

        int pos = row * 10 + column;
        notify(Integer.toString(pos));
    }

    public void displayTakenPosition() {
        System.out.println(occupiedCellMessage);
        askInitPosition();
    }

    /**
     * Ask row to the user and get the input
     * @return chosen row
     */
    private int askRow() {
        System.out.print("Row: ");
        String currentRow = getInput();

        while (Integer.parseInt(currentRow) < 0 || Integer.parseInt(currentRow) > 4) {
            System.out.print("Invalid row. Choose a number between 0 and 4: ");
            currentRow = getInput();
        }

        return Integer.parseInt(currentRow);
    }

    /**
     * Ask column to the user and get the input
     * @return chosen column
     */
    private int askColumn() {
        System.out.print("Column: ");
        String currentColumn = getInput();

        while (Integer.parseInt(currentColumn) < 0 || Integer.parseInt(currentColumn) > 4) {
            System.out.print("Invalid column. Choose a number between 0 and 4: ");
            currentColumn = getInput();
        }

        return Integer.parseInt(currentColumn);
    }

    //TURN methods

    /**
     * Ask the user if he wants to use Worker 1 or 2 and get the input
     */
    public void askWorker() {
        System.out.print("Which worker do you want to use? (1 or 2): ");
        String worker = getInput();
        while (!worker.equals("1") && !worker.equals("2")) {
            System.out.println("Invalid worker number. (choose 1 or 2)");
            worker = getInput();
        }

        notify(worker);
    }

    /**
     * Ask the user if he wants to activate the God Power and get the input
     */
    public void askPowerActivation() {
        System.out.println("Do you want to activate your God Power? (yes or no)");
        String power = getInput();
        while (!power.equals("yes") && !power.equals("no")) {
            System.out.println("Invalid answer. (choose yes or no)");
            power = getInput();
        }

        notify(power);
    }

    public void startMovePhase() {
        System.out.println("Where do you want to " + ColorCLI.ANSI_GREEN + "MOVE" + ColorCLI.RESET + "?");
    }

    public void startBuildPhase() {
        System.out.println("Where do you want to " + ColorCLI.ANSI_GREEN + "BUILD" + ColorCLI.RESET + "?");
    }

    public void endTurn() {
        System.out.println(endTurn);
        System.out.println("\n");
    }

    public void displayCurrentPlayer(PlayersInfoMessage player) {
        switch (player.getPlayerColor()) {
            case RED:
                System.out.println("It's " + ColorCLI.ANSI_RED + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "'s turn!");
                break;
            case BLUE:
                System.out.println("It's " + ColorCLI.ANSI_BLUE + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "'s turn!");
                break;
            case YELLOW:
                System.out.println("It's " + ColorCLI.ANSI_YELLOW + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "'s turn!");
                break;
        }
    }

    public void displayLoser(PlayersInfoMessage player) {
        switch (player.getPlayerColor()) {
            case RED:
                System.out.println(ColorCLI.ANSI_RED + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "'s are both stuck. " + ColorCLI.ANSI_RED + player.getPlayerName().toUpperCase() + ColorCLI.RESET + " lost.\n");
                break;
            case BLUE:
                System.out.println(ColorCLI.ANSI_BLUE + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "'s are both stuck. " + ColorCLI.ANSI_BLUE + player.getPlayerName().toUpperCase() + ColorCLI.RESET + " lost.\n");
                break;
            case YELLOW:
                System.out.println(ColorCLI.ANSI_YELLOW + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "'s are both stuck. " + ColorCLI.ANSI_YELLOW + player.getPlayerName().toUpperCase() + ColorCLI.RESET + " lost.\n");
                break;
        }
    }

    public void displayWinner(PlayersInfoMessage player) {
        switch (player.getPlayerColor()) {
            case RED:
                System.out.println("The winner is " + ColorCLI.ANSI_RED + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "!\nThanks for playing!");
                break;
            case BLUE:
                System.out.println("The winner is " + ColorCLI.ANSI_BLUE + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "!\nThanks for playing!");
                break;
            case YELLOW:
                System.out.println("The winner is " + ColorCLI.ANSI_YELLOW + player.getPlayerName().toUpperCase() + ColorCLI.RESET + "!\nThanks for playing!");
                break;
        }
    }

    /**
     * Prints player and his/her respective god
     * @param nickname nickname of the player
     * @param color color of the player
     * @param godPower god chosen by the player
     */
    public void showPlayersInfo(String nickname, Color color, String godPower) {
        switch (color) {
            case RED:
                System.out.println(ColorCLI.ANSI_RED + nickname.toUpperCase() + ColorCLI.RESET + " (" + godPower + ")");
                break;
            case YELLOW:
                System.out.println(ColorCLI.ANSI_YELLOW + nickname.toUpperCase() + ColorCLI.RESET + " (" + godPower + ")");
                break;
            case BLUE:
                System.out.println(ColorCLI.ANSI_BLUE + nickname.toUpperCase() + ColorCLI.RESET + " (" + godPower + ")");
                break;
        }
    }


    //OTHER methods

    /**
     * Constructor of the game board
     * @param board that has to be represented
     * @return matrix containing the board ready to be printed
     */
    private CellCLI[][] buildBoard(Board board) {
        CellCLI[][] boardCells = new CellCLI[17][7];

        for(int i = 0; i < 17; ++i) {
            for (int j = 0; j < 7; ++j) {
                boardCells[i][j] = new CellCLI();
            }
        }

        boardCells[0][0].setString("        ");
        for(int i = 1; i < boardCells[16].length-1; i++) {
                boardCells[0][i].setString("   C" + (i - 1) + "   ");
        }

        for(int i = 0; i < boardCells.length-2; i += 3) {
            boardCells[i+2][0].setString("   R"+ (i/3) + "   ");
        }


        boardCells[1][1].setString("┌-------");
        boardCells[1][2].setString("┬-------");
        boardCells[1][3].setString("┬-------");
        boardCells[1][4].setString("┬-------");
        boardCells[1][5].setString("┬-------");
        boardCells[1][6].setString("┐       ");

        for(int x = 2; x < 17; x += 3) {
            for(int y = 1; y < 7; y++) {
                boardCells[x][y].setString("│       ");
            }
        }

        for(int x = 4; x < 17; x += 3) {
            boardCells[x][1].setString("├-------");
        }

        for(int x = 4; x < 17; x += 3) {
            for(int y = 2; y < 7; y++)
            boardCells[x][y].setString("┼-------");
        }

        for(int x = 4; x < 17; x += 3) {
            boardCells[x][6].setString("┤       ");
        }

        boardCells[16][1].setString("└-------");
        boardCells[16][2].setString("┴-------");
        boardCells[16][3].setString("┴-------");
        boardCells[16][4].setString("┴-------");
        boardCells[16][5].setString("┴-------");
        boardCells[16][6].setString("┘       ");

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {

                int level = board.getCell(i, j).getLevel();

                if (board.getCell(i, j).isDome()) {
                    if(level != 0){
                        boardCells[i*3 + 3][j+1].setString("│ X   " + level + " ");
                        boardCells[i*3 + 3][j+2].setString("│       ");
                    }
                    else {
                        boardCells[i*3 + 3][j+1].setString("│ X     ");
                        boardCells[i*3 + 3][j+2].setString("│       ");
                    }

                } else {
                    if (board.getCell(i, j).isOccupied()) {
                        if(board.getCell(i, j).getWorker().getNumber() == 1){
                            boardCells[i * 3 + 2][j+1].setString("│    w1    ");
                            //boardCells[i * 3 + 1][j].setString("│    ①    ");
                        }
                        else if(board.getCell(i, j).getWorker().getNumber() == 2) {
                            boardCells[i * 3 + 2][j+1].setString("│    w2    ");
                            //boardCells[i * 3 + 1][j].setString("│    ②    ");
                        }

                        switch(board.getCell(i, j).getWorker().getColor()) {
                            case BLUE:
                                boardCells[i * 3 + 2][j+1].setColor(ColorCLI.ANSI_BLUE);
                                break;
                            case RED:
                                boardCells[i * 3 + 2][j+1].setColor(ColorCLI.ANSI_RED);
                                break;
                            case YELLOW:
                                boardCells[i * 3 + 2][j+1].setColor(ColorCLI.ANSI_YELLOW);
                                break;
                        }
                    }

                    if(level != 0){
                        boardCells[i*3 + 3][j+1].setString("│     " + level + " ");
                        boardCells[i*3 + 3][j+2].setString("│       ");
                    }
                    else {
                        boardCells[i*3 + 3][j+1].setString("│       ");
                        boardCells[i*3 + 3][j+2].setString("│       ");
                    }

                }
            }
        }
        return boardCells;
    }

    /**
     * Method to print game board
     * @param board that has to be printed
     */
     public void printBoard(Board board) {
        CellCLI[][] boardCells = buildBoard(board);
        System.out.println();

        for (CellCLI[] boardCell : boardCells) {
            for (CellCLI cellCLI : boardCell) {
                if (cellCLI.getColor() != null) {
                    String[] strings = cellCLI.getString().split("\\s+");
                    System.out.print(strings[0]);
                    System.out.print("   ");

                    System.out.print(cellCLI.getColor());

                    System.out.print(strings[1]);
                    System.out.print("  ");
                    System.out.print(ColorCLI.RESET);
                } else {
                    System.out.print(cellCLI.getString());
                }
            }

            System.out.println();
        }
         System.out.println();

     }

    /**
     * Display available cells to move or build
     * @param positions where player can play its action
     */
    public void displayOptions(List<Position> positions) {
        System.out.println("Valid positions:");
        for (Position position : positions) {
            System.out.print("R" + position.getPosRow() + ",C" + position.getPosColumn() + "      ");
        }
        System.out.print("\n");
        askPosition();
    }

    /**
     * Ask the user where he wants to place his workers and get the input
     */
    public void askPosition() {
        int row = askRow();
        int column = askColumn();

        int pos = row * 10 + column;
        notify(Integer.toString(pos));
    }

    public void displayNetworkError() {
        System.out.println("Connection closed from server side");
    }

    /*
     * Generic method to print a message
     * @param message message to print
     *
    public void printMessage(String message) {
        System.out.println(message);
    }*/

    @Override
    public void run() {
        setUpGame();
        readInput();
    }

}

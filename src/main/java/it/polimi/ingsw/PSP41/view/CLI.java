package it.polimi.ingsw.PSP41.view;

import it.polimi.ingsw.PSP41.ModelObserver;
import it.polimi.ingsw.PSP41.ViewObservable;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Position;


import java.util.*;


public class CLI extends ViewObservable implements ModelObserver {
    private Scanner in;


    public CLI(){
        this.in = new Scanner(System.in);
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
        askNickname();
        askPlayersNumber();
    }

    public void askNickname() {
        String nickname;

        System.out.println("Enter your nickname: ");
        // Management of eventual format errors or double names -> controller

        System.out.print(">>> ");
        nickname = in.nextLine();

        notifyNickname(nickname);
    }

    public void printError() {
        System.out.println("This nickname is already taken. Please try again.");
        askNickname();
    }

    /**
     * Ask the first Player the number of Players that are going to play (2 or 3) and get the input
     */
    public void askPlayersNumber() {
        int players = -1;
        boolean firstError = true;

        do {
            System.out.println("Choose the number of players: 2 or 3?");
            if(in.hasNextInt()) {
                final int num = in.nextInt();
                in.nextLine();
                if (num != 2 && num != 3)
                    firstError = promptInputError(firstError, "Sorry, we support only a 2 or 3 players game.");
                else
                    players = num;
            }
            else {
                in.nextLine();
                firstError = promptInputError(firstError, "Invalid integer!");
            }
        } while(players == -1);

        notifyPlayersNumber(players);
    }

    /**
     * Print the God Power assigned to a Player
     */
    public void printGodPower(String nickname, String godName, String godPower) {
        System.out.println("\n"+ColorCLI.ANSI_RED+nickname+ColorCLI.RESET+", your God Power card is: "+ColorCLI.ANSI_GREEN+godName.toUpperCase());
        System.out.println(godPower+ColorCLI.RESET+"\n");
    }


    public void askInitialPosition() {
        System.out.println("Choose the initial position for your worker. ");
        int row = askRow();
        int column = askColumn();

        notifyPosition(new Position(row, column));
    }


    /**
     * Ask row to the Player and get the input
     */
    private int askRow() {
        int row = -1;
        boolean firstError = true;

        do {
            System.out.print("Row: ");
            if(in.hasNextInt()) {
                final int currentRow = in.nextInt();
                in.nextLine();
                if (currentRow < 0 || currentRow > 4)
                    firstError = promptInputError(firstError, "Invalid row!");
                else
                    row = currentRow;
            }
            else {
                in.nextLine();
                firstError = promptInputError(firstError, "Invalid integer!");
            }
        } while(row == -1);

        return row;
    }


    /**
     * Ask column to the Player and get the input
     */
    private int askColumn() {
        int column = -1;
        boolean firstError = true;

        do {
            System.out.print("Column: ");
            if(in.hasNextInt()) {
                final int currentColumn = in.nextInt();
                in.nextLine();
                if (currentColumn < 0 || currentColumn> 4)
                    firstError = promptInputError(firstError, "Invalid column!");
                else
                    column = currentColumn;
            }
            else {
                in.nextLine();
                firstError = promptInputError(firstError, "Invalid integer!");
            }
        } while(column == -1);

        return column;
    }

    public void positionTaken() {
        System.out.println("Sorry the selected position is already taken. Please try again.");
    }


    //TURN methods

    public void startTurn(String nickname) {
        System.out.println("It's your turn "+ColorCLI.ANSI_GREEN+nickname+ColorCLI.RESET+"!");
    }


    /**
     * Ask the Player if he wants to use Worker 1 or 2 and get the input
     */
    public void askWorker() {
        int chosenWorker = -1;
        boolean firstError = true;

        do {
            System.out.println("Which worker do you want to use? Choose Number 1 or Number 2. ");
            if(in.hasNextInt()) {
                final int currentWorker = in.nextInt();
                in.nextLine();
                if (currentWorker != 1 && currentWorker != 2)
                    firstError = promptInputError(firstError, "Invalid number. Try again.");
                else
                    chosenWorker = currentWorker;
            }
            else {
                in.nextLine();
                firstError = promptInputError(firstError, "Invalid integer!");
            }
        } while(chosenWorker == -1);

        if(chosenWorker == 1)
            notifyWorker(true);
        else
            notifyWorker(false);
    }


    /**
     * Ask the Player if he wants to activate the God Power and get the input
     */
    public void askPowerActivation() {
        boolean activate = false;
        String answer = null;
        System.out.println("Do you want to activate your God Power? Type yes or no.");

        do {
            final String currentAnswer = in.nextLine();
            if (!currentAnswer.equalsIgnoreCase("yes") && !currentAnswer.equalsIgnoreCase("no"))
                System.out.println("Invalid answer. Please try again\n");
            else
                answer = currentAnswer;
        } while(answer == null);

        if(answer.equalsIgnoreCase("yes")) activate = true;

        notifyPower(activate);
    }

    /**
     * Ask the Player if he wants to keep building and get the input (method used by Poseidon)
     */
    public boolean askToKeepBuilding() {
        boolean build = false;
        String answer = null;
        System.out.println("Do you want to build again? Type yes or no.");

        do {
            final String currentAnswer = in.nextLine();
            if (!currentAnswer.equalsIgnoreCase("yes") && !currentAnswer.equalsIgnoreCase("no"))
                System.out.println("Invalid answer. Please try again\n");
            else
                answer = currentAnswer;
        } while(answer == null);

        if(answer.equalsIgnoreCase("yes")) build = true;

        return build;
    }

    public void MovePhase() {
        System.out.println("Where do you want to "+ ColorCLI.ANSI_GREEN+"MOVE"+ColorCLI.RESET+"? ");
    }

    public void BuildPhase() {
        System.out.println("Where do you want to "+ ColorCLI.ANSI_GREEN+"BUILD"+ColorCLI.RESET+"? ");
    }

    public void endTurn() {
        System.out.println("Your turn is over.\n");
    }

    public void loser(String loser) {
        System.out.println(loser.toUpperCase()+" your workers are both stuck. You have lost.\n");
    }

    public void endGame(String winner) {
        System.out.println("Game over! The winner is "+ColorCLI.ANSI_GREEN+ winner.toUpperCase() +ColorCLI.RESET+"!!!\nThanks for playing.");
    }

    /*public void showPlayersInfo(String nickname, String color, String godpower) {
        System.out.println("Player " + nickname + " info:\n");
        System.out.println("Color: " + color + "\n");
        System.out.println("GodPower: " + godpower + "\n");
    }*/


    //OTHER methods

    /**
     * Constructor of the game board
     * @param board that has to be represented
     * @return matrix containing the board ready to be printed
     */
    private static CellCLI[][] buildBoard(Board board) {
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
     public static void printBoard(Board board) {
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


    @Override
    public void updateState(Board board) {
        printBoard(board);
    }


    /**
     * Display available cells to move or build and ask direction
     * @param positions where player can play its action
     * @param row of the current cell
     * @param column of the current cell
     */
    public void displayOptions(List<Position> positions, int row, int column) {
        System.out.println("These are the valid directions: ");

        List<String> directions = new ArrayList<>();
        if(positions != null) {
            for(Position position : positions)
            {
                if(position.getX() == row) {
                    if(position.getY() == column+1)
                        directions.add("E");

                    else if(position.getY() == column-1)
                        directions.add("W");

                    else if(position.getY() == column)
                        directions.add("CURR");
                }
                else if(position.getX() == row-1) {
                    if(position.getY() == column+1)
                        directions.add("NE");

                    else if(position.getY() == column-1)
                        directions.add("NW");

                    else if(position.getY() == column)
                        directions.add("N");
                }

                else if(position.getX() == row+1) {
                    if(position.getY() == column+1)
                        directions.add("SE");

                    else if(position.getY() == column-1)
                        directions.add("SW");

                    else if(position.getY() == column)
                        directions.add("S");
                }

            }
            for(String direction : directions) {
                System.out.print(" | "+direction+" | ");
            }
            System.out.println("\n");
        }
        else {
            System.out.println("No moves available.\n");
        }

        String answer = null;
        boolean firstError = true;

        do {
            System.out.print("Choose one:  ");
            if(in.hasNextLine()) {
                final String curr = in.nextLine().toUpperCase();

                if (!directions.contains(curr))
                    firstError = promptInputError(firstError, "Invalid direction!");
                else
                    answer = curr;
            }

        } while(answer == null);

        notifyDirection(answer);
    }


    /**
     * Prompts an input error
     * @param firstError true if it's the first error made
     * @param errorMessage the error message
     * @return false meaning that this isn't the first error
     */
    private boolean promptInputError(boolean firstError, String errorMessage) {
        System.out.print(ColorCLI.CLEAR_LINE);
        if (!firstError) {
            System.out.print(ColorCLI.CLEAR_LINE);
        }

        System.out.println(errorMessage);
        return false;
    }

}

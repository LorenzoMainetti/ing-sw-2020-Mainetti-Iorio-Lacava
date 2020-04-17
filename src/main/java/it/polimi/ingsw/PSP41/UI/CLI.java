package it.polimi.ingsw.PSP41.UI;

import it.polimi.ingsw.PSP41.Board;
import it.polimi.ingsw.PSP41.Player;
import it.polimi.ingsw.PSP41.Position;


import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class CLI implements Observer {
    private Scanner in;


    public CLI(){
        this.in = new Scanner(System.in);
    }


    /**
     * Update and print board when notified by an observer
     * @param o observable object
     * @param arg that has changed
     */
    @Override
    public void update(Observable o, Object arg) {
        if(!(o instanceof Player) || !(arg instanceof Board)){
            throw new IllegalArgumentException();
        }

        printBoard((Board)arg);
    }


    private String askNickname() {
        String nickname = null;

        System.out.println("Enter your nickname:\n");
        //gestisci eventuali errori di formattazione
        System.out.println(">>> ");
        nickname = in.nextLine();
        return nickname;
    }


    private void giveGodPower() {
        System.out.println("This is your God Power card.\n");
    }


    public Position askInitialPositions() {

        System.out.println("Where do you want to place your workers?\n");
        int row = askRow();
        int column = askColumn();

        return new Position(row, column);

    }


    public void askMove() {
        System.out.println("Where do you want to move your worker?\n");
    }


    public void askBuild() {
        System.out.println("Where do you want to build with your worker?\n");
    }


    public int askRow() {
        int row = -1;
        System.out.println("Row: ");
        do {
            if(in.hasNextInt()) {
                final int currentRow = in.nextInt();
                if (currentRow < 0 || currentRow > 4)
                    System.out.println("Invalid row\n");
                else
                    row = currentRow;
            }
            //else invalid Integer
        } while(row == -1);

        return row;
    }


    public int askColumn() {
        int column = -1;
        System.out.println("Column: \n");

        do {
            if(in.hasNextInt()) {
                final int currentColumn = in.nextInt();
                if (currentColumn < 0 || currentColumn> 4)
                    System.out.println("Invalid column\n");
                else
                    column = currentColumn;
            }
            //else invalid Integer
        } while(column == -1);

        return column;
    }


    public void yourTurn(String nickname) {
        System.out.println("It's your turn "+nickname+"!\n");
    }


    public int askWorker() {
        int chosenWorker = -1;
        System.out.println("Which worker do you want to use? Choose Number 1 or Number 2.\n");

        do {
            if(in.hasNextInt()) {
                final int currentWorker = in.nextInt();
                if (currentWorker != 1 && currentWorker != 2)
                    System.out.println("Invalid Worker's number. Please try again\n");
                else
                    chosenWorker = currentWorker;
            }
            //else invalid Integer
        } while(chosenWorker == -1);

        return chosenWorker;
    }


    public boolean askPowerActivation() {
        boolean activate = false;
        String answer = null;
        System.out.println("Do you want to activate your God Power? Type yes or no.\n");

        do {
            if(in.hasNextLine()) {
                final String currentAnswer = in.nextLine();
                if (!currentAnswer.equalsIgnoreCase("yes") && !currentAnswer.equalsIgnoreCase("no"))
                    System.out.println("Invalid answer. Please try again\n");
                else
                    answer = currentAnswer;
            }
            //else invalid String
        } while(answer == null);

        if(answer.equalsIgnoreCase("yes")) activate = true;

        return activate;
    }


    public void setUpGame() {
        System.out.println("Welcome to\n" +
                "     _______.     ___      .__   __. .___________.  ______   .______       __  .__   __.  __  \n" +
                "    /       |    /   \\     |  \\ |  | |           | /  __  \\  |   _  \\     |  | |  \\ |  | |  | \n" +
                "   |   (----`   /  ^  \\    |   \\|  | `---|  |----`|  |  |  | |  |_)  |    |  | |   \\|  | |  | \n" +
                "    \\   \\      /  /_\\  \\   |  . `  |     |  |     |  |  |  | |      /     |  | |  . `  | |  | \n" +
                ".----)   |    /  _____  \\  |  |\\   |     |  |     |  `--'  | |  |\\  \\----.|  | |  |\\   | |  | \n" +
                "|_______/    /__/     \\__\\ |__| \\__|     |__|      \\______/  | _| `._____||__| |__| \\__| |__| \n" +
                "                                                                                              Board Game!\n");
    }

    /**
     * Constructor of the game board
     * @param board that has to be represented
     * @return matrix containing the board ready to be printed
     */
    private static CellCLI[][] buildBoard(Board board) {
        CellCLI[][] boardCells = new CellCLI[16][6];

        for(int i = 0; i < 16; ++i) {
            for (int j = 0; j < 6; ++j) {
                boardCells[i][j] = new CellCLI();
            }
        }

        boardCells[0][0].setString("┌-------");
        boardCells[0][1].setString("┬-------");
        boardCells[0][2].setString("┬-------");
        boardCells[0][3].setString("┬-------");
        boardCells[0][4].setString("┬-------");
        boardCells[0][5].setString("┐       ");

        for(int x = 1; x < 16; x += 3) {
            for(int y = 0; y < 6; y++) {
                boardCells[x][y].setString("│       ");
            }
        }

        for(int x = 3; x < 16; x += 3) {
            boardCells[x][0].setString("├-------");
        }

        for(int x = 3; x < 16; x += 3) {
            for(int y = 1; y < 5; y++)
            boardCells[x][y].setString("┼-------");
        }

        for(int x = 3; x < 16; x += 3) {
            boardCells[x][5].setString("┤       ");
        }

        boardCells[15][0].setString("└-------");
        boardCells[15][1].setString("┴-------");
        boardCells[15][2].setString("┴-------");
        boardCells[15][3].setString("┴-------");
        boardCells[15][4].setString("┴-------");
        boardCells[15][5].setString("┘       ");

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {

                //se è una dome metto il simbolo apposta
                if (board.getCell(i, j).isDome()) {
                    boardCells[i * 3 + 1][j].setString("│  ☓  ");
                    boardCells[i * 3 + 2][j].setString("│  4  ");
                } else {
                    if (board.getCell(i, j).isOccupied()) {
                        //se la cella è occupata da un worker disegno la pedina
                        if(board.getCell(i, j).getWorker().getNumber() == 1){
                            boardCells[i * 3 + 1][j].setString("│   ①  ");
                        }
                        else if(board.getCell(i, j).getWorker().getNumber() == 2) {
                            boardCells[i * 3 + 1][j].setString("│   ②  ");
                        }

                        //coloro la casella del colore del worker
                        switch(board.getCell(i, j).getWorker().getColor()) {
                            case BLUE:
                                boardCells[i * 3 + 1][j].setColor(ColorCLI.ANSI_BLUE);
                                break;
                            case RED:
                                boardCells[i * 3 + 1][j].setColor(ColorCLI.ANSI_RED);
                                break;
                            case YELLOW:
                                boardCells[i * 3 + 1][j].setColor(ColorCLI.ANSI_YELLOW);
                                break;
                        }
                    }


                    int level = board.getCell(i, j).getLevel();
                    if(level != 0){
                        boardCells[i*3 + 2][j].setString("│   " + level + "   ");
                        boardCells[i*3 + 2][j+1].setString("│       ");
                    }
                    else {
                        boardCells[i*3 + 2][j].setString("│       ");
                        boardCells[i*3 + 2][j+1].setString("│       ");
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

        for (CellCLI[] boardCell : boardCells) {
            for (int j = 0; j < boardCell.length; ++j) {
                if (boardCell[j].getColor() != null) {
                    String[] strings = boardCell[j].getString().split("\\s+");
                    System.out.print(strings[0]);
                    System.out.print("   ");

                    System.out.print(boardCell[j].getColor());

                    System.out.print(strings[1]);
                    System.out.print("  ");
                    System.out.print(ColorCLI.RESET);
                }
                else {
                    if (!boardCell[j].getString().equals("        ")) {
                        System.out.print(boardCell[j].getString());
                    }
                }
            }

            System.out.println();
        }

    }


    public void showPlayersInfo(String nickname, String color, String godpower) {
        System.out.println("Player " + nickname + " info:\n");
        System.out.println("Color: " + color + "\n");
        System.out.println("GodPower: " + godpower + "\n");
    }


    public void displayMoves(List<Position> positions, int row, int column){
        System.out.println("Where do you want to move?\n");
        displayOptions(positions, row, column);
    }


    public void displayBuilds(List<Position> positions, int row, int column){
        System.out.println("Where do you want to build?\n");
        displayOptions(positions, row, column);
    }


    /**
     * Display available cells to move or build
     * @param positions where player can play its action
     * @param row of the current cell
     * @param column of the current cell
     */
    public void displayOptions(List<Position> positions, int row, int column) {
        System.out.println("Choose between:\n");

        if(positions != null) {
            for(Position position : positions)
            {
                if(position.getX() == row) {
                    if(position.getY() == column+1){
                        System.out.println("==> E\n");
                    }
                    else if(position.getY() == column-1){
                        System.out.println("==> W\n");
                    }
                }

                else if(position.getX() == row+1) {
                    if(position.getY() == column+1){
                        System.out.println("==> NE\n");
                    }
                    else if(position.getY() == column-1){
                        System.out.println("==> NW\n");
                    }
                    else if(position.getY() == column){
                        System.out.println("==> N\n");
                    }
                }

                else if(position.getX() == row-1) {
                    if(position.getY() == column+1){
                        System.out.println("==> SE\n");
                    }
                    else if(position.getY() == column-1){
                        System.out.println("==> SW\n");
                    }
                    else if(position.getY() == column){
                        System.out.println("==> S\n");
                    }
                }

            }
        }
        else {
            System.out.println("No moves available.\n");
        }
    }

    public void endGame(String winner) {
        System.out.println("Game over! The winner is "+ winner +".\n");
    }

}

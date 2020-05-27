package it.polimi.ingsw.PSP41.view;

import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.observer.UiObservable;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;


import java.util.*;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;


public class CLI extends UiObservable implements Runnable, View {
    private final Scanner in;
    private boolean needAnswer = false;
    private String answer;
    private boolean answerReady = false;

    private int playersNumber = 0;
    private List<PlayersInfoMessage> playersInfo = new ArrayList<>();
    private List<String> players = new ArrayList<>();



    public CLI(){
        this.in = new Scanner(System.in);
    }

    private void readInput() {
        while(true) {

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
        }
    }

    private String getInput() {
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

    private void startGame() {
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
    @Override
    public void askPlayersNumber() {
        System.out.println("You are the first player in the lobby, Choose the number of players (2 or 3)");
        String players = getInput();
        while (!players.equals("2") && !players.equals("3")) {
            System.out.println("Sorry, we support only a 2 or 3 players game. Choose the number of players (2 or 3)");
            players = getInput();
        }

        notify(players);
    }

    @Override
    public void displayPlayersNumber(int number) {
        playersNumber = number;
        System.out.println("The game will have " + number + " players");
    }

    @Override
    public void askGameGods(List<String> gameGods) {
        System.out.println("Choose " + playersNumber + " gods from the ones available");
        System.out.println(godDeckMessage);
        List<String> chosenGods = new ArrayList<>();

        for (int i = 1; i <= playersNumber; i++) {
            System.out.println("God #" + i + ": ");
            String selectedGod = getInput().toUpperCase();

            while (!gameGods.contains(selectedGod) || chosenGods.contains(selectedGod)) {
                System.out.println("Invalid God Card. Try again.");
                System.out.println("God #" + i + ": ");
                selectedGod = getInput().toUpperCase();
            }
            chosenGods.add(selectedGod);
        }

        String s = "";
        for (int i=0; i<chosenGods.size(); i++) {
            s = s.concat(chosenGods.get(i));
            if (i < chosenGods.size()-1)
                s = s.concat("/");
        }

        notify(s);
    }

    @Override
    public void askGodCard(List<String> gods) {
        System.out.println("Choose a god power from the ones chosen by the challenger: ");
        for(String god : gods)
            System.out.print(god + "  ");
        System.out.print("\n");

        String chosenGod = getInput().toUpperCase();

        while (!gods.contains(chosenGod)) {
            System.out.println("Invalid God Card. Try again.");
            for (String card : gods)
                System.out.print(card + "  ");
            System.out.print("\n");
            chosenGod = getInput().toUpperCase();
        }

        notify(chosenGod);

    }

    @Override
    public void askFirstPlayer() {
        //print legenda
        for(PlayersInfoMessage message : playersInfo)
            showPlayersInfo(message.getPlayerName(), message.getPlayerColor(), message.getGodName());

        System.out.println("Challenger, choose who starts playing!");

        String starter = getInput();
        while (!players.contains(starter)) {
            System.out.println("Invalid nickname. Try again.");
            starter = getInput();
        }

        notify(starter);
    }

    /**
     * Ask the user his nickname and get the input
     */
    @Override
    public void askNickname() {
        System.out.print("Enter your nickname: ");
        String nickname = getInput();

        notify(nickname);
    }

    @Override
    public void displayTakenNickname() {
        System.out.println("This nickname is already taken. Please try again.");
        askNickname();
    }

    /**
     * Ask the user where his workers want to be placed and get the input
     */
    @Override
    public void askInitPosition() {
        System.out.println("Choose the initial position for your worker.");
        int row = askRow();
        int column = askColumn();

        String pos = Integer.toString(row)+Integer.toString(column);

        notify(pos);
    }

    @Override
    public void displayTakenPosition() {
        System.out.println("The chosen cell is already occupied!");
        askInitPosition();
    }

    /**
     * Ask row to the user and get the input
     * @return chosen row
     */
    private int askRow() {
        System.out.print("Row: ");
        int row;

        while (true) {
            try {
                row = Integer.parseInt(getInput());
                if(row < 0 || row > 4)
                    System.out.println("Invalid row. Choose a number between 0 and 4: ");
                else break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Try again. ");
                System.out.print("Row: ");
            }
        }

        return row;
    }

    /**
     * Ask column to the user and get the input
     * @return chosen column
     */
    private int askColumn() {
        System.out.print("Column: ");
        int column;

        while (true) {
            try {
                column = Integer.parseInt(getInput());
                if(column < 0 || column > 4)
                    System.out.println("Invalid column. Choose a number between 0 and 4: ");
                else break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Try again. ");
                System.out.print("Column: ");
            }
        }

        return column;
    }

    //TURN methods

    /**
     * Display available cells to move or build
     * @param positions where player can play its action
     */
    @Override
    public void askPosition(List<Position> positions) {
        //TODO client side check
        System.out.println("Valid positions:");
        for (Position position : positions) {
            System.out.print("R" + position.getPosRow() + ", C" + position.getPosColumn() + "      ");
        }
        System.out.println("\n");
        int row = askRow();
        int column = askColumn();

        String pos = Integer.toString(row)+Integer.toString(column);

        notify(pos);
    }

    /**
     * Ask the user if he wants to use Worker 1 or 2 and get the input
     */
    @Override
    public void askWorker() {
        System.out.println("Which worker do you want to use? (1 or 2):");
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
    @Override
    public void askPowerActivation() {
        System.out.println("Do you want to activate your God Power? (yes or no)");
        String power = getInput();
        while (!power.equals("yes") && !power.equals("no")) {
            System.out.println("Invalid answer. (choose yes or no)");
            power = getInput();
        }

        notify(power);
    }

    @Override
    public void startMovePhase() {
        System.out.println(ColorCLI.ANSI_GREEN+"MOVE"+ColorCLI.RESET+" PHASE");
    }

    @Override
    public void startBuildPhase() {
        System.out.println(ColorCLI.ANSI_GREEN+"BUILD"+ColorCLI.RESET+" PHASE");
    }

    private void endGame() {
        System.out.println("Thanks for playing!");
        System.out.println("This videogame was made by Ginevra Iorio, Lorenzo Mainetti and Marco Lacava, " +
                "based on the official boardgame Santorini");
    }

    @Override
    public void endTurn() {
        System.out.println("Your turn is over.");
        System.out.println();
    }

    @Override
    public void displayChallenger(String name) {
        System.out.println(ColorCLI.ANSI_GREEN + name.toUpperCase() + ColorCLI.RESET  + " is the most godlike! " + name + " is the challenger!");
    }

    @Override
    public void displayCurrentPlayer(String name) {
        System.out.println("It's " + name.toUpperCase()  + "'s turn!");
    }

    @Override
    public void displayLoser(String name) {
        System.out.println(ColorCLI.ANSI_GREEN+name.toUpperCase()+ColorCLI.RESET +"'s workers are both stuck. He/She has lost.\n");
        playersInfo.removeIf(message -> message.getPlayerName().equals(name));
    }

    @Override
    public void displayWinner(String name) {
        System.out.println("Game over! The winner is "+ColorCLI.ANSI_GREEN+ name.toUpperCase() +ColorCLI.RESET+ "!!!");
        endGame();
    }

    /**
     * Prints player and his/her respective god
     * @param nickname nickname of the player
     * @param color color of the player
     * @param godPower god chosen by the player
     */
    private void showPlayersInfo(String nickname, Color color, String godPower) {
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

    @Override
    public void addPlayersInfo(PlayersInfoMessage message) {
        playersInfo.add(message);
        players.add(message.getPlayerName());
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
    @Override
     public void displayBoard(Board board) {
         //print legenda
         System.out.println();
         for(PlayersInfoMessage message : playersInfo)
             showPlayersInfo(message.getPlayerName(), message.getPlayerColor(), message.getGodName());

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
    public void displayNetworkError() {
        System.out.println("Connection closed from server side");
    }

    @Override
    public void displayFullLobby() {
        System.out.println("The lobby is already full!");
    }

    @Override
    public void waiting() {
        System.out.println("Wait...");
    }

    @Override
    public void run() {
        startGame();
        readInput();
    }

}

package it.polimi.ingsw.PSP41.UI;



import java.util.Scanner;

public class CLI {
    private Scanner in;


    public CLI(){
        this.in = new Scanner(System.in);


    }

    //metodi per la gestione degli input

    private String askNickname() {
        String nickname = null;

        System.out.println("Enter your nickname:\n");
        //gestisci eventuali errori di formattazione
        System.out.println(">>> ");
        nickname = in.nextLine();
        return nickname;
    }

    public void askInitialPositions() {
        System.out.println("Where do you want to place your workers?\n");
    }

    public void askMove() {
        System.out.println("Where do you want to move your worker?\n");
    }

    public void askBuild() {
        System.out.println("Where do you want to build with your worker?\n");
    }

    public int askRow() {
        int row = -1;
        System.out.println("Enter position:\n");
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
        System.out.println("Which worker do you want to use? Choose Number 1 or Number 2\n");

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
        System.out.println("Do you want to activate your God Power? Type yes or no\n");

        do {
            if(in.hasNextLine()) {
                final String currentAnswer = in.nextLine();
                if (!currentAnswer.equals("yes") && !currentAnswer.equals("no"))
                    System.out.println("Invalid answer. Please try again\n");
                else
                    answer = currentAnswer;
            }
            //else invalid String
        } while(answer == null);

        if(answer.equals("yes")) activate = true;

        return activate;
    }

    //metodi per la rappresentazione del gioco

    public void setUpGame() {
        System.out.println("Welcome to Santorini Board Game!\n");
    }

    private void printBoard() {

    }

    public void gameStateUpdate() {
        printBoard();
        System.out.println();
    }

    //non so
    public void showPlayersInfo() {

    }

    public void displayMoves() {

    }

    public void displayBuilds() {

    }

    public void endGame(String winner) {
        System.out.println("Game over! The winner is "+winner+".\n");
    }

    private void checkInput() {

    }
}

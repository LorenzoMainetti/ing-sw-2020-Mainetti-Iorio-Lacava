package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.ViewObserver;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.view.CLI;

import java.util.List;

/**
 * Controller class that sets the user inputs to manage a player's turn
 */
public class UserInputManager implements ViewObserver {

    private final CLI theView;
    private boolean chosenWorker;
    private boolean power;
    private int chosenRow;
    private int chosenColumn;
    private String direction;
    private String nickname;
    private int playersNumber;

    public UserInputManager(CLI theView) {
        this.theView = theView;
        theView.addObserver(this);
        chosenWorker = false;
        power = false;
        chosenRow = -1;
        chosenColumn = -1;
        direction = null;
        nickname = null;
        playersNumber = 0;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPlayersNumber() { return playersNumber; }

    @Override
    public void updatePlayersNumber(int number) {
        playersNumber = number;
    }


    public boolean isChosenWorker() { return chosenWorker; }

    public void readChosenWorker() { theView.askWorker(); }

    @Override
    public void updateWorker(boolean chosenWorker) {
        this.chosenWorker = chosenWorker;
    }


    public boolean isPower() { return power; }

    public void readPower() { theView.askPowerActivation(); }

    @Override
    public void updatePower(boolean power) {
        this.power = power;
    }

    public int getChosenRow() {
        return chosenRow;
    }

    public int getChosenColumn() {
        return chosenColumn;
    }

    @Override
    public void updatePosition(Position position) {
        chosenRow = position.getX();
        chosenColumn = position.getY();
    }


    /**
     * Tells the View to display valid options (moves/builds) and, after being notified
     * with the user input direction, convert it back to row and column
     * @param positions list of valid Position for moving/building
     * @param row current Worker's row
     * @param column current Worker's column
     */
    public void readChosenDirection(List<Position> positions, int row, int column) {
        theView.displayOptions(positions, row, column);
        switch(direction) {
        case "N":
            chosenRow = row - 1;
            chosenColumn = column;
            break;
        case "NE":
            chosenRow = row - 1;
            chosenColumn = column + 1;
            break;
        case "E":
            chosenRow = row;
            chosenColumn = column + 1;
            break;
        case "SE":
            chosenRow = row + 1;
            chosenColumn = column + 1;
            break;
        case "S":
            chosenRow = row + 1;
            chosenColumn = column;
            break;
        case "SW":
            chosenRow = row + 1;
            chosenColumn = column - 1;
            break;
        case "W":
            chosenRow = row;
            chosenColumn = column - 1;
            break;
        case "NW":
            chosenRow = row - 1;
            chosenColumn = column - 1;
            break;
        }
     }

    @Override
    public void updateDirection(String direction) {
        this.direction = direction;
    }

}

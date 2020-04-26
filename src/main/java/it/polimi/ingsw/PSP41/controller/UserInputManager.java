package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.ViewObserver;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.server.VirtualView;

import java.util.List;

/**
 * Controller class that sets the user inputs to manage a player's turn
 */
public class UserInputManager implements ViewObserver {

    private final VirtualView virtualView;
    private boolean chosenWorker;
    private boolean power;
    private int chosenRow;
    private int chosenColumn;
    private String direction;

    public UserInputManager(VirtualView virtualView) {
        this.virtualView = virtualView;
        virtualView.addObserver(this);
        chosenWorker = false;
        power = false;
        chosenRow = -1;
        chosenColumn = -1;
        direction = null;
    }

    public boolean isChosenWorker() { return chosenWorker; }

    //TODO inserire nickname del player come input
    public void readChosenWorker() { virtualView.askWorker(); }

    @Override
    public void updateWorker(boolean chosenWorker) {
        this.chosenWorker = chosenWorker;
    }


    public boolean isPower() { return power; }

    public void readPower() { virtualView.askPowerActivation(); }

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

    /**
     * Tells the View to display valid options (moves/builds) and, after being notified
     * with the user input direction, convert it back to row and column
     * @param positions list of valid Position for moving/building
     * @param row current Worker's row
     * @param column current Worker's column
     */
    public void readChosenDirection(List<Position> positions, int row, int column) {
        virtualView.displayOptions(positions, row, column);
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

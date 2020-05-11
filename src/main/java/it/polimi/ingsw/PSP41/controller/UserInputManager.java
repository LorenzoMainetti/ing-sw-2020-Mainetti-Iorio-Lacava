package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.observer.ViewObserver;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.server.VirtualView;
import it.polimi.ingsw.PSP41.utils.PositionMessage;

import java.util.List;

/**
 * Controller class that sets the user inputs to manage a player's turn
 */
//TODO check server-side here
public class UserInputManager implements ViewObserver {

    private final VirtualView virtualView;
    private boolean chosenWorker;
    private boolean power;
    private int chosenRow;
    private int chosenColumn;
    private String nickname;   //-> setUp (Lobby)
    private int playersNumber; //-> setUp (Lobby)

    public UserInputManager(VirtualView virtualView) {
        this.virtualView = virtualView;
        virtualView.addObserver(this);
        chosenWorker = false;
        power = false;
        chosenRow = -1;
        chosenColumn = -1;
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


    @Override
    public void updateWorker(boolean chosenWorker) {
        this.chosenWorker = chosenWorker;
    }


    public boolean isPower() { return power; }


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
        chosenRow = position.getPosRow();
        chosenColumn = position.getPosColumn();
    }


    /**
     * Tells the View to display valid options (moves/builds)
     * @param positions list of valid Positions for moving/building
     * @param row current Worker's row
     * @param column current Worker's column
     */
    public void readChosenDirection(List<Position> positions, int row, int column) {
        PositionMessage positionMessage = new PositionMessage(positions, new Position(row, column));
        virtualView.requestPosition(positionMessage);
    }

}


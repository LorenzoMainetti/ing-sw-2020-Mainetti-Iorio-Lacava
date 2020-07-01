package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.observer.ViewObserver;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.server.VirtualView;
import it.polimi.ingsw.PSP41.utils.PositionMessage;

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

    /**
     * Sets nickname
     * @param nickname given nickname
     */
    @Override
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }


    public int getPlayersNumber() { return playersNumber; }

    /**
     * Sets playersNumber
     * @param number of players
     */
    @Override
    public void updatePlayersNumber(int number) {
        playersNumber = number;
    }


    public boolean isChosenWorker() { return chosenWorker; }

    /**
     * Sets chosen Worker
     * @param chosenWorker true is Worker1, false is Worker2
     */
    @Override
    public void updateWorker(boolean chosenWorker) {
        this.chosenWorker = chosenWorker;
    }


    public boolean isPower() { return power; }

    /**
     * Sets power activation
     * @param power is true if power is active
     */
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
     * Sets Position
     * @param position given Position
     */
    @Override
    public void updatePosition(Position position) {
        chosenRow = position.getPosRow();
        chosenColumn = position.getPosColumn();
    }

    /**
     * Tells the View to display the valid Positions (moves/builds)
     * @param positions list of valid Positions for moving/building
     */
    public void readChosenDirection(List<Position> positions) {
        PositionMessage positionMessage = new PositionMessage(positions);
        virtualView.requestPosition(positionMessage);
    }

}


package it.polimi.ingsw.PSP41.controller;

public interface ViewObserver {
    void updateNickname(String nickname);

    void updatePosition(int row, int column);

    void updateWorker(boolean chosenWorker);

    void updatePower(boolean power);

    void updateDirection(String direction);

}

package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.model.Position;

/**
 * Observer interface used by UserInputManager
 */
public interface ViewObserver {

    void updateNickname(String nickname);

    void updatePlayersNumber(int number);

    void updatePosition(Position position);

    void updateWorker(boolean chosenWorker);

    void updatePower(boolean power);

}



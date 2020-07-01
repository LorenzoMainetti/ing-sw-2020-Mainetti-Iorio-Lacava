package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.model.Board;

/**
 * Observer interface used by VirtualView
 */
public interface ModelObserver {

    void updateState(Board board);

    void updateLoser(String string);

    void updateWinner(String string);

}

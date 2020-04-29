package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.Board;

public interface ModelObserver {

    void updateState(Board board);

    void updateLoser(String string);

    void updateWinner(String string);

}

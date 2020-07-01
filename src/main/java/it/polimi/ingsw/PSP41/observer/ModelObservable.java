package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.model.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by Model classes
 */
public class ModelObservable {
    private final List<ModelObserver> observers = new ArrayList<>();

    public void addObserver(ModelObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(ModelObserver observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notifyBoard(Board board){
        synchronized (observers) {
            for(ModelObserver observer : observers){
                observer.updateState(board);
            }
        }
    }

    public void notifyWinner(String winner){
        synchronized (observers) {
            for(ModelObserver observer : observers){
                observer.updateWinner(winner);
            }
        }
    }

    public void notifyLoser(String loser){
        synchronized (observers) {
            for(ModelObserver observer : observers){
                observer.updateLoser(loser);
            }
        }
    }
}

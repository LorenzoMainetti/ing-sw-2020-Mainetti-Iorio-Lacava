package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.Board;

import java.util.ArrayList;
import java.util.List;

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

    public void notify(Board board){
        synchronized (observers) {
            for(ModelObserver observer : observers){
                observer.updateState(board);
            }
        }
    }
}

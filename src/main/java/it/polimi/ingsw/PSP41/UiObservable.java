package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.Board;

import java.util.ArrayList;
import java.util.List;

public class UiObservable {
    private final List<UiObserver> observers = new ArrayList<>();

    public void addObserver(UiObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(UiObserver observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notify(String string){
        synchronized (observers) {
            for(UiObserver observer : observers){
                observer.update(string);
            }
        }
    }
}

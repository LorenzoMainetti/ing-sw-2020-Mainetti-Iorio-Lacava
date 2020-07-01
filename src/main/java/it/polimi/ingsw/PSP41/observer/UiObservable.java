package it.polimi.ingsw.PSP41.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by CLI and GUI
 */
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

    public void notifyConnection(String ip, String port){
        synchronized (observers) {
            for(UiObserver observer : observers){
                observer.updateConnection(ip, port);
            }
        }
    }
}

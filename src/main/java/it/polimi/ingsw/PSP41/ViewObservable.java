package it.polimi.ingsw.PSP41;

import java.util.ArrayList;
import java.util.List;

public class ViewObservable {
    private final List<ViewObserver> observers = new ArrayList<>();

    public void addObserver(ViewObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(ViewObserver observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }
/*
    // non serve più (lo faccio in lobby)
    public void notifyNickname(String nickname){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updateNickname(nickname);
            }
        }
    }

    // non serve più (lo faccio in lobby)
    public void notifyPlayersNumber(int numbers){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updatePlayersNumber(numbers);
            }
        }
    }

    // non serve più (lo faccio in lobby)
    public void notifyPosition(Position position){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updatePosition(position);
            }
        }
    }
*/
    // serve per la scelta del worker
    public void notifyWorker(boolean chosenWorker){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updateWorker(chosenWorker);
            }
        }
    }

    // serve per la scelta del potere
    public void notifyPower(boolean power){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updatePower(power);
            }
        }
    }

    // serve per la scelta della cella in cui muoversi/costruire
    public void notifyDirection(String direction){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updateDirection(direction);
            }
        }
    }
}

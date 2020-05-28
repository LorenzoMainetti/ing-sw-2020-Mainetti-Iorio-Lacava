package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

public class ConnectionObservable {
    private final List<ConnectionObserver> observers = new ArrayList<>();

    public void addObserver(ConnectionObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notifyDisconnection(ClientHandler clientHandler){
        synchronized (observers) {
            for(ConnectionObserver observer : observers){
                observer.updateDisconnection(clientHandler);
            }
        }
    }
}

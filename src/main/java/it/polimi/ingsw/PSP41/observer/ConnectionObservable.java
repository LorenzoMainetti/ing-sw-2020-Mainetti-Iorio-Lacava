package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.server.ClientHandler;
import it.polimi.ingsw.PSP41.server.VirtualView;

import java.util.ArrayList;
import java.util.List;

public class ConnectionObservable {
    private final List<ConnectionObserver> observers = new ArrayList<>();

    public void addObserver(ConnectionObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notifyServer(UserInputManager userInputManager, VirtualView virtualView, List<Player> players){
        synchronized (observers) {
            for(ConnectionObserver observer : observers){
                observer.updateServer(userInputManager, virtualView, players);
            }
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

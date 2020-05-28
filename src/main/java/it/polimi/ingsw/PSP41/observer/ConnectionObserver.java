package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.server.ClientHandler;

public interface ConnectionObserver {

    void updateDisconnection(ClientHandler clientHandler);

}

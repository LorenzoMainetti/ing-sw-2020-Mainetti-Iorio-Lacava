package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.server.ClientHandler;

/**
 * Observer interface used by Lobby
 */
public interface ConnectionObserver {

    void updateDisconnection(ClientHandler clientHandler);

}

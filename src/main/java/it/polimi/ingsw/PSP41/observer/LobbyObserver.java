package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.server.Lobby;

public interface LobbyObserver {

    void updatePlayersNumber(Lobby lobby);

    void createNewLobby();

}
